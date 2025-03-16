package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JWTUtils {

    private final String jwtSecretKey;

    public JWTUtils(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    // Method to get adminId from JWT in the SecurityContext
    public Long getAdminIdFromJwt() {
        // Extract the JWT from the SecurityContext (or the HTTP header)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = null;

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.getCredentials() != null) {
            jwtToken = authentication.getCredentials().toString(); // Typically JWT is in the credentials
        }

        if (jwtToken != null) {
            return extractAdminIdFromJwt(jwtToken);
        }

        return 0L; // Return null if no JWT is found in SecurityContext
    }

    // Decode the JWT and extract the adminId from it
    private Long extractAdminIdFromJwt(String token) {
        try {
            // Parse the JWT token using the secret key to get the claims
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // Extract the adminId from the claims (replace 'adminId' with the actual claim key used in your JWT)
            return Long.valueOf(claims.get("adminId").toString());  // Adjust the key according to your JWT's claim name
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null or handle exception if decoding fails
        }
    }
}
