package com.mini.project.library_muhriz.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final TokenBlackListService tokenBlackListService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,TokenBlackListService tokenBlackListService) {
        this.jwtUtil = jwtUtil;
        this.tokenBlackListService = tokenBlackListService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Token JWT tidak ditemukan di request.");
            chain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        if (tokenBlackListService.isTokenBlacklisted(token)) {
            log.warn("Token telah di-blacklist: {}", token);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token tidak valid atau sudah logout.");
            return;
        }

        final String userEmail = jwtUtil.extractEmail(token);
        final String role = jwtUtil.extractRole(token);

        // 🔥 Tambahkan authorities
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        log.info("Mendeteksi Token JWT: {}", token);
        log.info("Email dari Token: {}", userEmail);
        log.info("Role dari Token: {}", role);
        log.info("Authorities dari Token: {}", authorities);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 🔥 Pastikan UserDetails memiliki authorities
            UserDetails userDetails = new User(userEmail, "", authorities);

            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, authorities // 🔥 Gunakan authorities yang benar
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        chain.doFilter(request, response);
    }



}
