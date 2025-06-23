package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.ProveedorRepositorio;
import com.diegom.spring_boot.model.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepositorio repo_proveedor;

    public List<Proveedor> ListarTodo() {
        return repo_proveedor.findAll();
    }

    public Optional<Proveedor> BuscaId(Long id) {
        return repo_proveedor.findById(id);
    }

    public Proveedor guardar(Proveedor proveedor) {
        return repo_proveedor.save(proveedor);
    }

    public void eliminar(Long id) {
        repo_proveedor.deleteById(id);
    }
}