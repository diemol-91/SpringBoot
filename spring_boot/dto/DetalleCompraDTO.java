package com.diegom.spring_boot.dto;

import com.diegom.spring_boot.model.DetalleCompra;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetalleCompraDTO {
    private Long id;
    private LibrosDTO libroDTO;
    private int cantidad;

    public DetalleCompraDTO(DetalleCompra detalle) {
        this.id = detalle.getId();
        this.libroDTO = new LibrosDTO(detalle.getLibro());
        this.cantidad = detalle.getCantidad();
        // Nota: No se incluye la compraDTO para evitar ciclo
    }
}