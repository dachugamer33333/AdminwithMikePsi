package fes.aragon.unam.administracion.model;

import lombok.Data;

@Data
public class Trabajador {
    private int numTrabajador;
    private String nombre;
    private String ap;
    private String fotoEmpleado;
    private boolean isActive;

    public Trabajador(String nombre, String ap) {
        this.nombre = nombre;
        this.ap = ap;
    }

    public String getNombreCompleto()
    {
        return nombre + ap;
    }

}
