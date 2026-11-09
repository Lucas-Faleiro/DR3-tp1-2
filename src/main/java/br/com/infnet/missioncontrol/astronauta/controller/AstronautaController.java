package br.com.infnet.missioncontrol.astronauta.controller;

import br.com.infnet.missioncontrol.astronauta.model.Astronauta;
import br.com.infnet.missioncontrol.astronauta.model.StatusAstronauta;
import br.com.infnet.missioncontrol.astronauta.service.AstronautaService;
import br.com.infnet.missioncontrol.missao.model.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/astronautas")
public class AstronautaController {
    private final AstronautaService astronautaService;

    public AstronautaController(AstronautaService astronautaService) {
        this.astronautaService = astronautaService;
    }

    @GetMapping
    public List<Astronauta> listar(@RequestParam(required = false) StatusAstronauta status) {
        if (status == null) {
            return astronautaService.listar();
        }

        return astronautaService.listarComFiltros(status);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarPeloId(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("O id inserido é inválido"));
        }
        Astronauta astronauta = astronautaService.buscarPeloId(id);
        if (astronauta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Astronauta não encontrado"));
        }
        return ResponseEntity.ok().body(astronauta);
    }

    @PostMapping
    public ResponseEntity salvar(@Valid @RequestBody Astronauta astronauta) {
        if (astronautaService.existePorNome(astronauta.getNome())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Já existe um astronauta com esse nome"));
        }

        astronautaService.salvar(astronauta);
        return ResponseEntity.status(HttpStatus.CREATED).body(astronauta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("O id inserido é inválido"));
        }

        boolean removido = astronautaService.remover(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Astronauta não encontrado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @Valid @RequestBody Astronauta entrada) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("O id inserido é inválido"));
        }

        Astronauta existente =
                astronautaService.listar().stream().filter(astronauta -> astronauta.getId().equals(id)).findFirst().orElse(null);

        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Astronauta não foi encontrado"));
        }

        existente.setNome(entrada.getNome());
        existente.setPais(entrada.getPais());
        existente.setIdade(entrada.getIdade());
        existente.setAnosExperiencia(entrada.getAnosExperiencia());
        existente.setStatus(entrada.getStatus());
        return ResponseEntity.ok().body(existente);

    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> tratarErroValidacao(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(mensagem));
    }
}
