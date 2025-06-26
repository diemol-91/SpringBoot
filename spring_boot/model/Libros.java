package com.diegom.spring_boot.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private BigDecimal precio;

    private LocalDate fechaedicion;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "editorial_id", nullable = false)
    private Editorial editorial;
}