package com.sakti.toko.service.details;

import com.sakti.toko.data.entity.User;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailService {
    private final UserRepository userRepository;

    public UserDTO getUserDetails(User  user, String sessionId ) {

        return UserDTO.builder()
                .id(user.getId())
                .sessionId(sessionId)
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .isSuspended(user.getIsSuspended())
                .build();

    }

    public UserDTO getUserDetailWithoutSession(User  user ) {

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .isSuspended(user.getIsSuspended())
                .build();

    }

    public List<UserDTO> getListUserDetails(List<User> users) {
        return users.stream()
                .map(this::getUserDetailWithoutSession)
                .collect(Collectors.toList());
    }
}

