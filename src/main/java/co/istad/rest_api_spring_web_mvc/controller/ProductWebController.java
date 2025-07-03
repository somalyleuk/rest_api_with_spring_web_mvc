package co.istad.rest_api_spring_web_mvc.controller;

import co.istad.rest_api_spring_web_mvc.service.ProductService;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductWebController {
    private final ProductService productService;

    @GetMapping("/")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "index";
    }
}

