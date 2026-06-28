package com.sakti.toko.controller;

import com.sakti.toko.model.dto.ProductDTO;
import com.sakti.toko.model.request.AddProductRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdateProductRequest;
import com.sakti.toko.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ApiResponse<List<ProductDTO>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDTO> getProduct(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/name")
    public ApiResponse<ProductDTO> getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }

    @PostMapping("/Add")
    public ApiResponse<ProductDTO> addProduct(@RequestBody AddProductRequest addProductRequest) {
        return productService.addProduct(addProductRequest);
    }

    @PutMapping("/Update/{id}")
    public ApiResponse<ProductDTO> updateProduct(@PathVariable long id,  @RequestBody UpdateProductRequest updateProductRequest) {
        return productService.updateProduct(id, updateProductRequest);
    }

    @DeleteMapping("Delete/{id}")
    public ApiResponse<ProductDTO> deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }

}
