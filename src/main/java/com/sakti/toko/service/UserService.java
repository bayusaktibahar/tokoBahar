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
       var  user = userRepository.findById(id).orElseThrow();

       var result = userDetailService.getUserDetailWithoutSession(user);

       return new ApiResponse<>(
               true,
               200,
               "User is Found",
               result
       );

    }

    @Transactional
    public ApiResponse<UserDTO> deleteUser(UUID id, UserDTO currentUser) {
        var targetUser = userRepository.findById(id);

        if (targetUser.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "User not found",
                    null
            );
        }

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            return new ApiResponse<>(
                    false,
                    403,
                    "You are not allowed to perform this action",
                    null
            );
        }

        var userToDelete = targetUser.get();

        userRepository.deleteById(userToDelete.getId());

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
