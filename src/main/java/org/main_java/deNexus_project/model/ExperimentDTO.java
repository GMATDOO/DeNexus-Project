package org.main_java.deNexus_project.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.main_java.deNexus_project.model.samplesDTO.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ExperimentDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String experimentName;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private List<SampleDTO> samples;
}

