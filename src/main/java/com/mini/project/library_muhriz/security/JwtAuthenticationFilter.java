package com.mini.project.library_muhriz.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Token JWT tidak ditemukan di request.");
            chain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        final String userEmail = jwtUtil.extractEmail(token);
        final String role = jwtUtil.extractRole(token);

        System.out.println("Mendeteksi Token JWT: " + token);
        System.out.println("Email dari Token: " + userEmail);
        System.out.println("Role dari Token: " + role);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = new User(userEmail, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("Autentikasi berhasil untuk user: " + userEmail + " dengan role: " + role);
        } else {
            System.out.println("Autentikasi gagal atau user sudah terautentikasi.");
        }

        chain.doFilter(request, response);
    }

}
