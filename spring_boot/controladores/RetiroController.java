package com.diegom.spring_boot.controladores;

import com.diegom.spring_boot.Servicios.RetiroService;
import com.diegom.spring_boot.Servicios.VentaService;
import com.diegom.spring_boot.dto.RetiroDTO;
import com.diegom.spring_boot.model.Retiro;
import com.diegom.spring_boot.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/retiros")
public class RetiroController {

    @Autowired
    private RetiroService retiroService;

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<RetiroDTO>> listar() {
        List<RetiroDTO> lista = retiroService.ListaRetiros()
                .stream().map(RetiroDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetiroDTO> buscar(@PathVariable Long id) {
        Optional<Retiro> retiro = retiroService.BuscaId(id);
        return retiro.map(r -> new ResponseEntity<>(new RetiroDTO(r), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> insertar(@RequestBody RetiroDTO dto) {
        try {
            Retiro retiro = new Retiro();
            retiro.setDireccionRetiro(dto.getDireccionRetiro());
            retiro.setFechaRetiro(dto.getFechaRetiro());

            Venta venta = ventaService.BuscaId(dto.getVentaId())
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
            retiro.setVenta(venta);

            Retiro guardado = retiroService.guardar(retiro);
            return new ResponseEntity<>(new RetiroDTO(guardado), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody RetiroDTO dto) {
        Optional<Retiro> encontrado = retiroService.BuscaId(id);
        if (encontrado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Retiro no encontrado");
        }

        try {
            Retiro entidad = encontrado.get();
            entidad.setDireccionRetiro(dto.getDireccionRetiro());
            entidad.setFechaRetiro(dto.getFechaRetiro());

            Venta venta = ventaService.BuscaId(dto.getVentaId())
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
            entidad.setVenta(venta);

            Retiro actualizado = retiroService.guardar(entidad);
            return ResponseEntity.ok(new RetiroDTO(actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            retiroService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Retiro no encontrado");
        }
    }
}
