package fes.aragon.unam.administracion.model;

import lombok.Data;

@Data
public class Trabajador {
    private int id;
    private String nombre;
    private String apellidoPaterno;
    private String fotoEmpleado;
    private boolean isActive;

    public Trabajador() {

    }

    public String getNombreCompleto()
    {
        return nombre + apellidoPaterno;
    }

}
