package com.linwu.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LinwuNacosAppliction {
  public static void main(String[] args) {
    SpringApplication.run(LinwuNacosAppliction.class, args);
    System.err.println("http://localhost:8080/swagger-ui/index.html");
  }
}
