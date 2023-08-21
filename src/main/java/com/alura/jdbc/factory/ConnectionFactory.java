package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;
// hay que incluir esta dependencia en pom.xml
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
<<<<<<< HEAD
	private DataSource datasource;
	
	public ConnectionFactory() {
		// abrir un pool de colecciones
		var pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("root");
		pooledDataSource.setMaxPoolSize(10);  // 10 conexiones
		
		this.datasource = pooledDataSource;
		
=======
		return  DriverManager.getConnection(
                "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
                "root",
                "1224");
>>>>>>> 4b76c0de46d00567a0e5aa6a84a941e16600ba4e
	}
	
	public Connection recuperaConexion() throws SQLException {
		return  this.datasource.getConnection();
	}
	
}
