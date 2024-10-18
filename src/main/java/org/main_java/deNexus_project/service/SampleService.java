package org.main_java.deNexus_project.service;

import org.main_java.deNexus_project.domain.samples.CyberAttackSample;
import org.main_java.deNexus_project.domain.samples.SecurityIncidentSample;
import org.main_java.deNexus_project.util.NotFoundException;
import org.main_java.deNexus_project.service.mapper.SampleMapper;
import org.main_java.deNexus_project.model.samplesDTO.CyberAttackSampleDTO;
import org.main_java.deNexus_project.model.samplesDTO.SecurityIncidentSampleDTO;
import org.main_java.deNexus_project.model.samplesDTO.SampleDTO;
import org.main_java.deNexus_project.repos.samples_repos.CyberAttackSampleRepository;
import org.main_java.deNexus_project.repos.samples_repos.SecurityIncidentSampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SampleService {

    private final CyberAttackSampleRepository cyberAttackSampleRepository;
    private final SecurityIncidentSampleRepository securityIncidentSampleRepository;
    private final SampleMapper sampleMapper;

    @Autowired
    public SampleService(
            CyberAttackSampleRepository cyberAttackSampleRepository,
            SecurityIncidentSampleRepository securityIncidentSampleRepository,
            SampleMapper sampleMapper) {
        this.cyberAttackSampleRepository = cyberAttackSampleRepository;
        this.securityIncidentSampleRepository = securityIncidentSampleRepository;
        this.sampleMapper = sampleMapper;
    }

    // Retorna todas las muestras
    public List<SampleDTO> findAll() {
        List<CyberAttackSample> cyberAttackSamples = cyberAttackSampleRepository.findAll();
        List<SecurityIncidentSample> securityIncidentSamples = securityIncidentSampleRepository.findAll();

        List<SampleDTO> cyberAttackSampleDTOs = cyberAttackSamples.stream()
                .map(sampleMapper::cyberAttackSampleToDTO)
                .collect(Collectors.toList());

        List<SampleDTO> securityIncidentSampleDTOs = securityIncidentSamples.stream()
                .map(sampleMapper::securityIncidentSampleToDTO)
                .collect(Collectors.toList());

        List<SampleDTO> allSamples = new ArrayList<>();
        allSamples.addAll(cyberAttackSampleDTOs);
        allSamples.addAll(securityIncidentSampleDTOs);

        return allSamples;
    }

    // Obtiene un Sample específico por ID
    public SampleDTO get(Long id) {
        // Intentamos obtener el Sample de cada repositorio
        Optional<CyberAttackSample> cyberAttackSample = cyberAttackSampleRepository.findById(id);
        if (cyberAttackSample.isPresent()) {
            return sampleMapper.cyberAttackSampleToDTO(cyberAttackSample.get());
        }

        Optional<SecurityIncidentSample> securityIncidentSample = securityIncidentSampleRepository.findById(id);
        if (securityIncidentSample.isPresent()) {
            return sampleMapper.securityIncidentSampleToDTO(securityIncidentSample.get());
        }

        throw new NotFoundException();
    }

    // Crea un nuevo Sample
    public Long create(SampleDTO sampleDTO) {
        if (sampleDTO instanceof CyberAttackSampleDTO) {
            CyberAttackSample sample = sampleMapper.cyberAttackSampleDTOToSample((CyberAttackSampleDTO) sampleDTO);
            CyberAttackSample savedSample = cyberAttackSampleRepository.save(sample);
            return savedSample.getId();
        } else if (sampleDTO instanceof SecurityIncidentSampleDTO) {
            SecurityIncidentSample sample = sampleMapper.securityIncidentSampleDTOToSample((SecurityIncidentSampleDTO) sampleDTO);
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
                    .orElseThrow(NotFoundException::new);
            CyberAttackSample updatedSample = sampleMapper.cyberAttackSampleDTOToSample((CyberAttackSampleDTO) sampleDTO);
            updatedSample.setId(existingSample.getId());
            cyberAttackSampleRepository.save(updatedSample);
        } else if (sampleDTO instanceof SecurityIncidentSampleDTO) {
            SecurityIncidentSample existingSample = securityIncidentSampleRepository.findById(id)
                    .orElseThrow(NotFoundException::new);
            SecurityIncidentSample updatedSample = sampleMapper.securityIncidentSampleDTOToSample((SecurityIncidentSampleDTO) sampleDTO);
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
        throw new NotFoundException();
    }
}
