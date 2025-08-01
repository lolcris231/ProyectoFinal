package Ejercicio2.model; // Declara el paquete al que pertenece la clase CajaAhorro.

import java.util.stream.Collectors; // Importa Collectors (aunque no se usa directamente en este snippet, es común con streams).

public class CajaAhorro extends Cuenta { // Define la clase CajaAhorro que hereda de la clase abstracta Cuenta.
    private int movAnuales; // Declara un atributo privado para el número máximo de movimientos anuales permitidos.

    // Constructor de CajaAhorro.
    // Asume que la clase Cuenta tiene un constructor que acepta solo 'numero'.
    // Si Cuenta también necesita un saldo inicial, este constructor o el de Cuenta deberán ser ajustados.
    public CajaAhorro(int numero, int maxMovimientos) { 
        super(numero); // Llama al constructor de la clase padre (Cuenta) con el número de cuenta.
        this.movAnuales = maxMovimientos; // Inicializa el número máximo de movimientos anuales permitidos.
    }

    @Override // Anotación que indica que este método sobrescribe un método de la clase padre (Cuenta).
    // El método checkCuenta de CajaAhorro implementa la lógica específica para las cajas de ahorro.
    // Nota: El diagrama UML muestra checkCuenta() sin parámetros, pero aquí se incluye 'monto'.
    // Esto podría requerir un ajuste en la firma del método en la clase Cuenta base si se adhiere estrictamente al UML.
    public boolean checkCuenta(double monto) {
        // Calcula el número de movimientos (transacciones) realizados en el año actual.
        // Se asume que 'transacciones' es un campo accesible (por ejemplo, 'protected' o a través de un getter)
        // en la clase Cuenta y que 't.getFecha()' y 'Hora.getInstancia()' están correctamente implementados.
        long movimientosEsteAnio = transacciones.stream() // Accede al flujo de transacciones de esta cuenta.
            // Filtra las transacciones para incluir solo aquellas que ocurrieron en el año actual.
            // Se asume que Hora.getInstancia().year(fecha) y Hora.getInstancia().today() funcionan correctamente.
            .filter(t -> Hora.getInstancia().year(t.getFecha()) == Hora.getInstancia().year(Hora.getInstancia().today()))
            .count(); // Cuenta el número de transacciones filtradas.
        
        // Retorna true si se cumplen ambas condiciones:
        // 1. El número de movimientos de este año es menor que el máximo permitido.
        // 2. El saldo actual es suficiente para cubrir el monto del retiro.
        return (movimientosEsteAnio < movAnuales) && (saldo >= monto); 
    }
}