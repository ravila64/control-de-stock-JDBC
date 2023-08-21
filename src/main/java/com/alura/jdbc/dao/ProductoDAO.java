package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {

	final private Connection con;

	public ProductoDAO(Connection con) {
		// super();
		this.con = con;
	}

	// quitar guardar(Producto producto) throws SQLException
	public void guardar(Producto producto) {
//		ConnectionFactory factory = new ConnectionFactory();
//		final Connection con = factory.recuperaConexion();
		try {
			try (con) {
				// con.setAutoCommit(false); // control transacc la tenemos nosotros
				final PreparedStatement stmt = con.prepareStatement(
						"INSERT INTO PRODUCTO " + "(nombre, descripcion, cantidad)" + " VALUES (?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				try (stmt) {
					ejecutaRegistro(producto, stmt); // se deja producto y el statement
					con.commit(); // todos comandos del loop sean ejecutados
					System.out.println("Commit ejecutado");
				} catch (SQLException e) {
					//con.rollback();
					//System.out.println("RollBack ejecutado");
					throw new RuntimeException(e);
				}
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//                          se recibe con la clase Producto
	private void ejecutaRegistro(Producto producto, PreparedStatement stmt)
			throws SQLException {
		stmt.setString(1, producto.getNombre());
		stmt.setString(2, producto.getDescripcion());
		stmt.setInt(3, producto.getCantidad());
		stmt.execute();
		final ResultSet resultSet = stmt.getGeneratedKeys();
		try (resultSet) {
			while (resultSet.next()) {
				producto.setId(resultSet.getInt(1));
				System.out.println(String.format("Fue insertado el producto %s ", producto));
			}
		} // con este try() no se necesita cerrar resultSet.close();
	}

	// cambiar Map<String, String x Producto
	public static List<Producto> listar() {
		// List<Map<String, String>> resultado = new ArrayList<>();
		List<Producto> resultado = new ArrayList<>();
//		ProductoDAO productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
		
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		
		try (con) {
			String SQL = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO";
			final PreparedStatement statement = con.prepareStatement(SQL);
			
			try (statement) {
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();
				//List<Map<String, String>> resultado = new ArrayList<>();
				while (resultSet.next()) {
					// cambiar Map<String, String> x Producto, y HashMap x producto
					Producto fila = new Producto(resultSet.getInt("ID"),
							resultSet.getString("NOMBRE"),
							resultSet.getString("DESCRIPCION"),
							resultSet.getInt("CANTIDAD"));
					resultado.add(fila);
				}  // while
			} //statement
			return resultado;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
