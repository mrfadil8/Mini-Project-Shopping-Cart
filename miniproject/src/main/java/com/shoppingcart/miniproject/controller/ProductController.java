package com.shoppingcart.miniproject.controller;

import com.shoppingcart.miniproject.model.Product;
import com.shoppingcart.miniproject.repo.ProductRepository;
import com.shoppingcart.miniproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    private ProductService productService;

    public ProductController(ProductService productService) {
        super();
        this.productService = productService;
    }

    @GetMapping("/")
    public String showProductList(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "product-list";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        productRepository.save(product);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "edit_product";
    }

    @PostMapping ("/edit/{id}")
        public String updateStudent(@PathVariable Long id,
                @ModelAttribute("product") Product product, Model model) {

            // get student from database by id
            Product existingProduct = productService.getProductById(id);
            existingProduct.setId(id);
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());

            // save updated student object
            productService.updateProduct(existingProduct);
            return "redirect:/";
        }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        productRepository.delete(product);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = productRepository.searchByKeyword(keyword);
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/search/form")
    public String showSearchForm(Model model) {
        model.addAttribute("keyword", "");
        return "product-list";
    }
}
