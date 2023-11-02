package com.piko.ticketingservice.api;

import com.piko.ticketingservice.core.service.CoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.servlet.HandlerInterceptor;

//Interceptor to intercept every request and validate User-Token
@Component
public class RequestInterceptor implements HandlerInterceptor {
    private final CoreService coreService;
    private final String USER_TOKEN = "User-Token";

    public RequestInterceptor(CoreService coreService) {
        this.coreService = coreService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(USER_TOKEN);
        if (token == null) throw new MissingRequestHeaderException(USER_TOKEN, null);
        return coreService.validateToken(token);
    }
}
