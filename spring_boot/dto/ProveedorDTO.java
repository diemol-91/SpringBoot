package com.diegom.spring_boot.dto;

import com.diegom.spring_boot.model.Proveedor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProveedorDTO {
    private Long id;
    private String nombre;
    private String rut;
    private Integer telefono;
    private String correo;

    public ProveedorDTO(Proveedor proveedor) {
        this.id = proveedor.getId();
        this.nombre = proveedor.getNombre();
        this.rut = proveedor.getRut();
        this.telefono = proveedor.getTelefono();
        this.correo = proveedor.getCorreo();
    }
}