package cl.ferreteria.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
    
    // Opcionales (como tu compañero)
    private String apellido;
    private String run;
    private String region;
    private String comuna;
    private String direccion;
}