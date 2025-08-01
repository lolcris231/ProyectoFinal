package Ejercicio2.model; // Declara el paquete al que pertenece esta clase.

import java.util.ArrayList; // Importa la clase ArrayList para usar listas dinámicas.
import java.util.List; // Importa la interfaz List para definir colecciones de elementos.

public class Cliente { 
    private int id; // Declara un campo privado 'id' de tipo entero para almacenar el identificador único del cliente.
    private String nombre; // Declara un campo privado 'nombre' de tipo String para almacenar el nombre del cliente.
    private String telefono; // Declara un campo privado 'telefono' de tipo String para almacenar el número de teléfono del cliente.
    private List<Cuenta> cuentas; // Declara un campo privado 'cuentas' que es una lista de objetos Cuenta, representando las cuentas que posee el cliente.

    public Cliente(int id, String nombre, String telefono) { // Constructor de la clase Cliente.
        this.id = id; // Asigna el valor del parámetro 'id' al campo 'id' de la instancia actual.
        this.nombre = nombre; // Asigna el valor del parámetro 'nombre' al campo 'nombre' de la instancia actual.
        this.telefono = telefono; // Asigna el valor del parámetro 'telefono' al campo 'telefono' de la instancia actual.
        this.cuentas = new ArrayList<>(); // Inicializa la lista 'cuentas' como una nueva instancia de ArrayList, lista vacía para las cuentas del cliente.
    }

    public void agregarCuenta(Cuenta cuenta) { // Método público para añadir una cuenta a la lista de cuentas del cliente.
        cuentas.add(cuenta); // Añade el objeto 'cuenta' proporcionado a la lista 'cuentas' del cliente.
    }
    
    public double deposito(Cuenta cuenta, double monto) { // Método para realizar un depósito en una cuenta específica del cliente.
        if (cuentas.contains(cuenta)) { // Comprueba si la cuenta proporcionada pertenece a este cliente.
            return cuenta.deposito(monto, this); // Si la cuenta pertenece al cliente, delega la operación de depósito a la cuenta, pasando el monto y el propio cliente como referencia.
        }
        return -1; // Si la cuenta no pertenece a este cliente, retorna -1 para indicar un error o que la operación no se realizó.
    }

    public double retiro(Cuenta cuenta, double monto) { // Método para realizar un retiro de una cuenta específica del cliente.
        if (cuentas.contains(cuenta)) { // Comprueba si la cuenta proporcionada pertenece a este cliente.
            return cuenta.retiro(monto, this); // Si la cuenta pertenece al cliente, delega la operación de retiro a la cuenta, pasando el monto y el propio cliente como referencia.
        }
        return -1; // Si la cuenta no pertenece a este cliente, retorna -1 para indicar un error o que la operación no se realizó.
    }
  

    public int getId() { return id; } // Método getter para obtener el ID del cliente.
    public String getNombre() { return nombre; } // Método getter para obtener el nombre del cliente.
    public String getTelefono() { return telefono; } // Método getter para obtener el número de teléfono del cliente.
    public List<Cuenta> getCuentas() { return cuentas; } // Método getter para obtener la lista de cuentas del cliente.

    @Override // Anotación que indica que este método sobrescribe el método toString() de la clase Object.
    public String toString() { // Sobrescribe el método toString() para proporcionar una representación de cadena legible del objeto Cliente.
        return nombre; // Simplemente devuelve el nombre del cliente, lo que es útil para mostrar el objeto en interfaces de usuario como ComboBoxes.
    }
}