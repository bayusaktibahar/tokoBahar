package com.sakti.toko.service.details;

import com.sakti.toko.data.entity.Product;
import com.sakti.toko.data.entity.Reviews;
import com.sakti.toko.data.repository.ReviewsRepository;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.model.dto.ProductDTO;
import com.sakti.toko.model.dto.ReviewsDTO;
import com.sakti.toko.model.dto.StoreDTO;
import com.sakti.toko.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductDetailService {

    private final UserRepository userRepository;
    private final StoreDetailService storeDetailService;
    private final ReviewsRepository reviewsRepository;

    public ProductDTO getProductDetail(Product product) {

        StoreDTO storeDTO = storeDetailService.getStoreDetails(product.getStore());

        List<ReviewsDTO> reviews = reviewsRepository
                .findByProduct(product)
                .stream()
                .map(this::getReviewDetail)
                .collect(Collectors.toList());

        return ProductDTO.builder()
                .id(product.getId())
                .storeDTO(storeDTO)
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .reviews(reviews)
                .build();
    }

    public ProductDTO getProductDetailWithoutReview(Product product) {

        StoreDTO storeDTO = storeDetailService.getStoreDetails(product.getStore());

        return ProductDTO.builder()
                .id(product.getId())
                .storeDTO(storeDTO)
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .reviews(null)
                .build();
    }

    public List<ProductDTO> getListProductDetails(List<Product> products) {
        return products.stream()
                .map(this::getProductDetailWithoutReview)
                .collect(Collectors.toList());
    }


    private ReviewsDTO getReviewDetail(Reviews review) {

        UserDTO userDTO = userRepository.findById(review.getUser().getId())
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .createdAt(user.getCreatedAt())
                        .build())
                .orElse(null);

        StoreDTO storeDTO = storeDetailService.getStoreDetails(review.getStore());

        return ReviewsDTO.builder()
                .id(review.getId())
                .userDTO(userDTO)
                .storeDTO(storeDTO)
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}