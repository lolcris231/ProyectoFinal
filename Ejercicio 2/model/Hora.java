package Ejercicio2.model; // Declara el paquete al que pertenece esta clase.

import java.time.LocalDateTime; // Importa la clase LocalDateTime para trabajar con fechas y horas (año, mes, día, hora, minuto, segundo).
import java.time.format.DateTimeFormatter; // Importa DateTimeFormatter, aunque no se usa directamente en este código, es común para formatear fechas.

public class Hora { // Define la clase pública Hora.
    private static Hora instancia; // Declara una variable estática y privada de tipo Hora. Esta variable almacenará la única instancia de la clase (patrón Singleton).

    private Hora() {} // Constructor privado. Al ser privado, impide que se creen instancias de Hora directamente desde fuera de la clase, asegurando el patrón Singleton.

    public static Hora getInstancia() { // Método estático y público para obtener la única instancia de la clase Hora.
        if (instancia == null) { // Comprueba si la instancia ya ha sido creada. Si es la primera vez que se llama al método, 'instancia' será null.
            instancia = new Hora(); // Si no hay una instancia existente, crea una nueva y la asigna a 'instancia'.
        }
        return instancia; // Retorna la única instancia de la clase Hora.
    }

    public LocalDateTime today() { // Método público que retorna la fecha y hora actuales del sistema.
        return LocalDateTime.now(); // Utiliza LocalDateTime.now() para obtener la fecha y hora actuales.
    }

    public int year(LocalDateTime fecha) { // Método público que extrae y retorna el año de una fecha y hora dadas.
        return fecha.getYear(); // Utiliza el método getYear() de la clase LocalDateTime para obtener el año de la 'fecha' proporcionada.
    }
}