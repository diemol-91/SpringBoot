package com.diegom.spring_boot.controladores;

import com.diegom.spring_boot.Servicios.CompraProveedorService;
import com.diegom.spring_boot.Servicios.DetalleCompraService;
import com.diegom.spring_boot.Servicios.LibrosService;
import com.diegom.spring_boot.dto.DetalleCompraDTO;
import com.diegom.spring_boot.model.CompraProveedor;
import com.diegom.spring_boot.model.DetalleCompra;
import com.diegom.spring_boot.model.Libros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detallecompra")
public class DetalleCompraController {

    @Autowired
    private DetalleCompraService service;

    @Autowired
    private LibrosService librosService;
    @Autowired
    private CompraProveedorService compraService;

    @GetMapping
    public ResponseEntity<List<DetalleCompraDTO>> listar() {
        List<DetalleCompraDTO> lista = service.ListaDetalle()
                .stream().map(DetalleCompraDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCompraDTO> buscar(@PathVariable Long id) {
        Optional<DetalleCompra> encontrado = service.buscarPorId(id);
        if (encontrado.isPresent()) {
            return ResponseEntity.ok(new DetalleCompraDTO(encontrado.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> insertar(@RequestBody DetalleCompraDTO dto) {
        try {
            DetalleCompra detalle = new DetalleCompra();
            detalle.setCantidad(dto.getCantidad());

            // Buscar libro
            Libros libro = librosService.BuscaId(Math.toIntExact(dto.getLibroDTO().getId()))
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
            detalle.setLibro(libro);

            // Como ya no se incluye compra en el DTO, puedes omitir o manejar de otra forma.
            // detalle.setCompra(...); // Si es necesario, agregar este valor desde otra fuente.

            DetalleCompra guardado = service.guardar(detalle);
            return new ResponseEntity<>(new DetalleCompraDTO(guardado), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody DetalleCompraDTO dto) {
        Optional<DetalleCompra> encontrado = service.buscarPorId(id);
        if (encontrado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DetalleCompra no encontrado");
        }

        try {
            DetalleCompra det = encontrado.get();
            det.setCantidad(dto.getCantidad());

            Libros libro = librosService.BuscaId(Math.toIntExact(dto.getLibroDTO().getId()))
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
            det.setLibro(libro);


            DetalleCompra actualizado = service.guardar(det);
            return ResponseEntity.ok(new DetalleCompraDTO(actualizado));
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DetalleCompra no encontrado");
        }
    }
}