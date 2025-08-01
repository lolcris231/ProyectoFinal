package Ej1Intento2;

import java.util.ArrayList;
import java.util.List;

public class ProyectoEmprendimiento extends Proyecto{											//clase proyecto emprendimiento hereda de proyecto
	private double montoTotal, montoActual;														//variables de monto total y actual
	private List<Donacion> historialDonaciones;													//lista de donaciones para el historial
	
	public ProyectoEmprendimiento(String name, String descripcion, String url) {				//constructor
		super(name, descripcion, url);
		// TODO Auto-generated constructor stub
	}

	public ProyectoEmprendimiento(String name, String descripcion, String url, double montoTotal) {	//constructor
		super(name, descripcion, url);
		this.montoTotal = montoTotal;
		this.montoActual = 0.0;
		this.historialDonaciones = new ArrayList<>();
	}

	public double getMontoTotal() {													//getters y setters
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	public double getMontoActual() {
		return montoActual;
	}

	public void setMontoActual(double montoActual) {
		this.montoActual = montoActual;
	}
	
	public List<Donacion> getHistorialDonaciones() {
        return historialDonaciones;
    }
	
	public double calcDonacion(Colaborador colaborador, double monto) {				//metodo para calcular la donacion
		double montoNecesario = getMontoTotal() - getMontoActual();					//calcula recursivamente cuanto hace falta
		double devuelto = 0.0;														//cada que hay una nueva donacion
		
		if (monto > montoNecesario) {						
            devuelto = monto - montoNecesario;										//solo se dona lo necesario, en caso que
            montoActual = montoTotal;												//la donacion sea mayor al monto necesario
            historialDonaciones.add(new Donacion(colaborador, montoNecesario)); 	//agrega una nueva donacion en la lista
        } else {
            montoActual += monto;													//se dona el monto completo, se calcula el actual
            historialDonaciones.add(new Donacion(colaborador, monto)); 				//y tambien se agrega la donacion en la lista
        }
        return devuelto;															//regresa el valor del vuelto si es que lo hay
		}
	
}
