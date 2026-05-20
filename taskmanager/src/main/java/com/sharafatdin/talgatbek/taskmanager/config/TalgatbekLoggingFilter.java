package com.sharafatdin.talgatbek.taskmanager.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** @author Talgatbek Sharafatdin */
@Component
@Slf4j
public class TalgatbekLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("[{}] {} {} - Status: {} - Duration: {}ms",
                    request.getRemoteAddr(),
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration);
        }
    }
}
