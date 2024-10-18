package org.main_java.deNexus_project.service;

import org.main_java.deNexus_project.domain.Experiment;
import org.main_java.deNexus_project.domain.samples.CyberAttackSample;
import org.main_java.deNexus_project.domain.samples.Sample;
import org.main_java.deNexus_project.domain.samples.SecurityIncidentSample;
import org.main_java.deNexus_project.repos.ExperimentRepository;
import org.main_java.deNexus_project.service.factory.SampleFactoryService;
import org.main_java.deNexus_project.util.SampleNotFoundException;
import org.main_java.deNexus_project.model.samplesDTO.CyberAttackSampleDTO;
import org.main_java.deNexus_project.model.samplesDTO.SecurityIncidentSampleDTO;
import org.main_java.deNexus_project.model.samplesDTO.SampleDTO;
import org.main_java.deNexus_project.repos.samples_repos.CyberAttackSampleRepository;
import org.main_java.deNexus_project.repos.samples_repos.SecurityIncidentSampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class SampleService {

    private final CyberAttackSampleRepository cyberAttackSampleRepository;
    private final SecurityIncidentSampleRepository securityIncidentSampleRepository;
    private final ExperimentRepository experimentRepository;
    private final SampleFactoryService sampleFactoryService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    public SampleService(
            CyberAttackSampleRepository cyberAttackSampleRepository,
            SecurityIncidentSampleRepository securityIncidentSampleRepository,
            ExperimentRepository experimentRepository,
            SampleFactoryService sampleFactoryService) {
        this.cyberAttackSampleRepository = cyberAttackSampleRepository;
        this.securityIncidentSampleRepository = securityIncidentSampleRepository;
        this.experimentRepository = experimentRepository;
        this.sampleFactoryService = sampleFactoryService;
    }


    // Obtiene un Sample específico por ID
    public SampleDTO get(Long id) {
        // Intentamos obtener el Sample de cada repositorio
        Optional<CyberAttackSample> cyberAttackSample = cyberAttackSampleRepository.findById(id);
        if (cyberAttackSample.isPresent()) {
            return cyberAttackSampleToDTO(cyberAttackSample.get());
        }

        Optional<SecurityIncidentSample> securityIncidentSample = securityIncidentSampleRepository.findById(id);
        if (securityIncidentSample.isPresent()) {
            return securityIncidentSampleToDTO(securityIncidentSample.get());
        }

        throw new SampleNotFoundException(id);
    }

    // Crea un nuevo Sample
    public Long create(SampleDTO sampleDTO) {
        if (sampleDTO instanceof CyberAttackSampleDTO) {
            CyberAttackSample sample = cyberAttackSampleDTOToEntity((CyberAttackSampleDTO) sampleDTO);
            CyberAttackSample savedSample = cyberAttackSampleRepository.save(sample);
            return savedSample.getId();
        } else if (sampleDTO instanceof SecurityIncidentSampleDTO) {
            SecurityIncidentSample sample = securityIncidentSampleDTOToEntity((SecurityIncidentSampleDTO) sampleDTO);
            SecurityIncidentSample savedSample = securityIncidentSampleRepository.save(sample);
            return savedSample.getId();
        } else {
            throw new IllegalArgumentException("Unknown SampleDTO type");
        }
    }

    // Actualiza un Sample existente
    public void update(Long id, SampleDTO sampleDTO) {
        if (sampleDTO instanceof CyberAttackSampleDTO) {
            CyberAttackSample existingSample = cyberAttackSampleRepository.findById(id)
                    .orElseThrow(() -> new SampleNotFoundException(id));
            CyberAttackSample updatedSample = cyberAttackSampleDTOToEntity((CyberAttackSampleDTO) sampleDTO);
            updatedSample.setId(existingSample.getId());
            cyberAttackSampleRepository.save(updatedSample);
        } else if (sampleDTO instanceof SecurityIncidentSampleDTO) {
            SecurityIncidentSample existingSample = securityIncidentSampleRepository.findById(id)
                    .orElseThrow(() -> new SampleNotFoundException(id));
            SecurityIncidentSample updatedSample = securityIncidentSampleDTOToEntity((SecurityIncidentSampleDTO) sampleDTO);
            updatedSample.setId(existingSample.getId());
            securityIncidentSampleRepository.save(updatedSample);
        } else {
            throw new IllegalArgumentException("Unknown SampleDTO type");
        }
    }

    // Elimina un Sample por su ID
    public void delete(Long id) {
        boolean existsInCyberAttack = cyberAttackSampleRepository.existsById(id);
        if (existsInCyberAttack) {
            cyberAttackSampleRepository.deleteById(id);
            return;
        }
        boolean existsInSecurityIncident = securityIncidentSampleRepository.existsById(id);
        if (existsInSecurityIncident) {
            securityIncidentSampleRepository.deleteById(id);
            return;
        }
        throw new SampleNotFoundException(id);
    }

    // Métodos auxiliares para mapear entre entidades y DTOs sin usar la interfaz mapper

    // Mapea CyberAttackSample a CyberAttackSampleDTO
    private CyberAttackSampleDTO cyberAttackSampleToDTO(CyberAttackSample sample) {
        CyberAttackSampleDTO dto = new CyberAttackSampleDTO();
        dto.setId(sample.getId());
        dto.setAuthor(sample.getAuthor());
        dto.setTarget(sample.getTarget());
        dto.setDescription(sample.getDescription());
        dto.setAttackType(sample.getAttackType());
        dto.setTargetClassification(sample.getTargetClassification());
        dto.setAttackClassification(sample.getAttackClassification());
        dto.setLink(sample.getLink());
        dto.setTags(sample.getTags());
        dto.setRegions(sample.getRegions());
        // Añade otros campos según sea necesario
        return dto;
    }

    // Mapea CyberAttackSampleDTO a CyberAttackSample
    private CyberAttackSample cyberAttackSampleDTOToEntity(CyberAttackSampleDTO dto) {
        CyberAttackSample sample = new CyberAttackSample();
        sample.setAuthor(dto.getAuthor());
        sample.setTarget(dto.getTarget());
        sample.setDescription(dto.getDescription());
        sample.setAttackType(dto.getAttackType());
        sample.setTargetClassification(dto.getTargetClassification());
        sample.setAttackClassification(dto.getAttackClassification());
        sample.setLink(dto.getLink());
        sample.setTags(dto.getTags());
        sample.setRegions(dto.getRegions());
        // Añade otros campos según sea necesario
        return sample;
    }

    // Mapea SecurityIncidentSample a SecurityIncidentSampleDTO
    private SecurityIncidentSampleDTO securityIncidentSampleToDTO(SecurityIncidentSample sample) {
        SecurityIncidentSampleDTO dto = new SecurityIncidentSampleDTO();
        dto.setId(sample.getId());
        dto.setEventDescription(sample.getEventDescription());
        dto.setActor(sample.getActor());
        dto.setActorType(sample.getActorType());
        dto.setEventType(sample.getEventType());
        dto.setOrganization(sample.getOrganization());
        dto.setEventSubtype(sample.getEventSubtype());
        dto.setMotive(sample.getMotive());
        dto.setEventSource(sample.getEventSource());
        dto.setIndustryCode(sample.getIndustryCode());
        dto.setDnxId(sample.getDnxId());
        dto.setRegions(sample.getRegions());
        dto.setImpactTypes(sample.getImpactTypes());
        // Añade otros campos según sea necesario
        return dto;
    }

    // Mapea SecurityIncidentSampleDTO a SecurityIncidentSample
    private SecurityIncidentSample securityIncidentSampleDTOToEntity(SecurityIncidentSampleDTO dto) {
        SecurityIncidentSample sample = new SecurityIncidentSample();
        sample.setEventDescription(dto.getEventDescription());
        sample.setActor(dto.getActor());
        sample.setActorType(dto.getActorType());
        sample.setEventType(dto.getEventType());
        sample.setOrganization(dto.getOrganization());
        sample.setEventSubtype(dto.getEventSubtype());
        sample.setMotive(dto.getMotive());
        sample.setEventSource(dto.getEventSource());
        sample.setIndustryCode(dto.getIndustryCode());
        sample.setDnxId(dto.getDnxId());
        sample.setRegions(dto.getRegions());
        sample.setImpactTypes(dto.getImpactTypes());
        // Añade otros campos según sea necesario
        return sample;
    }

    @Async
    @Transactional
    public CompletableFuture<Void> processSamplesAsync(List<SampleDTO> samples) {
        int batchSize = 100;  // Tamaño del bloque de muestras para cada experimento
        List<Future<Void>> futures = new ArrayList<>();

        for (int i = 0; i < samples.size(); i += batchSize) {
            List<SampleDTO> batch = samples.subList(i, Math.min(i + batchSize, samples.size()));

            // Enviar el procesamiento del bloque a un thread pool
            Future<Void> future = executorService.submit(() -> {
                processBatch(batch);
                return null;
            });

            futures.add(future);
        }

        // Esperamos a que todos los bloques se procesen antes de devolver la respuesta
        for (Future<Void> future : futures) {
            try {
                future.get(); // Bloquea hasta que el procesamiento del batch se complete
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace(); // Manejar la excepción según sea necesario
            }
        }

        return CompletableFuture.completedFuture(null);
    }


    private synchronized void processBatch(List<SampleDTO> samples) {
        Experiment experiment = new Experiment();
        experiment.setExperimentName("Experiment " + System.currentTimeMillis()); // Generar un nombre único
        experiment.setStartDate(java.time.LocalDateTime.now());
        experiment.setEndDate(java.time.LocalDateTime.now().plusDays(30)); // Duración del experimento de ejemplo

        // Guardar el experimento en la base de datos
        experimentRepository.save(experiment);

        // Procesar cada muestra y asignarla al experimento
        samples.forEach(sampleDTO -> {
            Sample sample = sampleFactoryService.createSample(sampleDTO);
            sample.setExperiment(experiment);  // Asignar el experimento a la muestra

            // Verificamos el tipo de muestra para guardarla en el repositorio adecuado
            if (sample instanceof CyberAttackSample) {
                cyberAttackSampleRepository.save((CyberAttackSample) sample);
            } else if (sample instanceof SecurityIncidentSample) {
                securityIncidentSampleRepository.save((SecurityIncidentSample) sample);
            }
        });
    }

    @Transactional
    public List<SampleDTO> findAll() {
        ExecutorService executorService = Executors.newFixedThreadPool(1000); // Pool de 1000 hilos
        List<SampleDTO> allSamples = new ArrayList<>();
        Object lock = new Object(); // Bloqueo para sincronización

        try {
            // Tarea para obtener y procesar muestras de CyberAttackSample
            Future<List<SampleDTO>> cyberAttackTask = executorService.submit(() -> {
                List<CyberAttackSample> cyberAttackSamples = cyberAttackSampleRepository.findAll();
                List<SampleDTO> cyberAttackSampleDTOs = cyberAttackSamples.stream()
                        .map(this::cyberAttackSampleToDTO)
                        .collect(Collectors.toList());
                synchronized (lock) {
                    allSamples.addAll(cyberAttackSampleDTOs); // Sincronizamos el acceso a la lista compartida
                }
                return cyberAttackSampleDTOs;
            });

            // Tarea para obtener y procesar muestras de SecurityIncidentSample
            Future<List<SampleDTO>> securityIncidentTask = executorService.submit(() -> {
                List<SecurityIncidentSample> securityIncidentSamples = securityIncidentSampleRepository.findAll();
                List<SampleDTO> securityIncidentSampleDTOs = securityIncidentSamples.stream()
                        .map(this::securityIncidentSampleToDTO)
                        .collect(Collectors.toList());
                synchronized (lock) {
                    allSamples.addAll(securityIncidentSampleDTOs); // Sincronizamos el acceso a la lista compartida
                }
                return securityIncidentSampleDTOs;
            });

            // Esperamos a que ambas tareas finalicen
            cyberAttackTask.get();
            securityIncidentTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown(); // Apagamos el pool de hilos
        }

        return allSamples; // Retornamos la lista con todas las muestras
    }

    public SampleDTO sampleToDTO(Sample sample) {
        if (sample instanceof CyberAttackSample) {
            return cyberAttackSampleToDTO((CyberAttackSample) sample);  // Mapeamos a CyberAttackSampleDTO
        } else if (sample instanceof SecurityIncidentSample) {
            return securityIncidentSampleToDTO((SecurityIncidentSample) sample);  // Mapeamos a SecurityIncidentSampleDTO
        }
        throw new IllegalArgumentException("Tipo de muestra desconocido: " + sample.getClass().getSimpleName());
    }
}
