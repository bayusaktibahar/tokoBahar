package com.sakti.toko.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewRequest {
    private Long productId;
    private Integer rating;
    private String comment;
}
