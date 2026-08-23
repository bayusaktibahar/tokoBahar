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

        var trueEmail = loginRequest.getEmail();

        if (trueEmail == null || loginRequest.getEmail().trim().isEmpty() || !loginRequest.getEmail().contains("@")) {
            return new ApiResponse<>(
                false,
                403,
                "Email invalid, format email anda salah atau kosong",
                    null);
        }

        var truePassword = loginRequest.getPassword();

        if (truePassword == null || loginRequest.getPassword().trim().isEmpty()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Password Cannot Empty",
                    null
            );
        }

        var userOptional = userRepository.findByEmailIgnoreCase(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "Email not found",
                    null);
        }

        var user = userOptional.get();

        if (!AESOperation.matches(loginRequest.getPassword(), user.getPassword())) {
            return new ApiResponse<>(
                    false,
                    400, "Your Password is incorrect",
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

        var trueEmail = registerRequest.getEmail();

        if (trueEmail == null || registerRequest.getEmail().trim().isEmpty() || !registerRequest.getEmail().contains("@")) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Email invalid, format email anda salah atau kosong",
                    null);
        }

        var truePassword = registerRequest.getPassword();

        if (truePassword == null || registerRequest.getPassword().trim().isEmpty()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Password Cannot Empty",
                    null
            );
        }

        if (!registerRequest.getConfirmPassword().equals(registerRequest.getPassword())) {
            return new ApiResponse<>(false,
                    400,
                    "Jir Password lu beda!!!!!",
                    null
            );
        }

        var existingEmail =  userRepository.findByEmailIgnoreCase(registerRequest.getEmail());

        if (existingEmail.isPresent()  ) {
            return new ApiResponse<>(false,
                    400,
                    "Jir Email lu udah ada di system kocak, login aja!!!!!",
                    null
            );
        }

        var user = User.builder()
                .email(registerRequest.getEmail())
                .password(AESOperation.encode(registerRequest.getPassword()))
                .role(Role.BUYER)
                .createdAt(LocalDateTime.now())
                .isSuspended(false)
                .build();

        var savedUser = userRepository.save(user);
        var result = userDetailService.getUserDetails(savedUser, null);

        return new ApiResponse<>(true, 200, "ok", result);
    }

    public ApiRequest logout(UserDTO userDTO) {
        // 1. Cek jika userDTO null (Tanpa Token)
        if (userDTO == null) {
            return new ApiRequest(
                    false,
                    401,
                    "U Have Login First Before Loging Out"
            );
        }

        String sessionId = userDTO.getSessionId();

        // 2. Cek jika sessionId null
        if (sessionId == null) {
            return new ApiRequest(
                    false,
                    401,
                    "U Have Login First Before Loging Out"
            );
        }

        // 3. PERBAIKAN LOGIKA: Ambil data session
        var session = sessionService.getSessionById(sessionId);

        // Jika session tidak ditemukan/null ATAU session sudah expired (Logout kedua kali)
        if (session == null || session.isExpired()) {
            return new ApiRequest(
                    false,
                    401,
                    "Your Session has expired"
            );
        }

        // 4. Jika session valid dan aktif, hapus session (Logout sukses)
        sessionService.deleteSessionById(sessionId);

        return new ApiRequest(
                true,
                200,
                "ok"
        );
    }

}