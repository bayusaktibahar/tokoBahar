package com.sakti.toko.common.interceptor;

import java.io.IOException;
import java.util.List;

import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionService sessionService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            if (method.getMethodAnnotation(AuthCheck.class) != null) {
                String headerToken = request.getHeader("Authorization");
                String requestUri = request.getRequestURI();

                // Bypass untuk endpoint logout agar dihandle oleh AuthService (Sesuai setingan kita sebelumnya)
                if ("/api/auth/logout".equals(requestUri)) {
                    return true;
                }

                // 1. KONDISI TANPA TOKEN: Kirim 401 sesuai permintaan tugas
                if (headerToken == null || !headerToken.startsWith("Bearer ")) {
                    writeCustomResponse(response, 401, "Unauthorized: Token is missing or invalid");
                    return false;
                }

                // 2. KONDISI SESSION EXPIRED / SALAH: Kirim 403
                HttpSession httpSession = request.getSession(false);
                if (httpSession == null || httpSession.getAttribute("USER") == null) {
                    writeCustomResponse(response, 403, "You don't have permission to access this resource");
                    return false;
                }
            }
        }
        return true;
    }

    // PERBAIKAN: Ubah method menjadi dinamis agar bisa menerima statusCode sesuai kondisi
    private void writeCustomResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        ApiResponse<List<Object>> apiResponse = new ApiResponse<>(
                false,
                statusCode, // <--- Akan terisi 401 atau 403 secara dinamis
                message,
                null);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Set HTTP status code di header agar sinkron dengan bodi JSON
        if (statusCode == 401) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }


    private void writeUnauthorizedResponse(HttpServletResponse response) throws IOException {
        ApiResponse<List<Object>> apiResponse = new ApiResponse<>(
                false,
                403,
                "You don't have permission to access this resource",
                null);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}