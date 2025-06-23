package com.diegom.spring_boot.Servicios;


import com.diegom.spring_boot.LibrosRepositorio.CompraProveedorRepositorio;
import com.diegom.spring_boot.LibrosRepositorio.LibrosRepositorio;
import com.diegom.spring_boot.LibrosRepositorio.DetalleVentaRepositorio;
import com.diegom.spring_boot.model.DetalleCompra;
import com.diegom.spring_boot.model.Libros;
import com.diegom.spring_boot.model.CompraProveedor;
import com.diegom.spring_boot.model.DetalleVenta;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroStockService {

    @Autowired
    private LibrosRepositorio repo_libros;

    @Autowired
    private CompraProveedorRepositorio repo_detalleCompra;

    @Autowired
    private DetalleVentaRepositorio repo_detalleVenta;

    @Transactional
    public void procesarCompraProveedor(List<CompraProveedor> compras) {
        for (CompraProveedor compra : compras) {
            for (DetalleCompra detalle : compra.getDetalles()) {
                Libros libro = detalle.getLibro();
                Libros libroExistente = repo_libros.findById(Math.toIntExact(libro.getId()))
                        .orElseThrow(() -> new RuntimeException("Libro no encontrado: " + libro.getId()));

                libroExistente.setStock(libroExistente.getStock() + detalle.getCantidad());

                repo_libros.save(libroExistente);
            }
        }
    }
    @Transactional
    public void procesarVentaCliente(List<DetalleVenta> detalles) {
        for (DetalleVenta detalle : detalles) {
            Libros libro = detalle.getLibro();
            Libros libroExistente = repo_libros.findById(Math.toIntExact(libro.getId()))
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado: " + libro.getId()));
            int nuevoStock = libroExistente.getStock() - detalle.getCantidad();
            if (nuevoStock < 0) {
                throw new RuntimeException("Stock insuficiente para el libro ID: " + libro.getId());
            }
            libroExistente.setStock(nuevoStock);
            repo_libros.save(libroExistente);
        }
    }
}
