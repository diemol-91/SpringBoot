package com.diegom.spring_boot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class CompraProveedorDTO {
    private Long id;
    private LocalDate fecha;
    private Long proveedorId; // solo el ID
    private List<DetalleCompraDTO> detalles; // Lista de detalles de la compra
}
