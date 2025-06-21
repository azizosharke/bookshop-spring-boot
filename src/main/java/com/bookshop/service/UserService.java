package com.bookshop.service;

import com.bookshop.model.User;
import com.bookshop.model.UserRole;
import com.bookshop.model.Cart;
import com.bookshop.repository.UserRepository;
import com.bookshop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public User register(User user) {
        user.setRole(UserRole.CUSTOMER);
        User savedUser = userRepository.save(user);

        // Create cart for new customer
        Cart cart = new Cart(savedUser);
        cartRepository.save(cart);

        return savedUser;
    }

    public User login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            return userRepository.findById(userId).orElse(null);
        }
        return null;
    }
}