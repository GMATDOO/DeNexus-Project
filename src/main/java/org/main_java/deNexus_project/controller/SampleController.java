package org.main_java.deNexus_project.controller;

import org.main_java.deNexus_project.model.samplesDTO.SampleDTO;
import org.main_java.deNexus_project.service.SampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    // Obtener todas las muestras (sincrónico)
    @GetMapping
    public ResponseEntity<List<SampleDTO>> getAllSamples() {
        return ResponseEntity.ok(sampleService.findAll());
    }

    // Obtener una muestra por ID (sincrónico)
    @GetMapping("/{id}")
    public ResponseEntity<SampleDTO> getSampleById(@PathVariable Long id) {
        return ResponseEntity.ok(sampleService.get(id));
    }

    // Crear una nueva muestra (sincrónico)
    @PostMapping
    public ResponseEntity<Long> createSample(@RequestBody SampleDTO sampleDTO) {
        return ResponseEntity.ok(sampleService.create(sampleDTO));
    }

    // Actualizar una muestra existente (sincrónico)
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSample(@PathVariable Long id, @RequestBody SampleDTO sampleDTO) {
        sampleService.update(id, sampleDTO);
        return ResponseEntity.noContent().build();
    }

    // Eliminar una muestra por ID (sincrónico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSample(@PathVariable Long id) {
        sampleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Procesar muestras de manera asíncrona y asignarlas a experimentos
    @PostMapping("/process")
    public CompletableFuture<ResponseEntity<Void>> processSamples(@RequestBody List<SampleDTO> samples) {
        return sampleService.processSamplesAsync(samples)
                .thenApply(result -> ResponseEntity.noContent().build()); // Devolvemos un 204 No Content cuando se complete el procesamiento
    }
}
