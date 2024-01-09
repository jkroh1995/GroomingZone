package tdd.groomingzone.global.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tdd.groomingzone.auth.addapter.in.springsecurity.JwtAuthenticationFilter;
import tdd.groomingzone.auth.addapter.in.springsecurity.JwtVerificationFilter;
import tdd.groomingzone.auth.application.port.out.RedisSignInPort;
import tdd.groomingzone.auth.utils.CookieManager;
import tdd.groomingzone.auth.utils.handler.MemberAccessDeniedHandler;
import tdd.groomingzone.auth.utils.handler.MemberAuthenticationEntryPoint;
import tdd.groomingzone.auth.utils.handler.MemberAuthenticationFailureHandler;
import tdd.groomingzone.auth.utils.handler.MemberAuthenticationSuccessHandler;
import tdd.groomingzone.auth.application.service.MemberDetailsService;
import tdd.groomingzone.auth.utils.CustomAuthorityUtils;
import tdd.groomingzone.auth.utils.JwtManager;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtManager jwtManager;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberDetailsService memberDetailsService;
    private final RedisSignInPort redisSignInPort;
    private final CookieManager cookieManager;

    public SecurityConfig(JwtManager jwtManager, CustomAuthorityUtils authorityUtils, MemberDetailsService memberDetailsService, RedisSignInPort redisSignInPort, CookieManager cookieManager) {
        this.jwtManager = jwtManager;
        this.authorityUtils = authorityUtils;
        this.memberDetailsService = memberDetailsService;
        this.redisSignInPort = redisSignInPort;
        this.cookieManager = cookieManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/free-board/**", "/review/**", "/recruitment/**", "/comment/**").hasRole("CUSTOMER")
                        .antMatchers(HttpMethod.PUT, "/free-board/**", "/review/**", "/recruitment/**", "/comment/**", "/member/**").hasRole("CUSTOMER")
                        .antMatchers(HttpMethod.DELETE, "/free-board/**", "/review/**", "/recruitment/**", "/comment/**", "/member/**").hasRole("CUSTOMER")

                        .antMatchers(HttpMethod.POST, "/barber-shop/**").hasRole("BARBER")
                        .antMatchers(HttpMethod.PUT, "/barber-shop/**").hasRole("BARBER")
                        .antMatchers(HttpMethod.DELETE, "/barber-shop/**").hasRole("BARBER")

                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://grooming-zone.com", "http://localhost:3000", "http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh", "MemberId"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtManager, redisSignInPort, cookieManager);

            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/sign-in");

            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());


            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtManager, authorityUtils, memberDetailsService, cookieManager);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}
