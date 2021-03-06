package com.matrixmm.chakrabackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ChakraBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChakraBackendApplication.class, args);
    }

}
