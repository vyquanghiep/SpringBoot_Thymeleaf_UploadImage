package com.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequestMapping("/products")
public class ProductController{

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }


    @PostMapping("/save")
    public String saveProduct(@RequestParam("imageFile") MultipartFile imageFile, Product product) {
        product.setImageFile(imageFile);
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-form";
    }



}
