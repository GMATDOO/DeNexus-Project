package org.main_java.deNexus_project.controller;


import org.main_java.deNexus_project.model.AuthResponseDTO;
import org.main_java.deNexus_project.model.LoginRequestDTO;
import org.main_java.deNexus_project.model.RegisterRequestDTO;
import org.main_java.deNexus_project.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO registerRequest,
                                                 @RequestParam("rol") String rol) {
        return authService.register(registerRequest, rol);
    }
}
