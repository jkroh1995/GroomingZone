package tdd.groomingzone.util;

import org.springframework.boot.test.context.TestComponent;
import tdd.groomingzone.global.time.Time;

import java.time.LocalDateTime;

@TestComponent
public class StubTime implements Time {

    private final LocalDateTime currentTime;

    public StubTime(LocalDateTime currentTime){
        this.currentTime = currentTime;
    }

    @Override
    public LocalDateTime now() {
        return this.currentTime;
    }
}
