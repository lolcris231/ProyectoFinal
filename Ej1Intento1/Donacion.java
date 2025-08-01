package Ej1Intento1;

public class Donacion {
	private Colaborador colaborador;
    private double monto;

    public Donacion(Colaborador colaborador, double monto) {
        this.colaborador = colaborador;
        this.monto = monto;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public double getMonto() {
        return monto;
    }

    // Opcional: Para facilitar la depuración o visualización simple
    @Override
    public String toString() {
        return colaborador.getNombre() + ": $" + String.format("%.2f", monto);
    }
}
