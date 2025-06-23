package com.diegom.spring_boot.controladores;

import com.diegom.spring_boot.Servicios.CompraProveedorService;
import com.diegom.spring_boot.Servicios.LibroStockService;
import com.diegom.spring_boot.dto.CompraProveedorDTO;
import com.diegom.spring_boot.dto.DetalleCompraDTO;
import com.diegom.spring_boot.model.CompraProveedor;
import com.diegom.spring_boot.model.DetalleCompra;
import com.diegom.spring_boot.model.Libros;
import com.diegom.spring_boot.model.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraProveedorController {

    @Autowired
    private CompraProveedorService compraService;

    @Autowired
    private LibroStockService stockService;

    @PostMapping
    public ResponseEntity<?> registrarCompra(@RequestBody CompraProveedor compra) {
        CompraProveedor compraGuardada = compraService.guardar(compra);
        stockService.procesarCompraProveedor(List.of(compraGuardada));

        return ResponseEntity.status(HttpStatus.CREATED).body("Compra registrada y stock actualizado.");
    }

    @GetMapping
    public ResponseEntity<List<CompraProveedor>> listarCompras() {
        return ResponseEntity.ok(compraService.listar());
    }

    @PostMapping
    public ResponseEntity<?> insertar(@RequestBody CompraProveedorDTO dto) {
        try {
            Proveedor proveedor = proveedorService.BuscaId(dto.getProveedorId())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

            CompraProveedor compra = new CompraProveedor();
            compra.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDate.now());
            compra.setProveedor(proveedor);

            List<DetalleCompra> detalles = new ArrayList<>();
            for (DetalleCompraDTO detalleDTO : dto.getDetalles()) {
                Libros libro = librosService.BuscaId(detalleDTO.getLibroDTO().getId())
                        .orElseThrow(() -> new RuntimeException("Libro no encontrado: " + detalleDTO.getLibroDTO().getId()));

                DetalleCompra detalle = new DetalleCompra();
                detalle.setLibro(libro);
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setCompra(compra); // Relación inversa

                detalles.add(detalle);
            }

            compra.setDetalles(detalles);

            CompraProveedor guardado = compraService.guardar(compra);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}