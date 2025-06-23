package com.diegom.spring_boot.LibrosRepositorio;

import com.diegom.spring_boot.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepositorio  extends JpaRepository<Provincia, Long> {}
