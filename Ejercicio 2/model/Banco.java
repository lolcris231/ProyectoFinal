package Ejercicio2.model; // Declara el paquete al que pertenece esta clase, 'Ejercicio2.model'.

import java.util.*; // Importa todas las clases del paquete java.util, incluyendo List y ArrayList, que se usarán para colecciones.

// Clase que representa el banco
public class Banco { // Define la clase pública Banco.
 private List<Cliente> clientes; // Declara una variable de instancia privada 'clientes', que es una lista de objetos Cliente.

 // Constructor
 public Banco() { // Define el constructor de la clase Banco.
     clientes = new ArrayList<>(); // Inicializa la lista 'clientes' como una nueva instancia de ArrayList, que es una implementación de List.
 }

 // Agrega cliente
 public void agregarCliente(Cliente cliente) { // Método público para agregar un objeto Cliente a la lista de clientes del banco.
     clientes.add(cliente); // Añade el objeto 'cliente' pasado como argumento a la lista 'clientes'.
 }

 // Método para realizar un depósito en una cuenta de un cliente.
 public double deposito(Cliente cliente, Cuenta cuenta, double monto) { 
	    // Delega la operación de depósito al objeto 'cliente' específico, pasándole la cuenta y el monto.
	    // Asume que el método 'deposito' en la clase Cliente sabe cómo manejar la operación con la cuenta dada.
	    return cliente.deposito(cuenta, monto); 
	}

	// Método para realizar un retiro de una cuenta de un cliente.
	public double retiro(Cliente cliente, Cuenta cuenta, double monto) { 
	    // Delega la operación de retiro al objeto 'cliente' específico, pasándole la cuenta y el monto.
	    // Asume que el método 'retiro' en la clase Cliente sabe cómo manejar la operación con la cuenta dada.
	    return cliente.retiro(cuenta, monto); 
	}

 // Devuelve la lista de clientes
 public List<Cliente> getClientes() { // Método público para obtener la lista de clientes del banco.
     return clientes; // Retorna la lista de objetos Cliente.
 }
}