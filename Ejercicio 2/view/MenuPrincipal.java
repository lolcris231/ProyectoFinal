package Ejercicio2.view; // Declara el paquete al que pertenece esta clase.

import Ejercicio2.controller.BancoController; // Importa el controlador del banco, que maneja la lógica de negocio.
import javafx.application.Application; // Importa la clase base para las aplicaciones JavaFX.
import javafx.geometry.Insets; // Para especificar el relleno alrededor de los nodos.
import javafx.geometry.Pos; // Para especificar la alineación de los nodos dentro de un layout.
import javafx.scene.Parent; // La clase base para todos los nodos que tienen hijos en el árbol de escena.
import javafx.scene.Scene; // Representa el contenido de una ventana.
import javafx.scene.control.Button; // Control para botones.
import javafx.scene.control.Label; // Control para etiquetas de texto.
import javafx.scene.effect.DropShadow; // Efecto de sombra para nodos.
import javafx.scene.layout.Background; // Para configurar el fondo de un nodo.
import javafx.scene.layout.BackgroundFill; // Para crear rellenos de fondo.
import javafx.scene.layout.CornerRadii; // Para especificar los radios de las esquinas de un fondo.
import javafx.scene.layout.VBox; // Layout que organiza los nodos verticalmente.
import javafx.scene.paint.Color; // Para definir colores.
import javafx.scene.paint.LinearGradient; // Para crear gradientes lineales.
import javafx.scene.paint.Stop; // Para definir puntos de color en un gradiente.
import javafx.scene.paint.CycleMethod; // Para definir cómo se repite un gradiente.
import javafx.scene.text.Font; // Para especificar la fuente del texto.
import javafx.scene.text.FontWeight; // Para especificar el grosor de la fuente.
import javafx.stage.Stage; // Representa la ventana principal o una ventana secundaria.

public class MenuPrincipal extends Application { // Define la clase MenuPrincipal, que extiende Application para ser una aplicación JavaFX.

    private final BancoController banco = new BancoController(); // Crea una única instancia del BancoController. 'final' indica que no se reasignará.
    private Stage stageClientes; // Referencia a la ventana de gestión de clientes. Se usa para evitar abrir múltiples ventanas.
    private Stage stageCuentas; // Referencia a la ventana de gestión de cuentas.
    private Stage stageTransacciones; // Referencia a la ventana de reporte de transacciones.
    private VBox root; // El nodo raíz de la escena del menú principal.

    // Clase interna estática para definir colores utilizados en la UI.
    // Esto centraliza la configuración de colores y facilita su modificación.
    private static final class Colors {
        // Colores del background (fondo)
        static final Color BACKGROUND_START = Color.web("#2C3E50");  // Azul oscuro (código hexadecimal)
        static final Color BACKGROUND_END = Color.web("#34495E");    // Gris azulado
        
        // Colores de botones
        static final Color BUTTON_NORMAL = Color.web("#3498DB");     // Azul
        static final Color BUTTON_HOVER = Color.web("#2980B9");      // Azul más oscuro (cuando el ratón está sobre el botón)
        static final Color BUTTON_PRESSED = Color.web("#1F618D");    // Azul muy oscuro (cuando el botón está siendo presionado)
        
        // Colores de texto
        static final Color TEXT_PRIMARY = Color.WHITE; // Blanco para el texto principal
        static final Color TEXT_BUTTON = Color.WHITE;  // Blanco para el texto de los botones
        
        // Colores adicionales para efectos y acentos
        static final Color SHADOW = Color.web("#1A252F");           // Sombra oscura
        static final Color ACCENT = Color.web("#E74C3C");           // Rojo para acentos (ej. botón de cuentas)
    }

    public MenuPrincipal() { // Constructor de la clase MenuPrincipal.
        initializeMenu(); // Llama al método para inicializar los componentes del menú.
    }

