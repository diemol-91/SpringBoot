package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.CategoriaRepositorio;
import com.diegom.spring_boot.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepositorio repo_categoria;

    public List<Categoria> ListaCategoria() {
        return repo_categoria.findAll();
    }

    public Optional<Categoria> BuscaId(Long id) {
        return repo_categoria.findById(id);
    }

    public Categoria guardar(Categoria categoria) {
        return repo_categoria.save(categoria);
    }

    public Categoria actualizar(Long id, Categoria actualizar) {
        Categoria existente = repo_categoria.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no existe: " + id));
        existente.setNombre(actualizar.getNombre());
        return repo_categoria.save(existente);
    }

    public void eliminar(Long id) {
        repo_categoria.deleteById(id);
    }
}