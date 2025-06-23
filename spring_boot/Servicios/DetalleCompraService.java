package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.DetalleCompraRepositorio;
import com.diegom.spring_boot.model.DetalleCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleCompraService {

    @Autowired
    private DetalleCompraRepositorio repo;

    public List<DetalleCompra> ListaDetalle() {
        return repo.findAll();
    }

    public List<DetalleCompra> listarTodo() {
        return repo.findAll();
    }

    public Optional<DetalleCompra> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public DetalleCompra guardar(DetalleCompra detalle) {
        return repo.save(detalle);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}