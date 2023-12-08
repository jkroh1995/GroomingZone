package tdd.groomingzone.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import tdd.groomingzone.domain.board.freeboard.repository.FreeBoardCustomRepositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class TestConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public FreeBoardCustomRepositoryImpl adminRepository() {
        return new FreeBoardCustomRepositoryImpl(jpaQueryFactory());
    }
}
