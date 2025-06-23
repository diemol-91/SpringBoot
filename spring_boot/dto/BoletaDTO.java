package com.diegom.spring_boot.dto;

import com.diegom.spring_boot.model.Boleta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BoletaDTO {
    private Long id;
    private LocalDate fecha;
    private ClienteDTO cliente;

    public BoletaDTO(Boleta boleta) {
        this.id = boleta.getId();
        this.fecha = boleta.getFechaEmision();
        this.cliente = new ClienteDTO(boleta.getCliente());
    }
}