package fes.aragon.unam.administracion.model;

import javafx.scene.image.Image;
import lombok.var;

public class Producto {
    private int id;
    private String nombre;
    private String sabor;
    private double precio;
    private String descripcion;
    private String rutaImagen;

    public Producto(int id, String nombre, String sabor,
                    double precio, String descripcion, String rutaImagen) {
        this.id = id;
        this.nombre = nombre;
        this.sabor = sabor;
        this.precio = precio;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSabor() { return sabor; }
    public void setSabor(String sabor) { this.sabor = sabor; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public Image getImagen() {
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            try {
                var stream = getClass().getResourceAsStream(
                        "/fes/aragon/unam/administracion/Imagenes/" + rutaImagen
                );
                if (stream != null) return new Image(stream);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    }
