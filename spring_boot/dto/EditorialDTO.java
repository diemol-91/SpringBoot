package com.diegom.spring_boot.dto;


import com.diegom.spring_boot.model.Editorial;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditorialDTO {

    private Long id;
    private String nombre;

    public EditorialDTO(Editorial editorial) {
        this.id = editorial.getId();
        this.nombre = editorial.getNombre();
    }
}