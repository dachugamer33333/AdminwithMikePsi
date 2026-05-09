package fes.aragon.unam.administracion.model;

import java.util.ArrayList;

public class GestorTrabajadores {
    private static GestorTrabajadores instacia;
    private ArrayList<Trabajador> trabajadores;
    private GestorTrabajadores()
    {
        this.trabajadores=new ArrayList<>();
    }
    public static GestorTrabajadores getInstance()
    {
        if(instacia == null)
        {
            instacia=new GestorTrabajadores();
        }
        return instacia;
    }

    public void agregarTrabajador(Trabajador trabajador)
    {
        this.trabajadores.add(trabajador);

    }
    public ArrayList<Trabajador> obtenerTrabajadores()
    {
        return trabajadores;
    }
}
