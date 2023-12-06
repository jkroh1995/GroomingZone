package tdd.groomingzone.global.time;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Primary
public class JodaTime implements Time{

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
