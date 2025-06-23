package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.VentaRepositorio;
import com.diegom.spring_boot.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepositorio repo;

    public List<Venta> listar() {
        return repo.findAll();
    }

    public Optional<Venta> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Venta guardar(Venta venta) {
        return repo.save(venta);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
    public Optional<Venta> BuscaId(Long id) {
        return repo.findById(id);
    }
}