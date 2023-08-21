package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionFactory;

public class PruebaPoolDeConexiones {
	public static void main(String[] args) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		for (int i = 0; i < 10; i++) {
			Connection conexion = factory.recuperaConexion();
			System.out.println("Abriendo la conexion numero "+(i+1));
		}
	}
}
