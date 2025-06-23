package com.diegom.spring_boot.controladores;


import com.diegom.spring_boot.Servicios.RegionService;
import com.diegom.spring_boot.dto.RegionDTO;
import com.diegom.spring_boot.model.Region;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/regiones")
public class RegionController {

    @Autowired
    private RegionService serv_region;

    @GetMapping
    public ResponseEntity<List<RegionDTO>> listarTodo(){
        List<RegionDTO> regiones = serv_region.ListaRegion()
                .stream()
                .map(RegionDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(regiones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> buscar(@PathVariable Long id) {
        Optional<Region> region = serv_region.BuscaId(id);

        return region.map(value -> {
                    RegionDTO dto = new RegionDTO(value);
                    return new ResponseEntity<>(dto, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Region> insertar (@RequestBody Region in_region){
        Region insertado = serv_region.guardar(in_region);
        return new ResponseEntity<>(insertado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionDTO> actualizar(@PathVariable long id, @RequestBody Region region) {
        Optional<Region> regionExiste = serv_region.BuscaId(id);
        if (regionExiste.isPresent()) {
            Region actualizado = regionExiste.get();
            actualizado.setNombre(region.getNombre());
            Region guardado = serv_region.guardar(actualizado);
            return new ResponseEntity<>(new RegionDTO(guardado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> borradorRegion(@PathVariable long id ){
        try {
            serv_region.eliminar(id);
            return ResponseEntity.noContent().build();
        }catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar-todo")
    public ResponseEntity<Void> eliminarTodo() {
        serv_region.eliminarTodo();
        return ResponseEntity.noContent().build();
    }

}
