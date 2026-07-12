package com.sakti.toko.service;

import com.sakti.toko.data.entity.Role;
import com.sakti.toko.data.entity.Reviews;
import com.sakti.toko.data.repository.*;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.dto.ReviewsDTO;
import com.sakti.toko.model.request.AddReviewRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdateReviewRequest;
import com.sakti.toko.service.details.ReviewDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewsRepository reviewsRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ReviewDetailService reviewDetailService;

    public ApiResponse<List<ReviewsDTO>> getReviewsByProduct(Long productId) {

        var targetProduct = productRepository.findById(productId);

        if (targetProduct.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "Product Not Found",
                    null
            );
        }

        var product = targetProduct.get();

        List<Reviews> reviews = reviewsRepository.findByProduct(product);
        if (reviews.isEmpty()) {
            return new ApiResponse<>(
                    true,
                    200,
                    " Review About Product is Not Found",
                    null
            );
        }

        var result = reviewDetailService.getListReviewDetails(reviews);

        return new ApiResponse<>(
                true,
                200,
                "Review Details Product is Found",
                result
        );


    }

    public ApiResponse<List<ReviewsDTO>> getReviewsByStore(Long storeId) {
        var targetStore = storeRepository.findById(storeId);

        if (targetStore.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "Store Not Found",
                    null
            );
        }

        var store = targetStore.get();

        List<Reviews> reviews = reviewsRepository.findByStore(store);
        if (reviews.isEmpty()) {
            return new ApiResponse<>(
                    true,
                    200,
                    " Review About Store is Not Found",
                    null
            );
        }

        var result = reviewDetailService.getListReviewDetails(reviews);

        return new ApiResponse<>(
                true,
                200,
                "Review Details Store is Found",
                result
        );

    }

    @Transactional
    public ApiResponse<ReviewsDTO> addReview(AddReviewRequest addReviewRequest, UserDTO currentUser) {
        var targetUser = userRepository.findById(currentUser.getId());
        if (targetUser.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "User Not Found",
                    null
            );
        }

        var user  = targetUser.get();

        if (user.getIsSuspended()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Your Account is Suspended",
                    null
            );
        }

        var targetProduct = productRepository.findById(addReviewRequest.getProductId());
        if (targetProduct.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "Product Not Found",
                    null);
        }

        var product = targetProduct.get();

        boolean userIsBuying = orderItemsRepository.existsByOrderUserAndProduct(user, product);

        if (!userIsBuying) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Your you never buy this product, you must purchase this product first",
                    null
            );
        }

        var existingReview = reviewsRepository.findByUserAndProduct(user, product);

        if (existingReview.isPresent()) {
            return new ApiResponse<>(
                    false,
                    409,
                    "Your review already exists",
                    null
            );
        }

        var store = product.getStore();

        var review = Reviews.builder()
                .user(user)
                .product(product)
                .store(store)
                .rating(addReviewRequest.getRating())
                .comment(addReviewRequest.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        reviewsRepository.save(review);

        //Konversi Data
        var result = reviewDetailService.getReviewDetail(review);
        return new ApiResponse<>(true,
                201,
                "Review Added Successfully",
                result);
    }

    @Transactional
    public ApiResponse<ReviewsDTO> updateReview(Long reviewId, UpdateReviewRequest updateReviewRequest, UserDTO currentUser) {

        // 1. Cari review
        var targetReview = reviewsRepository.findById(reviewId);
        if (targetReview.isEmpty()) {
            return new ApiResponse<>(false,
                    404,
                    "Review Not Found",
                    null);
        }

        var review = targetReview.get();

        // 2. Cek apakah review milik currentUser
        if (!review.getUser().getId().equals(currentUser.getId())) {
            return new ApiResponse<>(false,
                    403,
                    "You don't have permission to update this review",
                    null);
        }

        // 3. Partial update
        if (updateReviewRequest.getRating() != null) {
            review.setRating(updateReviewRequest.getRating());
        }
        if (updateReviewRequest.getComment() != null) {
            review.setComment(updateReviewRequest.getComment());
        }

        reviewsRepository.save(review);

        var result = reviewDetailService.getReviewDetail(review);
        return new ApiResponse<>(true,
                200,
                "Review Updated Successfully",
                result);
    }

    @Transactional
    public ApiResponse<ReviewsDTO> deleteReview(Long reviewId, UserDTO currentUser) {

        // 1. Cari review
        var targetReview = reviewsRepository.findById(reviewId);
        if (targetReview.isEmpty()) {
            return new ApiResponse<>(false,
                    404,
                    "Review Not Found",
                    null);
        }

        var review = targetReview.get();

        // 2. Cek apakah review milik currentUser ATAU currentUser adalah ADMIN
        boolean isOwner = review.getUser().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole().equals(Role.ADMIN);

        if (!isOwner && !isAdmin) {
            return new ApiResponse<>(
                    false,
                    403,
                    "You don't have permission to delete this review",
                    null);
        }

        reviewsRepository.delete(review);

        return new ApiResponse<>(
                true,
                200,
                "Review Deleted Successfully",
                null);
    }
}
