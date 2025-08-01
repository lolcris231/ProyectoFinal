package Ej1Intento2;

import java.util.ArrayList;
import java.util.List;

public abstract class Proyecto {											//clase abstracta proyecto
	private String name, descripcion, url;									//variables nombre, descripcion y url
	protected Investigador1 responsable;									//investigador responsable
    protected List<Investigador1> asociados = new ArrayList<>();			//lista de investigadores asociados

	public Proyecto(String name, String descripcion, String url) {			//constructor para heredar
		super();
		this.name = name;
		this.descripcion = descripcion;
		this.url = url;
	}

	public Proyecto(String name, String descripcion, String url, Investigador1 responsable) {	//constructor
		super();
		this.name = name;
		this.descripcion = descripcion;
		this.url = url;
		this.responsable = responsable;
	}

	public String getName() {															//getters y setters
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Investigador1 getResponsable() {
        return responsable;
    }
	
	public void setInvestigadorResponsable(Investigador1 investigadorResponsable) {
        this.responsable = investigadorResponsable;
    }

    public List<Investigador1> getAsociados() {
        return asociados;
    }

    public boolean agregarAsociado(Investigador1 i) {							//metodos para añadir investigadores asociados
    	if (asociados.size() < 4 && !asociados.contains(i)) {					//se asegura que solo sean 4
            asociados.add(i);													//se asegura que el asociado no se repita
            return true;														
        }
        return false;
    }
    
    public boolean removeAsociado(Investigador1 i) {							//metodo para quitar investigadores asociados
        return asociados.remove(i);
    }
	
}
