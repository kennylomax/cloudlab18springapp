package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

// mvn spring-boot:run  -Dspring-boot.run.arguments=--server.port=8085
@RestController
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping("/hithere")
    public String visitHome() {
        int a = 2;
        int b = a;
        return "42";
    }
    
  
    public String somethingElse() {
        int a = 2;
        int b = a;
        return "EverGreen";
    }
    
      public String something() {
        int a = 2;
        int b = a;
        return "EverGreen";
    }
    
}

