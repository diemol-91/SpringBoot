package com.diegom.spring_boot.LibrosRepositorio;


import com.diegom.spring_boot.model.CompraProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraProveedorRepositorio extends JpaRepository<CompraProveedor, Long> {
}