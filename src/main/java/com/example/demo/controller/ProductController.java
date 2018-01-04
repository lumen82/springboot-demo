package com.example.demo.controller;

import com.example.demo.dao.domain.Product;
import com.example.demo.dao.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lumen on 18-1-4.
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductMapper productMapper;
    @GetMapping("/{id}")
    public Product getProductInfo(@PathVariable("id") long id){

        return productMapper.select(id);
    }

    @PutMapping("/{id}")
    public Product updateProductInfo(@PathVariable("id") long id, @RequestBody Product newProduct){
        Product product = productMapper.select(id);
        if (product != null){
            product.setName(newProduct.getName());
            product.setPrice(newProduct.getPrice());
        }
        productMapper.update(product);
        return product;
    }
}
