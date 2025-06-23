package com.diegom.spring_boot.Servicios;

import com.diegom.spring_boot.LibrosRepositorio.ClienteRepositorio;
import com.diegom.spring_boot.model.Categoria;
import com.diegom.spring_boot.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepositorio repo_cliente;


    public List<Cliente> ListaClientes() {
        return repo_cliente.findAll();
    }

    public Optional<Cliente> BuscaId(Long id) {
        return repo_cliente.findById(id);
    }

    public Cliente guardar(Cliente cliente) {
        return repo_cliente.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente actualizar) {
        Cliente existente = repo_cliente.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no existe: " + id));
        existente.setNombre(actualizar.getNombre());
        existente.setApellido(actualizar.getApellido());
        existente.setCorreo(actualizar.getCorreo());
        existente.setDireccion(actualizar.getDireccion());
        return repo_cliente.save(existente);
    }

    public void eliminar(Long id) {
        repo_cliente.deleteById(id);
    }
}
