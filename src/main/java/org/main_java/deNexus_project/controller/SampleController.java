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

    @GetMapping
    public ResponseEntity<List<SampleDTO>> getAllSamples() {
        return ResponseEntity.ok(sampleService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<SampleDTO> getSampleById(@PathVariable Long id) {
        return ResponseEntity.ok(sampleService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createSample(@RequestBody SampleDTO sampleDTO) {
        return ResponseEntity.ok(sampleService.create(sampleDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSample(@PathVariable Long id, @RequestBody SampleDTO sampleDTO) {
        sampleService.update(id, sampleDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSample(@PathVariable Long id) {
        sampleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
