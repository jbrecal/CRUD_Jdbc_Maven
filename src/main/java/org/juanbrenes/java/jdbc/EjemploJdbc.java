package org.juanbrenes.java.jdbc;

import org.juanbrenes.java.jdbc.models.Categoria;
import org.juanbrenes.java.jdbc.models.Producto;
import org.juanbrenes.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.juanbrenes.java.jdbc.repositorio.Repositorio;
import org.juanbrenes.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.Date;

public class EjemploJdbc {
    public static void main(String[] args) {

        try (Connection conn = ConexionBaseDatos.getConnection()){

            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("==============listar============");
            repositorio.listar().forEach(System.out::println);  //muestra el listado de productos

            System.out.println("==============obtener por id============");
            System.out.println(repositorio.porId(2L));  //muestra el producto 2

            System.out.println("==============insertar nuevo producto============");
            Producto producto = new Producto();
            producto.setNombre("Teclado mecanico");
            producto.setPrecio(149);
            producto.setFechaRegistro(new Date());  //obtenemos la fecha actual de java util
            Categoria categoria = new Categoria();
            categoria.setId(3L);
            producto.setCategoria(categoria);
            repositorio.guardar(producto);
            System.out.println("Se ha guardado el producto correctamente");
            repositorio.listar().forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
