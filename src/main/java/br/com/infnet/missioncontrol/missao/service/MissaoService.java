package br.com.infnet.missioncontrol.missao.service;

import br.com.infnet.missioncontrol.missao.model.Missao;
import br.com.infnet.missioncontrol.missao.model.StatusMissao;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//Fingir que é um singletron
@Service
public class MissaoService {
    private final ArrayList<Missao> missoes = new ArrayList<>() ;
    private final TesteService testeService;
    private Long proximoId = 4L;
    public MissaoService(TesteService testeService) {
        this.testeService = testeService;
        missoes.add(new Missao(1L, "Aurora Lunar", "Lua", 2028,
                StatusMissao.PLANEJADA, new ArrayList<>(List.of("Lia", "Mateus"))));

        missoes.add(new Missao(2L, "Vigia de Marte", "Marte", 2025,
                StatusMissao.EM_ANDAMENTO, new ArrayList<>(List.of("Nina"))));

        missoes.add(new Missao(3L, "Sonda Europa", "Europa", 2022,
                StatusMissao.CONCLUIDA, new ArrayList<>()));
    }

    public List<Missao> listarTodas() {

        return  missoes;
    }
    public List<Missao> filtrarPorStatus(StatusMissao status) {

        List<Missao> resultado = new ArrayList<>();
        for(Missao missao : missoes){
            if(missao.getStatus().equals(status)){
                resultado.add(missao);
            }
        }
        return resultado;
    }
    public Missao buscarPeloId(Long id) {
        for (Missao missao : missoes) {
            if (missao.getId().equals(id)) {
                return missao;
            }
        }
        return null;
    }
    public boolean remover(Long id){
        boolean removeu = false;
        for(int i=0; i<missoes.size(); i++){
            if(missoes.get(i).getId().equals(id)){
                missoes.remove(i);
                removeu = true;
            }
        }
        return removeu;
    }
    public void adicionar(Missao missao){
        missao.setId(proximoId++);
        missoes.add(missao);
    }
}
// new MissaoController()
