package com.bookshop.config;

import com.bookshop.model.*;
import com.bookshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User("admin", "admin123", UserRole.ADMIN);
            admin.setName("Admin");
            admin.setSurname("User");
            admin.setEmail("admin@bookshop.com");
            userRepository.save(admin);
        }

        // Create sample books if none exist
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book("Java Programming", "John Doe", 2023,
                    new BigDecimal("49.99"), 10));
            bookRepository.save(new Book("Spring Boot in Action", "Jane Smith", 2022,
                    new BigDecimal("59.99"), 15));
            bookRepository.save(new Book("Database Design", "Bob Johnson", 2021,
                    new BigDecimal("39.99"), 20));
        }
    }
}