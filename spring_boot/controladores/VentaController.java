package com.diegom.spring_boot.controladores;

import com.diegom.spring_boot.Servicios.ClienteService;
import com.diegom.spring_boot.Servicios.DetalleVentaService;
import com.diegom.spring_boot.Servicios.LibrosService;
import com.diegom.spring_boot.Servicios.VentaService;
import com.diegom.spring_boot.dto.DetalleVentaDTO;
import com.diegom.spring_boot.dto.VentaDTO;
import com.diegom.spring_boot.model.Cliente;
import com.diegom.spring_boot.model.DetalleVenta;
import com.diegom.spring_boot.model.Libros;
import com.diegom.spring_boot.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private LibrosService librosService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> listar() {
        List<VentaDTO> lista = ventaService.listar()
                .stream()
                .map(VentaDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> buscar(@PathVariable Long id) {
        return ventaService.buscarPorId(id)
                .map(v -> new ResponseEntity<>(new VentaDTO(v), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> insertar(@RequestBody VentaDTO dto) {
        try {
            Venta venta = new Venta();
            venta.setFecha(dto.getFecha());

            Cliente cliente = clienteService.BuscaId(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            venta.setCliente(cliente);

            List<DetalleVenta> detalles = dto.getDetalles().stream().map(detDTO -> {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setCantidad(detDTO.getCantidad());

                Libros libro = librosService.BuscaId(Math.toIntExact(detDTO.getLibro().getId()))
                        .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
                detalle.setLibro(libro);

                detalle.setVenta(venta);
                return detalle;
            }).collect(Collectors.toList());

            venta.setDetalles(detalles);

            Venta guardado = ventaService.guardar(venta);
            return new ResponseEntity<>(new VentaDTO(guardado), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody VentaDTO dto) {
        return ventaService.buscarPorId(id)
                .map(venta -> {
                    try {
                        venta.setFecha(dto.getFecha());

                        Cliente cliente = clienteService.BuscaId(dto.getClienteId())
                                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
                        venta.setCliente(cliente);

                        // Limpiar y actualizar los detalles
                        venta.getDetalles().clear();

                        List<DetalleVenta> detallesActualizados = dto.getDetalles().stream().map(detDTO -> {
                            DetalleVenta detalle = new DetalleVenta();
                            detalle.setCantidad(detDTO.getCantidad());

                            Libros libro = librosService.BuscaId(Math.toIntExact(detDTO.getLibro().getId()))
                                    .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

                            detalle.setLibro(libro);
                            detalle.setVenta(venta);
                            return detalle;
                        }).collect(Collectors.toList());

                        venta.setDetalles(detallesActualizados);

                        Venta actualizado = ventaService.guardar(venta);
                        return ResponseEntity.ok(new VentaDTO(actualizado));
                    } catch (RuntimeException e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((Object) "Venta no encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            ventaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta no encontrada");
        }
    }
}
