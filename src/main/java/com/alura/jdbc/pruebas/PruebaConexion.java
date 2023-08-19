package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionFactory;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
    	Connection con = new ConnectionFactory().recuperaConexion();
		if (con!=null) {
			System.out.println("CONEXION OK");
		}
        System.out.println("Cerrando la conexión");
        con.close();
    }

}
