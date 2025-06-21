package com.bookshop.controller;

import com.bookshop.model.Book;
import com.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BookService bookService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        model.addAttribute("books", bookService.getAllBooks());
        return "admin/dashboard";
    }

    @GetMapping("/book/add")
    public String showAddBookForm(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        model.addAttribute("book", new Book());
        return "admin/book-form";
    }

    @GetMapping("/book/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "admin/book-form";
    }

    @PostMapping("/book/save")
    public String saveBook(@ModelAttribute Book book,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        boolean isNew = book.getId() == null;
        Book savedBook = bookService.saveBook(book);

        if (isNew) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Book '" + savedBook.getTitle() + "' was successfully added!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Book '" + savedBook.getTitle() + "' was successfully updated!");
        }

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable Long id,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        Book book = bookService.getBookById(id);
        String bookTitle = book != null ? book.getTitle() : "Book";

        bookService.deleteBook(id);
        redirectAttributes.addFlashAttribute("successMessage",
                "Book '" + bookTitle + "' was successfully deleted!");

        return "redirect:/admin/dashboard";
    }
}