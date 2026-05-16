package fes.aragon.unam.administracion.model;

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
}