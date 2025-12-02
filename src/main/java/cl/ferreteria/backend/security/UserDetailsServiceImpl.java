package cl.ferreteria.backend.security;

import cl.ferreteria.backend.model.Usuario;
import cl.ferreteria.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository repo) { this.userRepository = repo; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario u = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        var authorities = u.getRoles().stream()
                .map(r -> new org.springframework.security.core.authority.SimpleGrantedAuthority(r))
                .collect(Collectors.toList());
        return User.builder()
                .username(u.getEmail())
                .password(u.getPassword())
                .authorities(authorities)
                .build();
    }
}
