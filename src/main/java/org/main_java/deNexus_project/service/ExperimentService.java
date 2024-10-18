package org.main_java.deNexus_project.service;

import org.main_java.deNexus_project.domain.Experiment;
import org.main_java.deNexus_project.domain.samples.Sample;
import org.main_java.deNexus_project.model.ExperimentDTO;
import org.main_java.deNexus_project.model.samplesDTO.SampleDTO;
import org.main_java.deNexus_project.repos.ExperimentRepository;
import org.main_java.deNexus_project.util.NotFoundException;
import org.main_java.deNexus_project.repos.samples_repos.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class ExperimentService {

    private final ExperimentRepository experimentRepository;

    private final SampleRepository sampleRepository;

    @Autowired
    private SampleService sampleService;


    public ExperimentService(final ExperimentRepository experimentRepository, final SampleRepository sampleRepository) {
        this.experimentRepository = experimentRepository;
        this.sampleRepository = sampleRepository;
    }

    public List<ExperimentDTO> findAll() {
        final List<Experiment> experiments = experimentRepository.findAll(Sort.by("id"));
        return experiments.stream()
                .map(experiment -> mapToDTO(experiment, new ExperimentDTO()))
                .collect(Collectors.toList());
    }

    public ExperimentDTO get(final Long id) {
        return experimentRepository.findById(id)
                .map(experiment -> mapToDTO(experiment, new ExperimentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ExperimentDTO experimentDTO) {
        Experiment experiment = new Experiment();
        mapToEntity(experimentDTO, experiment);
        return experimentRepository.save(experiment).getId();
    }

    public void update(final Long id, final ExperimentDTO experimentDTO) {
        Experiment experiment = experimentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(experimentDTO, experiment);
        experimentRepository.save(experiment);
    }

    public void delete(final Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }
        experimentRepository.deleteById(id);
    }

    // Mapeo de entidad a DTO.md
    private ExperimentDTO mapToDTO(final Experiment experiment, final ExperimentDTO experimentDTO) {
        experimentDTO.setId(experiment.getId());
        experimentDTO.setExperimentName(experiment.getExperimentName()); // Asignamos el nombre a el experimento
        experimentDTO.setStartDate(experiment.getStartDate());
        experimentDTO.setEndDate(experiment.getEndDate());
        return experimentDTO;
    }

    // Mapeo de DTO.md a entidad
    private Experiment mapToEntity(final ExperimentDTO experimentDTO, final Experiment experiment) {
        experiment.setExperimentName(experimentDTO.getExperimentName());
        experiment.setStartDate(experimentDTO.getStartDate());
        experiment.setEndDate(experimentDTO.getEndDate());
        return experiment;
    }

    public ExperimentDTO mapToDTO(final Experiment experiment) {
        ExperimentDTO experimentDTO = new ExperimentDTO();
        experimentDTO.setId(experiment.getId());
        experimentDTO.setExperimentName(experiment.getExperimentName());
        experimentDTO.setStartDate(experiment.getStartDate());
        experimentDTO.setEndDate(experiment.getEndDate());
        return experimentDTO;
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<ExperimentDTO>> findAllAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<Experiment> experiments = experimentRepository.findAll();
            return experiments.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        });
    }

    // Obtener todas las muestras asociadas a un experimento específico
    public List<SampleDTO> getSamplesByExperimentId(Long experimentId) {
        // Obtener todas las muestras asociadas al experimento
        List<Sample> samples = sampleRepository.findByExperimentId(experimentId);

        // Convertir las muestras a DTOs para devolverlas
        return samples.stream()
                .map(sample -> sampleService.sampleToDTO(sample))  // Usamos sampleService para mapear a DTO
                .collect(Collectors.toList());
    }


    // Obtener todas las muestras asociadas a un experimento específico (Async)
    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<SampleDTO>> getSamplesByExperimentIdAsync(Long experimentId) {
        return CompletableFuture.supplyAsync(() -> {
            List<Sample> samples = sampleRepository.findByExperimentId(experimentId);
            return samples.stream()
                    .map(sample -> sampleService.sampleToDTO(sample))
                    .collect(Collectors.toList());
        });
    }
}
