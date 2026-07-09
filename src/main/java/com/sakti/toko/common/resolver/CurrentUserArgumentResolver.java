package com.sakti.toko.common.resolver;


import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.service.SessionService;
import com.sakti.toko.service.details.UserDetailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionService sessionService;
    private final UserRepository userRepository;
    private final UserDetailService userDetailService;

    public CurrentUserArgumentResolver(
            SessionService sessionService,
            UserRepository userRepository,
            UserDetailService userDetailService) {
        this.sessionService = sessionService;
        this.userRepository = userRepository;
        this.userDetailService = userDetailService;
    }

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.getParameterAnnotation(CurrentUser.class) != null
                && parameter.getParameterType().equals(UserDTO.class);
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory) {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            return null;
        }

        // Extract token from Authorization header
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null) return null;
        String token = httpSession.getId();
        var session = sessionService.getSessionById(token);

        if (session == null) {
            return null;
        }

        // Get user email from session
        var userEmail = session.getAttribute("USER");
        if (userEmail == null) {
            return null;
        }

        // Find user by email
        var optionalUser = userRepository.findByEmail(userEmail.toString());
        if (optionalUser.isEmpty()) {
            return null;
        }

        var user = optionalUser.get();

        // Return user details
        return userDetailService.getUserDetails(user, session.getId());
    }
}
