package com.diegom.spring_boot.dto;


import com.diegom.spring_boot.model.Region;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RegionDTO {

    private Long id;
    private String nombre;
    private List<String> provincias;

    public RegionDTO(Region region) {
        this.id = region.getId();
        this.nombre = region.getNombre();
        this.provincias = region.getProvincias()
                .stream()
                .map(p -> p.getNombre())
                .collect(Collectors.toList());
    }
}