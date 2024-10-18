package org.main_java.deNexus_project.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.main_java.deNexus_project.model.samplesDTO.SampleDTO;
import org.main_java.deNexus_project.service.SampleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/samples")
public class SampleResource {

    private final SampleService sampleService;

    public SampleResource(final SampleService sampleService) {
        this.sampleService = sampleService;
    }

    // Retorna todas las muestras
    @GetMapping("/list")
    @ApiResponse(responseCode = "200", description = "Get all samples")
    public ResponseEntity<List<SampleDTO>> getAllSamples() {
        return ResponseEntity.ok(sampleService.findAll());
    }

    // Obtiene un Sample específico por ID
    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Get sample by ID")
    public ResponseEntity<SampleDTO> getSample(@PathVariable final Long id) {
        SampleDTO sample = sampleService.get(id);
        return ResponseEntity.ok(sample);
    }

    // Crea un nuevo Sample
    @PostMapping
    @ApiResponse(responseCode = "201", description = "Create a new sample")
    public ResponseEntity<Long> createSample(@RequestBody final SampleDTO sampleDTO) {
        Long createdId = sampleService.create(sampleDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    // Actualiza un Sample existente
    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Update an existing sample")
    public ResponseEntity<Void> updateSample(@PathVariable final Long id, @RequestBody final SampleDTO sampleDTO) {
        sampleService.update(id, sampleDTO);
        return ResponseEntity.ok().build();
    }

    // Elimina un Sample por su ID
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Delete a sample")
    public ResponseEntity<Void> deleteSample(@PathVariable final Long id) {
        sampleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
