package io.codelex.flightplanner.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        LocalDateTime timestamp = LocalDateTime.now();

        int status = HttpServletResponse.SC_UNAUTHORIZED;

        String responseBody = """
                {
                    "timestamp": "%s",
                    "status": %d,
                    "error": "Unauthorized",
                    "path": "%s"
                }
                """.formatted(timestamp, status, request.getRequestURI());

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(responseBody);
    }
}