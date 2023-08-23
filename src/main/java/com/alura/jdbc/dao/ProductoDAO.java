package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Producto;

// esta va en productoController
//ConnectionFactory factory = new ConnectionFactory();
//final Connection con = factory.recuperaConexion();

public class ProductoDAO {

	final private Connection con;

	public ProductoDAO(Connection con) {
		//super();
		this.con =con;
	}
	// quitar guardar(Producto producto) throws SQLException
	public void guardar(Producto producto) {
		try (con) {
			// con.setAutoCommit(false); // control transacc la tenemos nosotros
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO PRODUCTO " + "(nombre, descripcion, cantidad)" + " VALUES (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			try (statement) {
				statement.setString(1, producto.getNombre());
				statement.setString(2, producto.getDescripcion());
				statement.setInt(3, producto.getCantidad());
				statement.execute();
				final ResultSet resultSet = statement.getGeneratedKeys();
				try (resultSet) {
					while (resultSet.next()) {
						producto.setId(resultSet.getInt(1));
						System.out.println(String.format("Fue insertado el producto: %s", producto));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// cambiar Map<String, String x Producto
	public List<Producto> listar() {
		// List<Map<String, String>> resultado = new ArrayList<>();
		List<Producto> resultado = new ArrayList<>();
		
		try (con) {
			String SQL = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO";
			final PreparedStatement statement = con.prepareStatement(SQL);

			try (statement) {
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();
				// List<Map<String, String>> resultado = new ArrayList<>();
				while (resultSet.next()) {
					// cambiar Map<String, String> x Producto, y HashMap x producto
					Producto fila = new Producto(
							resultSet.getInt("ID"), 
							resultSet.getString("NOMBRE"),
							resultSet.getString("DESCRIPCION"), 
							resultSet.getInt("CANTIDAD"));
						resultado.add(fila);
				} // while
			} // statement
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}

	public int eliminar(int id) {
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
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
		try (con) {
			String sql = "UPDATE PRODUCTO SET " + " NOMBRE = ?" + ", DESCRIPCION = ?" + ", CANTIDAD = ?"
					+ " WHERE ID = ?";
			final PreparedStatement statement = con.prepareStatement(sql);
			try (statement) {
				statement.setString(1, nombre);
				statement.setString(2, descripcion);
				statement.setInt(3, cantidad);
				statement.setInt(4, id);
				statement.execute();
				int updateCount = statement.getUpdateCount(); // cuantas filas modificadas update==1
				// con.close(); suprimir este close x tener try(con)
				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
