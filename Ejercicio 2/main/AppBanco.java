package Ejercicio2.main; // Declara el paquete al que pertenece esta clase.

import javafx.application.Application; // Importa la clase Application de JavaFX, que es la base para todas las aplicaciones JavaFX.
import javafx.scene.Scene; // Importa la clase Scene, que representa el contenido de una ventana.
import javafx.stage.Stage; // Importa la clase Stage, que representa la ventana principal de la aplicación.
import Ejercicio2.view.MenuPrincipal; // Importa la clase MenuPrincipal de tu paquete view, que contendrá la interfaz de usuario del menú.

public class AppBanco extends Application { // Define la clase AppBanco, que extiende Application para ser una aplicación JavaFX.
    
    @Override // Anotación que indica que este método sobrescribe un método de la clase padre (Application).
    public void start(Stage stage) { // El método start es el punto de entrada principal para todas las aplicaciones JavaFX.
                                      // Se le pasa el 'Stage' (ventana principal) que la aplicación utilizará.
        try { // Inicia un bloque try-catch para manejar posibles excepciones durante la inicialización de la UI.
            MenuPrincipal menu = new MenuPrincipal(); // Crea una nueva instancia de tu clase MenuPrincipal, que probablemente construye la interfaz del menú.
            // Crea una nueva escena con el "nodo raíz" (root) de tu MenuPrincipal, y define su ancho y alto iniciales.
            Scene escena = new Scene(menu.getRoot(), 600, 400); 
            
            stage.setTitle("Sistema Bancario"); // Establece el título de la ventana principal de la aplicación.
            stage.setScene(escena); // Asigna la escena creada a la ventana (stage).
            stage.setResizable(true); // Permite que el usuario pueda redimensionar la ventana.
            stage.show(); // Hace visible la ventana principal de la aplicación.
            
        } catch (Exception e) { // Captura cualquier tipo de excepción que pueda ocurrir en el bloque try.
            e.printStackTrace(); // Imprime la traza de la pila de la excepción en la consola, útil para depuración.
            System.err.println("Error al inicializar la aplicación: " + e.getMessage()); // Imprime un mensaje de error más legible en la consola de errores.
        }
    }
    
    public static void main(String[] args) { // El método main es el punto de entrada estándar para cualquier aplicación Java.
        launch(args); // Llama al método launch() de la clase Application, que se encarga de iniciar el ciclo de vida de JavaFX
                      // y de llamar al método start() de esta clase.
    }
}