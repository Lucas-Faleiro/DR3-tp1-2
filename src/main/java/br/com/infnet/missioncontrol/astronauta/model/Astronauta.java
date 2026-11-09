package br.com.infnet.missioncontrol.astronauta.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Astronauta {
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O país é obrigatório")
    private String pais;

    @NotNull(message = "A idade de ser maior ou igual a 18")
    @Min(value = 18, message = "A idade de ser maior ou igual a 18")
    private Integer idade;

    @NotNull(message = "Anos de experiência deve ser maior ou igual a 0")
    @Min(value = 0, message = "Anos de experiência deve ser maior ou igual a 0")
    private Integer anosExperiencia;

    @NotNull(message = "Status é obrigatório")
    private StatusAstronauta status;
}
