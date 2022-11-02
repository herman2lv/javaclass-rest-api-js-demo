package com.hrm.demo.web.view;

import com.hrm.demo.service.BookService;
import com.hrm.demo.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookViewController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        BookDto book = bookService.get(id);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping
    public String getAll() {
        return "books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        BookDto book = bookService.get(id);
        model.addAttribute("book", book);
        return "edit-book";
    }

    @GetMapping("/create")
    public String create() {
        return "create-book";
    }

}
