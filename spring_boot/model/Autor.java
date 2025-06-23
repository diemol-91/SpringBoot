package com.diegom.spring_boot.model;
import jakarta.persistence.*;
import  lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String nombre, apellido, nacionalidad;


}
