package com.imdbclone.tvshow.filter;

import com.imdbclone.tvshow.service.client.AdminServiceClient;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.*;

public class JWTAuthFilter extends OncePerRequestFilter {

    private final String jwtSecretKey;
    private final AdminServiceClient adminServiceClient;

    public JWTAuthFilter(String jwtSecretKey, AdminServiceClient adminServiceClient) {
        this.jwtSecretKey = jwtSecretKey;
        this.adminServiceClient = adminServiceClient;
    }

    private Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean validateToken(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    private Long extractAdminId(String token) {
        return extractClaims(token).get("adminId", Long.class);
    }

    private boolean isAdminValid(Long adminId) {
        try {
            Boolean isAdminValid = adminServiceClient.isAdminValid(adminId);
            return isAdminValid != null && isAdminValid;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        boolean isTokenValid = validateToken(token);
        String role = extractRole(token);
        Long adminId = extractAdminId(token);

        if ("ADMIN".equals(role) && isAdminValid(adminId) && isTokenValid) {
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            User authenticatedUser = new User(adminId.toString(), "", authorities);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Unauthorized: Admin validation failed");
        }
    }
}
