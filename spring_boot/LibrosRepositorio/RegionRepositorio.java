package com.diegom.spring_boot.LibrosRepositorio;

import com.diegom.spring_boot.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepositorio extends JpaRepository<Region, Long> {}
