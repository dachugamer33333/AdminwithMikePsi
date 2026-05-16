package fes.aragon.unam.administracion.model;

import java.util.ArrayList;

public class GestorZonas {

    private static GestorZonas instancia;
    private ArrayList<Zona> listaZonas;

    private GestorZonas() {
        this.listaZonas = Zona.leerDeArchivo();
    }

    public static GestorZonas getInstance() {
        if (instancia == null) {
            instancia = new GestorZonas();
        }
        return instancia;
    }

    public ArrayList<Zona> obtenerTodos() {
        return listaZonas;
    }

    public void agregarZona(Zona zona) {
        listaZonas.add(zona);
        Zona.guardarEnArchivo(listaZonas);
    }

    public void eliminarZona(int id) {
        listaZonas.removeIf(z -> z.getId() == id);
        Zona.guardarEnArchivo(listaZonas);
    }

    public void editarZona(int id, String cp, String referencia) {
        for (Zona z : listaZonas) {
            if (z.getId() == id) {
                z.setCp(cp);
                z.setReferencia(referencia);
                break;
            }
        }
        Zona.guardarEnArchivo(listaZonas);
    }

    public void toggleEstado(int id) {
        for (Zona z : listaZonas) {
            if (z.getId() == id) {
                if (z.getEstado().equals("ACTIVA")) {
                    z.setEstado("BLOQUEADA");
                } else {
                    z.setEstado("ACTIVA");
                }
                break;
            }
        }
        Zona.guardarEnArchivo(listaZonas);
    }

    public int generarId() {
        int max = 0;
        for (Zona z : listaZonas) {
            if (z.getId() > max) max = z.getId();
        }
        return max + 1;
    }
}