package com.diegom.spring_boot.model;
import jakarta.persistence.*;
import  lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String rut;
    private Integer Telefono;
    private  String correo;
}
