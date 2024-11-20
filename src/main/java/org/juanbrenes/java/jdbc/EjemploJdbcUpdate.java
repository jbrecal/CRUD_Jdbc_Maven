package org.juanbrenes.java.jdbc;

import org.juanbrenes.java.jdbc.models.Categoria;
import org.juanbrenes.java.jdbc.models.Producto;
import org.juanbrenes.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.juanbrenes.java.jdbc.repositorio.Repositorio;
import org.juanbrenes.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class EjemploJdbcUpdate {
    public static void main(String[] args) {

        try (Connection conn = ConexionBaseDatos.getConnection()){

            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("==============listar============");
            repositorio.listar().forEach(System.out::println);  //muestra el listado de productos

            System.out.println("==============obtener por id============");
            System.out.println(repositorio.porId(2L));  //muestra el producto 2

            System.out.println("==============actualizar nuevo producto============");
            Producto producto = new Producto();
            producto.setId(5L);
            producto.setNombre("Teclado mecanico HP");
            producto.setPrecio(699);
            Categoria categoria = new Categoria();
            categoria.setId(2L);
            producto.setCategoria(categoria);
            repositorio.guardar(producto);
            System.out.println("Se ha actualizado el producto correctamente");
            repositorio.listar().forEach(System.out::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
