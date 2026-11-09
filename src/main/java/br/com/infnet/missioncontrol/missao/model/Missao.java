package br.com.infnet.missioncontrol.missao.model;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class Missao {
    private Long id;
    private String nome;
    private String destino;
    private Integer anoLancamento;
    private StatusMissao status;
    private List<String> tripulantes;

}
