package org.main_java.deNexus_project.controller;

import org.main_java.deNexus_project.domain.Experiment;
import org.main_java.deNexus_project.model.ExperimentDTO;
import org.main_java.deNexus_project.service.ExperimentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/experiment")
public class ExperimentController {

    private final ExperimentService experimentService;

    public ExperimentController(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @GetMapping
    public ResponseEntity<List<ExperimentDTO>> getAllExperiments() {
        return ResponseEntity.ok(experimentService.findAll());
    }

    @GetMapping("/async")
    public CompletableFuture<ResponseEntity<List<ExperimentDTO>>> getAllExperimentsAsync() {
        return experimentService.findAllAsync().thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExperimentDTO> getExperimentById(@PathVariable Long id) {
        return ResponseEntity.ok(experimentService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createExperiment(@RequestBody Experiment experiment) {
        return ResponseEntity.ok(experimentService.create(experimentService.mapToDTO(experiment)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExperiment(@PathVariable Long id, @RequestBody ExperimentDTO experimentDTO) {
        try {
            experimentService.update(id, experimentDTO);
            return ResponseEntity.ok("Experiment updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid data: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperiment(@PathVariable Long id) {
        experimentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

