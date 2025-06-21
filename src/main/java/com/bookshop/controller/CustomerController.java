package com.bookshop.controller;

import com.bookshop.model.*;
import com.bookshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("/books")
    public String viewBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "customer/books";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long bookId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        User user = userService.getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        Book book = bookService.getBookById(bookId);
        cartService.addToCart(user, bookId, quantity);

        
        redirectAttributes.addFlashAttribute("message",
                "'" + book.getTitle() + "' added to cart successfully!");

        
        return "redirect:/customer/books";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        User user = userService.getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        Cart cart = cartService.getCartByUser(user);
        model.addAttribute("cart", cart);
        return "customer/cart";
    }

    @GetMapping("/cart/remove/{itemId}")
    public String removeFromCart(@PathVariable Long itemId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        User user = userService.getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        
        Cart cart = cartService.getCartByUser(user);
        String bookTitle = "";
        if (cart != null && cart.getItems() != null) {
            CartItem item = cart.getItems().stream()
                    .filter(i -> i.getId().equals(itemId))
                    .findFirst()
                    .orElse(null);
            if (item != null && item.getBook() != null) {
                bookTitle = item.getBook().getTitle();
            }
        }

        cartService.removeFromCart(user, itemId);

       
        redirectAttributes.addFlashAttribute("message",
                "'" + bookTitle + "' was removed from your cart.");

        return "redirect:/customer/cart";
    }

    @GetMapping("/checkout")
    public String showCheckout(HttpSession session, Model model) {
        User user = userService.getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        Cart cart = cartService.getCartByUser(user);
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            return "redirect:/customer/cart";
        }

        model.addAttribute("cart", cart);
        return "customer/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String creditCard,
                                  HttpSession session,
                                  Model model) {
        User user = userService.getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        
        boolean success = cartService.processCheckout(user);

        if (success) {
            model.addAttribute("success", "Order placed successfully!");
            return "customer/checkout";
        } else {
            model.addAttribute("error", "Some items are no longer available in the requested quantity.");
            Cart cart = cartService.getCartByUser(user);
            model.addAttribute("cart", cart);
            return "customer/checkout";
        }
    }
}
