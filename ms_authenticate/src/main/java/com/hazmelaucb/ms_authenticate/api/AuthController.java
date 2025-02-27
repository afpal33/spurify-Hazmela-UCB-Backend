package com.hazmelaucb.ms_authenticate.api;


import com.hazmelaucb.ms_authenticate.bl.AuthService;
import com.hazmelaucb.ms_authenticate.dto.AuthRequest;
import com.hazmelaucb.ms_authenticate.dto.AuthResponse;
import com.hazmelaucb.ms_authenticate.dto.RegisterRequest;
import com.hazmelaucb.ms_authenticate.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
        return ResponseEntity.ok(authService.login(request, httpRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(null);
        }

        String refreshToken = authHeader.substring(7); // Remueve "Bearer "
        AuthResponse newTokens = authService.refreshAccessToken(refreshToken);

        return ResponseEntity.ok(newTokens);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token inválido");
        }

        String token = authHeader.substring(7); // Remueve "Bearer "
        boolean isValid = jwtTokenProvider.validateToken(token);

        if (!isValid) {
            return ResponseEntity.status(401).body("Token revocado o inválido.");
        }

        return ResponseEntity.ok("Token válido.");
    }


}
