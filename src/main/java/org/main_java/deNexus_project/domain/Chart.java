package org.main_java.deNexus_project.domain;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.util.Map;

/**
 * Entidad que representa un gráfico basado en experimentos que contienen muestras.
 */
@Entity
@Table(name = "charts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String chartType;

    // Mapa que ahora contiene claves String y valores String
    @ElementCollection
    @CollectionTable(name = "chart_data", joinColumns = @JoinColumn(name = "chart_id"))
    @MapKeyColumn(name = "data_key")
    @Column(name = "data_value")
    private Map<String, String> data;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "experiment_id", nullable = false)
    private Experiment experiment;

}

