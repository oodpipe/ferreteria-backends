package cl.ferreteria.backend.service;

import cl.ferreteria.backend.model.Usuario;
import cl.ferreteria.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario) {
        // Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Si es el primer usuario, hacerlo ADMIN
        if (usuarioRepository.count() == 0) {
            usuario.setRol(Usuario.Rol.ADMIN);
        } else if (usuario.getRol() == null) {
            usuario.setRol(Usuario.Rol.USER); // Por defecto
        }
        
        return usuarioRepository.save(usuario);
    }

    public Usuario registrarAdmin(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Usuario.Rol.ADMIN);
        return usuarioRepository.save(usuario);
    }

    public boolean validarCredenciales(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> passwordEncoder.matches(password, usuario.getPassword()))
                .orElse(false);
    }
}