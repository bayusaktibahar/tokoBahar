package com.sakti.toko.controller;


import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/manage-Account")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ApiResponse<List<UserDTO>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    @AuthCheck
    public ApiResponse<UserDTO> deleteUserById(@PathVariable UUID id, @CurrentUser UserDTO currentUser) {
        return userService.deleteUser(id, currentUser);
    }

    @PatchMapping("/{id}/suspend")
    @AuthCheck
    public ApiResponse<UserDTO> suspendUserById(@PathVariable UUID id, @CurrentUser UserDTO currentUser) {
        return userService.suspendUser(id, currentUser);
    }

}
