package Ejercicio2.controller; // Declara el paquete al que pertenece esta clase.

import Ejercicio2.model.*; // Importa todas las clases del paquete model (Cliente, Cuenta, etc.).
import javafx.collections.FXCollections; // Importa la clase FXCollections, necesaria para crear ObservableList.
import javafx.collections.ObservableList; // Importa la interfaz ObservableList, usada para listas que notifican cambios a la UI.
import Ejercicio2.model.Cliente; // Importa explícitamente la clase Cliente del paquete model. (Puede ser redundante si ya se usó Ejercicio2.model.*)
import Ejercicio2.model.Cuenta; // Importa explícitamente la clase Cuenta del paquete model. (Puede ser redundante si ya se usó Ejercicio2.model.*)

public class BancoController { // Define la clase pública BancoController, que actuará como el controlador principal de la lógica de negocio.

    private final ObservableList<Cliente> clientes; // Declara una lista observable de objetos Cliente. 'final' significa que la referencia a la lista no cambiará una vez inicializada.

    public BancoController() { // Constructor de la clase BancoController.
        this.clientes = FXCollections.observableArrayList(); // Inicializa la lista de clientes como una ObservableList vacía, lo que permite que JavaFX la observe para cambios.
    }

    public ObservableList<Cliente> getClientes() { // Método público para obtener la lista observable de clientes.
        return clientes; // Retorna la lista de clientes.
    }

    public void agregarCliente(Cliente cliente) { // Método para añadir un nuevo cliente a la lista.
        clientes.add(cliente); // Añade el objeto Cliente pasado como argumento a la lista de clientes.
    }

    public Cliente buscarClientePorId(int id) { // Método para buscar un cliente por su ID.
        for (Cliente cliente : clientes) { // Itera sobre cada cliente en la lista de clientes.
            if (cliente.getId() == id) { // Comprueba si el ID del cliente actual coincide con el ID buscado.
                return cliente; // Si encuentra una coincidencia, retorna ese objeto Cliente.
            }
        }
        return null; // Si el bucle termina y no se encuentra ningún cliente con el ID, retorna null.
    }

    public void agregarCuentaACliente(int idCliente, Cuenta cuenta) { // Método para agregar una cuenta a un cliente específico.
        Cliente cliente = buscarClientePorId(idCliente); // Busca el cliente utilizando el ID proporcionado.
        if (cliente != null) { // Comprueba si se encontró un cliente con ese ID.
            cliente.agregarCuenta(cuenta); // Si se encuentra el cliente, llama a su método agregarCuenta para añadir la cuenta.
        } else { // Si el cliente no fue encontrado.
            System.out.println("Cliente con ID " + idCliente + " no encontrado."); // Imprime un mensaje en la consola indicando que el cliente no existe.
        }
    }

    public ObservableList<Cuenta> getCuentasDeCliente(int idCliente) { // Método para obtener todas las cuentas de un cliente específico.
        Cliente cliente = buscarClientePorId(idCliente); // Busca el cliente por su ID.
        if (cliente != null) { // Comprueba si el cliente fue encontrado.
            // Retorna una nueva ObservableList que contiene las cuentas del cliente.
            // Se crea una nueva lista para evitar modificar directamente la lista interna del cliente si no es deseado.
            return FXCollections.observableArrayList(cliente.getCuentas()); 
        }
        return FXCollections.observableArrayList(); // Si el cliente no fue encontrado, retorna una ObservableList vacía.   
    }
    
    public ObservableList<Cuenta> getTodasLasCuentas() { // Método para obtener una lista de todas las cuentas de todos los clientes en el sistema.
        ObservableList<Cuenta> todasLasCuentas = FXCollections.observableArrayList(); // Crea una nueva lista observable para almacenar todas las cuentas.
        for (Cliente cliente : clientes) { // Itera sobre cada cliente en la lista de clientes.
            todasLasCuentas.addAll(cliente.getCuentas()); // Añade todas las cuentas del cliente actual a la lista general de cuentas.
        }
        return todasLasCuentas; // Retorna la lista que contiene todas las cuentas del sistema.
    }

    public Cliente buscarClientePorNombre(String nombre) { // Método para buscar un cliente por una parte de su nombre (no sensible a mayúsculas/minúsculas).
        for (Cliente cliente : clientes) { // Itera sobre cada cliente en la lista.
            // Comprueba si el nombre del cliente (en minúsculas) contiene el fragmento de nombre buscado (también en minúsculas).
            if (cliente.getNombre().toLowerCase().contains(nombre.toLowerCase())) { 
                return cliente; // Si encuentra una coincidencia, retorna ese objeto Cliente.
            }
        }
        return null; // Si no se encuentra ningún cliente, retorna null.
    }

    public int getTotalCuentas() { // Método para calcular el número total de cuentas en el sistema.
        int total = 0; // Inicializa un contador para el total de cuentas.
        for (Cliente cliente : clientes) { // Itera sobre cada cliente.
            total += cliente.getCuentas().size(); // Suma el número de cuentas de cada cliente al total.
        }
        return total; // Retorna el número total de cuentas.
    }

    public int getTotalTransacciones() { // Método para calcular el número total de transacciones en el sistema.
        int total = 0; // Inicializa un contador para el total de transacciones.
        for (Cliente cliente : clientes) { // Itera sobre cada cliente.
            for (Cuenta cuenta : cliente.getCuentas()) { // Para cada cliente, itera sobre sus cuentas.
                // Suma el número de transacciones de cada cuenta al total.
                // Requiere que la clase Cuenta tenga un método getTransacciones() que devuelva una colección de transacciones.
                total += cuenta.getTransacciones().size(); 
            }
        }
        return total; // Retorna el número total de transacciones.
    }

    public double getSaldoTotalSistema() { // Método para calcular el saldo total de todas las cuentas en el sistema.
        double total = 0.0; // Inicializa una variable para el saldo total.
        for (Cliente cliente : clientes) { // Itera sobre cada cliente.
            for (Cuenta cuenta : cliente.getCuentas()) { // Para cada cliente, itera sobre sus cuentas.
                total += cuenta.getSaldo(); // Suma el saldo de cada cuenta al saldo total.
            }
        }
        return total; // Retorna el saldo total de todas las cuentas.
    }
}