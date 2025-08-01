package Ejercicio2.model; // Declara el paquete al que pertenece esta clase.

import java.time.LocalDateTime; // Importa la clase LocalDateTime para manejar la fecha y hora de la transacción.

public class Transaccion { // Define la clase pública Transaccion.
    private TipoTransaccion tipo; // Declara un campo privado 'tipo' de tipo TipoTransaccion (un enumerado), para indicar si es un depósito o retiro.
    private double monto; // Declara un campo privado 'monto' de tipo double, para almacenar la cantidad de dinero de la transacción.
    private LocalDateTime fecha; // Declara un campo privado 'fecha' de tipo LocalDateTime, para almacenar la fecha y hora exacta de la transacción.

    // Constructor de la clase Transaccion.
    // Recibe el tipo, monto y fecha de la transacción como parámetros.
    public Transaccion(TipoTransaccion tipo, double monto, LocalDateTime fecha) { 
        this.tipo = tipo; // Asigna el valor del parámetro 'tipo' al campo 'tipo' de la instancia actual.
        this.monto = monto; // Asigna el valor del parámetro 'monto' al campo 'monto' de la instancia actual.
        this.fecha = fecha; // Asigna el valor del parámetro 'fecha' al campo 'fecha' de la instancia actual.
    }

    public TipoTransaccion getTipo() { return tipo; } // Método getter para obtener el tipo de transacción.
    public double getMonto() { return monto; } // Método getter para obtener el monto de la transacción.
    public LocalDateTime getFecha() { return fecha; } // Método getter para obtener la fecha y hora de la transacción.
}