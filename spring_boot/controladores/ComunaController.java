package com.diegom.spring_boot.controladores;


import com.diegom.spring_boot.Servicios.ComunaService;
import com.diegom.spring_boot.dto.ComunaDTO;
import com.diegom.spring_boot.dto.ProvinciaDTO;
import com.diegom.spring_boot.model.Comuna;
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
@RequestMapping("/api/comunas")
public class ComunaController {

    @Autowired
    private ComunaService serv_comuna;

    @GetMapping
    public ResponseEntity<List<ComunaDTO>> listarTodo() {
        List<Comuna> comunas = serv_comuna.ListaComuna();
        List<ComunaDTO> resultado = comunas.stream()
                .map(ComunaDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunaDTO> buscar(@PathVariable Long id) {
        Optional<Comuna> comuna = serv_comuna.BuscaId(id);
        return comuna.map(p -> new ResponseEntity<>(new ComunaDTO(p), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Comuna> insertar (@RequestBody Comuna in_comuna){
        Comuna insertado = serv_comuna.guardar(in_comuna);
        return new ResponseEntity<>(insertado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComunaDTO> actualizar(@PathVariable long id, @RequestBody Comuna comuna) {
        Optional<Comuna> comunaExiste = serv_comuna.BuscaId(id);
        if (comunaExiste.isPresent()) {
            Comuna actualizado = comunaExiste.get();
            actualizado.setNombre(comuna.getNombre());
            actualizado.setProvincia(comuna.getProvincia());
            Comuna guardado = serv_comuna.guardar(actualizado);
            return new ResponseEntity<>(new ComunaDTO(guardado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> borradorcomuna(@PathVariable long id ){
        try {
            serv_comuna.eliminar(id);
            return ResponseEntity.noContent().build();
        }catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar-todo")
    public ResponseEntity<Void> eliminarTodo() {
        serv_comuna.eliminarTodo();
        return ResponseEntity.noContent().build();
    }

}
