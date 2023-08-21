package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		
		try(con){
			String sql = "UPDATE PRODUCTO SET " + " NOMBRE = ?" + ", DESCRIPCION = ?" 
					+ ", CANTIDAD = ?" + " WHERE ID = ?";
			final PreparedStatement statement = con.prepareStatement(sql);
			try(statement){
				statement.setString(1, nombre);
				statement.setString(2, descripcion);
				statement.setInt(3, cantidad);
				statement.setInt(4, id);
				statement.execute();
				int updateCount = statement.getUpdateCount(); // cuantas filas modificadas update==1
				// con.close(); suprimir este close x tener try(con) 
				return updateCount;
			}
		}
	}

	// se cambio void por int, porque tiene return
	public int eliminar(Integer id) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		try (con) {
			// Statement statement = con.createStatement();
			String sql = "DELETE FROM PRODUCTO WHERE ID = ?";
			final PreparedStatement statement = con.prepareStatement(sql);
			try (statement) {
				statement.setInt(1, id);
				statement.execute();
				int updateCount = statement.getUpdateCount(); // cuantas filas modificadas delete==1
				// con.close(); se quita con try(con) y try(statement)
				return updateCount;
			}
		}
	}

	// se cambio Map<String, String>
	public List<Producto> listar() {
		return ProductoDAO.listar();
	}

	// Object producto, SE CAMBIA POR HASHMAP
	// luego se cambio hashMap por la clase producto
	//                  Map<String, String> x la clase Producto
	// se quito guardar(Producto producto) throws SQLException
	public void guardar(Producto producto) {
		ProductoDAO productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
		productoDAO.guardar(producto);
	}	

}
