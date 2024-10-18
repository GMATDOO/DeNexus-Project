package org.main_java.deNexus_project.domain.samples;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.main_java.deNexus_project.domain.Experiment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Entity
@Table(name= "Samples")
@MappedSuperclass
@Getter
@Setter
public abstract class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "experiment_id")
    private Experiment experiment;
}

