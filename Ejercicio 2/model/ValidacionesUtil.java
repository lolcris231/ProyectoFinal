package Ejercicio2.model; // Declara el paquete al que pertenece esta clase, 'Ejercicio2.model'.

public class ValidacionesUtil { // Define la clase pública ValidacionesUtil.
                                 // Esta clase es una clase de utilidad (utility class),
                                 // lo que significa que contiene métodos estáticos para realizar tareas comunes
                                 // sin necesidad de crear una instancia de la clase.
    
    // Método estático para validar si una cadena representa un monto numérico válido y positivo.
    public static boolean esMontoValido(String monto) { 
        try { // Inicia un bloque try para intentar convertir la cadena a un número.
            double valor = Double.parseDouble(monto); // Intenta convertir la cadena 'monto' a un valor double.
            return valor > 0; // Si la conversión es exitosa, retorna true si el valor es mayor que cero.
        } catch (NumberFormatException e) { // Captura la excepción NumberFormatException si la cadena no puede ser convertida a un double.
            return false; // Si ocurre un error de formato (no es un número válido), retorna false.
        }
    }
    
    // Método estático para validar si una cadena representa un ID numérico válido y positivo.
    public static boolean esIdValido(String id) { 
        try { // Inicia un bloque try para intentar convertir la cadena a un número entero.
            int valor = Integer.parseInt(id); // Intenta convertir la cadena 'id' a un valor entero (int).
            return valor > 0; // Si la conversión es exitosa, retorna true si el valor es mayor que cero.
        } catch (NumberFormatException e) { // Captura la excepción NumberFormatException si la cadena no puede ser convertida a un entero.
            return false; // Si ocurre un error de formato, retorna false.
        }
    }
    
    // Método estático para validar si una cadena representa un nombre válido.
    // Un nombre es válido si no es nulo, no está vacío (después de eliminar espacios en blanco)
    // y tiene al menos 2 caracteres.
    public static boolean esNombreValido(String nombre) { 
        // Comprueba tres condiciones:
        // 1. 'nombre' no es nulo.
        // 2. 'nombre' no está vacío después de eliminar espacios en blanco iniciales/finales.
        // 3. La longitud de 'nombre' (después de eliminar espacios) es mayor o igual a 2.
        return nombre != null && !nombre.trim().isEmpty() && nombre.trim().length() >= 2; 
    }
    
    // Método estático para validar si una cadena representa un número de teléfono válido.
    // Un teléfono es válido si no es nulo, no está vacío (después de eliminar espacios en blanco)
    // y cumple con un patrón de expresión regular para formatos comunes de teléfono.
    public static boolean esTelefonoValido(String telefono) { 
        return telefono != null && !telefono.trim().isEmpty() && // Comprueba que no sea nulo y no esté vacío.
               // Utiliza una expresión regular para verificar el formato del teléfono:
               // - "\\d{3}-\\d{3}-\\d{3}" (ej. 123-456-789)
               // - "|\\d{9}" (o 9 dígitos, ej. 123456789)
               // - "|\\d{10}" (o 10 dígitos, ej. 1234567890)
               telefono.matches("\\d{3}-\\d{3}-\\d{3}|\\d{9}|\\d{10}"); 
    }
    
    // Método estático para formatear un valor double como una cadena de monto de dinero.
    // El formato será "$X.YY" (ej. $123.45).
    public static String formatearMonto(double monto) { 
        return String.format("$%.2f", monto); // Usa String.format para crear una cadena formateada.
                                             // "%.2f" significa un número de punto flotante con 2 decimales.
    }
    
    // Método estático para limpiar una cadena de texto.
    // Si la cadena es nula, retorna una cadena vacía; de lo contrario, retorna la cadena sin espacios en blanco iniciales/finales.
    public static String limpiarTexto(String texto) { 
        return texto == null ? "" : texto.trim(); // Utiliza un operador ternario: si 'texto' es nulo, retorna "", de lo contrario, retorna 'texto.trim()'.
    }
}