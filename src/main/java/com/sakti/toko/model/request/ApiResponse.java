package com.sakti.toko.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private int statusCode;
    private String message;
    private T data;
}
