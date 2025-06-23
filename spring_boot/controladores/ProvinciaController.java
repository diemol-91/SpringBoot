package com.diegom.spring_boot.controladores;


import com.diegom.spring_boot.Servicios.ProvinciaService;
import com.diegom.spring_boot.dto.ProvinciaDTO;
import com.diegom.spring_boot.model.Provincia;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/provincias")
public class ProvinciaController {

    @Autowired
    private ProvinciaService serv_provincia;

    @GetMapping
    public ResponseEntity<List<ProvinciaDTO>> listarTodo() {
        List<ProvinciaDTO> dtoList = serv_provincia.ListaProvincia().stream()
                .map(ProvinciaDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinciaDTO> buscar(@PathVariable Long id) {
        Optional<Provincia> provincia = serv_provincia.BuscaId(id);
        return provincia.map(p -> new ResponseEntity<>(new ProvinciaDTO(p), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Provincia> insertar (@RequestBody Provincia in_provincia){
        Provincia insertado = serv_provincia.guardar(in_provincia);
        return new ResponseEntity<>(insertado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvinciaDTO> actualizarProvincia(@PathVariable Long id, @RequestBody Provincia provinciaActualizada) {
        Optional<Provincia> existente = serv_provincia.BuscaId(id);
        if (existente.isPresent()) {
            Provincia actual = existente.get();
            actual.setNombre(provinciaActualizada.getNombre());
            actual.setRegion(provinciaActualizada.getRegion());
            Provincia guardado = serv_provincia.guardar(actual);
            ProvinciaDTO respuesta = new ProvinciaDTO(guardado); // Mapear a DTO
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> borradorprovincia(@PathVariable long id ){
        try {
            serv_provincia.eliminar(id);
            return ResponseEntity.noContent().build();
        }catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar-todo")
    public ResponseEntity<Void> eliminarTodo() {
        serv_provincia.eliminarTodo();
        return ResponseEntity.noContent().build();
    }

}
