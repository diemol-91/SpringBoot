package com.diegom.spring_boot.dto;

import com.diegom.spring_boot.model.Venta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class VentaDTO {
    private Long id;
    private LocalDate fecha;
    private Long clienteId;
    private List<DetalleVentaDTO> detalles;

    private RetiroDTO retiroDTO; // Para complementar con Retiro

    public VentaDTO(Venta venta) {
        this.id = venta.getId();
        this.fecha = venta.getFecha();

        if (venta.getCliente() != null) {
            this.clienteId = venta.getCliente().getId();
        }

        if (venta.getDetalles() != null) {
            this.detalles = venta.getDetalles().stream()
                    .map(DetalleVentaDTO::new)
                    .collect(Collectors.toList());
        }

        if (venta.getRetiro() != null) {
            this.retiroDTO = new RetiroDTO(venta.getRetiro());
        }
    }
}