    private void initializeMenu() { // Método privado para configurar la interfaz del menú principal.
        // Crear título de la ventana del menú
        Label titleLabel = createTitleLabel(); 
        
        // Crear botones con estilos personalizados.
        // Se utiliza la clase Colors para la consistencia.
        Button btnClientes = createStyledButton("👥 Gestión de Clientes", Colors.BUTTON_NORMAL);
        Button btnCuentas = createStyledButton("💳 Gestión de Cuentas", Colors.ACCENT); // Usa color de acento para este botón.
        Button btnTransacciones = createStyledButton("📊 Reporte de Transacciones", Color.web("#17a2b8")); // Usa un color web específico.
        
        // Configurar los eventos (acciones) que se ejecutan al hacer clic en los botones.
        setupButtonEvents(btnClientes, btnCuentas, btnTransacciones);

        // Crear el layout principal (VBox) para organizar los elementos verticalmente.
        root = new VBox(25); // VBox con un espaciado de 25 píxeles entre sus hijos.
        root.setAlignment(Pos.CENTER); // Alinea los hijos del VBox al centro.
        root.setPadding(new Insets(40)); // Establece un relleno de 40 píxeles alrededor del VBox.
        
        // Establecer el fondo del VBox con un gradiente de color.
        setGradientBackground(root);
        
        // Agregar el título y los botones al VBox.
        root.getChildren().addAll(titleLabel, btnClientes, btnCuentas, btnTransacciones);
    }

