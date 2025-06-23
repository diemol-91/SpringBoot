package com.diegom.spring_boot.controladores;


import com.diegom.spring_boot.Servicios.BoletaService;
import com.diegom.spring_boot.Servicios.ClienteService;
import com.diegom.spring_boot.dto.BoletaDTO;
import com.diegom.spring_boot.model.Boleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boletas")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<BoletaDTO>> listar() {
        List<BoletaDTO> lista = boletaService.ListaBoleta()
                .stream().map(BoletaDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoletaDTO> buscar(@PathVariable Long id) {
        return boletaService.BuscaId(id)
                .map(b -> new ResponseEntity<>(new BoletaDTO(b), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> insertar(@RequestBody BoletaDTO dto) {
        try {
            Boleta boleta = new Boleta();
            boleta.setFechaEmision(dto.getFecha()); // Corrige el nombre del setter aquí
            boleta.setCliente(clienteService.BuscaId(dto.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
            Boleta guardada = boletaService.guardar(boleta);
            return new ResponseEntity<>(new BoletaDTO(guardada), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody BoletaDTO dto) {
        Optional<Boleta> optionalBoleta = boletaService.BuscaId(id);

        if (optionalBoleta.isPresent()) {
            Boleta boleta = optionalBoleta.get();

            boleta.setFechaEmision(dto.getFecha());
            boleta.setCliente(clienteService.BuscaId(dto.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));

            Boleta actualizado = boletaService.guardar(boleta);
            return ResponseEntity.ok(new BoletaDTO(actualizado));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Boleta no encontrada");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boletaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Boleta no encontrada");
        }
    }
}