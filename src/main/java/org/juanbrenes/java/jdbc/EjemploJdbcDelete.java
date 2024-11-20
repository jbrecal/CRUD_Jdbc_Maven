package org.juanbrenes.java.jdbc;

import org.juanbrenes.java.jdbc.models.Producto;
import org.juanbrenes.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.juanbrenes.java.jdbc.repositorio.Repositorio;
import org.juanbrenes.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;

public class EjemploJdbcDelete {
    public static void main(String[] args) {

        try (Connection conn = ConexionBaseDatos.getConnection()){

            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("==============listar============");
            repositorio.listar().forEach(System.out::println);  //muestra el listado de productos

            System.out.println("==============obtener por id============");
            System.out.println(repositorio.porId(2L));  //muestra el producto 2

            System.out.println("==============eliminar producto============");
            repositorio.eliminar(3L);
            System.out.println("Se ha eliminado el producto correctamente");
            repositorio.listar().forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
