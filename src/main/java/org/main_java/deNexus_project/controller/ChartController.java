package org.main_java.deNexus_project.controller;

import org.main_java.deNexus_project.model.ChartDTO;
import org.main_java.deNexus_project.service.ChartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/charts")
public class ChartController {

    private final ChartService chartService;

    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    // Obtener todos los gráficos
    @GetMapping
    public ResponseEntity<List<ChartDTO>> getAllCharts() {
        return ResponseEntity.ok(chartService.getAllCharts());
    }

    // Obtener gráficos por ID
    @GetMapping("/{id}")
    public ResponseEntity<ChartDTO> getChartById(@PathVariable Long id) {
        return ResponseEntity.ok(chartService.getChartById(id));
    }

    // Obtener gráficos asociados a un experimento específico
    @GetMapping("/experiment/{experimentId}")
    public ResponseEntity<List<ChartDTO>> getChartsByExperiment(@PathVariable Long experimentId) {
        List<ChartDTO> charts = chartService.getChartsByExperimentId(experimentId);
        return ResponseEntity.ok(charts);
    }

    // Obtener gráficos de manera asíncrona para un experimento específico
    @GetMapping("/experiment/{experimentId}/async")
    public CompletableFuture<ResponseEntity<List<ChartDTO>>> getChartsByExperimentAsync(@PathVariable Long experimentId) {
        return chartService.getChartsByExperimentIdAsync(experimentId)
                .thenApply(ResponseEntity::ok);
    }

    // Crear un nuevo gráfico asociado a un experimento
    @PostMapping
    public ResponseEntity<ChartDTO> createChart(@RequestParam Long experimentId,
                                                @RequestParam String chartType,
                                                @RequestBody Map<String, String> data) {
        ChartDTO chart = chartService.createChart(experimentId, chartType, data);
        return ResponseEntity.ok(chart);
    }

    // Actualizar un gráfico existente
    @PutMapping("/{id}")
    public ResponseEntity<ChartDTO> updateChart(@PathVariable Long id,
                                                @RequestParam String chartType,
                                                @RequestBody Map<String, String> data) {
        ChartDTO updatedChart = chartService.updateChart(id, chartType, data);
        return ResponseEntity.ok(updatedChart);
    }

    // Eliminar un gráfico por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChart(@PathVariable Long id) {
        chartService.deleteChart(id);
        return ResponseEntity.noContent().build();
    }

    // Generar gráficos de manera concurrente para todos los experimentos
    @PostMapping("/generate")
    public CompletableFuture<ResponseEntity<Void>> generateChartsConcurrently() {
        return chartService.generateChartsConcurrently()
                .thenApply(result -> ResponseEntity.noContent().build());
    }
}
