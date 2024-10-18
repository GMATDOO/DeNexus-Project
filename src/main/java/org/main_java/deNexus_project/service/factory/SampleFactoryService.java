package org.main_java.deNexus_project.service.factory;
import org.main_java.deNexus_project.domain.samples.CyberAttackSample;
import org.main_java.deNexus_project.domain.samples.Sample;
import org.main_java.deNexus_project.domain.samples.SecurityIncidentSample;
import org.main_java.deNexus_project.model.samplesDTO.CyberAttackSampleDTO;
import org.main_java.deNexus_project.model.samplesDTO.SampleDTO;
import org.main_java.deNexus_project.model.samplesDTO.SecurityIncidentSampleDTO;
import org.springframework.stereotype.Service;

@Service
public class SampleFactoryService {

    // Metodo para crear la entidad Sample a partir del DTO adecuado
    public Sample createSample(SampleDTO sampleDTO) {
        if (sampleDTO instanceof CyberAttackSampleDTO) {
            return createCyberAttackSample((CyberAttackSampleDTO) sampleDTO);
        } else if (sampleDTO instanceof SecurityIncidentSampleDTO) {
            return createSecurityIncidentSample((SecurityIncidentSampleDTO) sampleDTO);
        }
        throw new IllegalArgumentException("Tipo de muestra desconocido: " + sampleDTO.getClass().getSimpleName());
    }

    // Creación de un CyberAttackSample a partir de CyberAttackSampleDTO
    private CyberAttackSample createCyberAttackSample(CyberAttackSampleDTO sampleDTO) {
        CyberAttackSample cyberSample = new CyberAttackSample();
        cyberSample.setAuthor(sampleDTO.getAuthor());
        cyberSample.setTarget(sampleDTO.getTarget());
        cyberSample.setDescription(sampleDTO.getDescription());
        cyberSample.setAttackType(sampleDTO.getAttackType());
        cyberSample.setTargetClassification(sampleDTO.getTargetClassification());
        cyberSample.setAttackClassification(sampleDTO.getAttackClassification());
        cyberSample.setLink(sampleDTO.getLink());
        cyberSample.setTags(sampleDTO.getTags());
        cyberSample.setRegions(sampleDTO.getRegions());
        return cyberSample;
    }

    // Creación de un SecurityIncidentSample a partir de SecurityIncidentSampleDTO
    private SecurityIncidentSample createSecurityIncidentSample(SecurityIncidentSampleDTO sampleDTO) {
        SecurityIncidentSample securitySample = new SecurityIncidentSample();
        securitySample.setEventDescription(sampleDTO.getEventDescription());
        securitySample.setActor(sampleDTO.getActor());
        securitySample.setActorType(sampleDTO.getActorType());
        securitySample.setEventType(sampleDTO.getEventType());
        securitySample.setOrganization(sampleDTO.getOrganization());
        securitySample.setEventSubtype(sampleDTO.getEventSubtype());
        securitySample.setMotive(sampleDTO.getMotive());
        securitySample.setEventSource(sampleDTO.getEventSource());
        securitySample.setIndustryCode(sampleDTO.getIndustryCode());
        securitySample.setDnxId(sampleDTO.getDnxId());
        securitySample.setRegions(sampleDTO.getRegions());
        return securitySample;
    }
}

