package com.example.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrainingApplication {

//    @Autowired
//    private DataSource dataSource;
//
//    @PostConstruct
//    public void init() {
//        System.out.printf("bla");
//    }
    public static void main(String[] args) {
        SpringApplication.run(TrainingApplication.class, args);
    }
}
