package com.diegom.spring_boot.Servicios;


import com.diegom.spring_boot.LibrosRepositorio.ComunaRepositorio;
import com.diegom.spring_boot.model.Comuna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComunaService {
    @Autowired
    private ComunaRepositorio repo_comuna;

    public List<Comuna> ListaComuna(){
        return repo_comuna.findAll();

    }
    public Optional<Comuna> BuscaId(Long id){
        return repo_comuna.findById(id);
    }
    public Comuna guardar(Comuna comuna){
        return (Comuna) repo_comuna.save(comuna);
    }


    public Comuna Actualizar(long id, Comuna actualizar ){
        Comuna comunaExiste = repo_comuna.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no existe " + id));

        comunaExiste.setNombre(actualizar.getNombre());
        comunaExiste.setProvincia(actualizar.getProvincia());

        return repo_comuna.save(comunaExiste);
    }

    public void eliminar (Long id){
        repo_comuna.deleteById(id);
    }

    public void eliminarTodo() {
        repo_comuna.deleteAll();
    }
}
