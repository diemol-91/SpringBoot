package com.diegom.spring_boot.LibrosRepositorio;

import com.diegom.spring_boot.model.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, Long> {
}
