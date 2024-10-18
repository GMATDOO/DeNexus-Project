package org.main_java.deNexus_project.model.samplesDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CyberAttackSampleDTO extends SampleDTO {

    @NotNull
    @Size(max = 255)
    private String author;

    @NotNull
    @Size(max = 255)
    private String target;

    private String description;
    private String attackType;
    private String targetClassification;
    private String attackClassification;
    private String link;
    private String tags;

    private Set<String> regions;

}

