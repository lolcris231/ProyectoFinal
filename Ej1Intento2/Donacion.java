package Ej1Intento2;

public class Donacion {											//clase donacion para el hashmap
	private Colaborador colaborador;							//variable colaborador
    private double monto;										//variable monto

    public Donacion(Colaborador colaborador, double monto) {	//constructor
        this.colaborador = colaborador;
        this.monto = monto;
    }

    public Colaborador getColaborador() {						//getters y setters
        return colaborador;
    }

    public double getMonto() {
        return monto;
    }

}
