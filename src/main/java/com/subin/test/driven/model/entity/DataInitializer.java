package com.subin.test.driven.model.entity;

import com.subin.test.driven.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository){
        return args -> {
            employeeRepository.save(new EmployeeEntity(1L, "Alice", "HR", 50000, "Permanent"));
            employeeRepository.save(new EmployeeEntity(2L, "Bob", "IT", 60000, "Contract"));
            employeeRepository.save(new EmployeeEntity(3L, "Charlie", "Finance", 55000, "Intern"));
        };
    }
}
