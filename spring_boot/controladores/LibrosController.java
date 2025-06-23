package com.diegom.spring_boot.controladores;

import com.diegom.spring_boot.Servicios.AutorService;
import com.diegom.spring_boot.Servicios.CategoriaService;
import com.diegom.spring_boot.Servicios.EditorialService;
import com.diegom.spring_boot.Servicios.LibrosService;
import com.diegom.spring_boot.dto.LibrosDTO;
import com.diegom.spring_boot.model.Autor;
import com.diegom.spring_boot.model.Categoria;
import com.diegom.spring_boot.model.Editorial;
import com.diegom.spring_boot.model.Libros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/libros")
public class LibrosController {

    @Autowired
    private LibrosService serv_libro;

    @Autowired
    private AutorService serv_autor;

    @Autowired
    private CategoriaService serv_categoria;

    @Autowired
    private EditorialService serv_editorial;

    @GetMapping
    public ResponseEntity<List<LibrosDTO>> listarTodo() {
        List<Libros> libros = serv_libro.ListarTodos();
        List<LibrosDTO> librosDTO = libros.stream()
                .map(LibrosDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(librosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibrosDTO> buscar(@PathVariable Long id) {
        Optional<Libros> libro = serv_libro.BuscaId(Math.toIntExact(id));
        return libro.map(value -> ResponseEntity.ok(new LibrosDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> insertar(@RequestBody LibrosDTO dto) {
        try {
            Libros libro = new Libros();
            libro.setTitulo(dto.getTitulo());
            libro.setPrecio(dto.getPrecio());
            libro.setFechaedicion(dto.getFechaedicion());
            libro.setStock(dto.getStock());

            Autor autor = serv_autor.BuscaId(Long.valueOf(dto.getAutorDTO().getId()))
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
            libro.setAutor(autor);

            Categoria categoria = serv_categoria.BuscaId(dto.getCategoriaDTO().getId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            libro.setCategoria(categoria);

            Editorial editorial = serv_editorial.BuscaId(dto.getEditorialDTO().getId())
                    .orElseThrow(() -> new RuntimeException("Editorial no encontrada"));
            libro.setEditorial(editorial);

            Libros guardado = serv_libro.guardar(libro);
            return new ResponseEntity<>(new LibrosDTO(guardado), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody LibrosDTO dto) {
        Optional<Libros> existente = serv_libro.BuscaId(Math.toIntExact(id));
        if (!existente.isPresent()) {
            return new ResponseEntity<>("Libro no encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Libros libro = existente.get();
            libro.setTitulo(dto.getTitulo());
            libro.setPrecio(dto.getPrecio());
            libro.setFechaedicion(dto.getFechaedicion());
            libro.setStock(dto.getStock());

            Autor autor = serv_autor.BuscaId(Long.valueOf(dto.getAutorDTO().getId()))
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
            libro.setAutor(autor);

            Categoria categoria = serv_categoria.BuscaId(dto.getCategoriaDTO().getId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            libro.setCategoria(categoria);

            Editorial editorial = serv_editorial.BuscaId(dto.getEditorialDTO().getId())
                    .orElseThrow(() -> new RuntimeException("Editorial no encontrada"));
            libro.setEditorial(editorial);

            Libros actualizado = serv_libro.guardar(libro);
            return new ResponseEntity<>(new LibrosDTO(actualizado), HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        serv_libro.eliminar(Math.toIntExact(id));
        return ResponseEntity.noContent().build();
    }
}