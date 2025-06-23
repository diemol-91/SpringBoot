package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.EditorialRepositorio;
import com.diegom.spring_boot.model.Categoria;
import com.diegom.spring_boot.model.Editorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialService {
    @Autowired
    private EditorialRepositorio repo_editorial;

    public List<Editorial> ListaEditorial() {
        return repo_editorial.findAll();
    }

    public Optional<Editorial> BuscaId(Long id) {
        return repo_editorial.findById(id);
    }

    public Editorial guardar(Editorial editorial) {
        return repo_editorial.save(editorial);
    }

    public Editorial actualizar(Long id, Editorial actualizar) {
        Editorial existente = repo_editorial.findById(id)
                .orElseThrow(() -> new RuntimeException("Editorial no existe: " + id));
        existente.setNombre(actualizar.getNombre());
        return repo_editorial.save(existente);
    }

    public void eliminar(Long id) {
        repo_editorial.deleteById(id);
    }
}
