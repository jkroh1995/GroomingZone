package tdd.groomingzone.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import tdd.groomingzone.post.freeboard.adapter.out.persistence.repository.FreeBoardEntityCustomRepositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@TestConfiguration
public class TestConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public FreeBoardEntityCustomRepositoryImpl adminRepository() {
        return new FreeBoardEntityCustomRepositoryImpl(jpaQueryFactory());
    }
}
