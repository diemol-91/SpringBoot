package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.BoletaRepositorio;
import com.diegom.spring_boot.model.Boleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoletaService {

    @Autowired
    private BoletaRepositorio repo;

    // Listar todas las boletas
    public List<Boleta> ListaBoleta() {
        return repo.findAll();
    }

    // Buscar boleta por ID
    public Optional<Boleta> BuscaId(Long id) {
        return repo.findById(id);
    }

    // Guardar o actualizar una boleta
    public Boleta guardar(Boleta boleta) {
        return repo.save(boleta);
    }

    // Eliminar una boleta
    public void eliminar(Long id) {
        repo.deleteById(id);
    }


}