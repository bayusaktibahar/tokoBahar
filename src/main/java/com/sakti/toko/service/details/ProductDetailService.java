package com.sakti.toko.service.details;

import com.sakti.toko.data.entity.Product;
import com.sakti.toko.data.repository.ReviewsRepository;
import com.sakti.toko.model.dto.ProductDTO;
import com.sakti.toko.model.dto.ReviewsDTO;
import com.sakti.toko.model.dto.StoreDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductDetailService {

    private final StoreDetailService storeDetailService;
    private final ReviewsRepository reviewsRepository;
    private final ReviewDetailService reviewDetailService;

    public ProductDTO getProductDetail(Product product) {

        StoreDTO storeDTO = storeDetailService.getStoreDetails(product.getStore());

        List<ReviewsDTO> reviews = reviewsRepository
                .findByProduct(product)
                .stream()
                .map(reviewDetailService::getReviewDetail)
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


}