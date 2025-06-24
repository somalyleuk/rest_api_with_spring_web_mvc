package co.istad.rest_api_spring_web_mvc.controller;

import org.springframework.ui.Model;
import co.istad.rest_api_spring_web_mvc.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BookWebController {
    private final BookService bookService;

    @GetMapping("/")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }
}
