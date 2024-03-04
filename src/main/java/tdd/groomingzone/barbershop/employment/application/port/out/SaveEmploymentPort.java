package tdd.groomingzone.barbershop.employment.application.port.out;

import tdd.groomingzone.barbershop.employment.domain.Employment;

public interface SaveEmploymentPort {
    Employment save(Employment employment);
}
