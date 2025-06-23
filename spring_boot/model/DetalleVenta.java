package com.diegom.spring_boot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Libros libro;
    private int cantidad;
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;
}