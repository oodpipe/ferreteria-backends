package cl.ferreteria.backend.controller;

import cl.ferreteria.backend.dto.AuthResponse;
import cl.ferreteria.backend.dto.LoginRequest;
import cl.ferreteria.backend.dto.RegisterRequest;
import cl.ferreteria.backend.model.Usuario;
import cl.ferreteria.backend.repository.UsuarioRepository;
import cl.ferreteria.backend.security.JwtService;
import cl.ferreteria.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            // Autenticar con Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Obtener usuario de la BD
            Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            // Generar token
            String token = jwtService.generateToken(usuario);

            return ResponseEntity.ok(AuthResponse.builder()
                    .token(token)
                    .usuario(usuario)
                    .build());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrarUsuario(@RequestBody RegisterRequest request) {
        try {
            // Verificar si el email ya existe
            if (usuarioRepository.existsByEmail(request.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado");
            }

            // Convertir DTO a entidad
            Usuario usuario = Usuario.builder()
                    .nombre(request.getNombre())
                    .apellido(request.getApellido())
                    .email(request.getEmail())
                    .password(request.getPassword()) // Se encriptará en el servicio
                    .run(request.getRun())
                    .region(request.getRegion())
                    .comuna(request.getComuna())
                    .direccion(request.getDireccion())
                    .rol(Usuario.Rol.USER) // Por defecto USER
                    .build();

            // Registrar usuario
            Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);
            
            // Generar token automáticamente
            String token = jwtService.generateToken(usuarioRegistrado);

            return ResponseEntity.status(HttpStatus.CREATED).body(AuthResponse.builder()
                    .token(token)
                    .usuario(usuarioRegistrado)
                    .build());

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en el registro: " + e.getMessage());
        }
    }
}