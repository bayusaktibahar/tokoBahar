package com.sakti.toko.service;


import com.sakti.toko.data.entity.Role;
import com.sakti.toko.data.entity.User;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.service.details.UserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDetailService userDetailService;

    public ApiResponse<List<UserDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();

        var result = userDetailService.getListUserDetails(users);

        return new ApiResponse<>(
                true,
                200,
                "Data User is Found",
                result
        );
    }

    public ApiResponse<UserDTO> getUserById(UUID id) {
       Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "Account with ID is not Found",
                    null
            );
        }

        User userReal = userOptional.get();

       var result = userDetailService.getUserDetailWithoutSession(userReal);

       return new ApiResponse<>(
               true,
               200,
               "User is Found",
               result
       );

    }

    @Transactional
    public ApiResponse<UserDTO> deleteUser(UUID id, UserDTO currentUser) {
        // 1. Cek apakah user target ada di database
        var targetUser = userRepository.findById(id);

        if (targetUser.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "User not found",
                    null
            );
        }

        // 2. Pengecekan role ADMIN (Sudah lolos karena Anda login pakai admin)
        if (!currentUser.getRole().equals(Role.ADMIN)) {
            return new ApiResponse<>(
                    false,
                    403,
                    "You are not allowed to perform this action",
                    null
            );
        }

        // 3. PERBAIKAN: Hapus langsung menggunakan parameter 'id' dari URL,
        // bukan menggunakan 'userToDelete.getId()' yang bisa memicu NullPointer

        userRepository.deleteProductsByUserId(id);

        userRepository.deleteStoresByUserId(id);

        userRepository.deleteReviewsByUserId(id);

        userRepository.deleteById(id);

        return new ApiResponse<>(
                true,
                200,
                "User Deleted Successfully",
                null
        );
    }


    @Transactional
    public ApiResponse<UserDTO> suspendUser(UUID id, UserDTO currentUser) {

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            return new ApiResponse<>(
                    false,
                    403,
                    "You are not allowed to perform this action",
                    null
            );
        }

        var targetUser = userRepository.findById(id);

        if (targetUser.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "User not found",
                    null
            );
        }

        var userToSuspend =  targetUser.get();

        userToSuspend.setIsSuspended(!userToSuspend.getIsSuspended());

        userRepository.save(userToSuspend);

        var result = userDetailService.getUserDetailWithoutSession(userToSuspend);

        return new ApiResponse<>(
                true,
                200,
                userToSuspend.getIsSuspended() ? "User Suspended" : "User UnSuspended",
                result
        );

    }

}
