package org.main_java.deNexus_project.service;

import jakarta.transaction.Transactional;
import org.main_java.deNexus_project.domain.Chart;
import org.main_java.deNexus_project.domain.Experiment;
import org.main_java.deNexus_project.model.ChartDTO;
import org.main_java.deNexus_project.model.ExperimentDTO;
import org.main_java.deNexus_project.model.samplesDTO.CyberAttackSampleDTO;
import org.main_java.deNexus_project.model.samplesDTO.SecurityIncidentSampleDTO;
import org.main_java.deNexus_project.util.ResourceNotFoundException;
import org.main_java.deNexus_project.repos.ChartRepository;
import org.main_java.deNexus_project.repos.ExperimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class ChartService {

    private final ChartRepository chartRepository;
    private final ExperimentRepository experimentRepository;
    private final ExperimentService experimentService;

    // ExecutorService con un pool de hilos
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

    @Autowired
    public ChartService(ChartRepository chartRepository, ExperimentRepository experimentRepository, ExperimentService experimentService) {
        this.chartRepository = chartRepository;
        this.experimentRepository = experimentRepository;
        this.experimentService = experimentService;
    }

    // Obtener todos los gráficos
    public List<ChartDTO> getAllCharts() {
        return chartRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Obtener gráfico por ID
    public ChartDTO getChartById(Long id) {
        Chart chart = chartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chart not found with id " + id));
        return mapToDTO(chart);
    }

    // Obtener gráficos asociados a un experimento específico
    public List<ChartDTO> getChartsByExperimentId(Long experimentId) {
        // Verificamos si existe el experimento
        Experiment experiment = experimentRepository.findById(experimentId)
                .orElseThrow(() -> new ResourceNotFoundException("Experiment not found with id " + experimentId));

        // Obtenemos los gráficos asociados a ese experimento
        List<Chart> charts = chartRepository.findByExperiment(experiment);

        return charts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Obtener gráficos de manera asíncrona para un experimento específico
    @Async
    public CompletableFuture<List<ChartDTO>> getChartsByExperimentIdAsync(Long experimentId) {
        return CompletableFuture.supplyAsync(() -> getChartsByExperimentId(experimentId));
    }

    // Actualizar un gráfico existente
    public ChartDTO updateChart(Long id, String chartType, Map<String, String> data) {
        Chart chart = chartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chart not found with id " + id));

        chart.setChartType(chartType);
        chart.setData(data);

        Chart updatedChart = chartRepository.save(chart);
        return mapToDTO(updatedChart);
    }

    // Eliminar un gráfico por ID
    public void deleteChart(Long id) {
        Chart chart = chartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chart not found with id " + id));
        chartRepository.delete(chart);
    }

    // Método para mapear de entidad Chart a DTO
    private ChartDTO mapToDTO(Chart chart) {
        return new ChartDTO(
                chart.getId(),
                chart.getChartType(),
                chart.getData(),
                chart.getExperiment().getId()
        );
    }

    // Método inverso: de DTO a entidad Chart (si es necesario)
    private Chart mapToEntity(ChartDTO chartDTO) {
        Chart chart = new Chart();
        chart.setId(chartDTO.getId());
        chart.setChartType(chartDTO.getChartType());
        chart.setData(chartDTO.getData());

        Experiment experiment = experimentRepository.findById(chartDTO.getExperimentId())
                .orElseThrow(() -> new ResourceNotFoundException("Experiment not found with id " + chartDTO.getExperimentId()));
        chart.setExperiment(experiment);

        return chart;
    }

    @Async
    @Transactional
    public CompletableFuture<Void> generateChartsConcurrently() {
        try {
            // Llamamos al metodo findAllAsync de ExperimentService para obtener todos los experimentos de manera asíncrona
            CompletableFuture<List<ExperimentDTO>> experimentsFuture = experimentService.findAllAsync();

            // Esperamos a que la lista de experimentos esté disponible
            List<ExperimentDTO> experiments = experimentsFuture.get();

            // Procesamos cada experimento en paralelo con una espera de 10 segundos entre actualizaciones
            List<Future<?>> futures = experiments.stream()
                    .map(experiment -> scheduledExecutorService.schedule(() -> processExperiment(experiment), 10, TimeUnit.SECONDS))
                    .collect(Collectors.toList());

            // Esperar que todos los experimentos se procesen
            for (Future<?> future : futures) {
                future.get();  // Bloquear hasta que todas las tareas hayan finalizado
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();  // Manejar las excepciones de concurrencia
        }

        return CompletableFuture.completedFuture(null);
    }

    // Metodo para procesar un experimento y asignar gráficos
    private void processExperiment(ExperimentDTO experimentDTO) {
        System.out.println("Procesando experimento: " + experimentDTO.getExperimentName());

        // Obtener las muestras procesadas del experimento (samples se manejan como parte del experimento)
        Map<String, String> sampleData = getSampleDataForExperiment(experimentDTO);

        // Crear gráfico para este experimento
        createChart(experimentDTO.getId(), "Bar", sampleData);

        // Simulación de actualización del gráfico en el frontend, esperando 10 segundos
        try {
            Thread.sleep(10000);  // Espera de 10 segundos antes de continuar con el siguiente experimento
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getSampleDataForExperiment(ExperimentDTO experimentDTO) {
        // Cada entrada del Map será el ID de la muestra y un mapa de atributos clave-valor de esa muestra
        return experimentDTO.getSamples().stream()
                .collect(Collectors.toMap(
                        sample -> "Sample" + sample.getId(),  // La clave principal será el ID de la muestra
                        sample -> {
                            Map<String, String> sampleAttributes = new HashMap<>();

                            // Filtrar por tipo de SampleDTO usando instanceof y procesar cada tipo
                            if (sample instanceof CyberAttackSampleDTO) {
                                CyberAttackSampleDTO cyberSample = (CyberAttackSampleDTO) sample;
                                sampleAttributes.put("Author", cyberSample.getAuthor());
                                sampleAttributes.put("Target", cyberSample.getTarget());
                                sampleAttributes.put("Description", cyberSample.getDescription());
                                sampleAttributes.put("AttackType", cyberSample.getAttackType());
                                sampleAttributes.put("TargetClassification", cyberSample.getTargetClassification());
                                sampleAttributes.put("AttackClassification", cyberSample.getAttackClassification());
                                sampleAttributes.put("Link", cyberSample.getLink());
                                sampleAttributes.put("Tags", String.join(",", cyberSample.getTags()));  // Convertimos lista a String
                                sampleAttributes.put("Regions", String.join(",", cyberSample.getRegions()));  // Convertimos lista a String
                            } else if (sample instanceof SecurityIncidentSampleDTO) {
                                SecurityIncidentSampleDTO securitySample = (SecurityIncidentSampleDTO) sample;
                                sampleAttributes.put("EventDescription", securitySample.getEventDescription());
                                sampleAttributes.put("Actor", securitySample.getActor());
                                sampleAttributes.put("ActorType", securitySample.getActorType());
                                sampleAttributes.put("EventType", securitySample.getEventType());
                                sampleAttributes.put("Organization", securitySample.getOrganization());
                                sampleAttributes.put("EventSubtype", securitySample.getEventSubtype());
                                sampleAttributes.put("Motive", securitySample.getMotive());
                                sampleAttributes.put("EventSource", securitySample.getEventSource());
                                sampleAttributes.put("IndustryCode", securitySample.getIndustryCode());
                                sampleAttributes.put("Regions", String.join(",", securitySample.getRegions()));  // Convertimos lista a String
                            }

                            // Unir todos los atributos en una única cadena serializada
                            return sampleAttributes.entrySet().stream()
                                    .map(e -> e.getKey() + "=" + e.getValue())
                                    .collect(Collectors.joining(", "));
                        }
                ));
    }


    // Crear un nuevo gráfico asociado a un experimento
    public ChartDTO createChart(Long experimentId, String chartType, Map<String, String> data) {
        Experiment experiment = experimentRepository.findById(experimentId)
                .orElseThrow(() -> new ResourceNotFoundException("Experiment not found with id " + experimentId));

        Chart chart = new Chart();
        chart.setChartType(chartType);
        chart.setData(data);
        chart.setExperiment(experiment);

        Chart savedChart = chartRepository.save(chart);
        return mapToDTO(savedChart);
    }

}
