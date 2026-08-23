package com.sakti.toko.controller;

import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.model.dto.ProductDTO;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.AddProductRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdateProductRequest;
import com.sakti.toko.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@Tag(name = "4. Product", description = "Manajemen produk")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get all products")
    @GetMapping("/all")
    public ApiResponse<List<ProductDTO>> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get product by ID")
    @GetMapping("/{id}")
    public ApiResponse<ProductDTO> getProduct(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Get product by name")
    @GetMapping("/name")
    public ApiResponse<ProductDTO> getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }

    @Operation(summary = "Add product", description = "Menambahkan produk baru. Hanya untuk pemilik toko yang sudah login.")
    @PostMapping("/add")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ProductDTO> addProduct(@RequestBody AddProductRequest addProductRequest, @Parameter(hidden = true) @CurrentUser UserDTO currentUser) {
        return productService.addProduct(addProductRequest, currentUser);
    }

    @Operation(summary = "Update product", description = "Mengubah data produk milik toko sendiri.")
    @PutMapping("/update/{id}")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ProductDTO> updateProduct(@PathVariable long id, @RequestBody UpdateProductRequest updateProductRequest, @Parameter(hidden = true) @CurrentUser UserDTO currentUser) {
        return productService.updateProduct(id, updateProductRequest, currentUser);
    }

    @Operation(summary = "Delete product", description = "Menghapus produk milik toko sendiri.")
    @DeleteMapping("delete/{id}")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ProductDTO> deleteProduct(@PathVariable long id, @Parameter(hidden = true) @CurrentUser UserDTO currentUser) {
        return productService.deleteProduct(id, currentUser);
    }
}