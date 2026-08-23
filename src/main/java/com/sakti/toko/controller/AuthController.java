package com.sakti.toko.controller;

import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.ApiRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.LoginRequest;
import com.sakti.toko.model.request.RegisterRequest;
import com.sakti.toko.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "1. Auth", description = "Login, register, dan logout untuk TokoBahar")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Login",
            description = "Mengembalikan data user beserta session. Session disimpan via cookie/header sesuai konfigurasi Spring Session.")
    @PostMapping("/login")
    public ApiResponse<UserDTO> login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest){
        return authService.login(loginRequest, httpServletRequest);
    }

    @Operation(
            summary = "Register",
            description = "Mendaftarkan user baru. Endpoint publik, tidak butuh autentikasi.")
    @PostMapping("/register")
    public ApiResponse<UserDTO> register(@RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }

    @Operation(
            summary = "Logout",
            description = "Menghapus session user yang sedang aktif.")
    @DeleteMapping("/logout")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiRequest> logout(@Parameter(hidden = true) @CurrentUser UserDTO currentUser) {
        ApiRequest response = authService.logout(currentUser);

        // Jika di dalam service menghasilkan status tidak sukses (misal 401)
        if (response.getStatusCode() == 401) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Jika sukses, kembalikan 200 OK
        return ResponseEntity.ok(response);
    }
}