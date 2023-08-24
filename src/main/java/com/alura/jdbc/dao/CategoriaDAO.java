package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

public class CategoriaDAO {

	private Connection con;

	public CategoriaDAO(Connection con) {
		this.con = con;
	}

	public List<Categoria> listar() {
		List<Categoria> resultado = new ArrayList<>();
		try {
			final PreparedStatement stm = con.prepareStatement("SELECT ID,NOMBRE FROM CATEGORIA");
			try (stm) {
				final ResultSet resultSet = stm.executeQuery();
				try (resultSet) {
					while (resultSet.next()) {
						var categoria = new Categoria(resultSet.getInt("ID"), resultSet.getString("NOMBRE"));
						resultado.add(categoria);
					}
				}
				;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}

	public List<Categoria> listarConProductos() {
		List<Categoria> resultado = new ArrayList<>();
		try {
			var querySelect = "SELECT C.ID,C.NOMBRE, P.ID, P.NOMBRE, P.CANTIDAD FROM CATEGORIA C "
					+ "INNER JOIN PRODUCTO P ON C.ID=P.CATEGORIA_ID";
			System.out.println(querySelect);
			
			final PreparedStatement stm = con.prepareStatement(querySelect);
			try (stm) {
				final ResultSet resultSet = stm.executeQuery();
				try (resultSet) {
					while (resultSet.next()) {
						Integer categoriaId = resultSet.getInt("C.ID");
						String categoriaNomb = resultSet.getString("C.NOMBRE");
						//var categoria = new Categoria(resultSet.getInt("ID"), resultSet.getString("NOMBRE"));
						var categoria = resultado
								.stream()
								.filter(cat -> cat.getId().equals(categoriaId))
								.findAny().orElseGet(()-> {
									Categoria cat = new Categoria(
											categoriaId,
											categoriaNomb);
									resultado.add(cat);
								   return cat;
								});
						Producto producto = new Producto(
								resultSet.getInt("P.ID"),
								resultSet.getString("P.NOMBRE"),
								resultSet.getInt("P.CANTIDAD")
								);
						categoria.agregar(producto);
					}
				 };
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}
}
