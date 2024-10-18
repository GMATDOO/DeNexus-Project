package org.main_java.deNexus_project.model.samplesDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SecurityIncidentSampleDTO extends SampleDTO {

    @NotNull
    @Size(max = 255)
    private String eventDescription;

    @NotNull
    @Size(max = 255)
    private String actor;

    private String actorType;
    private String eventType;
    private String organization;
    private String eventSubtype;
    private String motive;
    private String eventSource;
    private String industryCode;
    private Integer dnxId;

    private Set<String> regions;
    private Set<String> impactTypes;

    // Otros campos específicos si es necesario
}
