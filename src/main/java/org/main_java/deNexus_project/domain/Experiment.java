package org.main_java.deNexus_project.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.main_java.deNexus_project.domain.samples.Sample;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "Experimentos")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Experiment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String experimentName;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "experiment", cascade = CascadeType.ALL)
    private List<Sample> samples = new ArrayList<>();

}