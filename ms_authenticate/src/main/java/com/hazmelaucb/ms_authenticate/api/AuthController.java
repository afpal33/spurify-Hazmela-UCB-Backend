package com.hazmelaucb.ms_authenticate.api;


import com.hazmelaucb.ms_authenticate.bl.AuthService;
import com.hazmelaucb.ms_authenticate.dao.RoleRepository;
import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.dto.AuthRequest;
import com.hazmelaucb.ms_authenticate.dto.AuthResponse;
import com.hazmelaucb.ms_authenticate.dto.RegisterRequest;
import com.hazmelaucb.ms_authenticate.entity.RoleEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import com.hazmelaucb.ms_authenticate.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "API para la autenticación de usuarios")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider,
                          UserRepository userRepository, RoleRepository roleRepository) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Operation(summary = "${api.auth.login.description}", description = "${api.auth.login.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "401", description = "${api.responseCodes.unauthorized.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
        return ResponseEntity.ok(authService.login(request, httpRequest));
    }

    @Operation(summary = "${api.auth.register.description}", description = "${api.auth.register.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${api.responseCodes.created.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "409", description = "${api.responseCodes.conflict.description}")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @Operation(summary = "${api.auth.refreshToken.description}", description = "${api.auth.refreshToken.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "401", description = "${api.responseCodes.unauthorized.description}")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String authHeader) {
        System.out.println("SDASDFASDF");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("SDASDFASDF");
        String refreshToken = authHeader.substring(7); // Remueve "Bearer "
        System.out.println("Refresh token: " + refreshToken);
        AuthResponse newTokens = authService.refreshAccessToken(refreshToken);

        return ResponseEntity.ok(newTokens);
    }

    @Operation(summary = "${api.auth.validateToken.description}", description = "${api.auth.validateToken.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "401", description = "${api.responseCodes.unauthorized.description}")
    })
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

    @Operation(summary = "${api.auth.assignAdmin.description}", description = "${api.auth.assignAdmin.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PutMapping("/assign-admin/{email}")
    public ResponseEntity<String> assignAdminRole(@PathVariable String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        System.out.println("Todos los datos de usuario");
        System.out.println(user.getEmail());
        System.out.println(user.getRoles());
        for (RoleEntity role : user.getRoles()) {
            System.out.println(role.getName());
            System.out.println(role.getId());
        }
        System.out.println(user.getId());

        RoleEntity adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Rol 'ADMIN' no encontrado"));

        if (user.getRoles().contains(adminRole)) {
            return ResponseEntity.ok("⚠️ El usuario ya tiene el rol de ADMIN.");
        }


        System.out.println(adminRole.getName());
        System.out.println(adminRole.getId());
        user.getRoles().add(adminRole);
        userRepository.save(user);
        userRepository.flush(); // 🔥 Asegurar persistencia inmediata

// 🔥 Insertar manualmente en la tabla intermedia si no se ha actualizado
        roleRepository.assignRoleToUser(user.getId(), adminRole.getId());

        return ResponseEntity.ok("✅ Usuario ahora es ADMIN.");
    }

}
