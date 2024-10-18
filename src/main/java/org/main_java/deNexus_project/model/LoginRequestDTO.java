package org.main_java.deNexus_project.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotNull
    private String correo;

    @NotNull
    private String contrasena;
}
