package org.main_java.deNexus_project.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.main_java.deNexus_project.model.UsuarioDTO;
import org.main_java.deNexus_project.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Get all users")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable final Long id) {
        UsuarioDTO usuario = usuarioService.get(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Create a new user")
    public ResponseEntity<Long> createUsuario(@RequestBody final UsuarioDTO usuarioDTO) {
        Long createdId = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUsuario(@PathVariable final Long id,
                                              @RequestBody final UsuarioDTO usuarioDTO) {
        usuarioService.update(id, usuarioDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Delete a user")
    public ResponseEntity<Void> deleteUsuario(@PathVariable final Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

