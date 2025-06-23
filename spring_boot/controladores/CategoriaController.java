package com.diegom.spring_boot.controladores;

import com.diegom.spring_boot.Servicios.CategoriaService;
import com.diegom.spring_boot.dto.CategoriaDTO;
import com.diegom.spring_boot.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodo() {
        List<CategoriaDTO> lista = categoriaService.ListaCategoria()
                .stream().map(CategoriaDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscar(@PathVariable Long id) {
        return categoriaService.BuscaId(id)
                .map(categoria -> new ResponseEntity<>(new CategoriaDTO(categoria), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> insertar(@RequestBody CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        Categoria guardada = categoriaService.guardar(categoria);
        return new ResponseEntity<>(new CategoriaDTO(guardada), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        Optional<Categoria> categoriaOpt = categoriaService.BuscaId(id);

        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            categoria.setNombre(dto.getNombre());
            Categoria actualizada = categoriaService.guardar(categoria);
            return new ResponseEntity<>(new CategoriaDTO(actualizada), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}") // DELETE para eliminar un Categoria
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria no encontrado(a)");
        }
    }
}
