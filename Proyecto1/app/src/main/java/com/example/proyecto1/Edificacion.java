package com.example.proyecto1;

public class Edificacion {
    private String titulo;
    private String categoria;
    private String descripcion;
    private String imagen;
    private double latitud = 0.0;
    private double longitud = 0.0;

    public Edificacion(String titulo, String categoria, String descripcion, String imagen) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public Edificacion(String titulo, String categoria, String descripcion, String imagen, double latitud, double longitud) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getTitulo() { return titulo; }
    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }

    @Override
    public String toString() {
        return "Edificacion{" +
                "titulo='" + titulo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
