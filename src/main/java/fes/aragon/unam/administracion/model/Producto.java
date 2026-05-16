package fes.aragon.unam.administracion.model;

public class Producto {
    private int id;
    private String nombre;
    private String sabor;
    private String imagen;

    public Producto() {}

    public Producto(int id, String nombre, String sabor, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.sabor = sabor;
        this.imagen = imagen;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getSabor() { return sabor; }
    public String getImagen() { return imagen; }
}
