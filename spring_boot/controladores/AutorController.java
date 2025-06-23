package com.diegom.spring_boot.controladores;


import com.diegom.spring_boot.Servicios.AutorService;
import com.diegom.spring_boot.dto.AutorDTO;
import com.diegom.spring_boot.model.Autor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private AutorService serv_autor;

    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarTodo() {
        List<Autor> autores = serv_autor.ListaAutor();
        List<AutorDTO> autoresDTO = autores.stream()
                .map(AutorDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(autoresDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscar(@PathVariable Long id) {
        return serv_autor.BuscaId(id)
                .map(autor -> new ResponseEntity<>(new AutorDTO(autor), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AutorDTO> insertar(@RequestBody AutorDTO dto) {
        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setNacionalidad(dto.getNacionalidad());
        Autor guardado = serv_autor.guardar(autor);
        return new ResponseEntity<>(new AutorDTO(guardado), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody AutorDTO dto) {
        Optional<Autor> existente = serv_autor.BuscaId(id);
        if (!existente.isPresent()) {
            return new ResponseEntity<>("Autor no encontrado", HttpStatus.NOT_FOUND);
        }

        Autor autor = existente.get();
        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setNacionalidad(dto.getNacionalidad());
        Autor actualizado = serv_autor.guardar(autor);
        return new ResponseEntity<>(new AutorDTO(actualizado), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        serv_autor.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
