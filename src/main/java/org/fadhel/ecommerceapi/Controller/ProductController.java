package org.fadhel.ecommerceapi.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fadhel.ecommerceapi.Api.ApiResponse;
import org.fadhel.ecommerceapi.Model.Product;
import org.fadhel.ecommerceapi.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;

    // to get all products
    @GetMapping("/get")
    public ResponseEntity<?> getProducts() {

        ArrayList<Product> products = productService.getProducts();

        return ResponseEntity.status(200).body(products);
    }

    // to add a product
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isAdded = productService.addProduct(product);

        if(isAdded == false) {
            return ResponseEntity.status(400).body(new ApiResponse("Product ID is taken or no category found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
    }

    // to update a product
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean isUpdated = productService.updateProduct(id, product);

        if (isUpdated == false) {
            return ResponseEntity.status(400).body(new ApiResponse("No product or category found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));
    }

    // to delete a product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {

        boolean isDeleted = productService.deleteProduct(id);

        if (isDeleted == false) {
            return ResponseEntity.status(400).body(new ApiResponse("No product found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));
    }

    // to get a product by id
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id){

        Product product = productService.getProductById(id);

        if(product == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }

        return ResponseEntity.status(200).body(product);
    }

    // 5 outOf 5 mandatory extra: to get all products by the price range
    @GetMapping("/get-products-by-price-range/{minPrice}/{maxPrice}")
    public ResponseEntity<?> getProductsByPriceRange(@PathVariable Double minPrice,@PathVariable Double maxPrice){

        if(minPrice == null || maxPrice == null) {
            return ResponseEntity.status(400).body(new ApiResponse("No min and max price found"));
        }

        if(minPrice > maxPrice) {
            return ResponseEntity.status(400).body(new ApiResponse("min price can not be greater than max price"));
        }

        ArrayList<Product> products = productService.getProductsByPriceRange(minPrice,maxPrice);

        if(products == null) {
            return ResponseEntity.status(400).body(new ApiResponse("No products found"));
        }
        return ResponseEntity.status(200).body(products);
    }
    
    
    
}
