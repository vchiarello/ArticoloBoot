package it.vito.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
//@Configuration
//@EnableAspectJAutoProxy
public class ArticoloBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticoloBootApplication.class, args);
    }
}
