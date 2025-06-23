package com.diegom.spring_boot.Servicios;


import com.diegom.spring_boot.LibrosRepositorio.RegionRepositorio;
import com.diegom.spring_boot.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepositorio repo_region;

    public List<Region> ListaRegion(){
        return repo_region.findAll();

    }
    public Optional<Region> BuscaId(Long id){
        return repo_region.findById(id);
    }
    public Region guardar(Region region){
        return (Region) repo_region.save(region);
    }


    public Region Actualizar(long id, Region actualizar ){
        Region regionExiste = repo_region.findById(id)
                .orElseThrow(()-> new RuntimeException("Region no existe "+id));
        return repo_region.save(regionExiste);
    }

    public void eliminar (Long id){
         repo_region.deleteById(id);
    }

    public void eliminarTodo() {
        repo_region.deleteAll();
    }
}
