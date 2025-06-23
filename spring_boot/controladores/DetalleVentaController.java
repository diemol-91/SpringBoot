package com.diegom.spring_boot.controladores;

import com.diegom.spring_boot.Servicios.DetalleVentaService;
import com.diegom.spring_boot.Servicios.LibrosService;
import com.diegom.spring_boot.Servicios.VentaService;
import com.diegom.spring_boot.dto.DetalleVentaDTO;
import com.diegom.spring_boot.model.DetalleVenta;
import com.diegom.spring_boot.model.Libros;
import com.diegom.spring_boot.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detalleventa")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService service;

    @Autowired
    private LibrosService librosService;

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<DetalleVentaDTO>> listar() {
        List<DetalleVentaDTO> lista = service.ListaDetalle()
                .stream().map(DetalleVentaDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaDTO> buscar(@PathVariable Long id) {
        return service.BuscaId(id)
                .map(det -> new ResponseEntity<>(new DetalleVentaDTO(det), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> insertar(@RequestBody DetalleVentaDTO dto) {
        try {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setCantidad(dto.getCantidad());

            Libros libro = librosService.BuscaId(Math.toIntExact(dto.getLibro().getId()))
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
            detalle.setLibro(libro);

            // Aquí puedes asociar una venta si el DTO la incluyera
            // detalle.setVenta(...);

            DetalleVenta guardado = service.guardar(detalle);
            return new ResponseEntity<>(new DetalleVentaDTO(guardado), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody DetalleVentaDTO dto) {
        Optional<DetalleVenta> encontrado = service.BuscaId(id);
        if (encontrado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DetalleVenta no encontrado");
        }

        try {
            DetalleVenta detalle = encontrado.get();
            detalle.setCantidad(dto.getCantidad());

            Libros libro = librosService.BuscaId(Math.toIntExact(dto.getLibro().getId()))
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
            detalle.setLibro(libro);

            // Si necesitas setear la venta también, agrégala aquí.

            DetalleVenta actualizado = service.guardar(detalle);
            return ResponseEntity.ok(new DetalleVentaDTO(actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DetalleVenta no encontrado");
        }
    }
}