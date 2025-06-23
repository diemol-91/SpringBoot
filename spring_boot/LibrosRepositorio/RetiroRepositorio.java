package com.diegom.spring_boot.LibrosRepositorio;


import com.diegom.spring_boot.model.Retiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetiroRepositorio extends JpaRepository<Retiro, Long> {
}