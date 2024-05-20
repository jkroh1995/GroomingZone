package tdd.groomingzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class GroomingzoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroomingzoneApplication.class, args);
	}

}
