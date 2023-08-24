package com.alura.jdbc.modelo;

public class Producto {
	private Integer id;
	private String nombre;
	private String descripcion;
	private Integer cantidad;
	private Integer categoriaID;
	
	public Producto(int id, String nombre, Integer cantidad) {
		this.id = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	public Producto(Integer id, String nombre, String descripcion, Integer cantidad) {
		//super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}
	
	public Producto(String nombre, String descripcion, Integer cantidad) {
		//super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}
	
	// getters
	public String getNombre() {
		return nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	
	public Integer getId() {
		return id;
	}

	//SETTERS
	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getCategoriaID() {
		return this.categoriaID;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public void setCategoriaID(Integer categoriaID) {
		this.categoriaID = categoriaID;
	}

	@Override
	public String toString() {
		return String.format(
				"{id: %s, nombre: %s, descripcion: %s, cantidad: %d}", 
				this.id,
				this.nombre,
				this.descripcion,
				this.cantidad);
		//return super.toString();
	}
	
}
