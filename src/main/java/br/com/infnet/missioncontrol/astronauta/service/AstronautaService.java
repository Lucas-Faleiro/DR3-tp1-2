package br.com.infnet.missioncontrol.astronauta.service;

import br.com.infnet.missioncontrol.astronauta.model.Astronauta;
import br.com.infnet.missioncontrol.astronauta.model.StatusAstronauta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AstronautaService {
    private final ArrayList<Astronauta> astronautas = new ArrayList<>();
    private Long proximoId = 4L;

    public AstronautaService() {
        astronautas.add(new Astronauta(1L, "Luquinhas da Galera", "Brasil", 28, 10,
                StatusAstronauta.EM_MISSAO));
        astronautas.add(new Astronauta(2L, "Joyce Girassol", "Turquia", 24, 3,
                StatusAstronauta.AFASTADO));
        astronautas.add(new Astronauta(3L, "Kora Mercedes", "Noruega", 37, 15,
                StatusAstronauta.DISPONIVEL));
    }

    public List<Astronauta> listar() {
        return astronautas;
    }

    public List<Astronauta> listarComFiltros(StatusAstronauta status) {
        return astronautas.stream().filter(a -> a.getStatus().equals(status)).toList();
    }

    public void salvar(Astronauta astronauta) {
        astronauta.setId(proximoId++);
        astronautas.add(astronauta);
    }

    public boolean remover(Long id) {
        return astronautas.removeIf(astronauta -> astronauta.getId().equals(id));
    }

    public Astronauta buscarPeloId(Long id) {
        return astronautas.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean existePorNome(String nome) {
        return astronautas.stream().anyMatch(n -> n.getNome().equalsIgnoreCase(nome));
    }

}
