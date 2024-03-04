package tdd.groomingzone.barbershop.employment.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmploymentEntityRepository extends JpaRepository<EmploymentEntity, Long> {
}
