package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.AutorRepositorio;
import com.diegom.spring_boot.LibrosRepositorio.LibrosRepositorio;
import com.diegom.spring_boot.model.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    @Autowired
    private AutorRepositorio repo_autor;
    @Autowired
    private LibrosRepositorio repo_libros;

    public List<Autor> ListaAutor(){
        return repo_autor.findAll();

    }
    public Optional<Autor> BuscaId(Long id) {
        return repo_autor.findById(id);
    }
    public Autor guardar(Autor autor){
        return (Autor) repo_autor.save(autor);
    }


    public Autor Actualizar(long id, Autor actualizar ){
        Autor autorExiste = repo_autor.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no existe " + id));

        autorExiste.setNombre(actualizar.getNombre());
        autorExiste.setApellido(actualizar.getApellido());
        autorExiste.setNacionalidad(actualizar.getNacionalidad());

        return repo_autor.save(autorExiste);
    }


    public void eliminar(Long id) {
        Autor autor = repo_autor.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        if (!repo_libros.findByAutor_Id(id).isEmpty()) {
            throw new RuntimeException("No se puede eliminar el autor, tiene libros asociados.");
        }

        repo_autor.delete(autor);
    }
}
