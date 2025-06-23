package com.diegom.spring_boot.dto;

import com.diegom.spring_boot.model.Autor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutorDTO {

    private Integer id;
    private String nombre, apellido, nacionalidad;

    public AutorDTO(Autor p_autor) {
        this.id = p_autor.getId();
        this.nombre = p_autor.getNombre();
        this.apellido = p_autor.getApellido();
        this.nacionalidad = p_autor.getNacionalidad();
    }
}
