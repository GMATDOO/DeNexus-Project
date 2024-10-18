package org.main_java.deNexus_project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {

    private String mensaje;

    private String token;

    private String role;

    public AuthResponseDTO(String mensaje, String token, String role) {
        this.mensaje = mensaje;
        this.token = token;
        this.role = role;
    }

}

