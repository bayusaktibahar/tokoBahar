package com.sakti.toko.service.details;

import com.sakti.toko.data.entity.Reviews;
import com.sakti.toko.model.dto.ReviewsDTO;
import com.sakti.toko.model.dto.StoreDTO;
import com.sakti.toko.model.dto.ProductDTO;
import com.sakti.toko.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewDetailService {
    private final UserDetailService userDetailService;
    private final StoreDetailService storeDetailService;

    public ReviewsDTO getReviewDetail(Reviews review) {

        UserDTO userDTO = userDetailService.getUserDetailWithoutSession(review.getUser());

        StoreDTO storeDTO = storeDetailService.getStoreDetails(review.getStore());

        ProductDTO productDTO = ProductDTO.builder()
                .id(review.getProduct().getId())
                .sku(review.getProduct().getSku())
                .name(review.getProduct().getName())
                .description(review.getProduct().getDescription())
                .price(review.getProduct().getPrice())
                .stock(review.getProduct().getStock())
                .storeDTO(storeDetailService.getStoreDetails(review.getProduct().getStore()))
                .reviews(null)
                .build();

        return ReviewsDTO.builder()
                .id(review.getId())
                .userDTO(userDTO)
                .storeDTO(storeDTO)
                .productDTO(productDTO)
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public List<ReviewsDTO> getListReviewDetails(List<Reviews> reviews) {
        return reviews.stream()
                .map(this::getReviewDetail)
                .collect(Collectors.toList());
    }
}
