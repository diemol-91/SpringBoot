package com.diegom.spring_boot.controladores;

import com.diegom.spring_boot.Servicios.CompraProveedorService;
import com.diegom.spring_boot.Servicios.ProveedorService;
import com.diegom.spring_boot.Servicios.LibrosService;
import com.diegom.spring_boot.dto.CompraProveedorDTO;
import com.diegom.spring_boot.dto.DetalleCompraDTO;
import com.diegom.spring_boot.dto.LibrosDTO;
import com.diegom.spring_boot.dto.ProveedorDTO;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/compras")
public class CompraProveedorController {

    @Autowired
    private CompraProveedorService compraService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private LibrosService librosService;

    @GetMapping
    public ResponseEntity<List<CompraProveedorDTO>> listar() {
        List<CompraProveedorDTO> lista = compraService.listar()
                .stream()
                .map(compra -> {
                    CompraProveedorDTO dto = new CompraProveedorDTO();
                    dto.setId(compra.getId());
                    dto.setFecha(compra.getFecha());
                    dto.setProveedorId(compra.getProveedor().getId());

                    List<DetalleCompraDTO> detallesDTO = compra.getDetalles().stream().map(det -> {
                        DetalleCompraDTO detDTO = new DetalleCompraDTO();
                        detDTO.setId(det.getId());
                        detDTO.setCantidad(det.getCantidad());
                        detDTO.setLibroDTO(new LibrosDTO(det.getLibro()));
                        return detDTO;
                    }).collect(Collectors.toList());

                    dto.setDetalles(detallesDTO);
                    return dto;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraProveedorDTO> buscar(@PathVariable Long id) {
        Optional<CompraProveedor> optional = compraService.listar()
                .stream().filter(c -> c.getId().equals(id)).findFirst();

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CompraProveedor compra = optional.get();
        CompraProveedorDTO dto = new CompraProveedorDTO();
        dto.setId(compra.getId());
        dto.setFecha(compra.getFecha());
        dto.setProveedorId(compra.getProveedor().getId());

        List<DetalleCompraDTO> detallesDTO = compra.getDetalles().stream().map(det -> {
            DetalleCompraDTO detDTO = new DetalleCompraDTO();
            detDTO.setId(det.getId());
            detDTO.setCantidad(det.getCantidad());
            detDTO.setLibroDTO(new LibrosDTO(det.getLibro()));
            return detDTO;
        }).collect(Collectors.toList());

        dto.setDetalles(detallesDTO);

        return ResponseEntity.ok(dto);
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
                Libros libro = librosService.BuscaId(Math.toIntExact(detalleDTO.getLibroDTO().getId()))
                        .orElseThrow(() -> new RuntimeException("Libro no encontrado: " + detalleDTO.getLibroDTO().getId()));

                DetalleCompra detalle = new DetalleCompra();
                detalle.setLibro(libro);
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setCompra(compra);

                detalles.add(detalle);
            }

            compra.setDetalles(detalles);

            CompraProveedor guardado = compraService.guardar(compra);

            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            compraService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Compra no encontrada");
        }
    }
}