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

	public List<Map<String, String>> listar() throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		try (con) {
			if (con != null) {
				System.out.println("CONEXION OK");
			}
			String SQL = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO";
			// Statement statement = con.createStatement();
			final PreparedStatement statement = con.prepareStatement(SQL);
			try (statement) {
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				List<Map<String, String>> resultado = new ArrayList<>();
				while (resultSet.next()) {
					Map<String, String> fila = new HashMap<>();
					fila.put("ID", String.valueOf(resultSet.getInt("ID")));
					fila.put("NOMBRE", resultSet.getString("NOMBRE"));
					fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
					fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));
					resultado.add(fila);
				}
//				System.out.println("Cerrando la conexión");
//				con.close(); no va por tener try(statement)
				return resultado;
			}
		}
	}

	// Object producto, SE CAMBIA POR HASHMAP
	// luego se cambio hashMap por la clase producto
	//                  Map<String, String> x la clase Producto
	public void guardar(Producto producto) throws SQLException {
//		String nombre = producto.getNombre();
//		String descripcion = producto.getDescripcion();
//		Integer cantidad = producto.getCantidad();
//		Integer maxCantidad = 50; int cantGuardar = 0;

		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		try (con) {
			con.setAutoCommit(false); // control transacc la tenemos nosotros
			final PreparedStatement stmt = con.prepareStatement(
					"INSERT INTO PRODUCTO " + "(nombre, descripcion, cantidad)" + " VALUES (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			try (stmt) {
//				do {
//					cantGuardar = Math.min(cantidad, maxCantidad);
//					// control + 1 = para extraer variables locales
//					// ejecutaRegistro(nombre, descripcion, cantidad, stmt);
					ejecutaRegistro(producto, stmt); // se deja producto y el statement
//					// ojo con cantGuardar
//					cantidad -= maxCantidad;
//				} while (cantidad > 0);
				con.commit(); // todos comandos del loop sean ejecutados
				System.out.println("Commit ejecutado");
			} catch (Exception e) {
				con.rollback();
				System.out.println("RollBack ejecutado");
			}
		}
		// stmt.close(); no se necesiota por try(stmt)
		// con.close(); // coloque cierre BD new
		// se quita close() porque tiene try(con){}
	}

	//                          se recibe con la clase Producto
	private void ejecutaRegistro(Producto producto, PreparedStatement stmt)
			throws SQLException {
		// error que se coloco para ver el funcionamiento del ROLLBACK()
//		if (cantidad<50) {
//			throw new RuntimeException("Ocurrio un error...");
//		}
		stmt.setString(1, producto.getNombre());
		stmt.setString(2, producto.getDescripcion());
		stmt.setInt(3, producto.getCantidad());
		stmt.execute();
		final ResultSet resultSet = stmt.getGeneratedKeys();
		// System.out.println("ResulSet "+resultSet.getInt(1));
		try (resultSet) {
			while (resultSet.next()) {
				producto.setId(resultSet.getInt(1));
				System.out.println(String.format("Fue insertado el producto %s ", producto));
			}
		} // con este try() no se necesita cerrar
			// resultSet.close();
	}

}
