package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.RetiroRepositorio;
import com.diegom.spring_boot.model.Retiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetiroService {

    @Autowired
    private RetiroRepositorio repo;

    public List<Retiro> ListaRetiros() {
        return repo.findAll();
    }

    public Optional<Retiro> BuscaId(Long id) {
        return repo.findById(id);
    }

    public Retiro guardar(Retiro retiro) {
        return repo.save(retiro);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}