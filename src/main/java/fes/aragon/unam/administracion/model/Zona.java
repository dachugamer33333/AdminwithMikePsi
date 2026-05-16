package fes.aragon.unam.administracion.model;

import java.io.*;
import java.util.ArrayList;

public class Zona {

    private int id;
    private String departamento;
    private String cp;
    private String referencia;
    private String estado;

    public Zona(int id, String departamento, String cp, String referencia, String estado) {
        this.id = id;
        this.departamento = departamento;
        this.cp = cp;
        this.referencia = referencia;
        this.estado = estado;
    }

    public int getId()              { return id; }
    public String getDepartamento() { return departamento; }
    public String getCp()           { return cp; }
    public String getReferencia()   { return referencia; }
    public String getEstado()       { return estado; }

    public void setCp(String cp)                { this.cp = cp; }
    public void setReferencia(String ref)       { this.referencia = ref; }
    public void setEstado(String estado)        { this.estado = estado; }
    public void setDepartamento(String dep)     { this.departamento = dep; }


    public static ArrayList<Zona> leerDeArchivo() {
        ArrayList<Zona> zonas = new ArrayList<>();
        File archivo = new File(System.getProperty("user.dir") + "/datos/zonas.txt");

        if (!archivo.exists()) {
            zonas = obtenerDepartamentos();
            guardarEnArchivo(zonas);
            return zonas;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(",", 5);
                if (partes.length == 5) {
                    int id = Integer.parseInt(partes[0].trim());
                    String dep  = partes[1].trim();
                    String cp   = partes[2].trim();
                    String ref  = partes[3].trim();
                    String est  = partes[4].trim();
                    zonas.add(new Zona(id, dep, cp, ref, est));
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo zonas.txt: " + e.getMessage());
        }

        return zonas;
    }

    public static void guardarEnArchivo(ArrayList<Zona> zonas) {
        File carpeta = new File(System.getProperty("user.dir") + "/datos");
        if (!carpeta.exists()) carpeta.mkdirs();

        File archivo = new File(carpeta, "zonas.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Zona z : zonas) {
                bw.write(z.getId() + "," +
                        z.getDepartamento() + "," +
                        z.getCp() + "," +
                        z.getReferencia() + "," +
                        z.getEstado());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando zonas.txt: " + e.getMessage());
        }
    }

    public static ArrayList<Zona> obtenerDepartamentos() {
        ArrayList<Zona> zonas = new ArrayList<>();
        zonas.add(new Zona(1,  "Amazonas",      "04001", "", "ACTIVA"));
        zonas.add(new Zona(2,  "Áncash",        "05001", "", "ACTIVA"));
        zonas.add(new Zona(3,  "Apurímac",      "03001", "", "ACTIVA"));
        zonas.add(new Zona(4,  "Arequipa",      "04401", "", "ACTIVA"));
        zonas.add(new Zona(5,  "Ayacucho",      "05201", "", "ACTIVA"));
        zonas.add(new Zona(6,  "Cajamarca",     "06001", "", "ACTIVA"));
        zonas.add(new Zona(7,  "Callao",        "15001", "", "ACTIVA"));
        zonas.add(new Zona(8,  "Cusco",         "08001", "", "ACTIVA"));
        zonas.add(new Zona(9,  "Huancavelica",  "09001", "", "ACTIVA"));
        zonas.add(new Zona(10, "Huánuco",       "10001", "", "ACTIVA"));
        zonas.add(new Zona(11, "Ica",           "11001", "", "ACTIVA"));
        zonas.add(new Zona(12, "Junín",         "12001", "", "ACTIVA"));
        zonas.add(new Zona(13, "La Libertad",   "13001", "", "ACTIVA"));
        zonas.add(new Zona(14, "Lambayeque",    "14001", "", "ACTIVA"));
        zonas.add(new Zona(15, "Lima",          "15001", "", "ACTIVA"));
        zonas.add(new Zona(16, "Loreto",        "16001", "", "ACTIVA"));
        zonas.add(new Zona(17, "Madre de Dios", "17001", "", "ACTIVA"));
        zonas.add(new Zona(18, "Moquegua",      "18001", "", "ACTIVA"));
        zonas.add(new Zona(19, "Pasco",         "19001", "", "ACTIVA"));
        zonas.add(new Zona(20, "Piura",         "20001", "", "ACTIVA"));
        zonas.add(new Zona(21, "Puno",          "21001", "", "ACTIVA"));
        zonas.add(new Zona(22, "San Martín",    "22001", "", "ACTIVA"));
        zonas.add(new Zona(23, "Tacna",         "23001", "", "ACTIVA"));
        zonas.add(new Zona(24, "Tumbes",        "24001", "", "ACTIVA"));
        zonas.add(new Zona(25, "Ucayali",       "25001", "", "ACTIVA"));
        return zonas;
    }

    public static String[] getDepartamentos() {
        return new String[]{
                "Amazonas","Áncash","Apurímac","Arequipa","Ayacucho",
                "Cajamarca","Callao","Cusco","Huancavelica","Huánuco",
                "Ica","Junín","La Libertad","Lambayeque","Lima",
                "Loreto","Madre de Dios","Moquegua","Pasco","Piura",
                "Puno","San Martín","Tacna","Tumbes","Ucayali"
        };
    }
}