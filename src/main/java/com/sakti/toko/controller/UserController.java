package com.sakti.toko.controller;


import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/manage-Account")
@AllArgsConstructor
@Tag(name = "2. Account", description = "Manajemen akun pengguna (admin)")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get all users")
    @GetMapping("/all")
    public ApiResponse<List<UserDTO>> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Delete user", description = "Menghapus akun pengguna. Aksi admin.")
    @DeleteMapping("/{id}")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<UserDTO> deleteUserById(@PathVariable UUID id, @Parameter(hidden = true) @CurrentUser UserDTO currentUser) {
        return userService.deleteUser(id, currentUser);
    }

    @Operation(summary = "Suspend user", description = "Menangguhkan akun pengguna. Aksi admin.")
    @PatchMapping("/{id}/suspend")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<UserDTO> suspendUserById(@PathVariable UUID id, @Parameter(hidden = true) @CurrentUser UserDTO currentUser) {
        return userService.suspendUser(id, currentUser);
    }
}