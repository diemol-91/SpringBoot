package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.AutorRepositorio;
import com.diegom.spring_boot.LibrosRepositorio.LibrosRepositorio;
import com.diegom.spring_boot.model.Autor;
import com.diegom.spring_boot.model.Libros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LibrosService {
    @Autowired
    private LibrosRepositorio repo_libros;

    @Autowired
    private AutorRepositorio repo_autor; // si usas directamente el repositorio

    public List<Libros> ListarTodos(){
        return repo_libros.findAll();

    }
    public Optional<Libros> BuscaId(int id){
        return repo_libros.findById(id);
    }


    public Libros guardar(Libros libro) {
        if (libro.getAutor() == null || libro.getAutor().getId() == null) {
            throw new RuntimeException("Debe proporcionar un autor con ID.");
        }

        // Verificar si el autor existe en la base de datos
        Autor autorEncontrado = repo_autor.findById(Long.valueOf(libro.getAutor().getId()))
                .orElseThrow(() -> new RuntimeException("El autor con ID " + libro.getAutor().getId() + " no existe."));

        libro.setAutor(autorEncontrado);
        return repo_libros.save(libro);
    }
    public Libros Actualizar(int id, Libros actualizar ){
        Libros librosExiste = repo_libros.findById(id)
                .orElseThrow(()-> new RuntimeException("Libro no existe "+id));
        return repo_libros.save(librosExiste);
    }

    public void eliminar (int id){
        repo_libros.deleteById(id);
    }
}

