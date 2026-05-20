package fes.aragon.unam.administracion.model;

import fes.aragon.unam.administracion.ArchivoTrabajador;
import fes.aragon.unam.administracion.dao.ProductoDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorCamiones {
    private static GestorCamiones instancia;
    private ArrayList<Camion> listaCamiones;

    private GestorCamiones() {
        this.listaCamiones = leerDeArchivo();
    }

    public static GestorCamiones getInstance() {
        if (instancia == null) {
            instancia = new GestorCamiones();
        }
        return instancia;
    }

    public void agregarCamion(Camion camion) {
        this.listaCamiones.add(camion);
        guardarEnArchivo(listaCamiones);
    }

    public void editarCamion(Camion editado) {
        for (int i = 0; i < listaCamiones.size(); i++) {
            if (listaCamiones.get(i).getId() == editado.getId()) {
                listaCamiones.set(i, editado);
                break;
            }
        }
        guardarEnArchivo(listaCamiones);
    }

    public ArrayList<Camion> obtenerTodos() {
        return this.listaCamiones;
    }

    public void eliminarCamion(int id) {
        listaCamiones.removeIf(c -> c.getId() == id);
        guardarEnArchivo(listaCamiones);
    }

    public Camion buscarPorId(int id) {
        for (Camion c : listaCamiones) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public int generarId() {
        int max = 0;
        for (Camion c : listaCamiones) {
            if (c.getId() > max) max = c.getId();
        }
        return max + 1;
    }

    private ArrayList<Camion> leerDeArchivo() {
        ArrayList<Camion> camiones = new ArrayList<>();
        File archivo = new File(System.getProperty("user.dir") + "/datos/camiones.txt");

        if (!archivo.exists()) {
            return camiones;
        }

        GestorZonas gestorZonas = GestorZonas.getInstance();
        ArrayList<Trabajador> trabajadores = cargarTrabajadores();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split("\\|", -1);
                if (partes.length >= 3) {
                    int id = Integer.parseInt(partes[0].trim());
                    String matricula = partes[1].trim();
                    String fecha = partes[2].trim();

                    Camion c = new Camion(id, matricula);
                    c.setFecha(fecha);

                    if (partes.length >= 4 && !partes[3].trim().isEmpty()) {
                        int idTrabajador = Integer.parseInt(partes[3].trim());
                        for (Trabajador t : trabajadores) {
                            if (t.getId() == idTrabajador) {
                                c.setTrabajador(t);
                                break;
                            }
                        }
                    }

                    if (partes.length >= 5 && !partes[4].trim().isEmpty()) {
                        String[] idsZonas = partes[4].trim().split(",");
                        for (String idStr : idsZonas) {
                            int idZona = Integer.parseInt(idStr.trim());
                            for (Zona z : gestorZonas.obtenerTodos()) {
                                if (z.getId() == idZona) {
                                    c.agregarZona(z);
                                    break;
                                }
                            }
                        }
                    }

                    if (partes.length >= 6 && !partes[5].trim().isEmpty()) {
                        String[] cajasData = partes[5].trim().split(",");
                        List<Producto> productos = new ProductoDAO().listar();
                        for (String cajaStr : cajasData) {
                            String[] parts = cajaStr.trim().split(":");
                            if (parts.length == 3) {
                                int idCaja = Integer.parseInt(parts[0].trim());
                                int idProducto = Integer.parseInt(parts[1].trim());
                                int cantidad = Integer.parseInt(parts[2].trim());
                                for (Producto p : productos) {
                                    if (p.getId() == idProducto) {
                                        Caja caja = new Caja(p, cantidad);
                                        caja.setId(idCaja);
                                        c.agregarCaja(caja);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    camiones.add(c);
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo camiones.txt: " + e.getMessage());
        }

        return camiones;
    }

    private ArrayList<Trabajador> cargarTrabajadores() {
        try {
            return ArchivoTrabajador.leer("trabajadores.dat");
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarEnArchivo(ArrayList<Camion> camiones) {
        File carpeta = new File(System.getProperty("user.dir") + "/datos");
        if (!carpeta.exists()) carpeta.mkdirs();

        File archivo = new File(carpeta, "camiones.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Camion c : camiones) {
                StringBuilder sb = new StringBuilder();
                sb.append(c.getId()).append("|");
                sb.append(c.getMatricula() != null ? c.getMatricula() : "").append("|");
                sb.append(c.getFecha() != null ? c.getFecha() : "").append("|");
                sb.append(c.getTrabajador() != null ? c.getTrabajador().getId() : "").append("|");
                sb.append(zonasIdsToString(c.getZonas())).append("|");
                sb.append(cajasToString(c.getCajas()));
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando camiones.txt: " + e.getMessage());
        }
    }

    private String cajasToString(ArrayList<Caja> cajas) {
        if (cajas == null || cajas.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cajas.size(); i++) {
            if (i > 0) sb.append(",");
            Caja c = cajas.get(i);
            sb.append(c.getId()).append(":");
            sb.append(c.getTipo() != null ? c.getTipo().getId() : 0).append(":");
            sb.append(c.getTotalCajas());
        }
        return sb.toString();
    }

    private String zonasIdsToString(ArrayList<Zona> zonas) {
        if (zonas == null || zonas.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < zonas.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(zonas.get(i).getId());
        }
        return sb.toString();
    }
}
