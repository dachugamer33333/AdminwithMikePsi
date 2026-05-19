package fes.aragon.unam.administracion.model;

import lombok.Data;

@Data
public class Caja {
    private int id;
    private Producto tipo;
    private int totalCajas;


    public Caja(Producto tipo, int totalRefresco) {
        this.tipo = tipo;
        this.totalCajas= totalRefresco;
    }
}
