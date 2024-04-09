package tdd.groomingzone.barbershop.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberShopEntityRepository extends JpaRepository<BarberShopEntity, Long> {
}