    private Label createTitleLabel() { // Método privado para crear y configurar la etiqueta del título.
        Label titleLabel = new Label("🏦 Sistema Bancario"); // Crea una nueva etiqueta con el texto.
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28)); // Establece la fuente, grosor y tamaño.
        titleLabel.setTextFill(Colors.TEXT_PRIMARY); // Establece el color del texto.
        
        // Aplicar efecto de sombra al título.
        DropShadow shadow = new DropShadow(); // Crea una nueva sombra.
        shadow.setColor(Colors.SHADOW); // Establece el color de la sombra.
        shadow.setOffsetX(2); // Desplazamiento horizontal de la sombra.
        shadow.setOffsetY(2); // Desplazamiento vertical de la sombra.
        shadow.setRadius(5); // Radio de la sombra (difuminado).
        titleLabel.setEffect(shadow); // Aplica el efecto al título.
        
        return titleLabel; // Retorna la etiqueta configurada.
    }

    private Button createStyledButton(String text, Color baseColor) { // Método privado para crear botones con estilos comunes.
        Button button = new Button(text); // Crea un nuevo botón con el texto dado.
        
        // Configuración básica del botón.
        button.setPrefWidth(250); // Ancho preferido del botón.
        button.setPrefHeight(50); // Altura preferida del botón.
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Fuente del texto del botón.
        
        // Aplica los estilos CSS personalizados al botón.
        applyButtonStyles(button, baseColor); 
        
        return button; // Retorna el botón configurado.
    }

    private void applyButtonStyles(Button button, Color baseColor) { // Método privado para aplicar estilos CSS a un botón.
        // Calcula colores más claros y oscuros para los estados de "hover" (ratón encima) y "pressed" (presionado).
        Color hoverColor = baseColor.deriveColor(0, 1, 1.2, 1); // Deriva un color más brillante.
        Color pressedColor = baseColor.deriveColor(0, 1, 0.8, 1); // Deriva un color más oscuro.
        
        // Define la cadena de estilo CSS base para el botón.
        // Usa `toRGBCode` para convertir los objetos `Color` a cadenas hexadecimales RGB.
        String baseStyle = String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s); " + // Fondo con gradiente lineal.
            "-fx-text-fill: %s; " + // Color del texto.
            "-fx-background-radius: 10; " + // Radio de las esquinas del fondo.
            "-fx-border-radius: 10; " + // Radio de las esquinas del borde.
            "-fx-border-color: %s; " + // Color del borde.
            "-fx-border-width: 2; " + // Ancho del borde.
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 2);", // Efecto de sombra.
            toRGBCode(baseColor.brighter()), // Color más claro para la parte superior del gradiente.
            toRGBCode(baseColor),            // Color base para la parte inferior del gradiente.
            toRGBCode(Colors.TEXT_BUTTON),   // Color del texto del botón.
            toRGBCode(baseColor.darker())    // Color más oscuro para el borde.
        );
        
        // Define la cadena de estilo CSS para el estado "hover".
        String hoverStyle = String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s); " +
            "-fx-text-fill: %s; " +
            "-fx-background-radius: 10; " +
            "-fx-border-radius: 10; " +
            "-fx-border-color: %s; " +
            "-fx-border-width: 2; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 8, 0, 0, 3); " + // Sombra más pronunciada.
            "-fx-scale-x: 1.05; " + // Escala el botón ligeramente en el eje X.
            "-fx-scale-y: 1.05;",   // Escala el botón ligeramente en el eje Y.
            toRGBCode(hoverColor.brighter()),
            toRGBCode(hoverColor),
            toRGBCode(Colors.TEXT_BUTTON),
            toRGBCode(hoverColor.darker())
        );
        
        // Define la cadena de estilo CSS para el estado "pressed".
        String pressedStyle = String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s); " +
            "-fx-text-fill: %s; " +
            "-fx-background-radius: 10; " +
            "-fx-border-radius: 10; " +
            "-fx-border-color: %s; " +
            "-fx-border-width: 2; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);", // Sombra más sutil.
            toRGBCode(pressedColor),
            toRGBCode(pressedColor.darker()),
            toRGBCode(Colors.TEXT_BUTTON),
            toRGBCode(pressedColor.darker())
        );

        button.setStyle(baseStyle); // Establece el estilo base inicial del botón.
        
        // Configura los eventos del ratón para cambiar los estilos del botón dinámicamente.
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));   // Cuando el ratón entra, aplica estilo hover.
        button.setOnMouseExited(e -> button.setStyle(baseStyle));    // Cuando el ratón sale, vuelve al estilo base.
        button.setOnMousePressed(e -> button.setStyle(pressedStyle));  // Cuando el botón es presionado, aplica estilo pressed.
        button.setOnMouseReleased(e -> button.setStyle(hoverStyle)); // Cuando el botón es soltado, vuelve al estilo hover (esto es temporal, luego el mouseExited lo regresará a base).
    }

    private void setGradientBackground(VBox container) { // Método privado para establecer un fondo con gradiente a un contenedor.
        // Crea un gradiente lineal.
        LinearGradient gradient = new LinearGradient(
            0, 0, 0, 1, true, CycleMethod.NO_CYCLE, // Inicio (0,0), Fin (0,1) = vertical; proporcional; no se repite.
            new Stop(0, Colors.BACKGROUND_START), // Punto de color inicial (arriba).
            new Stop(1, Colors.BACKGROUND_END)    // Punto de color final (abajo).
        );
        
        // Crea un relleno de fondo con el gradiente.
        BackgroundFill backgroundFill = new BackgroundFill(
            gradient, 
            CornerRadii.EMPTY, // Sin radios en las esquinas (recto).
            Insets.EMPTY       // Sin relleno adicional en el fondo.
        );
        
        // Establece el fondo del contenedor.
        container.setBackground(new Background(backgroundFill));
    }

    private void setupButtonEvents(Button btnClientes, Button btnCuentas, Button btnTransacciones) { // Método para configurar los eventos de los botones.
        // Evento para el botón de "Gestión de Clientes".
        btnClientes.setOnAction(e -> { // Define la acción cuando se hace clic.
            if (stageClientes == null || !stageClientes.isShowing()) { // Si la ventana de clientes no existe o no está visible.
                ClienteUI clienteUI = new ClienteUI(); // Crea una nueva instancia de ClienteUI.
                clienteUI.setBancoController(banco); // Pasa la instancia del BancoController a la UI de clientes.
                stageClientes = new Stage(); // Crea un nuevo Stage (ventana).
                try {
                    clienteUI.start(stageClientes); // Inicia la UI de clientes en el nuevo Stage.
                    // Configura un evento para cuando la ventana de clientes se cierre,
                    // para que la referencia 'stageClientes' se anule y pueda ser reabierta.
                    stageClientes.setOnCloseRequest(ev -> stageClientes = null); 
                } catch (Exception ex) { // Captura cualquier excepción durante el inicio de la UI.
                    ex.printStackTrace(); // Imprime el rastro de la pila para depuración.
                }
            } else {
                stageClientes.toFront(); // Si la ventana ya está abierta, la trae al frente.
            }
        });

        // Evento para el botón de "Gestión de Cuentas".
        btnCuentas.setOnAction(e -> { // Define la acción cuando se hace clic.
            if (stageCuentas == null || !stageCuentas.isShowing()) { // Si la ventana de cuentas no existe o no está visible.
                CuentaUI cuentaUI = new CuentaUI(); // Crea una nueva instancia de CuentaUI.
                cuentaUI.setBancoController(banco); // Pasa la instancia del BancoController a la UI de cuentas.
                stageCuentas = new Stage(); // Crea un nuevo Stage.
                try {
                    cuentaUI.start(stageCuentas); // Inicia la UI de cuentas.
                    // Configura el evento de cierre de la ventana de cuentas.
                    stageCuentas.setOnCloseRequest(ev -> stageCuentas = null); 
                } catch (Exception ex) { // Captura excepciones.
                    ex.printStackTrace(); // Imprime el rastro de la pila.
                }
            } else {
                stageCuentas.toFront(); // Si ya está abierta, la trae al frente.
            }
        });
        
        // Evento para el botón de "Reporte de Transacciones".
        btnTransacciones.setOnAction(e -> { // Define la acción cuando se hace clic.
            if (stageTransacciones == null || !stageTransacciones.isShowing()) { // Si la ventana de transacciones no existe o no está visible.
                // CORRECCIÓN: Se necesita una clase TransaccionUI o ReporteTransaccionesUI.
                // Suponiendo que existe, aquí se instanciaría y se le pasaría el controlador.
                // Para fines de este comentario, asumimos que existe 'TransaccionUI'.
                TransaccionUI transaccionUI = new TransaccionUI(); // Crea una nueva instancia de TransaccionUI.
                transaccionUI.setBancoController(banco); // Pasa la instancia del BancoController.
                stageTransacciones = new Stage(); // Crea un nuevo Stage.
                try {
                    transaccionUI.start(stageTransacciones); // Inicia la UI de transacciones.
                    // Configura el evento de cierre de la ventana de transacciones.
                    stageTransacciones.setOnCloseRequest(ev -> stageTransacciones = null); 
                } catch (Exception ex) { // Captura excepciones.
                    ex.printStackTrace(); // Imprime el rastro de la pila.
                }
            } else {
                stageTransacciones.toFront(); // Si ya está abierta, la trae al frente.
            }
        });
    }

    // Método utilitario para convertir un objeto `javafx.scene.paint.Color` a su representación de cadena hexadecimal RGB.
    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X", // Formato para tres valores hexadecimales de 2 dígitos.
            (int) (color.getRed() * 255),    // Componente rojo (0-1) escalado a (0-255).
            (int) (color.getGreen() * 255),  // Componente verde.
            (int) (color.getBlue() * 255));  // Componente azul.
    }

    public Parent getRoot() { // Método para obtener el nodo raíz del menú.
        if (root == null) { // Si el nodo raíz no ha sido inicializado (ej. si se llama antes que el constructor o start).
            initializeMenu(); // Lo inicializa.
        }
        return root; // Retorna el nodo raíz.
    }

    @Override // Anotación que indica que este método sobrescribe el método 'start' de la clase Application.
    public void start(Stage primaryStage) { // El método 'start' es el punto de entrada principal para la aplicación JavaFX.
        primaryStage.setTitle("Bienvenido al Sistema Bancario"); // Establece el título de la ventana principal.
        
        if (root == null) { // Asegura que el menú esté inicializado antes de crear la escena.
            initializeMenu();
        }
        
        Scene scene = new Scene(root, 500, 400); // Crea una nueva escena con el nodo raíz y dimensiones iniciales.
        
        // Opcional: Agregar una hoja de estilos CSS externa (descomentado si se usa).
        // scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setScene(scene); // Asigna la escena a la ventana principal.
        primaryStage.show(); // Muestra la ventana principal.
    }

    public static void main(String[] args) { // Método principal, el punto de inicio de la aplicación Java.
        launch(args); // Llama al método `launch` de `Application` para iniciar la aplicación JavaFX.
    }
    
    public BancoController getBancoController() { // Getter para el controlador del banco.
        return banco; // Retorna la instancia del BancoController.
    }
}