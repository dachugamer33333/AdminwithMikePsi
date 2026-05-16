package fes.aragon.unam.administracion.model;

import java.util.ArrayList;

public class Camion {
    private int id;
    private  String matricula;
    private ArrayList<Caja> cajas = new ArrayList<>();
    private ArrayList<Zona> zonas = new ArrayList<>();
    private Trabajador trabajador;
    private double ventasTotales;

    public void agregarCaja(Caja caja)
    {
      for (Caja cajalocal : cajas)
      {
          if(cajalocal.getTipo().equals(caja.getTipo()))
          {
              return;
          }

      }
        cajas.add(caja);



    }

    public void agregarZona(Zona zona)
    {
        zonas.add(zona);
    }
    public void descargarCaja(int id)
    {
        zonas.remove(id);
    }
    public void asignarConductor(Trabajador trabajador)
    {
        this.trabajador=trabajador;
    }
    public void calcularVentasTotales()
    {

        for(Caja caja : cajas)
        {
            this.ventasTotales+=caja.getTotalRefresco();
        }
    }
    public int getTotalCajas()
    {

        return 0;
    }

}
