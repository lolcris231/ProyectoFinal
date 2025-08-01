package model;

//CuatroEnLineaApp.java
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class CuatroEnRayaApp extends Application {

    private Tablero tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private Label mensajeTurno;
    private Circle[][] celdasUI;
    private VBox pantallaJuego;
    private VBox pantallaBienvenida;
    private VBox pantallaSeleccionModo;
    private Scene scene;
    private Stage primaryStage;
    
    // Variables para modos de juego
    private boolean modoUnJugador = false;
    private boolean turnoComputadora = false;
    private Random random = new Random();
    private String dificultad = "NORMAL"; // FACIL, NORMAL, DIFICIL

    private static final double RADIO_FICHA = 35;
    private static final Color COLOR_VACIO = Color.WHITE;
    private static final Color COLOR_JUGADOR1 = Color.GOLD;
    private static final Color COLOR_JUGADOR2 = Color.CRIMSON;
    private static final Color COLOR_HOVER = Color.LIGHTBLUE;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Crear las pantallas
        crearPantallaBienvenida();
        crearPantallaSeleccionModo();
        crearPantallaJuego();
        
        // Mostrar pantalla de bienvenida inicialmente
        scene = new Scene(pantallaBienvenida, 800, 700);
        
        primaryStage.setTitle("🎮 Cuatro en Línea - Juego Clásico");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void crearPantallaBienvenida() {
        pantallaBienvenida = new VBox(30);
        pantallaBienvenida.setAlignment(Pos.CENTER);
        pantallaBienvenida.setPadding(new Insets(50));
        
        // Fondo degradado
        pantallaBienvenida.setStyle(
            "-fx-background: linear-gradient(to bottom, #1e3c72, #2a5298);"
        );

        // Título principal
        Label titulo = new Label("🔴 CUATRO EN LÍNEA 🟡");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        titulo.setTextFill(Color.WHITE);
        titulo.setEffect(new DropShadow(10, Color.BLACK));

        // Subtítulo
        Label subtitulo = new Label("¡El juego clásico de estrategia!");
        subtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        subtitulo.setTextFill(Color.LIGHTGRAY);

        // Descripción del juego
        Label descripcion = new Label(
            "Conecta cuatro fichas de tu color en línea:\n" +
            "• Horizontal, vertical o diagonal\n" +
            "• Haz clic en las columnas para colocar fichas\n" +
            "• ¡El primero en conseguirlo gana!"
        );
        descripcion.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        descripcion.setTextFill(Color.WHITE);
        descripcion.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Botón de inicio
        Button btnComenzar = new Button("🚀 COMENZAR A JUGAR");
        btnComenzar.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        btnComenzar.setPrefSize(280, 60);
        btnComenzar.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #ff6b6b, #ee5a52);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 30;" +
            "-fx-border-radius: 30;" +
            "-fx-cursor: hand;"
        );
        
        btnComenzar.setOnMouseEntered(e -> btnComenzar.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #ff5252, #d32f2f);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 30;" +
            "-fx-border-radius: 30;" +
            "-fx-cursor: hand;" +
            "-fx-scale-x: 1.05;" +
            "-fx-scale-y: 1.05;"
        ));
        
        btnComenzar.setOnMouseExited(e -> btnComenzar.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #ff6b6b, #ee5a52);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 30;" +
            "-fx-border-radius: 30;" +
            "-fx-cursor: hand;" +
            "-fx-scale-x: 1.0;" +
            "-fx-scale-y: 1.0;"
        ));

        btnComenzar.setOnAction(e -> mostrarSeleccionModo());

        pantallaBienvenida.getChildren().addAll(
            titulo, subtitulo, descripcion, btnComenzar
        );
    }

    private void crearPantallaSeleccionModo() {
        pantallaSeleccionModo = new VBox(25);
        pantallaSeleccionModo.setAlignment(Pos.CENTER);
        pantallaSeleccionModo.setPadding(new Insets(50));
        
        pantallaSeleccionModo.setStyle(
            "-fx-background: linear-gradient(to bottom, #667eea, #764ba2);"
        );

        // Título
        Label titulo = new Label("🎯 SELECCIONA MODO DE JUEGO");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        titulo.setTextFill(Color.WHITE);
        titulo.setEffect(new DropShadow(8, Color.BLACK));

        // Modo 2 Jugadores
        VBox modo2Jugadores = new VBox(15);
        modo2Jugadores.setAlignment(Pos.CENTER);
        
        Button btn2Jugadores = new Button("👥 DOS JUGADORES");
        btn2Jugadores.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        btn2Jugadores.setPrefSize(300, 70);
        btn2Jugadores.setStyle(crearEstiloBoton("#4caf50", "#45a049"));
        btn2Jugadores.setOnAction(e -> iniciarJuego2Jugadores());
        
        Label desc2Jugadores = new Label("Juega contra un amigo en la misma computadora");
        desc2Jugadores.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        desc2Jugadores.setTextFill(Color.LIGHTGRAY);
        
        modo2Jugadores.getChildren().addAll(btn2Jugadores, desc2Jugadores);

        // Modo 1 Jugador
        VBox modo1Jugador = new VBox(15);
        modo1Jugador.setAlignment(Pos.CENTER);
        
        Button btn1Jugador = new Button("🤖 UN JUGADOR");
        btn1Jugador.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        btn1Jugador.setPrefSize(300, 70);
        btn1Jugador.setStyle(crearEstiloBoton("#ff9800", "#f57c00"));
        btn1Jugador.setOnAction(e -> mostrarSeleccionDificultad());
        
        Label desc1Jugador = new Label("Desafía a la computadora con diferentes niveles");
        desc1Jugador.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        desc1Jugador.setTextFill(Color.LIGHTGRAY);
        
        modo1Jugador.getChildren().addAll(btn1Jugador, desc1Jugador);

        // Botón volver
        Button btnVolver = new Button("⬅️ VOLVER");
        btnVolver.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnVolver.setPrefSize(150, 45);
        btnVolver.setStyle(crearEstiloBoton("#607d8b", "#546e7a"));
        btnVolver.setOnAction(e -> volverAlMenuPrincipal());

        pantallaSeleccionModo.getChildren().addAll(
            titulo, modo2Jugadores, modo1Jugador, btnVolver
        );
    }

    private void mostrarSeleccionDificultad() {
        VBox seleccionDificultad = new VBox(20);
        seleccionDificultad.setAlignment(Pos.CENTER);
        seleccionDificultad.setPadding(new Insets(50));
        seleccionDificultad.setStyle("-fx-background: linear-gradient(to bottom, #667eea, #764ba2);");

        Label titulo = new Label("🎚️ SELECCIONA DIFICULTAD");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titulo.setTextFill(Color.WHITE);
        titulo.setEffect(new DropShadow(8, Color.BLACK));

        // Botones de dificultad
        Button btnFacil = crearBotonDificultad("😊 FÁCIL", "La computadora juega al azar", "#4caf50", "FACIL");
        Button btnNormal = crearBotonDificultad("🎯 NORMAL", "La computadora tiene estrategia básica", "#ff9800", "NORMAL");
        Button btnDificil = crearBotonDificultad("🔥 DIFÍCIL", "La computadora es muy inteligente", "#f44336", "DIFICIL");

        Button btnVolverModo = new Button("⬅️ VOLVER");
        btnVolverModo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnVolverModo.setPrefSize(150, 45);
        btnVolverModo.setStyle(crearEstiloBoton("#607d8b", "#546e7a"));
        btnVolverModo.setOnAction(e -> mostrarSeleccionModo());

        seleccionDificultad.getChildren().addAll(
            titulo, btnFacil, btnNormal, btnDificil, btnVolverModo
        );

        // Transición suave
        FadeTransition fade = new FadeTransition(Duration.millis(300), pantallaSeleccionModo);
        fade.setToValue(0);
        fade.setOnFinished(e -> {
            scene.setRoot(seleccionDificultad);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), seleccionDificultad);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
        fade.play();
    }

    private Button crearBotonDificultad(String texto, String descripcion, String color, String nivelDificultad) {
        VBox contenedor = new VBox(8);
        contenedor.setAlignment(Pos.CENTER);

        Button boton = new Button(texto);
        boton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        boton.setPrefSize(350, 60);
        boton.setStyle(crearEstiloBoton(color, color));
        boton.setOnAction(e -> iniciarJuego1Jugador(nivelDificultad));

        Label desc = new Label(descripcion);
        desc.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        desc.setTextFill(Color.LIGHTGRAY);

        // Para mantener la funcionalidad, devolvemos solo el botón principal
        return boton;
    }

    private String crearEstiloBoton(String colorPrimario, String colorHover) {
        return "-fx-background-color: " + colorPrimario + ";" +
               "-fx-text-fill: white;" +
               "-fx-background-radius: 25;" +
               "-fx-border-radius: 25;" +
               "-fx-cursor: hand;" +
               "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 3);";
    }

    private void crearPantallaJuego() {
        tablero = new Tablero();
        
        pantallaJuego = new VBox(20);
        pantallaJuego.setPadding(new Insets(20));
        pantallaJuego.setAlignment(Pos.CENTER);
        pantallaJuego.setStyle(
            "-fx-background: linear-gradient(to bottom, #667eea, #764ba2);"
        );

        // Header con información del juego
        VBox header = crearHeader();
        
        // Tablero de juego
        GridPane gridTablero = crearTableroUI();
        
        // Botones de control
        HBox controles = crearControles();

        pantallaJuego.getChildren().addAll(header, gridTablero, controles);
    }

    private VBox crearHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);

        Label tituloJuego = new Label("🎯 CUATRO EN LÍNEA");
        tituloJuego.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        tituloJuego.setTextFill(Color.WHITE);
        tituloJuego.setEffect(new DropShadow(5, Color.BLACK));

        mensajeTurno = new Label("");
        mensajeTurno.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        mensajeTurno.setTextFill(Color.WHITE);
        mensajeTurno.setStyle(
            "-fx-background-color: rgba(0,0,0,0.3);" +
            "-fx-padding: 10px 20px;" +
            "-fx-background-radius: 20;"
        );

        header.getChildren().addAll(tituloJuego, mensajeTurno);
        return header;
    }

    private GridPane crearTableroUI() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(8);
        gridPane.setVgap(8);
        gridPane.setPadding(new Insets(20));
        gridPane.setStyle(
            "-fx-background-color: #1a237e;" +
            "-fx-background-radius: 15;" +
            "-fx-border-color: #3f51b5;" +
            "-fx-border-width: 4px;" +
            "-fx-border-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 5);"
        );

        celdasUI = new Circle[Tablero.FILAS][Tablero.COLUMNAS];

        // Crear columnas clicables
        for (int col = 0; col < Tablero.COLUMNAS; col++) {
            VBox columna = new VBox(8);
            columna.setAlignment(Pos.CENTER);
            columna.setPrefWidth(RADIO_FICHA * 2 + 10);
            columna.setStyle("-fx-cursor: hand;");
            
            final int currentColumn = col;
            
            // Indicador de columna
            Label indicadorColumna = new Label("↓");
            indicadorColumna.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            indicadorColumna.setTextFill(Color.TRANSPARENT);
            indicadorColumna.setStyle("-fx-padding: 5px;");

            columna.setOnMouseEntered(e -> {
                if (!turnoComputadora) {
                    indicadorColumna.setTextFill(COLOR_HOVER);
                    columna.setStyle("-fx-cursor: hand; -fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 10;");
                }
            });
            
            columna.setOnMouseExited(e -> {
                indicadorColumna.setTextFill(Color.TRANSPARENT);
                columna.setStyle("-fx-cursor: hand;");
            });
            
            columna.setOnMouseClicked(e -> {
                if (!turnoComputadora) {
                    manejarClickColumna(currentColumn);
                }
            });

            // Crear las fichas de la columna
            for (int row = 0; row < Tablero.FILAS; row++) {
                Circle circle = new Circle(RADIO_FICHA, COLOR_VACIO);
                circle.setStroke(Color.DARKBLUE);
                circle.setStrokeWidth(2);
                circle.setEffect(new DropShadow(3, Color.BLACK));
                
                celdasUI[row][col] = circle;
                columna.getChildren().add(circle);
            }

            // Añadir indicador al principio
            columna.getChildren().add(0, indicadorColumna);
            gridPane.add(columna, col, 0);
        }

        return gridPane;
    }

    private HBox crearControles() {
        HBox controles = new HBox(20);
        controles.setAlignment(Pos.CENTER);

        Button btnReiniciar = new Button("🔄 Nueva Partida");
        btnReiniciar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnReiniciar.setPrefSize(150, 40);
        btnReiniciar.setStyle(crearEstiloBoton("#4caf50", "#45a049"));
        btnReiniciar.setOnAction(e -> reiniciarJuego());

        Button btnCambiarModo = new Button("🎮 Cambiar Modo");
        btnCambiarModo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnCambiarModo.setPrefSize(160, 40);
        btnCambiarModo.setStyle(crearEstiloBoton("#2196f3", "#1976d2"));
        btnCambiarModo.setOnAction(e -> mostrarSeleccionModo());

        Button btnMenuPrincipal = new Button("🏠 Menú Principal");
        btnMenuPrincipal.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnMenuPrincipal.setPrefSize(180, 40);
        btnMenuPrincipal.setStyle(crearEstiloBoton("#ff9800", "#f57c00"));
        btnMenuPrincipal.setOnAction(e -> volverAlMenuPrincipal());

        controles.getChildren().addAll(btnReiniciar, btnCambiarModo, btnMenuPrincipal);
        return controles;
    }

    // Métodos de navegación y transiciones
    private void mostrarSeleccionModo() {
        FadeTransition fade = new FadeTransition(Duration.millis(300), scene.getRoot());
        fade.setToValue(0);
        fade.setOnFinished(e -> {
            scene.setRoot(pantallaSeleccionModo);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), pantallaSeleccionModo);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
        fade.play();
    }

    private void volverAlMenuPrincipal() {
        FadeTransition fade = new FadeTransition(Duration.millis(300), scene.getRoot());
        fade.setToValue(0);
        fade.setOnFinished(e -> {
            scene.setRoot(pantallaBienvenida);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), pantallaBienvenida);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
        fade.play();
    }

    private void iniciarJuego2Jugadores() {
        modoUnJugador = false;
        turnoComputadora = false;
        
        jugador1 = new Jugador("Jugador 1", Ficha.BLANCA);
        jugador2 = new Jugador("Jugador 2", Ficha.ROJA);
        jugadorActual = jugador1;
        
        iniciarJuego();
    }

    private void iniciarJuego1Jugador(String nivelDificultad) {
        modoUnJugador = true;
        turnoComputadora = false;
        this.dificultad = nivelDificultad;
        
        jugador1 = new Jugador("Jugador", Ficha.BLANCA);
        jugador2 = new Jugador("Computadora", Ficha.ROJA);
        jugadorActual = jugador1;
        
        iniciarJuego();
    }

    private void iniciarJuego() {
        tablero.inicializarJuego();
        actualizarMensajeTurno();
        
        // Transición suave
        FadeTransition fade = new FadeTransition(Duration.millis(300), scene.getRoot());
        fade.setToValue(0);
        fade.setOnFinished(e -> {
            scene.setRoot(pantallaJuego);
            actualizarTableroUI();
            
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), pantallaJuego);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
        fade.play();
    }

    private void actualizarMensajeTurno() {
        String emoji = jugadorActual.getFicha() == Ficha.BLANCA ? "🟡" : "🔴";
        String modo = modoUnJugador ? " (vs Computadora)" : " (vs Jugador)";
        mensajeTurno.setText("Turno de " + jugadorActual.getNombre() + " " + emoji + modo);
    }

    private void manejarClickColumna(int columna) {
        int filaColocada = tablero.colocarFicha(columna, jugadorActual.getFicha());

        if (filaColocada != -1) {
            actualizarTableroUI();
            animarFichaColocada(filaColocada, columna);

            if (tablero.comprobarGanador(jugadorActual.getFicha(), filaColocada, columna)) {
                String ganador = jugadorActual.getNombre();
                String emoji = jugadorActual.getFicha() == Ficha.BLANCA ? "🟡" : "🔴";
                mostrarAlerta("🎉 ¡Victoria!", "¡Felicidades, " + ganador + "! " + emoji + "\n¡Has conseguido cuatro en línea!");
                
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> reiniciarJuego());
                pause.play();
                return;
            } else if (tablero.estaLleno()) {
                mostrarAlerta("🤝 Empate", "¡El tablero está completo!\nLa partida ha terminado en tablas.");
                
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> reiniciarJuego());
                pause.play();
                return;
            }

            // Cambiar de turno
            cambiarTurno();

        } else {
            mostrarAlerta("⚠️ Columna llena", "Esta columna está completa.\n¡Elige otra columna!");
        }
    }

    private void cambiarTurno() {
        jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
        actualizarMensajeTurno();

        // Si es modo un jugador y ahora es turno de la computadora
        if (modoUnJugador && jugadorActual == jugador2) {
            turnoComputadora = true;
            mensajeTurno.setText("🤖 Computadora pensando...");
            
            // Pausa para simular que la computadora está "pensando"
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                turnoComputadoraJugar();
                turnoComputadora = false;
            });
            pause.play();
        }
    }

    private void turnoComputadoraJugar() {
        int columna = -1;
        
        switch (dificultad) {
            case "FACIL":
                columna = jugarFacil();
                break;
            case "NORMAL":
                columna = jugarNormal();
                break;
            case "DIFICIL":
                columna = jugarDificil();
                break;
        }

        if (columna != -1) {
            manejarClickColumna(columna);
        }
    }

    private int jugarFacil() {
        // Juego completamente aleatorio
        List<Integer> columnasDisponibles = new ArrayList<>();
        for (int i = 0; i < Tablero.COLUMNAS; i++) {
            if (tablero.getFicha(0, i) == Ficha.VACIA) {
                columnasDisponibles.add(i);
            }
        }
        return columnasDisponibles.isEmpty() ? -1 : columnasDisponibles.get(random.nextInt(columnasDisponibles.size()));
    }

    private int jugarNormal() {
        // Primero verifica si puede ganar
        for (int col = 0; col < Tablero.COLUMNAS; col++) {
            if (puedeJugarEnColumna(col)) {
                int fila = obtenerFilaDisponible(col);
                if (simulaVictoria(fila, col, Ficha.ROJA)) {
                    return col;
                }
            }
        }

        // Luego verifica si debe bloquear al jugador
        for (int col = 0; col < Tablero.COLUMNAS; col++) {
            if (puedeJugarEnColumna(col)) {
                int fila = obtenerFilaDisponible(col);
                if (simulaVictoria(fila, col, Ficha.BLANCA)) {
                    return col;
                }
            }
        }

        // Si no, juega al azar
        return jugarFacil();
    }

    private int jugarDificil() {
        // Estrategia más avanzada con evaluación de posiciones
        int mejorColumna = -1;
        int mejorPuntuacion = Integer.MIN_VALUE;

        for (int col = 0; col < Tablero.COLUMNAS; col++) {
            if (puedeJugarEnColumna(col)) {
                int fila = obtenerFilaDisponible(col);
                int puntuacion = evaluarJugada(fila, col, Ficha.ROJA);
                
                if (puntuacion > mejorPuntuacion) {
                    mejorPuntuacion = puntuacion;
                    mejorColumna = col;
                }
            }
        }

        return mejorColumna != -1 ? mejorColumna : jugarNormal();
    }

    private boolean puedeJugarEnColumna(int columna) {
        return columna >= 0 && columna < Tablero.COLUMNAS && tablero.getFicha(0, columna) == Ficha.VACIA;
    }

    private int obtenerFilaDisponible(int columna) {
        for (int fila = Tablero.FILAS - 1; fila >= 0; fila--) {
            if (tablero.getFicha(fila, columna) == Ficha.VACIA) {
                return fila;
            }
        }
        return -1;
    }

    private boolean simulaVictoria(int fila, int columna, Ficha ficha) {
        // Simula colocar la ficha y verifica si gana
        Ficha fichaOriginal = tablero.getFicha(fila, columna);
        // Simular la colocación (no podemos modificar directamente el tablero aquí)
        // Usamos una lógica similar a comprobarGanador pero simulada
        return verificarVictoriaSimulada(fila, columna, ficha);
    }

    private boolean verificarVictoriaSimulada(int fila, int columna, Ficha ficha) {
        // Verificación horizontal
        int contador = 1;
        
        // Izquierda
        for (int c = columna - 1; c >= 0 && tablero.getFicha(fila, c) == ficha; c--) {
            contador++;
        }
        
        // Derecha
        for (int c = columna + 1; c < Tablero.COLUMNAS && tablero.getFicha(fila, c) == ficha; c++) {
            contador++;
        }
        
        if (contador >= 4) return true;

        // Verificación vertical (solo hacia abajo)
        contador = 1;
        for (int f = fila + 1; f < Tablero.FILAS && tablero.getFicha(f, columna) == ficha; f++) {
            contador++;
        }
        
        if (contador >= 4) return true;

        // Verificación diagonal 1 (\)
        contador = 1;
        // Arriba-izquierda
        for (int f = fila - 1, c = columna - 1; f >= 0 && c >= 0 && tablero.getFicha(f, c) == ficha; f--, c--) {
            contador++;
        }
        // Abajo-derecha
        for (int f = fila + 1, c = columna + 1; f < Tablero.FILAS && c < Tablero.COLUMNAS && tablero.getFicha(f, c) == ficha; f++, c++) {
            contador++;
        }
        if (contador >= 4) return true;

        // Verificación diagonal 2 (/)
        contador = 1;
        // Arriba-derecha
        for (int f = fila - 1, c = columna + 1; f >= 0 && c < Tablero.COLUMNAS && tablero.getFicha(f, c) == ficha; f--, c++) {
            contador++;
        }
        // Abajo-izquierda
        for (int f = fila + 1, c = columna - 1; f < Tablero.FILAS && c >= 0 && tablero.getFicha(f, c) == ficha; f++, c--) {
            contador++;
        }
        
        return contador >= 4;
    }

    private int evaluarJugada(int fila, int columna, Ficha ficha) {
        int puntuacion = 0;

        // Verificar si esta jugada gana inmediatamente
        if (verificarVictoriaSimulada(fila, columna, ficha)) {
            return 1000;
        }

        // Verificar si esta jugada bloquea una victoria del oponente
        Ficha fichaOponente = (ficha == Ficha.ROJA) ? Ficha.BLANCA : Ficha.ROJA;
        if (verificarVictoriaSimulada(fila, columna, fichaOponente)) {
            return 500;
        }

        // Preferir el centro
        if (columna == 3) puntuacion += 10;
        if (columna == 2 || columna == 4) puntuacion += 5;

        // Evaluar conexiones potenciales
        puntuacion += evaluarConexiones(fila, columna, ficha);

        return puntuacion;
    }

    private int evaluarConexiones(int fila, int columna, Ficha ficha) {
        int puntuacion = 0;
        
        // Evaluar en las 4 direcciones
        int[][] direcciones = {{0,1}, {1,0}, {1,1}, {1,-1}};
        
        for (int[] dir : direcciones) {
            int conectadas = contarFichasEnDireccion(fila, columna, dir[0], dir[1], ficha) +
                           contarFichasEnDireccion(fila, columna, -dir[0], -dir[1], ficha);
            
            if (conectadas >= 2) puntuacion += conectadas * 2;
        }
        
        return puntuacion;
    }

    private int contarFichasEnDireccion(int fila, int columna, int deltaFila, int deltaColumna, Ficha ficha) {
        int contador = 0;
        int f = fila + deltaFila;
        int c = columna + deltaColumna;
        
        while (f >= 0 && f < Tablero.FILAS && c >= 0 && c < Tablero.COLUMNAS && 
               tablero.getFicha(f, c) == ficha) {
            contador++;
            f += deltaFila;
            c += deltaColumna;
        }
        
        return contador;
    }

    private void animarFichaColocada(int fila, int columna) {
        Circle ficha = celdasUI[fila][columna];
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), ficha);
        scale.setFromX(0.1);
        scale.setFromY(0.1);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.play();
    }

    private void actualizarTableroUI() {
        for (int r = 0; r < Tablero.FILAS; r++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                Ficha fichaEnCelda = tablero.getFicha(r, c);
                Color color;
                if (fichaEnCelda == Ficha.BLANCA) {
                    color = COLOR_JUGADOR1;
                } else if (fichaEnCelda == Ficha.ROJA) {
                    color = COLOR_JUGADOR2;
                } else {
                    color = COLOR_VACIO;
                }
                celdasUI[r][c].setFill(color);
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        
        // Personalizar el estilo de la alerta
        alert.getDialogPane().setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #667eea, #764ba2);" +
            "-fx-font-family: Arial;" +
            "-fx-font-size: 14px;"
        );
        
        alert.showAndWait();
    }

    private void reiniciarJuego() {
        tablero.inicializarJuego();
        actualizarTableroUI();
        jugadorActual = jugador1;
        turnoComputadora = false;
        actualizarMensajeTurno();
    }

    public static void main(String[] args) {
        launch(args);
    }
}