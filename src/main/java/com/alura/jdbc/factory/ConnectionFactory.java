package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;
// hay que incluir esta dependencia en pom.xml
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	private DataSource datasource;
	
	public ConnectionFactory() {
		// abrir un pool de colecciones
		var pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("1224");
		pooledDataSource.setMaxPoolSize(10);  // 10 conexiones
		this.datasource = pooledDataSource;
	}
	
	public Connection recuperaConexion() {
		try {
			return  this.datasource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
