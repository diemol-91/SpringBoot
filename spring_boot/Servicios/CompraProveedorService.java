package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.CompraProveedorRepositorio;
import com.diegom.spring_boot.model.CompraProveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraProveedorService {

    @Autowired
    private CompraProveedorRepositorio repo_compra;

    @Autowired
    private LibroStockService libroStockService;

    public CompraProveedor guardar(CompraProveedor compra) {
        CompraProveedor guardada = repo_compra.save(compra);
        libroStockService.procesarCompraProveedor(List.of(guardada));
        return guardada;
    }

    public List<CompraProveedor> listar() {
        return repo_compra.findAll();
    }

    public Optional<CompraProveedor> buscarPorId(Long id) {
        return repo_compra.findById(id);
    }

    public void eliminar(Long id) {
        if (repo_compra.existsById(id)) {
            repo_compra.deleteById(id);
        } else {
            throw new RuntimeException("CompraProveedor con ID " + id + " no existe");
        }
    }
}