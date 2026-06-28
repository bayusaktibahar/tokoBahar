package com.sakti.toko.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsDTO {
    private Long id;
    private UserDTO userDTO;
    private StoreDTO storeDTO;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
