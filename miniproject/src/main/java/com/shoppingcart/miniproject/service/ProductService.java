package com.shoppingcart.miniproject.service;

import com.shoppingcart.miniproject.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product saveProduct(Product product);

    Product getProductById(Long id);

    Product updateProduct(Product product);

    void deleteProductById(Long id);
}
