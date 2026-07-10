package com.sakti.toko.service;

import com.sakti.toko.common.extension.AESOperation;
import com.sakti.toko.data.entity.Role;
import com.sakti.toko.data.entity.User;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.ApiRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.LoginRequest;
import com.sakti.toko.model.request.RegisterRequest;
import com.sakti.toko.service.details.UserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final UserDetailService userDetailService;
    private final SessionService sessionService;

    public ApiResponse<UserDTO> login(LoginRequest loginRequest, HttpServletRequest  httpServletRequest) {
        var userOptional = userRepository.findByEmailIgnoreCase(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return new ApiResponse<>(false,
                    404,
                    "Email not found",
                    null);
        }

        var user = userOptional.get();

        if (!AESOperation.matches(loginRequest.getPassword(), user.getPassword())) {
            return new ApiResponse<>(
                    false,
                    400, "Jir Eror Euy",
                    null);
        }

        if (user.getIsSuspended()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Your account has been suspended",
                    null
            );
        }

        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("USER", user.getEmail());

        var userDetails = userDetailService.getUserDetails(user, session.getId());

        return new ApiResponse<>(
                true,
                200,
                "ok",
                userDetails);
    }

    @Transactional
    public ApiResponse<UserDTO> register(RegisterRequest registerRequest) {

        if (!registerRequest.getConfirmPassword().equals(registerRequest.getPassword())) {
            return new ApiResponse<>(false,
                    400,
                    "Jir Password beda!!!!!",
                    null
            );
        }

        var user = User.builder()
                .email(registerRequest.getEmail())
                .password(AESOperation.encode(registerRequest.getPassword()))
                .role(Role.BUYER)
                .createdAt(LocalDateTime.now())
                .build();

        var savedUser = userRepository.save(user);
        var result = userDetailService.getUserDetails(savedUser, null);

        return new ApiResponse<>(true, 200, "ok", result);
    }

    public ApiRequest logout(UserDTO userDTO) {
        String sessionId = userDTO.getSessionId();

        if (sessionId == null) {
            return new ApiRequest(
                    false,
                    401,
                    "U Have Login First Before Loging Out"
            );
        }

        sessionService.deleteSessionById(sessionId);

        return new ApiRequest(
                true,
                200,
                "ok"
        );

    }

}