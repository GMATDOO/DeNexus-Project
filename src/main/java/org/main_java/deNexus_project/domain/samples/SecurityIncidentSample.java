package org.main_java.deNexus_project.domain.samples;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.main_java.deNexus_project.domain.Credenciales;
import org.main_java.deNexus_project.domain.Rol;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Set;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class SecurityIncidentSample extends Sample {

    // Campos específicos de SecurityIncidentSample
    private String eventDescription;

    private String actor;

    private String actorType;

    private String eventType;

    private String organization;

    private String eventSubtype;

    private String motive;

    private String eventSource;

    private String industryCode;

    private Integer dnxId;

    @ElementCollection
    @CollectionTable(name = "security_incident_regions", joinColumns = @JoinColumn(name = "sample_id"))
    @Column(name = "region")
    private Set<String> regions;

    @ElementCollection
    @CollectionTable(name = "impact_types", joinColumns = @JoinColumn(name = "sample_id"))
    @Column(name = "impact_type")
    private Set<String> impactTypes;

}

