package Ej1Intento2;

public class Investigador1 extends Usuario {									//clase investigador hereda de usuario
	private String paginaWeb, descripcion;										//dos variables pagina web y descripcion

	public Investigador1(String name, String paginaWeb, String descripcion) {	//constructor con las dos variables y la heredada
		super(name);
		this.paginaWeb = paginaWeb;
		this.descripcion = descripcion;
	}

	public String getPaginaWeb() {												//getters y setters
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
