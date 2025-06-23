package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.DetalleVentaRepositorio;
import com.diegom.spring_boot.model.DetalleVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepositorio repo;

    // Método para listar todos los detalles
    public List<DetalleVenta> ListaDetalle() {
        return repo.findAll();
    }

    // Método para buscar detalle por ID
    public Optional<DetalleVenta> BuscaId(Long id) {
        return repo.findById(id);
    }

    // Método para guardar
    public DetalleVenta guardar(DetalleVenta detalle) {
        return repo.save(detalle);
    }

    // Método para eliminar
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}