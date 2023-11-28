package tdd.groomingzone.global.time;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JodaTime implements Time{

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
