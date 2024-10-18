package org.main_java.deNexus_project.model.samplesDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public abstract class SampleDTO {

    private Long id;

    @NotNull
    private Long experimentId;

    @NotNull
    private OffsetDateTime dateCreated;

}

