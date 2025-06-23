package com.diegom.spring_boot.LibrosRepositorio;


import com.diegom.spring_boot.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibrosRepositorio extends JpaRepository<Libros, Integer> {
    List<Libros> findByAutor_Id(Long autorId);



}

