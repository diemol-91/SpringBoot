package com.diegom.spring_boot.controladores;

import com.diegom.spring_boot.Servicios.ClienteService;
import com.diegom.spring_boot.dto.ClienteDTO;
import com.diegom.spring_boot.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        List<ClienteDTO> lista = clienteService.ListaClientes()
                .stream().map(ClienteDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
        return clienteService.BuscaId(id)
                .map(cliente -> new ResponseEntity<>(new ClienteDTO(cliente), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> insertar(@RequestBody ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setCorreo(dto.getCorreo());
        cliente.setDireccion(dto.getDireccion());
        Cliente guardado = clienteService.guardar(cliente);
        return new ResponseEntity<>(new ClienteDTO(guardado), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        Optional<Cliente> clienteOpt = clienteService.BuscaId(id);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setNombre(dto.getNombre());
            cliente.setApellido(dto.getApellido());
            cliente.setCorreo(dto.getCorreo());
            cliente.setDireccion(dto.getDireccion());

            Cliente actualizado = clienteService.guardar(cliente);
            ClienteDTO actualizadoDTO = new ClienteDTO(actualizado);

            return ResponseEntity.ok(actualizadoDTO); // Tipo claro
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cliente no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
