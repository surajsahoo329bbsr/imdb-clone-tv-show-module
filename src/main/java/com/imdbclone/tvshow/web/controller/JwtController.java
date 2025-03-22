package com.imdbclone.tvshow.web.controller;

import com.imdbclone.tvshow.aspect.annotation.SetRequestAttributes;
import com.imdbclone.tvshow.service.api.IJWTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin")
@Tag(
        name = "JWT Authentication",
        description = "APIs for admin authentication, including verifying credentials and generating JWT tokens."
)
public class JwtController {

    private final IJWTService jwtService;

    @Autowired
    public JwtController(IJWTService jwtService) {
        this.jwtService = jwtService;
    }

    @SetRequestAttributes
    @GetMapping(value = "/dummy/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Verify dummy admin credentials and generate JWT token",
            description = "Authenticate a dummy admin using username, email, and password. "
                    + "If the credentials are valid, a JWT token is generated for temporary access."
    )
    public ResponseEntity<?> verifyDummyAdminCredentialsToGenerateToken(@RequestParam String username, @RequestParam String email, @RequestParam @NotBlank String password) {
        String token = jwtService.verifyDummyAdminCredentialsToGenerateToken(username, email, password);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @SetRequestAttributes
    @GetMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Verify admin credentials and generate JWT token",
            description = "Authenticate a real admin user with valid credentials. "
                    + "If authentication is successful, a JWT token is generated for API access."
    )
    public ResponseEntity<?> verifyAdminCredentialsToGenerateToken(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        String token = jwtService.verifyAdminCredentialsToGenerateToken(username, email, password);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}