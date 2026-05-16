package fes.aragon.unam.administracion.model;

import java.util.ArrayList;

public class Camion {
    private int id;
    private String matricula;
    private ArrayList<Caja> cajas = new ArrayList<>();
    private ArrayList<Zona> zonas = new ArrayList<>();
    private Trabajador trabajador;
    private double ventasTotales;

    public Camion() {}

    public Camion(int id, String matricula) {
        this.id = id;
        this.matricula = matricula;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public ArrayList<Caja> getCajas() { return cajas; }
    public ArrayList<Zona> getZonas() { return zonas; }

    public Trabajador getTrabajador() { return trabajador; }
    public void setTrabajador(Trabajador trabajador) { this.trabajador = trabajador; }

    public double getVentasTotales() { return ventasTotales; }

    public void agregarCaja(Caja caja) {
        for (Caja cajalocal : cajas) {
            if (cajalocal.getTipo().equals(caja.getTipo())) {
                return;
            }
        }
        cajas.add(caja);
    }

    public void agregarZona(Zona zona) {
        zonas.add(zona);
    }

    public void descargarCaja(int id) {
        zonas.remove(id);
    }

    public void asignarConductor(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void calcularVentasTotales() {
        for (Caja caja : cajas) {
            this.ventasTotales += caja.getTotalRefresco();
        }
    }

    public int getTotalCajas() {
        return cajas.size();
    }
}
