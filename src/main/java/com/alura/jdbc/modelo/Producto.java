package com.alura.jdbc.modelo;

public class Producto {
	private Integer id;
	private String nombre;
	private String descripcion;
	private Integer cantidad;
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
	//SETTERS
	public void setId(Integer id) {
		this.id = id;
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
