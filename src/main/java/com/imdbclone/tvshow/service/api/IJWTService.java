package com.imdbclone.tvshow.service.api;

public interface IJWTService {

    String generateToken(Long adminId, String username, String role);

    String verifyDummyAdminCredentialsToGenerateToken(String username, String email, String password);

    String verifyAdminCredentialsToGenerateToken(String username, String email, String password);
}
