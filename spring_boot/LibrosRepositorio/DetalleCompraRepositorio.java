package com.diegom.spring_boot.LibrosRepositorio;

import com.diegom.spring_boot.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCompraRepositorio extends JpaRepository<DetalleCompra, Long> {
    // Puedes agregar métodos personalizados si lo necesitas, por ejemplo:
    // List<DetalleCompra> findByCompra_Id(Long compraId);
}