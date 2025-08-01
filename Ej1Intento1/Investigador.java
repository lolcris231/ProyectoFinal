package Ej1Intento1;

public class Investigador extends Usuario {
	  private String paginaWeb;
	    private String descripcionAreaExperiencia;

	    public Investigador(String nombre, String paginaWeb, String descripcionAreaExperiencia) {
	        super(nombre);
	        this.paginaWeb = paginaWeb;
	        this.descripcionAreaExperiencia = descripcionAreaExperiencia;
	    }

	    public String getPaginaWeb() {
	        return paginaWeb;
	    }

	    public void setPaginaWeb(String paginaWeb) {
	        this.paginaWeb = paginaWeb;
	    }

	    public String getDescripcionAreaExperiencia() {
	        return descripcionAreaExperiencia;
	    }

	    public void setDescripcionAreaExperiencia(String descripcionAreaExperiencia) {
	        this.descripcionAreaExperiencia = descripcionAreaExperiencia;
	    }
}
