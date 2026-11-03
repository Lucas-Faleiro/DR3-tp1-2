package br.com.infnet.missioncontrol.controller;

import br.com.infnet.missioncontrol.model.ErrorResponse;
import br.com.infnet.missioncontrol.model.Missao;
import br.com.infnet.missioncontrol.model.StatusMissao;
import br.com.infnet.missioncontrol.service.MissaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissaoController {
    private final MissaoService missaoService;

    public MissaoController(MissaoService missaoService) {
        this.missaoService = missaoService;
    }
    @GetMapping("/{id}")
    public ResponseEntity buscarPeloId(@PathVariable Long id) {
        Missao missao = missaoService.buscarPeloId(id);
        if(missao != null) {
            return ResponseEntity.ok().body(missao);
        }else  {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public List<Missao> listar(@RequestParam(required = false) StatusMissao status) {
        if(status == null){
            return  missaoService.listarTodas();
        }
            return missaoService.filtrarPorStatus(status);
    }
    @PostMapping
    public ResponseEntity salvar(@RequestBody Missao missao) {
        missaoService.adicionar(missao);
        return ResponseEntity.status(HttpStatus.CREATED).body(missao);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id){
        if(id <= 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse("ID invalido"));
        }
        boolean removeu = missaoService.remover(id);
        if(removeu) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Id nao localizado"));
    }
    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long  id,
                                                   @RequestBody Missao entrada){
        if(id <= 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Id invalido"));
        }
        Missao existente = null;
        for(Missao missao : missaoService.listarTodas()){
            if(missao.getId().equals(id)){
                existente = missao;
                break;
            }
        }
        if(existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Missao nao localizada"));
        }
        existente.setNome(entrada.getNome());
        existente.setDestino(entrada.getDestino());
        existente.setAnoLancamento(entrada.getAnoLancamento());
        existente.setStatus(entrada.getStatus());
        existente.setTripulantes(entrada.getTripulantes());
        return ResponseEntity.ok().body(existente);

    }
}


/*
 Missao m = new MissaoService() -> construtor -> Cria uma nova Lista com 3 itens.
 deletar 1 missao de m


 Missao n = new MissaoService() -> construtor -> Cria uma nova Lista com 3 itens.
 n -> 3 itens
 ms = new MissaoService();
 new MissaoController(ms); -> MissaoService

 missaoService.listar(lista)

Bean Gerenciado Pelo Spring
[
t(TesteService)
z
]
t = new TesteService()
z = new MissaoService(t)
m = new MissaoController(z)

m.listar()
Design PAttern -> Injeção dependência
IoC

 */








