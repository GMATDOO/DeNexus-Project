package org.main_java.deNexus_project.domain.samples;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.main_java.deNexus_project.domain.Credenciales;
import org.main_java.deNexus_project.domain.Experiment;
import org.main_java.deNexus_project.domain.Rol;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Set;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CyberAttackSample extends Sample {

    // Campos específicos de CyberAttackSample

    private String author;

    private String target;

    private String description;

    private String attackType;

    private String targetClassification;

    private String attackClassification;

    private String link;

    private String tags;

    @ElementCollection
    @CollectionTable(name = "cyber_attack_regions", joinColumns = @JoinColumn(name = "sample_id"))
    @Column(name = "region")
    private Set<String> regions;
}
