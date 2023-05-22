package com.shoppingcart.miniproject.controller;

import com.shoppingcart.miniproject.model.Cart;
import com.shoppingcart.miniproject.model.Product;
import com.shoppingcart.miniproject.repo.CartRepository;
import com.shoppingcart.miniproject.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartController(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public String getCart(Model model) {
        Cart cart = cartRepository.findById(1L).orElse(new Cart()); // Assuming cart ID 1 exists
        model.addAttribute("cart", cart);
        model.addAttribute("products", productRepository.findAll());
        return "cart";
    }

    @PostMapping("/add/{productId}")
    public String addProductToCart(@PathVariable("productId") Long productId) {
        Cart cart = cartRepository.findById(1L).orElse(new Cart());
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            cart.addProduct(product);
            cartRepository.save(cart);
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove/{productId}")
    public String removeProductFromCart(@PathVariable("productId") Long productId) {
        Cart cart = cartRepository.findById(1L).orElse(new Cart());
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            cart.removeProduct(product);
            cartRepository.save(cart);
        }
        return "redirect:/cart";
    }
}
