package Ej1Intento1;

import java.util.ArrayList;
import java.util.List;

public class ProyectoEmprendimiento extends Proyecto {
	private double montoTotalAFinanciar;
    private double montoActualFinanciado;
    // Mapa para registrar donaciones: Colaborador -> Monto total donado por ese colaborador
    private List<Donacion> historialDonaciones;

    public ProyectoEmprendimiento(String nombre, String descripcion, String urlVideoExplicativo, double montoTotalAFinanciar) {
        super(nombre, descripcion, urlVideoExplicativo);
        this.montoTotalAFinanciar = montoTotalAFinanciar;
        this.montoActualFinanciado = 0.0;
		this.historialDonaciones = new ArrayList<>();
    }

    public double getMontoTotalAFinanciar() {
        return montoTotalAFinanciar;
    }

    public void setMontoTotalAFinanciar(double montoTotalAFinanciar) {
        this.montoTotalAFinanciar = montoTotalAFinanciar;
    }

    public double getMontoActualFinanciado() {
        return montoActualFinanciado;
    }

    public void setMontoActualFinanciado(double montoActualFinanciado) {
        this.montoActualFinanciado = montoActualFinanciado;
    }

    public List<Donacion> getHistorialDonaciones() {
        return historialDonaciones;
    }

    // Restricción: No se puede donar más si el proyecto ya está financiado.
    public double recibirDonacion(Colaborador colaborador, double montoDonado) {
    	double montoNecesario = montoTotalAFinanciar - montoActualFinanciado;
        double devuelto = 0.0;

        if (montoDonado > montoNecesario) {
            devuelto = montoDonado - montoNecesario;
            montoActualFinanciado = montoTotalAFinanciar;
            historialDonaciones.add(new Donacion(colaborador, montoNecesario)); // Solo se dona lo necesario
        } else {
            montoActualFinanciado += montoDonado;
            historialDonaciones.add(new Donacion(colaborador, montoDonado)); // Se dona el monto completo
        }
        return devuelto;
    }
}
