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

                if (headerToken == null || !headerToken.startsWith("Bearer ")) {
                    writeUnauthorizedResponse(response);
                    return false;
                }

                HttpSession httpSession = request.getSession(false);
                if (httpSession == null || httpSession.getAttribute("USER") == null) {
                    writeUnauthorizedResponse(response);
                    return false;
                }
            }
        }
        return true;
    }

    private void writeUnauthorizedResponse(HttpServletResponse response) throws IOException {
        ApiResponse<List<Object>> apiResponse = new ApiResponse<>(
                false,
                403,
                "Lu Ga Punya Akses Kocak",
                null);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}