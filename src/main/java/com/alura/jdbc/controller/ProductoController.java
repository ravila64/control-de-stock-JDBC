package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public int modificar(Integer id, String nombre, String descripcion,Integer cantidad) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();
		statement.execute("UPDATE PRODUCTO SET "
				+ " NOMBRE = '"+nombre+ "'"
				+ ", DESCRIPCION = '"+descripcion+"'"
				+ ", CANTIDAD = "+cantidad 
				+ " WHERE ID = " + id);
		int updateCount = statement.getUpdateCount(); // cuantas filas modificadas update==1
		con.close();
		return updateCount;
	}

	// se cambio void por int, porque tiene return
	public int eliminar(Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();
		statement.execute("DELETE FROM PRODUCTO  WHERE ID = "+id);
		int updateCount = statement.getUpdateCount(); // cuantas filas modificadas delete==1
		con.close();
		return updateCount;
	}

	public List<Map<String, String>> listar() throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		if (con!=null) {
			System.out.println("CONEXION OK");
		}
	
		String SQL = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO";
		
		//PreparedStatement statement = con.prepareStatement(SQL);
		Statement statement = con.createStatement();
		statement.execute(SQL);
		ResultSet resultSet = statement.getResultSet() ;
		
		List<Map<String, String>> resultado = new ArrayList<>();
		
		while (resultSet.next()) {
			Map<String, String> fila = new HashMap<>();
			fila.put("ID", String.valueOf(resultSet.getInt("ID")));
			fila.put("NOMBRE", resultSet.getString("NOMBRE"));
			fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
			fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));
			resultado.add(fila);
		}
		System.out.println("Cerrando la conexión");
		con.close();
		return resultado;
	}

	// Object producto, SE CAMBIA POR HASHMAP
	public void guardar(Map<String, String> producto) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();	
		Statement statement = con.createStatement();
//		String sql = "INSERT INTO PRODUCTO (nombre, descripcion, cantidad) "
//		+"VALUES('" + producto.get("NOMBRE") + "', '"
//		+producto.get("DESCRIPCION")+"', "
//		+producto.get("CANTIDAD")+ ")"+ ",Statement.RETURN_GENERATED_KEYS+')";
		statement.execute("INSERT INTO PRODUCTO (nombre, descripcion, cantidad) "
				+"VALUES('" + producto.get("NOMBRE") + "', '"
				+producto.get("DESCRIPCION")+"', "
				+producto.get("CANTIDAD")+ ")" , Statement.RETURN_GENERATED_KEYS);
		ResultSet resultSet = statement.getGeneratedKeys();
		//System.out.println("ResulSet "+resultSet+ "\n"+"SQL "+sql);
//		System.out.println("SQL: "+sql);
		//statement.execute(sql);
		//System.out.println("ResulSet "+resultSet.getInt(1));
		while (resultSet.next()) {
			System.out.println(String.format("Fue insertado el producto ID %d ", resultSet.getInt(1)));
		}
		resultSet.close();
		con.close();   // coloque cierre BD new
	}

}

