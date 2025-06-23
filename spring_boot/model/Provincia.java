package com.diegom.spring_boot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "region_id")  // Llave foránea a Region
    private Region region;

    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL)
    private List<Comuna> comunas;
}