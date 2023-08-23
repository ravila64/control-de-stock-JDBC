package com.alura.jdbc.controller;

import java.util.List;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {

	private ProductoDAO productoDAO;
	
	public ProductoController() {
		var factory = new ConnectionFactory();
		this.productoDAO = new ProductoDAO(factory.recuperaConexion());
		System.out.println("Conexion en Producto Controller Ok");
	}

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
		return productoDAO.modificar(nombre, descripcion, cantidad, id);
	}

	// se cambio void por int, porque tiene return // se paso a ProductoDao
	public int eliminar(Integer id) {
		return productoDAO.eliminar(id);
	}

	// se cambio Map<String, String>
	public List<Producto> listar() {
		return this.productoDAO.listar();
	}

	// Object producto, SE CAMBIA POR HASHMAP
	// luego se cambio hashMap por la clase producto
	//                  Map<String, String> x la clase Producto
	// se quito guardar(Producto producto) throws SQLException
	public void guardar(Producto producto) {
		productoDAO.guardar(producto);
	}	

}
