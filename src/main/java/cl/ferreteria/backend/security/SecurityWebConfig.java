package cl.ferreteria.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityWebConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityWebConfig(JwtUtil jwtUtil, UserDetailsServiceImpl uds, PasswordEncoder encoder) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = uds;
        this.passwordEncoder = encoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);

        http
          .csrf().disable()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .authorizeHttpRequests()
            .requestMatchers("/api/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/h2-console/**").permitAll()
            .requestMatchers("/api/productos/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
            .anyRequest().authenticated()
          .and()
          .headers().frameOptions().sameOrigin() // para h2-console
          .and()
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(UserDetailsService uds) {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(passwordEncoder);
        return p;
    }
}
