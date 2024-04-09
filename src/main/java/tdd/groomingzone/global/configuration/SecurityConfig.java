package tdd.groomingzone.global.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tdd.groomingzone.auth.adapter.in.springsecurity.JwtAuthenticationFilter;
import tdd.groomingzone.auth.adapter.in.springsecurity.JwtVerificationFilter;
import tdd.groomingzone.auth.application.port.out.RedisSignInPort;
import tdd.groomingzone.auth.oauth2.OAuth2MemberService;
import tdd.groomingzone.auth.utils.CookieManager;
import tdd.groomingzone.auth.application.service.SuccessfulAuthenticationProcessor;
import tdd.groomingzone.auth.utils.handler.*;
import tdd.groomingzone.auth.application.service.MemberDetailsService;
import tdd.groomingzone.auth.utils.CustomAuthorityUtils;
import tdd.groomingzone.auth.utils.JwtManager;
import tdd.groomingzone.member.adapter.out.persistence.repository.MemberEntitiyRepository;

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
    private final OAuth2MemberService OAuth2MemberService;
    private final MemberEntitiyRepository memberEntityRepository;
    private final SuccessfulAuthenticationProcessor successfulAuthenticationProcessor;

    public SecurityConfig(JwtManager jwtManager, CustomAuthorityUtils authorityUtils, MemberDetailsService memberDetailsService, RedisSignInPort redisSignInPort, CookieManager cookieManager, OAuth2MemberService OAuth2MemberService, MemberEntitiyRepository memberEntityRepository, SuccessfulAuthenticationProcessor successfulAuthenticationProcessor) {
        this.jwtManager = jwtManager;
        this.authorityUtils = authorityUtils;
        this.memberDetailsService = memberDetailsService;
        this.redisSignInPort = redisSignInPort;
        this.cookieManager = cookieManager;
        this.OAuth2MemberService = OAuth2MemberService;
        this.memberEntityRepository = memberEntityRepository;
        this.successfulAuthenticationProcessor = successfulAuthenticationProcessor;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers(configurer ->
                        configurer
                                .frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                )
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(source -> corsConfigurationSource())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(handler -> handler
                        .accessDeniedHandler(new MemberAccessDeniedHandler())
                        .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                )
                .with(new CustomFilterConfigurer(), CustomFilterConfigurer::build)
                .oauth2Login(oAuth2 -> oAuth2
                        .successHandler(new OAuth2MemberSuccessHandler(memberEntityRepository, successfulAuthenticationProcessor))
                        .userInfoEndpoint(userService -> userService
                                .userService(OAuth2MemberService)))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/free-board/**", "/review/**", "/recruitment/**", "/comment/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/free-board/**", "/review/**", "/recruitment/**", "/comment/**", "/member/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE, "/free-board/**", "/review/**", "/recruitment/**", "/comment/**", "/member/**").hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.POST, "/barber-shop/**").hasRole("BARBER")
                        .requestMatchers(HttpMethod.PUT, "/barber-shop/**").hasRole("BARBER")
                        .requestMatchers(HttpMethod.DELETE, "/barber-shop/**").hasRole("BARBER")

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
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, redisSignInPort, successfulAuthenticationProcessor);

            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/sign-in");

            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());


            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtManager, authorityUtils, memberDetailsService, cookieManager);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }

        public HttpSecurity build() {
            return getBuilder();
        }
    }
}
