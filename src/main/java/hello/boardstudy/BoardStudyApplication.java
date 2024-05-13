package hello.boardstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoardStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardStudyApplication.class, args);
    }

}
