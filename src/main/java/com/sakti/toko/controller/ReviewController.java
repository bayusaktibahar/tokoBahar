package com.sakti.toko.controller;


import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.model.dto.ReviewsDTO;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.AddReviewRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdateReviewRequest;
import com.sakti.toko.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
@Tag(name = "9. Review", description = "Ulasan produk dan toko dari pembeli terverifikasi")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "Get reviews by product", description = "Menampilkan semua review untuk sebuah produk.")
    @GetMapping("/product/{productId}")
    public ApiResponse<List<ReviewsDTO>> getReviewByProduct(@PathVariable Long productId) {
        return reviewService.getReviewsByProduct(productId);
    }

    @Operation(summary = "Get reviews by store", description = "Menampilkan semua review untuk sebuah toko.")
    @GetMapping("/store/{storeId}")
    public ApiResponse<List<ReviewsDTO>> getReviewByStore(@PathVariable Long storeId) {
        return reviewService.getReviewsByStore(storeId);
    }

    @Operation(summary = "Add review", description = "Membuat review baru. Hanya bisa dilakukan oleh pembeli yang sudah login.")
    @PostMapping("/add")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ReviewsDTO> createReview(@RequestBody AddReviewRequest addReviewRequest, @Parameter(hidden = true) @CurrentUser UserDTO currentUser) {
        return reviewService.addReview(addReviewRequest, currentUser);
    }

    @Operation(summary = "Update review", description = "Mengubah review milik sendiri.")
    @PutMapping("/update/{id}")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ReviewsDTO> updateReview(@RequestBody UpdateReviewRequest updateReviewRequest, @PathVariable long id, @Parameter(hidden = true) @CurrentUser UserDTO currentUser) {
        return reviewService.updateReview(id, updateReviewRequest, currentUser);
    }

    @Operation(summary = "Delete review", description = "Menghapus review milik sendiri.")
    @DeleteMapping("/delete/{id}")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ReviewsDTO> deleteReview(@PathVariable long id, @Parameter(hidden = true) @CurrentUser UserDTO currentUser) {
        return reviewService.deleteReview(id, currentUser);
    }
}