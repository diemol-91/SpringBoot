package com.diegom.spring_boot.controladores;

import com.diegom.spring_boot.Servicios.EditorialService;
import com.diegom.spring_boot.dto.EditorialDTO;
import com.diegom.spring_boot.model.Editorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/editoriales")
public class EditorialController {

    @Autowired
    private EditorialService editorialService;

    @GetMapping
    public ResponseEntity<List<EditorialDTO>> listarTodo() {
        List<EditorialDTO> lista = editorialService.ListaEditorial()
                .stream().map(EditorialDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditorialDTO> buscar(@PathVariable Long id) {
        return editorialService.BuscaId(id)
                .map(editorial -> new ResponseEntity<>(new EditorialDTO(editorial), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<EditorialDTO> insertar(@RequestBody EditorialDTO dto) {
        Editorial editorial = new Editorial();
        editorial.setNombre(dto.getNombre());
        Editorial guardada = editorialService.guardar(editorial);
        return new ResponseEntity<>(new EditorialDTO(guardada), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody EditorialDTO dto) {
        Optional<Editorial> editorialOpt = editorialService.BuscaId(id);

        if (editorialOpt.isPresent()) {
            Editorial editorial = editorialOpt.get();
            editorial.setNombre(dto.getNombre());

            Editorial actualizado = editorialService.guardar(editorial);
            EditorialDTO actualizadoDTO = new EditorialDTO(actualizado);

            return ResponseEntity.ok(actualizadoDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Editorial no encontrado(a)");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            editorialService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Editorial no encontrado(a)");
        }
    }
}