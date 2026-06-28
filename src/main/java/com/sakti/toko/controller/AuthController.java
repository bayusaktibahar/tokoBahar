package com.sakti.toko.controller;

import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.ApiRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.LoginRequest;
import com.sakti.toko.model.request.RegisterRequest;
import com.sakti.toko.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<UserDTO> login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest){
        return authService.login(loginRequest, httpServletRequest);
    }

    @PostMapping("/register")
    public ApiResponse<UserDTO> register(@RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }

    @DeleteMapping("/logout")
    public ApiRequest logout(UserDTO currentUser) {
       return authService.logout(currentUser);

    }

}
