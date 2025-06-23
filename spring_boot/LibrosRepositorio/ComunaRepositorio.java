package com.diegom.spring_boot.LibrosRepositorio;

import com.diegom.spring_boot.model.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComunaRepositorio extends JpaRepository<Comuna, Long> {}
