package co.edu.udistrital.labarchivistica.controller;

import co.edu.udistrital.labarchivistica.dto.request.LoginRequest;
import co.edu.udistrital.labarchivistica.dto.response.ApiResponse;
import co.edu.udistrital.labarchivistica.dto.response.AuthResponse;
import co.edu.udistrital.labarchivistica.dto.response.UserResponse;
import co.edu.udistrital.labarchivistica.security.JwtUtil;
import co.edu.udistrital.labarchivistica.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticación.
 *
 * <ul>
 *   <li>{@code POST /auth/login}    — Login, emite cookie JWT HttpOnly.</li>
 *   <li>{@code GET  /auth/me}       — Perfil del usuario autenticado.</li>
 *   <li>{@code GET  /auth/validate} — Endpoint consumido por Nginx {@code auth_request}.</li>
 *   <li>{@code POST /auth/logout}   — Invalida la cookie JWT en el cliente.</li>
 * </ul>
 *
 * <p>El contexto de la aplicación ya tiene {@code /api} como prefix por application.yml,
 * así que estas rutas resultan en {@code /api/auth/...}.</p>
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String COOKIE_NAME = "jwt";
    private static final int    COOKIE_MAX_AGE_SECONDS = 86_400; // 24 horas

    private final AuthenticationManager authenticationManager;
    private final JwtUtil                jwtUtil;
    private final UserService            userService;

    // -------------------------------------------------------
    // POST /auth/login
    // -------------------------------------------------------

    /**
     * Autentica al usuario y emite un JWT en una cookie HttpOnly.
     *
     * @param request  credenciales email + password
     * @param response respuesta HTTP donde se añade la cookie
     * @return perfil del usuario autenticado (sin token en body)
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        // Delegar autenticación a Spring Security (lanza BadCredentialsException si falla)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        // Actualizar fecha de último login
        userService.updateLastLogin(userDetails.getUsername());

        // Emitir JWT como cookie HttpOnly
        addJwtCookie(response, token);

        UserResponse userResponse = userService.getCurrentUser();
        AuthResponse authResponse = AuthResponse.builder()
                .user(userResponse)
                .message("Login exitoso")
                .build();

        log.info("Login exitoso para: {}", userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.ok("Login exitoso", authResponse));
    }

    // -------------------------------------------------------
    // GET /auth/me
    // -------------------------------------------------------

    /**
     * Devuelve el perfil del usuario autenticado.
     * Requiere cookie JWT válida (o header Authorization Bearer).
     *
     * @return datos del usuario en sesión
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me() {
        UserResponse user = userService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.ok("Perfil obtenido", user));
    }

    // -------------------------------------------------------
    // GET /auth/validate
    // -------------------------------------------------------

    /**
     * Endpoint de validación para Nginx {@code auth_request}.
     *
     * <p>Nginx envía la cookie del usuario a este endpoint antes de redirigir
     * al aplicativo solicitado. Si el JWT es válido devuelve {@code 200 OK};
     * si no está autenticado, el filtro JWT no populará el contexto de seguridad
     * y Spring Security devolverá {@code 401}.</p>
     *
     * @return {@code 200 OK} si el JWT en la cookie es válido
     */
    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<Void>> validate() {
        // Si la petición llega aquí, el JwtFilter ya validó el token correctamente.
        return ResponseEntity.ok(ApiResponse.ok("Token válido"));
    }

    // -------------------------------------------------------
    // POST /auth/logout
    // -------------------------------------------------------

    /**
     * Cierra sesión invalidando la cookie JWT en el cliente.
     * El JWT no se revoca del lado servidor (stateless); simplemente
     * se sobrescribe la cookie con MaxAge=0.
     *
     * @param response respuesta HTTP donde se elimina la cookie
     * @return confirmación de logout
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Elimina la cookie en el navegador
        response.addCookie(cookie);
        return ResponseEntity.ok(ApiResponse.ok("Sesión cerrada correctamente"));
    }

    // -------------------------------------------------------
    // Helper
    // -------------------------------------------------------

    private void addJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setHttpOnly(true);   // No accesible desde JavaScript
        cookie.setPath("/");        // Disponible para toda la aplicación
        cookie.setMaxAge(COOKIE_MAX_AGE_SECONDS);
        // cookie.setSecure(true); // Habilitar en producción con HTTPS
        response.addCookie(cookie);
    }
}
