package com.sakti.toko.controller;


import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.model.dto.ReviewsDTO;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.AddReviewRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdateReviewRequest;
import com.sakti.toko.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ReviewsDTO>> getReviewByProduct(@PathVariable Long productId) {
        return reviewService.getReviewsByProduct(productId);
    }

    @GetMapping("/store/{storeId}")
    public ApiResponse<List<ReviewsDTO>> getReviewByStore(@PathVariable Long storeId) {
        return reviewService.getReviewsByStore(storeId);
    }

    @PostMapping("/add")
    @AuthCheck
    public ApiResponse<ReviewsDTO> createReview(@RequestBody AddReviewRequest addReviewRequest, @CurrentUser UserDTO currentUser) {
        return reviewService.addReview(addReviewRequest, currentUser);
    }

    @PutMapping("/update/{id}")
    @AuthCheck
    public ApiResponse<ReviewsDTO> updateReview(@RequestBody UpdateReviewRequest updateReviewRequest, @PathVariable long id, @CurrentUser UserDTO currentUser) {
        return reviewService.updateReview(id, updateReviewRequest, currentUser);
    }

    @DeleteMapping("/delete/{id}")
    @AuthCheck
    public ApiResponse<ReviewsDTO> deleteReview(@PathVariable long id, @CurrentUser UserDTO currentUser ) {
        return reviewService.deleteReview(id, currentUser);
    }
}
