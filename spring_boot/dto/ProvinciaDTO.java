package com.diegom.spring_boot.dto;


import com.diegom.spring_boot.model.Provincia;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProvinciaDTO {

    private Long id;
    private String nombre;
    private List<String> comunas;

    public ProvinciaDTO(Provincia provincia) {
        this.id = provincia.getId();
        this.nombre = provincia.getNombre();
        this.comunas = provincia.getComunas()
                .stream()
                .map(c -> c.getNombre())
                .collect(Collectors.toList());
    }
}
