package com.diegom.spring_boot.LibrosRepositorio;


import com.diegom.spring_boot.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletaRepositorio extends JpaRepository<Boleta, Long> {
}