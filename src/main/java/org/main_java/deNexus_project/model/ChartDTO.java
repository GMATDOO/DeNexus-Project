package org.main_java.deNexus_project.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * Data Transfer Object para la entidad Chart.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChartDTO {

    private Long id;

    @NotNull(message = "El tipo de gráfico no puede ser nulo")
    @Size(max = 50, message = "El tipo de gráfico no puede exceder los 50 caracteres")
    private String chartType;

    @NotNull(message = "Los datos del gráfico no pueden ser nulos")
    private Map<String, String> data;

    @NotNull(message = "El ID del experimento no puede ser nulo")
    private Long experimentId;

    // Puedes agregar más campos o métodos si es necesario
}
