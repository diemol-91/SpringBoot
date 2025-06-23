package com.diegom.spring_boot.Servicios;


import com.diegom.spring_boot.LibrosRepositorio.CompraProveedorRepositorio;
import com.diegom.spring_boot.LibrosRepositorio.LibrosRepositorio;
import com.diegom.spring_boot.model.CompraProveedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompraProveedorService {

    @Autowired
    private CompraProveedorRepositorio repo_compra;

    @Autowired
    private LibroStockService libroStockService;

    public CompraProveedor guardar(CompraProveedor compra) {
        CompraProveedor guardada = repo_compra.save(compra);
        libroStockService.procesarCompraProveedor(List.of(guardada)); // lo convertimos en lista
        return guardada;
    }

    public List<CompraProveedor> listar() {
        return repo_compra.findAll();
    }
}