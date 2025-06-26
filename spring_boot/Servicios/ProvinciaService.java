package com.diegom.spring_boot.Servicios;


import com.diegom.spring_boot.LibrosRepositorio.ProvinciaRepositorio;
import com.diegom.spring_boot.model.Provincia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinciaService {
    @Autowired
    private ProvinciaRepositorio repo_provincia;

    public List<Provincia> ListaProvincia(){
        return repo_provincia.findAll();

    }
    public Optional<Provincia> BuscaId(Long id){
        return repo_provincia.findById(id);
    }
    public Provincia guardar(Provincia provincia){
        return (Provincia) repo_provincia.save(provincia);
    }


    public Provincia Actualizar(long id, Provincia actualizar ){
        Provincia provinciaExiste = repo_provincia.findById(id)
                .orElseThrow(() -> new RuntimeException("Provincia no existe " + id));

        provinciaExiste.setNombre(actualizar.getNombre());
        provinciaExiste.setRegion(actualizar.getRegion());

        return repo_provincia.save(provinciaExiste);
    }

    public void eliminar (Long id){
        repo_provincia.deleteById(id);
    }

    public void eliminarTodo() {
        repo_provincia.deleteAll();
    }
}
