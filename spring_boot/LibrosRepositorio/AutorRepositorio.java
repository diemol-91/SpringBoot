package com.diegom.spring_boot.LibrosRepositorio;

import com.diegom.spring_boot.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Long> {

}
