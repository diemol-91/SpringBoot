package com.diegom.spring_boot.dto;

import com.diegom.spring_boot.model.DetalleVenta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetalleVentaDTO {
    private Long id;
    private LibrosDTO libro;
    private int cantidad;

    public DetalleVentaDTO(DetalleVenta detalle) {
        this.id = detalle.getId();
        this.libro = new LibrosDTO(detalle.getLibro());
        this.cantidad = detalle.getCantidad();
    }
}