package com.diegom.spring_boot.dto;

import com.diegom.spring_boot.model.Retiro;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RetiroDTO {
    private Long id;
    private String direccionRetiro;
    private LocalDate fechaRetiro;
    private Long ventaId;

    public RetiroDTO(Retiro retiro) {
        this.id = retiro.getId();
        this.direccionRetiro = retiro.getDireccionRetiro();
        this.fechaRetiro = retiro.getFechaRetiro();
        if (retiro.getVenta() != null) {
            this.ventaId = retiro.getVenta().getId();
        }
    }
}