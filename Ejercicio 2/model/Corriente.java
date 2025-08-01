package Ejercicio2.model; // Declara el paquete al que pertenece esta clase.

public class Corriente extends Cuenta { // Define la clase pública Corriente, que hereda de la clase abstracta Cuenta.
                                        // Esto significa que Corriente es un tipo específico de Cuenta.

    // Constructor de la clase Corriente.
    // Recibe el número de cuenta como parámetro.
    public Corriente(int numero) { 
        super(numero); // Llama al constructor de la clase padre (Cuenta) para inicializar el número de cuenta.
                       // Asume que la clase 'Cuenta' tiene un constructor que acepta un 'int numero'.
                       // Si la 'Cuenta' también inicializa 'saldo' en su constructor, este 'super(numero)' funcionará bien.
    }

    @Override // Anotación que indica que este método sobrescribe un método de la clase padre (Cuenta).
    // Implementa el método abstracto checkCuenta() definido en la clase Cuenta.
    // Este método verifica si se cumplen las restricciones para un retiro en una cuenta corriente.
    // Nota: El diagrama UML muestra checkCuenta() sin parámetros, pero aquí se incluye 'monto' para la verificación del saldo.
    public boolean checkCuenta(double monto) { 
        return saldo >= monto; // Para una cuenta corriente, la única restricción es que el saldo sea suficiente
                               // para cubrir el monto del retiro. Retorna true si hay saldo suficiente, false en caso contrario.
    }

}