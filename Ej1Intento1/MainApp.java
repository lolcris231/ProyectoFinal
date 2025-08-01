package Ej1Intento1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class MainApp extends Application {
	
    private ObservableList<Investigador> investigadores = FXCollections.observableArrayList(
            new Investigador("Ana Torres", "https://ana.com", "Experta en IA"),
            new Investigador("Luis Pérez", "https://luis.com", "Especialista en redes"),
            new Investigador("Marta García", "https://marta.org", "Investigadora en robótica"),
            new Investigador("Pedro Gómez", "https://pedro.edu", "Científico de datos"),
            new Investigador("Laura Díaz", "https://laura.net", "Desarrolladora de software"),
            new Investigador("Javier Ruiz", "https://javier.tech", "Especialista en ciberseguridad"),
            new Investigador("Sofía Castro", "https://sofia.med", "Investigadora biomédica"),
            new Investigador("Daniel Blanco", "https://daniel.art", "Diseñador UX/UI"),
            new Investigador("Carla Vargas", "https://carla.blog", "Experta en sostenibilidad"),
            new Investigador("Ricardo Morales", "https://ricardo.ai", "Ingeniero de aprendizaje automático")
    );
    private ObservableList<Proyecto> proyectos = FXCollections.observableArrayList();
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();

    private Map<String, Map<ProyectoEmprendimiento, Double>> historialDonacionesGlobal = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Carga de Proyectos");

        Button btnColaborar = new Button("Colaborar");
        Button btnCrearProyecto = new Button("Crear proyecto");

        btnColaborar.setOnAction(e -> abrirVentanaColaborar(primaryStage));
        btnCrearProyecto.setOnAction(e -> abrirVentanaCrearProyecto(primaryStage));

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(new Label("Bienvenido al Sistema de Innovación"), btnColaborar, btnCrearProyecto);
        root.setPadding(new Insets(20));
        
        Scene scene = new Scene(root, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void abrirVentanaCrearProyecto(Stage ownerStage) {
        Stage crearProyectoStage = new Stage();
        crearProyectoStage.setTitle("Sistema de Proyectos");
        crearProyectoStage.initModality(Modality.APPLICATION_MODAL);
        crearProyectoStage.initOwner(ownerStage);

        TextField txtNombreUsuario = new TextField();
        txtNombreUsuario.setPromptText("Nombre del Usuario Creador");
        TextField txtNombreProyecto = new TextField();
        txtNombreProyecto.setPromptText("Nombre del Proyecto");
        TextArea txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Descripción");
        txtDescripcion.setWrapText(true);
        TextField txtUrlVideo = new TextField();
        txtUrlVideo.setPromptText("URL Video Explicativo");

        Button btnEmprendimiento = new Button("Emprendimiento");
        Button btnInvestigacion = new Button("Investigación");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Ingresar Datos del Proyecto:"),
                txtNombreUsuario,
                txtNombreProyecto,
                txtDescripcion,
                txtUrlVideo,
                new HBox(10, btnEmprendimiento, btnInvestigacion)
        );

        btnEmprendimiento.setOnAction(e -> {
            try {
                if (txtNombreUsuario.getText().isEmpty() || txtNombreProyecto.getText().isEmpty() ||
                    txtDescripcion.getText().isEmpty() || txtUrlVideo.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
                    return;
                }

                String nombreUsuario = txtNombreUsuario.getText();
                String nombreProyecto = txtNombreProyecto.getText();
                String descripcion = txtDescripcion.getText();
                String url = txtUrlVideo.getText();

                abrirVentanaMontoEmprendimiento(crearProyectoStage, nombreUsuario, nombreProyecto, descripcion, url);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error al procesar: " + ex.getMessage());
            }
        });

        btnInvestigacion.setOnAction(e -> {
            try {
                if (txtNombreUsuario.getText().isEmpty() || txtNombreProyecto.getText().isEmpty() ||
                    txtDescripcion.getText().isEmpty() || txtUrlVideo.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
                    return;
                }

                String nombreUsuario = txtNombreUsuario.getText();
                String nombreProyecto = txtNombreProyecto.getText();
                String descripcion = txtDescripcion.getText();
                String url = txtUrlVideo.getText();

                abrirVentanaRegistroInvestigadores(crearProyectoStage, nombreUsuario, nombreProyecto, descripcion, url, 0.0, false);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error al procesar: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(layout, 400, 450);
        crearProyectoStage.setScene(scene);
        crearProyectoStage.show();
    }

    private void abrirVentanaMontoEmprendimiento(Stage ownerStage, String nombreUsuario, String nombreProyecto, String descripcion, String url) {
        Stage montoStage = new Stage();
        montoStage.setTitle("Monto a Financiar");
        montoStage.initModality(Modality.APPLICATION_MODAL);
        montoStage.initOwner(ownerStage);

        Label lblMonto = new Label("Monto total a financiar:");
        TextField txtMonto = new TextField();
        txtMonto.setPromptText("Ej. 1000.00");
        Button btnSiguiente = new Button("Siguiente");

        btnSiguiente.setOnAction(e -> {
            try {
                double monto = Double.parseDouble(txtMonto.getText());
                if (monto <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Error", "El monto debe ser un número positivo.");
                    return;
                }
                montoStage.close();
                abrirVentanaRegistroInvestigadores(ownerStage, nombreUsuario, nombreProyecto, descripcion, url, monto, true);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error de Formato", "Por favor, ingrese un número válido para el monto.");
            }
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(lblMonto, txtMonto, btnSiguiente);

        Scene scene = new Scene(layout, 300, 150);
        montoStage.setScene(scene);
        montoStage.showAndWait();
    }

    private void abrirVentanaRegistroInvestigadores(Stage ownerStage, String nombreUsuarioCreador, String nombreProyecto, String descripcion, String url, double monto, boolean esEmprendimiento) {
        Stage registroInvStage = new Stage();
        registroInvStage.setTitle("Registro de Investigadores");
        registroInvStage.initModality(Modality.APPLICATION_MODAL);
        registroInvStage.initOwner(ownerStage);

        Label lblResponsable = new Label("Investigador Responsable:");
        TextField txtResponsable = new TextField();
        txtResponsable.setPromptText("Nombre del Investigador Responsable");

        Label lblAsociados = new Label("Investigadores Asociados (Máx. 4):");
        TableView<Investigador> tvTodosInvestigadores = new TableView<>(investigadores);
        TableColumn<Investigador, String> colNombreInv = new TableColumn<>("Nombre");
        colNombreInv.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombreInv.setPrefWidth(150);
        colNombreInv.setStyle("-fx-alignment: CENTER;");
        tvTodosInvestigadores.getColumns().add(colNombreInv);

        tvTodosInvestigadores.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        Button btnAddAsociado = new Button("Añadir Asociado");
        Button btnRemoveAsociado = new Button("Quitar Asociado");
        Button btnContinuar = new Button("Continuar");

        ObservableList<Investigador> investigadoresSeleccionados = FXCollections.observableArrayList();
        TableView<Investigador> tvAsociadosSeleccionados = new TableView<>(investigadoresSeleccionados);
        TableColumn<Investigador, String> colNombreAsociado = new TableColumn<>("Investigadores Seleccionados");
        colNombreAsociado.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombreAsociado.setPrefWidth(200);
        colNombreAsociado.setStyle("-fx-alignment: CENTER;");
        tvAsociadosSeleccionados.getColumns().add(colNombreAsociado);

        btnAddAsociado.setOnAction(e -> {
            ObservableList<Investigador> selected = tvTodosInvestigadores.getSelectionModel().getSelectedItems();
            for (Investigador inv : selected) {
                if (investigadoresSeleccionados.size() < 4 && !investigadoresSeleccionados.contains(inv)) {
                    investigadoresSeleccionados.add(inv);
                } else if (investigadoresSeleccionados.contains(inv)) {
                    showAlert(Alert.AlertType.INFORMATION, "Duplicado", inv.getNombre() + " ya está en la lista de asociados.");
                } else {
                    showAlert(Alert.AlertType.WARNING, "Límite Excedido", "Solo se pueden añadir hasta 4 investigadores asociados.");
                    break;
                }
            }
            tvTodosInvestigadores.getSelectionModel().clearSelection();
        });

        btnRemoveAsociado.setOnAction(e -> {
            Investigador selected = tvAsociadosSeleccionados.getSelectionModel().getSelectedItem();
            if (selected != null) {
                investigadoresSeleccionados.remove(selected);
            } else {
                showAlert(Alert.AlertType.WARNING, "Advertencia", "Seleccione un investigador asociado para quitar.");
            }
        });

        btnContinuar.disableProperty().bind(
                txtResponsable.textProperty().isEmpty().or(
                        javafx.beans.binding.Bindings.size(investigadoresSeleccionados).isNotEqualTo(4)
                )
        );

        btnContinuar.setOnAction(e -> {
            if (investigadoresSeleccionados.size() != 4) {
                showAlert(Alert.AlertType.WARNING, "Advertencia", "Tiene que seleccionar exactamente 4 investigadores asociados.");
                return;
            }

            String nombreResponsable = txtResponsable.getText().trim();
            if (nombreResponsable.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Debe ingresar el nombre del investigador responsable.");
                return;
            }

            Investigador responsable = investigadores.stream()
                    .filter(inv -> inv.getNombre().equalsIgnoreCase(nombreResponsable))
                    .findFirst()
                    .orElseGet(() -> {
                        showAlert(Alert.AlertType.INFORMATION, "Nuevo Investigador", "El investigador responsable '" + nombreResponsable + "' no fue encontrado y será creado como nuevo.");
                        Investigador newInv = new Investigador(nombreResponsable, "N/A", "N/A");
                        investigadores.add(newInv);
                        return newInv;
                    });


            Proyecto nuevoProyecto;
            if (esEmprendimiento) {
                nuevoProyecto = new ProyectoEmprendimiento(nombreProyecto, descripcion, url, monto);
            } else {
                nuevoProyecto = new ProyectoInvestigacion(nombreProyecto, descripcion, url);
            }
            nuevoProyecto.setInvestigadorResponsable(responsable);

            for (Investigador inv : investigadoresSeleccionados) {
                nuevoProyecto.addInvestigadorAsociado(inv);
            }

        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                lblResponsable, txtResponsable,
                new HBox(10, lblAsociados, btnAddAsociado, btnRemoveAsociado),
                new HBox(20, tvTodosInvestigadores, tvAsociadosSeleccionados),
                btnContinuar
        );

        Scene scene = new Scene(layout, 800, 600);
        registroInvStage.setScene(scene);
        registroInvStage.showAndWait();
    }

    private void abrirVentanaColaborar(Stage stage) {
    	Stage colaborarStage = new Stage();
        colaborarStage.setTitle("Colaborar con un Proyecto");
        colaborarStage.initOwner(stage);
        colaborarStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        ObservableList<Proyecto> todosLosProyectos = FXCollections.observableArrayList(proyectos);

        TableView<Proyecto> tvProyectos = new TableView<>(todosLosProyectos);
        tvProyectos.setPlaceholder(new Label("No hay proyectos registrados."));
        tvProyectos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); 

        TableColumn<Proyecto, String> colNombreProyecto = new TableColumn<>("Proyecto");
        colNombreProyecto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombreProyecto.setPrefWidth(150);
        colNombreProyecto.setStyle("-fx-alignment: CENTER;");

        TableColumn<Proyecto, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDescripcion.setPrefWidth(200);
        colDescripcion.setStyle("-fx-alignment: CENTER;");

        TableColumn<Proyecto, Double> colMontoTotal = new TableColumn<>("Monto Total");
        colMontoTotal.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof ProyectoEmprendimiento) {
                return new javafx.beans.property.SimpleDoubleProperty(((ProyectoEmprendimiento) cellData.getValue()).getMontoTotalAFinanciar()).asObject();
            }
            return new javafx.beans.property.SimpleDoubleProperty(0.0).asObject();
        });
        colMontoTotal.setPrefWidth(90);
        colMontoTotal.setStyle("-fx-alignment: CENTER;");

        TableColumn<Proyecto, Double> colMontoActual = new TableColumn<>("Monto Financiado");
        colMontoActual.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof ProyectoEmprendimiento) {
                return new javafx.beans.property.SimpleDoubleProperty(((ProyectoEmprendimiento) cellData.getValue()).getMontoActualFinanciado()).asObject();
            }
            return new javafx.beans.property.SimpleDoubleProperty(0.0).asObject();
        });
        colMontoActual.setPrefWidth(100);
        colMontoActual.setStyle("-fx-alignment: CENTER;");

        TableColumn<Proyecto, String> colDonacionesDetalle = new TableColumn<>("Historial de Donaciones");
        colDonacionesDetalle.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof ProyectoEmprendimiento) {
                ProyectoEmprendimiento pe = (ProyectoEmprendimiento) cellData.getValue();
                StringBuilder sb = new StringBuilder();
                if (pe.getHistorialDonaciones().isEmpty()) {
                    sb.append("Sin donaciones");
                } else {
                    for (Donacion donacion : pe.getHistorialDonaciones()) {
                        sb.append(donacion.getColaborador().getNombre()) // Usar getName()
                          .append(": $").append(String.format("%.2f", donacion.getMonto()))
                          .append(" | "); 
                    }
                    if (sb.length() > 0 && sb.toString().endsWith(" | ")) {
                        sb.setLength(sb.length() - 3);
                    }
                }
                return new javafx.beans.property.SimpleStringProperty(sb.toString());
            }
            return new javafx.beans.property.SimpleStringProperty("N/A (Investigación)"); // Para proyectos de investigación
        });
        colDonacionesDetalle.setPrefWidth(250);

        tvProyectos.getColumns().addAll(colNombreProyecto, colDescripcion, colMontoTotal, colMontoActual, colDonacionesDetalle);

        Label lblInstruccionDonar = new Label("Para donar, ingresa tu nombre y el nombre EXACTO del proyecto:");
        
        Label lblNombreColaborador = new Label("Tu Nombre:");
        TextField txtNombreColaborador = new TextField();
        txtNombreColaborador.setPromptText("Nombre del Colaborador");

        Label lblNombreProyectoDonar = new Label("Nombre del Proyecto a Donar:");
        TextField txtNombreProyectoDonar = new TextField();
        txtNombreProyectoDonar.setPromptText("Ej. App de Reciclaje (Debe coincidir)");

        Label lblMontoDonar = new Label("Cantidad a Donar:");
        TextField txtMontoDonar = new TextField();
        txtMontoDonar.setPromptText("Ej. 50.00");

        Button btnDonar = new Button("Donar");

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Proyectos de Emprendimiento Disponibles:"),
                tvProyectos,
                new Separator(),
                lblInstruccionDonar,
                new HBox(10, lblNombreColaborador, txtNombreColaborador),
                new HBox(10, lblNombreProyectoDonar, txtNombreProyectoDonar),
                new HBox(10, lblMontoDonar, txtMontoDonar),
                btnDonar
        );

        Scene scene = new Scene(layout, 850, 650);
        colaborarStage.setScene(scene);
        colaborarStage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
