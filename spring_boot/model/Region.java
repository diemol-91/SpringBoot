package com.diegom.spring_boot.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.util.List;
import lombok.Data;

@Entity
@NoArgsConstructor
@Data
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<Provincia> provincias;


}
