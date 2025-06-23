package com.diegom.spring_boot.dto;


import com.diegom.spring_boot.model.Libros;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
public class LibrosDTO {

    private Long id;
    private String titulo;
    private BigDecimal precio;
    private LocalDate fechaedicion;
    private Integer stock;

    private AutorDTO autorDTO;
    private CategoriaDTO categoriaDTO;
    private EditorialDTO editorialDTO;

    public LibrosDTO(Libros p_libros) {
        this.id = p_libros.getId();
        this.titulo = p_libros.getTitulo();
        this.precio = p_libros.getPrecio();
        this.fechaedicion = p_libros.getFechaedicion();
        this.stock = p_libros.getStock();
        this.autorDTO = new AutorDTO(p_libros.getAutor());
        this.categoriaDTO = new CategoriaDTO(p_libros.getCategoria());
        this.editorialDTO = new EditorialDTO(p_libros.getEditorial());
    }
}