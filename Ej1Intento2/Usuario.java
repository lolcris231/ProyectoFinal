package Ej1Intento2;

public abstract class Usuario {				//clase abstracta usuario
	private String name;					//cuenta con variable nombre

	public Usuario(String name) {			//constructor
		super();
		this.name = name;
	}

	public String getName() {				//getter
		return name;
	}

	public void setName(String name) {		//setter
		this.name = name;
	}
	
}
