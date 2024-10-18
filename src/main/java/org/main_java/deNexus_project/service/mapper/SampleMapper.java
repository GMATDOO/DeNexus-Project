package org.main_java.deNexus_project.service.mapper;

import org.main_java.deNexus_project.domain.samples.CyberAttackSample;
import org.main_java.deNexus_project.domain.samples.SecurityIncidentSample;
import org.main_java.deNexus_project.model.samplesDTO.CyberAttackSampleDTO;
import org.main_java.deNexus_project.model.samplesDTO.SecurityIncidentSampleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface SampleMapper {

    SampleMapper INSTANCE = Mappers.getMapper(SampleMapper.class);

    CyberAttackSampleDTO cyberAttackSampleToDTO(CyberAttackSample sample);

    SecurityIncidentSampleDTO securityIncidentSampleToDTO(SecurityIncidentSample sample);

    CyberAttackSample cyberAttackSampleDTOToSample(CyberAttackSampleDTO sampleDTO);

    SecurityIncidentSample securityIncidentSampleDTOToSample(SecurityIncidentSampleDTO sampleDTO);
}

