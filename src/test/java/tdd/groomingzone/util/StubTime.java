package tdd.groomingzone.util;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.TestComponent;
import tdd.groomingzone.global.time.Time;

import java.time.LocalDateTime;

@TestComponent
public class StubTime implements Time {

    private final LocalDateTime currentTime;

    public StubTime(LocalDateTime currentTime){
        this.currentTime = currentTime;
    }

    public static LocalDateTime of(int year, int month, int day, int hour, int minute, int second){
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    @Override
    public LocalDateTime now() {
        return this.currentTime;
    }
}
