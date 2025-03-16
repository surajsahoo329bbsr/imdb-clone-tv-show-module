package com.imdbclone.tvshow.service.implementation;

import com.imdbclone.tvshow.service.api.IJWTService;
import com.imdbclone.tvshow.service.client.AdminServiceClient;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JWTServiceImpl implements IJWTService {

    private final AdminServiceClient adminServiceClient;
    private final PasswordEncoder passwordEncoder;
    private final String jwtSecretKey;
    private final String username;
    private final String email;
    private final String hashedPassword;


    public JWTServiceImpl(AdminServiceClient adminServiceClient, PasswordEncoder passwordEncoder, String jwtSecretKey, String username, String email, String hashedPassword) {
        this.adminServiceClient = adminServiceClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtSecretKey = jwtSecretKey;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    @Override
    public String generateToken(Long adminId, String username, String role) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
        return Jwts.builder()
                .claims(Map.of(
                        "adminId", adminId,
                        "sub", username,
                        "role", role
                ))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1-hour expiry
                .signWith(key)
                .compact();
    }

    @Override
    public String verifyDummyAdminCredentialsToGenerateToken(String username, String email, String password) {
        boolean isUsernameValid = this.username.equals(username);
        boolean isEmailValid = this.email.equals(email);
        boolean isPasswordValid = passwordEncoder.matches(password, hashedPassword);
        Long adminId = adminServiceClient.authenticateAdminAndFetchId(username, email, password);

        if ((isUsernameValid || isEmailValid) && isPasswordValid) {
            String subject = isUsernameValid ? username : email; // Use the valid identifier
            return generateToken(adminId, subject, "ADMIN");
        }

        return "Invalid Dummy Admin Credentials";
    }

    @Override
    public String verifyAdminCredentialsToGenerateToken(String username, String email, String password) {
        Long adminId = adminServiceClient.authenticateAdminAndFetchId(username, email, password);
        return adminId != null ? generateToken(adminId, username, "ADMIN") : "Invalid Admin Credentials";
    }
}
