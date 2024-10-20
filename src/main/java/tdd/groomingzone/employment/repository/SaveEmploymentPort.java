package tdd.groomingzone.employment.repository;

import tdd.groomingzone.employment.domain.Employment;

public interface SaveEmploymentPort {
    Employment save(Employment employment);
}
