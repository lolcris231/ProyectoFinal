package Ejercicio2.model; // Declara el paquete al que pertenece esta clase.

import java.util.ArrayList; // Importa la clase ArrayList para usar listas dinámicas.
import java.util.List; // Importa la interfaz List para definir colecciones de elementos.

public abstract class Cuenta { // Define la clase abstracta Cuenta. Una clase abstracta no puede ser instanciada directamente,
                                 // y puede contener métodos abstractos (sin implementación) que deben ser implementados por sus subclases.
    protected int numero; // Declara un campo 'numero' de tipo entero, protegido, para almacenar el número de cuenta.
                          // 'protected' significa que es accesible dentro de la clase, sus subclases y clases del mismo paquete.
    protected double saldo; // Declara un campo 'saldo' de tipo double, protegido, para almacenar el saldo actual de la cuenta.
    protected List<Transaccion> transacciones; // Declara un campo 'transacciones' que es una lista de objetos Transaccion, protegida,
                                               // para almacenar el historial de transacciones de la cuenta.

    public Cuenta(int numero) { // Constructor de la clase Cuenta.
        this.numero = numero; // Inicializa el número de cuenta con el valor proporcionado.
        this.saldo = 0; // Inicializa el saldo de la cuenta a 0 por defecto.
        this.transacciones = new ArrayList<>(); // Inicializa la lista de transacciones como una nueva ArrayList vacía.
    }
    
    public List<Transaccion> getTransacciones() { // Método getter para obtener la lista de transacciones.
        // Retorna una nueva ArrayList que contiene todas las transacciones.
        // Esto es una buena práctica para devolver una copia de la lista,
        // evitando que la lista interna sea modificada directamente desde fuera de la clase.
        return new ArrayList<>(transacciones); 
    }

    // Método para realizar un depósito en la cuenta.
    // Recibe el monto a depositar y el cliente que realiza la operación.
    public double deposito(double monto, Cliente cliente) { 
        saldo += monto; // Aumenta el saldo de la cuenta en el monto depositado.
        // Crea una nueva transacción de tipo DEPOSITO con el monto, la fecha actual del sistema (obtenida del Singleton Hora)
        // y la añade a la lista de transacciones de la cuenta.
        transacciones.add(new Transaccion(TipoTransaccion.DEPOSITO, monto, Hora.getInstancia().today())); 
        return saldo; // Retorna el nuevo saldo de la cuenta.
    }

    // Método para realizar un retiro de la cuenta.
    // Recibe el monto a retirar y el cliente que realiza la operación.
    public double retiro(double monto, Cliente cliente) { 
        // Llama al método abstracto checkCuenta para verificar si el retiro es permitido.
        if (!checkCuenta(monto)) { 
            // Si checkCuenta retorna false (indicando que las restricciones no se cumplen),
            // lanza una excepción RuntimeException.
            throw new RuntimeException("Restricción violada: saldo insuficiente o límite alcanzado."); 
        }
        saldo -= monto; // Disminuye el saldo de la cuenta en el monto retirado.
        // Crea una nueva transacción de tipo RETIRO con el monto, la fecha actual del sistema,
        // y la añade a la lista de transacciones de la cuenta.
        transacciones.add(new Transaccion(TipoTransaccion.RETIRO, monto, Hora.getInstancia().today())); 
        return saldo; // Retorna el nuevo saldo de la cuenta.
    }

    // Método abstracto para verificar las restricciones de la cuenta.
    // Debe ser implementado por las subclases (Corriente, CajaAhorro) para definir su lógica específica.
    // El diagrama UML lo muestra sin parámetros, pero aquí se incluye 'monto' para que las subclases puedan verificar si el saldo es suficiente.
    public abstract boolean checkCuenta(double monto); 
    
    public int getNumero() { return numero; } // Método getter para obtener el número de cuenta.
    public double getSaldo() { return saldo; } // Método getter para obtener el saldo de la cuenta.
    
   
}