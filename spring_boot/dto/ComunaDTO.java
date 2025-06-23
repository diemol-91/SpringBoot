package com.diegom.spring_boot.dto;



import com.diegom.spring_boot.model.Comuna;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ComunaDTO {

    private Long id;
    private String nombre;
    private String provincia;

    public ComunaDTO(Comuna comuna) {
        this.id = comuna.getId();
        this.nombre = comuna.getNombre();
        this.provincia = comuna.getProvincia().getNombre();
    }
}
