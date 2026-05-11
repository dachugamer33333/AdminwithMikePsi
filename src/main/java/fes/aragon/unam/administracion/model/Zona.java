package fes.aragon.unam.administracion.model;

import java.util.ArrayList;

public class Zona {

    private int id;
    private String cp;           // código postal
    private String departamento; // nombre del departamento de Perú

    public Zona(int id, String cp, String departamento) {
        this.id = id;
        this.cp = cp;
        this.departamento = departamento;
    }

    public int getId()              { return id; }
    public String getCp()           { return cp; }
    public String getDepartamento() { return departamento; }

    public void setId(int id)                   { this.id = id; }
    public void setCp(String cp)               { this.cp = cp; }
    public void setDepartamento(String dep)    { this.departamento = dep; }

    public static ArrayList<Zona> obtenerDepartamentos() {
        ArrayList<Zona> zonas = new ArrayList<>();

        // Los 25 departamentos oficiales de Perú con sus códigos postales
        zonas.add(new Zona(1,  "04001", "Amazonas"));
        zonas.add(new Zona(2,  "05001", "Áncash"));
        zonas.add(new Zona(3,  "03001", "Apurímac"));
        zonas.add(new Zona(4,  "04401", "Arequipa"));
        zonas.add(new Zona(5,  "05201", "Ayacucho"));
        zonas.add(new Zona(6,  "06001", "Cajamarca"));
        zonas.add(new Zona(7,  "15001", "Callao"));
        zonas.add(new Zona(8,  "08001", "Cusco"));
        zonas.add(new Zona(9,  "09001", "Huancavelica"));
        zonas.add(new Zona(10, "10001", "Huánuco"));
        zonas.add(new Zona(11, "11001", "Ica"));
        zonas.add(new Zona(12, "12001", "Junín"));
        zonas.add(new Zona(13, "13001", "La Libertad"));
        zonas.add(new Zona(14, "14001", "Lambayeque"));
        zonas.add(new Zona(15, "15001", "Lima"));
        zonas.add(new Zona(16, "16001", "Loreto"));
        zonas.add(new Zona(17, "17001", "Madre de Dios"));
        zonas.add(new Zona(18, "18001", "Moquegua"));
        zonas.add(new Zona(19, "19001", "Pasco"));
        zonas.add(new Zona(20, "20001", "Piura"));
        zonas.add(new Zona(21, "21001", "Puno"));
        zonas.add(new Zona(22, "22001", "San Martín"));
        zonas.add(new Zona(23, "23001", "Tacna"));
        zonas.add(new Zona(24, "24001", "Tumbes"));
        zonas.add(new Zona(25, "25001", "Ucayali"));

        return zonas;
    }
}