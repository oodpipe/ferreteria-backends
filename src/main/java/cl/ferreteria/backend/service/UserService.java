package cl.ferreteria.backend.service;

import cl.ferreteria.backend.model.Usuario;
import cl.ferreteria.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario) {
        if (userRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Asignar roles como Strings directamente
        usuario.setRoles(new HashSet<>(Arrays.asList("ROLE_USER")));
        
        return userRepository.save(usuario);
    }

    public Usuario registrarAdmin(Usuario usuario) {
        if (userRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Asignar roles de admin
        usuario.setRoles(new HashSet<>(Arrays.asList("ROLE_USER", "ROLE_ADMIN")));
        
        return userRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean validarCredenciales(String email, String password) {
        Optional<Usuario> usuarioOpt = userRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            return passwordEncoder.matches(password, usuarioOpt.get().getPassword());
        }
        return false;
    }
}