package com.yx.swaggerv3_enum;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void check() {
        System.out.println(
                "WRITE_ENUMS_USING_TO_STRING = " +
                        objectMapper.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
        );
    }
}
