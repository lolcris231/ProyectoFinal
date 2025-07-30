package proyectoFinal.proyectoFinal;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import juego1.Tablero;

public class visualApp extends Application {

	private Tablero tablero;
	private GridPane gridTablero;
	private Label lblMovimientos;
	private Label lblVidas;
	private Button btnMover;
	private int dificultad = 8; // Tamaño del tablero

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		mostrarPantallaInicial(primaryStage);
	}

	private void mostrarPantallaInicial(Stage stage) {
		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: #12a4ff;");

		Label titulo = new Label("El Pirata Pata de Palo");
		titulo.setFont(Font.font("Times new Roman", 38));
		titulo.setTextFill(Color.GOLD);

		Label instrucciones = new Label("Encuentra el tesoro en menos de 50 movimientos");
		instrucciones.setFont(Font.font("Times new Roman",28));
		instrucciones.setTextFill(Color.DARKRED);

		Button btnJugar = new Button("JUGAR");
		btnJugar.setStyle("-fx-font-size: 20; -fx-background-color: #005500; -fx-text-fill: white; -fx-padding: 10 30;");
		btnJugar.setOnAction(e -> {
			tablero = new Tablero();
			tablero.inicializarTablero(dificultad);
			mostrarPantallaJuego(stage);
		});

		root.getChildren().addAll(titulo,instrucciones, btnJugar);
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle("El Pirata Pata de Palo");
		stage.show();
	}

	private void mostrarPantallaJuego(Stage stage) {
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #0d47a1;");

		// Encabezado
		Label tituloJuego = new Label("BUSCA EL TESORO");

		tituloJuego.setFont(Font.font("Times new Roman", 28));
		tituloJuego.setTextFill(Color.WHITE);
		BorderPane.setAlignment(tituloJuego, Pos.CENTER);
		root.setTop(tituloJuego);

		// Panel central con el tablero
		gridTablero = new GridPane();
		gridTablero.setAlignment(Pos.CENTER);
		gridTablero.setHgap(5);
		gridTablero.setVgap(5);


		StackPane centerPane = new StackPane(gridTablero);
		centerPane.setPadding(new Insets(20));
		root.setCenter(centerPane);

		// Panel inferior con controles
		VBox panelInferior = new VBox(15);
		panelInferior.setAlignment(Pos.CENTER);
		panelInferior.setPadding(new Insets(20));

		lblMovimientos = new Label("Movimientos: 0");
		lblVidas = new Label("Vidas perdidas: 0");
		lblMovimientos.setFont(Font.font("Arial", 18));
		lblVidas.setFont(Font.font("Arial", 18));
		lblMovimientos.setTextFill(Color.WHITE);
		lblVidas.setTextFill(Color.WHITE);

		btnMover = new Button("MOVER PIRATA");
		btnMover.setStyle("-fx-font-size: 18; -fx-background-color: #ff9800; -fx-text-fill: white; -fx-padding: 10 30;");
		btnMover.setOnAction(e -> moverPirata());

		Button btnReiniciar = new Button("REINICIAR JUEGO");
		btnReiniciar.setStyle("-fx-font-size: 14; -fx-background-color: #f44336; -fx-text-fill: white;");
		btnReiniciar.setOnAction(e -> {
			tablero = new Tablero();
			tablero.inicializarTablero(dificultad);
			actualizarTablero();
			btnMover.setDisable(false);
		});

		panelInferior.getChildren().addAll(lblMovimientos, lblVidas, btnMover, btnReiniciar);
		root.setBottom(panelInferior);

		actualizarTablero();

		Scene scene = new Scene(root, 800, 700);
		stage.setScene(scene);
		stage.setTitle("El Pirata Pata de Palo");
	}

	private void actualizarTablero() {
		gridTablero.getChildren().clear();
		int[][] estadoTablero = tablero.getTablero();

		for (int i = 0; i < dificultad; i++) {
			for (int j = 0; j < dificultad; j++) {
				StackPane celda = new StackPane();
				celda.setMinSize(50, 50);
				celda.setMaxSize(50, 50);

				//estilo de las casillas
				int valor = estadoTablero[i][j];
				if (valor == 0) {
					celda.setStyle("-fx-background-color: #fad166; -fx-border-color: #000000;"); // arena
				} else if (valor == 1) {
					celda.setStyle("-fx-background-color: #4051ff; -fx-border-color: #000000;"); // Agua
				} else if (valor == 2) {
					celda.setStyle("-fx-background-color: #995100; -fx-border-color: #000000;"); // Pirata
				} else if (valor == 3) {
					celda.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000;"); // Tesoro
				}

				// Agregar etiqueta con el valor
				Label lblValor = new Label(String.valueOf(valor));
				lblValor.setFont(Font.font("Times new Roman", 16));
				//celda.getChildren().add(lblValor); //numeros que representan el agua, arena, pirata y tesoro

				gridTablero.add(celda, j, i);
			}
		}

		if (lblMovimientos != null && lblVidas != null) {
			lblMovimientos.setText("Movimientos: " + tablero.numMov);
			lblVidas.setText("Vidas perdidas: " + tablero.vidasPerdidas);
		}
	}

	private void moverPirata() {
		int mov = (int)(Math.random() * 4);
		int resultado = 0; // 0: movimiento válido, 1: cayó al agua, 2: encontró tesoro
		int err = 0;
		switch (mov) {
		case 0: // Abajo
			if (tablero.tablero[tablero.posPiratai + 1][tablero.posPirataj] == 1) {
				resultado = 1;
				break;
			}
			tablero.tablero[tablero.posPiratai][tablero.posPirataj] = 0;
			tablero.tablero[tablero.posPiratai + 1][tablero.posPirataj] = 2;
			tablero.posPiratai += 1;
			tablero.numMov++;
			if (tablero.posPiratai == tablero.posTesoroi && tablero.posPirataj == tablero.posTesoroj) {
				resultado = 2;
			}

			break;
		case 1: // Derecha
			if (tablero.tablero[tablero.posPiratai][tablero.posPirataj + 1] == 1) {
				resultado = 1;
				break;
			}
			tablero.tablero[tablero.posPiratai][tablero.posPirataj] = 0;
			tablero.tablero[tablero.posPiratai][tablero.posPirataj + 1] = 2;
			tablero.posPirataj += 1;
			tablero.numMov++;
			if (tablero.posPiratai == tablero.posTesoroi && tablero.posPirataj == tablero.posTesoroj) {
				resultado = 2;
			}
			break;
		case 2: // Arriba
			if (tablero.tablero[tablero.posPiratai - 1][tablero.posPirataj] == 1) {
				resultado = 1;
				break;
			}
			tablero.tablero[tablero.posPiratai][tablero.posPirataj] = 0;
			tablero.tablero[tablero.posPiratai - 1][tablero.posPirataj] = 2;
			tablero.posPiratai -= 1;
			tablero.numMov++;
			if (tablero.posPiratai == tablero.posTesoroi && tablero.posPirataj == tablero.posTesoroj) {
				resultado = 2;
			}
			break;
		case 3: // Izquierda
			if (tablero.tablero[tablero.posPiratai][tablero.posPirataj - 1] == 1) {
				resultado = 1;
				break;
			}
			tablero.tablero[tablero.posPiratai][tablero.posPirataj] = 0;
			tablero.tablero[tablero.posPiratai][tablero.posPirataj - 1] = 2;
			tablero.posPirataj -= 1;
			tablero.numMov++;
			if (tablero.posPiratai == tablero.posTesoroi && tablero.posPirataj == tablero.posTesoroj) {
				resultado = 2;
			}
			break;
		}

		actualizarTablero();

		if(tablero.numMov<50) {

			// Mostrar alertas según el resultado
			if (resultado == 1) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Peligro");
				alert.setHeaderText("El pirata cayó al agua");
				alert.setContentText("¡Cuidado! El pirata ha caído al agua. Se aumentó la cantidad de mi.");
				alert.showAndWait();
				tablero.vidasPerdidas++;
				lblVidas.setText("Vidas perdidas: " + tablero.vidasPerdidas);
			} else if (resultado == 2) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("¡Victoria!");
				alert.setHeaderText("¡Tesoro encontrado!");
				alert.setContentText("El pirata encontró el tesoro en " + tablero.numMov + " movimientos.\n" +
						"Vidas perdidas: " + tablero.vidasPerdidas);
				alert.showAndWait();
				btnMover.setDisable(true);
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Perdiste");
			alert.setHeaderText("El pirata se cansó de buscar el tesoro");
			alert.setContentText("El pirata alcanzó el limite de movimientos");
			alert.showAndWait();
			btnMover.setDisable(true);
		}
	}
}