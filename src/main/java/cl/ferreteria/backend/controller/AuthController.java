package cl.ferreteria.backend.controller;

import cl.ferreteria.backend.model.Usuario;
import cl.ferreteria.backend.security.JwtUtil;
import cl.ferreteria.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Validar credenciales
            if (userService.validarCredenciales(loginRequest.getEmail(), loginRequest.getPassword())) {
                Usuario usuario = userService.buscarPorEmail(loginRequest.getEmail()).get();
                
                // Generar token JWT
                String token = jwtUtil.generateToken(
                    usuario.getEmail(),
                    usuario.getRoles().stream().collect(Collectors.toList())
                );
                
                // Respuesta con token y datos del usuario
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("usuario", Map.of(
                    "id", usuario.getId(),
                    "nombre", usuario.getNombre(),
                    "email", usuario.getEmail(),
                    "roles", usuario.getRoles()
                ));
                
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("Credenciales inválidas");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el login: " + e.getMessage());
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioRegistrado = userService.registrarUsuario(usuario);
            
            // Generar token automáticamente después del registro
            String token = jwtUtil.generateToken(
                usuarioRegistrado.getEmail(),
                usuarioRegistrado.getRoles().stream().collect(Collectors.toList())
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", Map.of(
                "id", usuarioRegistrado.getId(),
                "nombre", usuarioRegistrado.getNombre(),
                "email", usuarioRegistrado.getEmail(),
                "roles", usuarioRegistrado.getRoles()
            ));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el registro: " + e.getMessage());
        }
    }

    // Clase interna para el request de login
    public static class LoginRequest {
        private String email;
        private String password;

        // Getters y Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}