package fes.aragon.unam.administracion.model;

import java.util.ArrayList;

public class Camion {
    private int id;
    private String matricula;
    private String fecha;
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

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

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

    public boolean descargarCaja(int id) {
        return cajas.removeIf(c -> c.getId() == id);
    }

    public void asignarConductor(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public int getTotalCajas() {
        return cajas.size();
    }
}
