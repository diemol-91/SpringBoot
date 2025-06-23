package com.diegom.spring_boot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Libros libro;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private CompraProveedor compra;
}