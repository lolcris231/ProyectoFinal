package Ej1Intento1;

import java.util.ArrayList;
import java.util.List;

public class Proyecto {
	protected String nombre;
    protected String descripcion;
    protected String urlVideoExplicativo;
    protected Investigador investigadorResponsable;
    protected List<Investigador> investigadoresAsociados; // Máximo 4

    public Proyecto(String nombre, String descripcion, String urlVideoExplicativo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlVideoExplicativo = urlVideoExplicativo;
        this.investigadoresAsociados = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlVideoExplicativo() {
        return urlVideoExplicativo;
    }

    public void setUrlVideoExplicativo(String urlVideoExplicativo) {
        this.urlVideoExplicativo = urlVideoExplicativo;
    }

    public Investigador getInvestigadorResponsable() {
        return investigadorResponsable;
    }

    public void setInvestigadorResponsable(Investigador investigadorResponsable) {
        this.investigadorResponsable = investigadorResponsable;
    }

    public List<Investigador> getInvestigadoresAsociados() {
        return investigadoresAsociados;
    }

    public boolean addInvestigadorAsociado(Investigador inv) {
        if (investigadoresAsociados.size() < 4 && !investigadoresAsociados.contains(inv)) {
            investigadoresAsociados.add(inv);
            return true;
        }
        return false;
    }

    public boolean removeInvestigadorAsociado(Investigador inv) {
        return investigadoresAsociados.remove(inv);
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
