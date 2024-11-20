package org.juanbrenes.java.jdbc.repositorio;

import org.juanbrenes.java.jdbc.models.Categoria;
import org.juanbrenes.java.jdbc.models.Producto;
import org.juanbrenes.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Producto> {

    private Connection getConnection() throws SQLException {  //método privado para obtener la conexión
        return ConexionBaseDatos.getConnection();
    }
    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombre as categoria  FROM productos as p " +
                     "inner join categorias as c ON (p.categoria_id = c.id)")) {
            while (rs.next()) {
                Producto producto = crearProducto(rs);
                productos.add(producto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }



    @Override
    public Producto porId(Long id) {
        Producto producto = null;

        try (PreparedStatement stmt = getConnection().
                prepareStatement("SELECT p.*, c.nombre as categoria  FROM productos as p " +
                        "inner join categorias as c ON (p.categoria_id = c.id) WHERE p.id = ?")){
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) { // anidamos para implementar el auto-close
                if (rs.next()) {
                    producto = crearProducto(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return producto;
    }

    @Override
    public void guardar(Producto producto) {
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE productos SET nombre = ?, precio = ?, categoria_id = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO productos (nombre, precio, categoria_id, fecha_registro) " +
                    "VALUES (?, ?, ?, ?)";
        }

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setLong(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getId());

            if (producto.getId() != null && producto.getId() > 0) {
                stmt.setLong(4, producto.getId());
            } else {
                stmt.setDate(4, new Date(producto.getFechaRegistro().getTime()));
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void eliminar(Long id) {
        try (PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM productos WHERE id = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private static Producto crearProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getLong("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setPrecio(rs.getInt("precio"));
        producto.setFechaRegistro(rs.getDate("fecha_registro"));
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("categoria"));
        producto.setCategoria(categoria);
        return producto;
    }
}
