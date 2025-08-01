package Ej1Intento2;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainApp extends Application {		//clase main

    //investigadores de ejemplo
    private ObservableList<Investigador1> investigadores = FXCollections.observableArrayList(
            new Investigador1("Ana Torres", "https://ana.com", "Experta en IA"),
            new Investigador1("Luis Pérez", "https://luis.com", "Especialista en redes"),
            new Investigador1("Marta García", "https://marta.org", "Investigadora en robótica"),
            new Investigador1("Pedro Gómez", "https://pedro.edu", "Científico de datos"),
            new Investigador1("Laura Díaz", "https://laura.net", "Desarrolladora de software"),
            new Investigador1("Javier Ruiz", "https://javier.tech", "Especialista en ciberseguridad"),
            new Investigador1("Sofía Castro", "https://sofia.med", "Investigadora biomédica"),
            new Investigador1("Daniel Blanco", "https://daniel.art", "Diseñador UX/UI"),
            new Investigador1("Carla Vargas", "https://carla.blog", "Experta en sostenibilidad"),
            new Investigador1("Ricardo Morales", "https://ricardo.ai", "Ingeniero de aprendizaje automático")
    );
    //observable list para las table
    private ObservableList<Proyecto> proyectos = FXCollections.observableArrayList();
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();

    //historial de donaciones
    private Map<String, Map<ProyectoEmprendimiento, Double>> historialDonacionesGlobal = new HashMap<>();

    private Image backgroundImage;									//imagenes de fondo
    private Image crearProyectosImage;
    private Image investigadoresImage;
    private Image colaborarImage;

    
    public MainApp() {												//constructor para la imagen
        try {
            backgroundImage = new Image(getClass().getResourceAsStream("/images/imagen.png"));
            crearProyectosImage = new Image(getClass().getResourceAsStream("/images/crearProyectos.png"));
            investigadoresImage = new Image(getClass().getResourceAsStream("/images/investigadores.jpg"));
            colaborarImage = new Image(getClass().getResourceAsStream("/images/colaborar.png"));

            
        } catch (Exception e) {										//busca la imagen
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
            backgroundImage = null; 								//en caso de error, la imagen será nula
        }
    }

    @Override
    public void start(Stage primaryStage) {
    	generarProyectosDeEjemplo(); 								//generar algunos proyectos de ejemplo

        primaryStage.setTitle("Sistema de Proyectos");				//titulo y botones
        Button btnColaborar = new Button("Colaborar");	
        btnColaborar.setStyle(										//estilo del boton
        		"-fx-background-color: #4CAF50;" +"-fx-text-fill:white;" +"-fx-font-weight: bold;" +"-fx-front-size: 14px;" +"-fx-padding: 8 16 8 16;"
				);
        Button btnCrearProyecto = new Button("Crear proyecto");
        btnCrearProyecto.setStyle(
				"-fx-background-color: #4CAF50;" +"-fx-text-fill:white;" +"-fx-font-weight: bold;" +"-fx-front-size: 14px;" +"-fx-padding: 8 16 8 16;"
				);

        btnColaborar.setOnAction(e -> abrirVentanaColaborar(primaryStage));			//boton colaborar abre metodo colaborar
        btnCrearProyecto.setOnAction(e -> abrirVentanaCrearProyecto(primaryStage));	//boton crear proyecto abre metodo crear proyecto

        //visualizacion de la pestaña principal
        Label lblBienvenida = new Label("Bienvenido al Sistema de Proyectos");
        lblBienvenida.setStyle(										//estilo del label
            "-fx-text-fill: #333333;" +"-fx-font-size: 24px;" +"-fx-font-weight: bold;" +"-fx-padding: 10px 20px;" +"-fx-background-color: rgba(255, 255, 255, 0.7);" +"-fx-background-radius: 10px;"
        );
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lblBienvenida, btnColaborar, btnCrearProyecto);
        root.setPadding(new Insets(20));
        
        if (backgroundImage != null) {
        root.setBackground(createWatermarkBackground(backgroundImage));
        }
        
        Scene scene = new Scene(root, 500, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void generarProyectosDeEjemplo() {		//metodo que genera proyectos de ejemplo, agrega investigadores y sus variables
        ProyectoEmprendimiento pe1 = new ProyectoEmprendimiento("App de Reciclaje", "App para conectar usuarios con centros de reciclaje.", "http://video1.com", 1000.0);
        pe1.setInvestigadorResponsable(investigadores.get(0));
        pe1.agregarAsociado(investigadores.get(1));
        pe1.agregarAsociado(investigadores.get(2));
        pe1.agregarAsociado(investigadores.get(3));
        proyectos.add(pe1);

        ProyectoEmprendimiento pe2 = new ProyectoEmprendimiento("Plataforma de Freelance", "Plataforma para conectar freelancers con clientes.", "http://video2.com", 2500.0);
        pe2.setInvestigadorResponsable(investigadores.get(2));
        pe2.agregarAsociado(investigadores.get(3));
        pe2.agregarAsociado(investigadores.get(4));
        proyectos.add(pe2);

        ProyectoInvestigacion pi1 = new ProyectoInvestigacion("Estudio de Cambio Climático", "Investigación sobre el impacto del cambio climático en la región.", "http://video3.com");
        pi1.setInvestigadorResponsable(investigadores.get(5));
        pi1.agregarAsociado(investigadores.get(6));
        pi1.agregarAsociado(investigadores.get(7));
        pi1.agregarAsociado(investigadores.get(8));
        pi1.agregarAsociado(investigadores.get(9));
        proyectos.add(pi1);
    }

    private void abrirVentanaCrearProyecto(Stage ownerStage) {			//metodo crear proyecto
        Stage crearProyectoStage = new Stage();
        crearProyectoStage.setTitle("Sistema de carga de Proyectos");
        crearProyectoStage.initModality(Modality.APPLICATION_MODAL);	//bloquea interacciones con otras ventanas hasta que se cierre
        crearProyectoStage.initOwner(ownerStage);						//pertenencia a la ventana anterior

        //text fields para guardar la informacion en las variables
        TextField txtNombreUsuario = new TextField();
        txtNombreUsuario.setPromptText("Nombre del Usuario Creador");
        txtNombreUsuario.setStyle(
                "-fx-control-inner-background: rgba(255, 255, 255, 0.6);" + "-fx-background-color: transparent;" +"-fx-border-color: #B0C4DE;" +"-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +"-fx-background-radius: 5px;" +"-fx-padding: 5px 8px;" +"-fx-text-fill: #333333;" +"-fx-prompt-text-fill: #666666;"
            );
        TextField txtNombreProyecto = new TextField();
        txtNombreProyecto.setPromptText("Nombre del Proyecto");
        txtNombreProyecto.setStyle(
                "-fx-control-inner-background: rgba(255, 255, 255, 0.6);" +"-fx-background-color: transparent;" +"-fx-border-color: #B0C4DE;" +"-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +"-fx-background-radius: 5px;" +"-fx-padding: 5px 8px;" +"-fx-text-fill: #333333;" +"-fx-prompt-text-fill: #666666;"
            );
        TextArea txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Descripción");
        txtDescripcion.setWrapText(true);
        txtDescripcion.setStyle(
                "-fx-control-inner-background: rgba(255, 255, 255, 0.6);"+"-fx-background-color: transparent;" +"-fx-border-color: #B0C4DE;" +"-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +"-fx-background-radius: 5px;" +"-fx-padding: 5px 8px;" +"-fx-text-fill: #333333;" +"-fx-prompt-text-fill: #666666;"
            );
        TextField txtUrlVideo = new TextField();
        txtUrlVideo.setPromptText("URL Video Explicativo");
        txtUrlVideo.setStyle(
                "-fx-control-inner-background: rgba(255, 255, 255, 0.6);" +"-fx-background-color: transparent;" +"-fx-border-color: #B0C4DE;" +                             
                "-fx-border-width: 1px;" +"-fx-border-radius: 5px;" +"-fx-background-radius: 5px;" +"-fx-padding: 5px 8px;" +"-fx-text-fill: #333333;" +
                "-fx-prompt-text-fill: #666666;"
            );

        //botones para indicar si es de emprendimiento o investigacion
        Button btnEmprendimiento = new Button("Emprendimiento");
        btnEmprendimiento.setStyle(										//estilo del boton
				"-fx-background-color: #FFDAB9;" +"-fx-text-fill:black;" +"-fx-font-weight: bold;" +"-fx-front-size: 14px;" +"-fx-padding: 8 16 8 16;"
				);
        Button btnInvestigacion = new Button("Investigación");
        btnInvestigacion.setStyle(										//estilo del boton
				"-fx-background-color: #FFDAB9;" + "-fx-text-fill:black;" +"-fx-font-weight: bold;" +"-fx-front-size: 14px;" +"-fx-padding: 8 16 8 16;"
				);

        //visualizacion de la pestaña crear proyecto
        Label lblProyecto = new Label("Bienvenido al Sistema de Carga de Proyectos");
        lblProyecto.setStyle(
            "-fx-text-fill: #333333;" +"-fx-font-size: 24px;" +"-fx-font-weight: bold;" +"-fx-padding: 10px 20px;" +"-fx-background-color: rgba(255, 255, 255, 0.7);" +"-fx-background-radius: 10px;"
        );
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                lblProyecto,
                txtNombreUsuario,
                txtNombreProyecto,
                txtDescripcion,
                txtUrlVideo,
                new HBox(10, btnEmprendimiento, btnInvestigacion)
        );
        
        if (crearProyectosImage != null) {
            layout.setBackground(createWatermarkBackground(crearProyectosImage));
            }

        btnEmprendimiento.setOnAction(e -> {			//accion del boton de emprendimiento
            try {
                if (txtNombreUsuario.getText().isEmpty() || txtNombreProyecto.getText().isEmpty() ||
                    txtDescripcion.getText().isEmpty() || txtUrlVideo.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
                    return;
                }										//valida si los campos estan vacios

                //guarda la informacion en variables
                String nombreUsuario = txtNombreUsuario.getText();
                String nombreProyecto = txtNombreProyecto.getText();
                String descripcion = txtDescripcion.getText();
                String url = txtUrlVideo.getText();

                //en caso que se pulse el boton emprendimiento se abre una pequeña pestaña para indicar el monto a financiar
                abrirVentanaMontoEmprendimiento(crearProyectoStage, nombreUsuario, nombreProyecto, descripcion, url);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error al procesar: " + ex.getMessage());
            }	//muestra la alerta
        });

        btnInvestigacion.setOnAction(e -> {				//accion del boton investigacion
            try {
                if (txtNombreUsuario.getText().isEmpty() || txtNombreProyecto.getText().isEmpty() ||
                    txtDescripcion.getText().isEmpty() || txtUrlVideo.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
                    return;
                }										//valida si los campos estan vacios

              //guarda la informacion en variables
                String nombreUsuario = txtNombreUsuario.getText();
                String nombreProyecto = txtNombreProyecto.getText();
                String descripcion = txtDescripcion.getText();
                String url = txtUrlVideo.getText();

                //al ser proyecto de investigacion, directamente pasa al registro de investigadores
                abrirVentanaRegistroInvestigadores(crearProyectoStage, nombreUsuario, nombreProyecto, descripcion, url, 0.0, false);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error al procesar: " + ex.getMessage());
            }	//muestra la alerta
        });

        Scene scene = new Scene(layout, 750, 450);
        crearProyectoStage.setScene(scene);
        crearProyectoStage.show();
    }

    //metodo para ingresar el monto del proyecto de emprendimiento
    private void abrirVentanaMontoEmprendimiento(Stage ownerStage, String nombreUsuario, String nombreProyecto, String descripcion, String url) {
        Stage montoStage = new Stage();
        montoStage.setTitle("Monto a Financiar");
        montoStage.initModality(Modality.APPLICATION_MODAL);
        montoStage.initOwner(ownerStage);

        Label lblMonto = new Label("Monto total a financiar:");				//label, almacenar monto en una variable y boton siguiente
        TextField txtMonto = new TextField();
        txtMonto.setPromptText("Ej. 1000.00");
        Button btnSiguiente = new Button("Siguiente");
        btnSiguiente.setStyle(										//estilo del boton
				"-fx-background-color: #ADD8E6;" + "-fx-text-fill:black;" +"-fx-font-weight: bold;" +"-fx-front-size: 14px;" +"-fx-padding: 8 16 8 16;"
				);

        btnSiguiente.setOnAction(e -> {										//accion boton siguiente
            try {
                double monto = Double.parseDouble(txtMonto.getText());		//leemos el monto
                if (monto <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Error", "El monto debe ser un número positivo.");
                    return;													//muestra error si el numero es menor o igual a cero
                }
                montoStage.close();
                abrirVentanaRegistroInvestigadores(ownerStage, nombreUsuario, nombreProyecto, descripcion, url, monto, true);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error de Formato", "Por favor, ingrese un número válido para el monto.");
            }																//muestra la alerta
        });

        VBox layout = new VBox(10);											//visualizacion pestaña monto emprendimiento
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(lblMonto, txtMonto, btnSiguiente);

        Scene scene = new Scene(layout, 300, 150);
        montoStage.setScene(scene);
        montoStage.showAndWait();
    }

    //metodo para ingresar en el registro de los investigadores
    private void abrirVentanaRegistroInvestigadores(Stage ownerStage, String nombreUsuarioCreador, String nombreProyecto, String descripcion, 
    		String url, double monto, boolean esEmprendimiento) {			//se le pasa los datos, mas el monto, mas el booleano
        Stage registroInvStage = new Stage();								//validar si es de emprendimiento o no
        registroInvStage.setTitle("Registro de Investigadores");
        registroInvStage.initModality(Modality.APPLICATION_MODAL);
        registroInvStage.initOwner(ownerStage);

        Label lblResponsable = new Label("Investigador Responsable:");						//label y registro del nombre del investigador
        lblResponsable.setStyle(
                "-fx-text-fill: #333333;" +"-fx-font-size: 18px;" +"-fx-font-weight: bold;" +"-fx-padding: 10px 20px;" +"-fx-background-color: rgba(255, 255, 255, 0.7);" +"-fx-background-radius: 10px;"
            );
        TextField txtResponsable = new TextField();
        txtResponsable.setPromptText("Nombre del Investigador Responsable");

        Label lblAsociados = new Label("Investigadores Asociados (Máx. 4):");				//se crea un TableView para mostrar
        lblAsociados.setStyle(
                "-fx-text-fill: #333333;" +"-fx-font-size: 18px;" +"-fx-font-weight: bold;" +"-fx-padding: 10px 20px;" +"-fx-background-color: rgba(255, 255, 255, 0.7);" +"-fx-background-radius: 10px;"
            );
        TableView<Investigador1> tvTodosInvestigadores = new TableView<>(investigadores);	//los investigadores que podemos elegir
        TableColumn<Investigador1, String> colNombreInv = new TableColumn<>("Nombre");
        colNombreInv.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNombreInv.setPrefWidth(150);
        colNombreInv.setStyle("-fx-alignment: CENTER;");
        tvTodosInvestigadores.getColumns().add(colNombreInv);

        tvTodosInvestigadores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);	//permite seleccionar en el TableView

        Button btnAddAsociado = new Button("Añadir Asociado");								//botones
        btnAddAsociado.setStyle(										//estilo del boton
				"-fx-background-color: #98FB98;" + "-fx-text-fill:black;" +"-fx-font-weight: bold;" +"-fx-front-size: 14px;" +"-fx-padding: 8 16 8 16;"
				);
        Button btnRemoveAsociado = new Button("Quitar Asociado");
        btnRemoveAsociado.setStyle(										//estilo del boton
				"-fx-background-color: #98FB98;" + "-fx-text-fill:black;" +"-fx-font-weight: bold;" +"-fx-front-size: 14px;" +"-fx-padding: 8 16 8 16;"
				);
        Button btnContinuar = new Button("Continuar");
        btnContinuar.setStyle(										//estilo del boton
				"-fx-background-color: #98FB98;" + "-fx-text-fill:black;" +"-fx-font-weight: bold;" +"-fx-front-size: 14px;" +"-fx-padding: 8 16 8 16;"
				);

        ObservableList<Investigador1> investigadoresSeleccionados = FXCollections.observableArrayList();
        TableView<Investigador1> tvAsociadosSeleccionados = new TableView<>(investigadoresSeleccionados);
        TableColumn<Investigador1, String> colNombreAsociado = new TableColumn<>("Investigadores Seleccionados");
        colNombreAsociado.setCellValueFactory(new PropertyValueFactory<>("name"));			//se crea otro TableView para mostrar
        colNombreAsociado.setPrefWidth(200);												//los investigadores que son seleccionados
        colNombreAsociado.setStyle("-fx-alignment: CENTER;");
        tvAsociadosSeleccionados.getColumns().add(colNombreAsociado);

        btnAddAsociado.setOnAction(e -> {													//accion boton de agregar asociado
            ObservableList<Investigador1> selected = tvTodosInvestigadores.getSelectionModel().getSelectedItems();
            for (Investigador1 inv : selected) {											//otro observable list para los seleccionados
                if (investigadoresSeleccionados.size() < 4 && !investigadoresSeleccionados.contains(inv)) {
                    investigadoresSeleccionados.add(inv);									//agrega hasta que sean 4 investogadores
                } else if (investigadoresSeleccionados.contains(inv)) {
                    showAlert(Alert.AlertType.INFORMATION, "Duplicado", inv.getName() + " ya está en la lista de asociados.");
                } else {																	//valida si ya esta duplicado
                    showAlert(Alert.AlertType.WARNING, "Límite Excedido", "Solo se pueden añadir hasta 4 investigadores asociados.");
                    break;																	//valida si el limite paso de 4 investigadores
                }
            }
            tvTodosInvestigadores.getSelectionModel().clearSelection();
        });

        btnRemoveAsociado.setOnAction(e -> {												//accion boton quitar asociado
            Investigador1 selected = tvAsociadosSeleccionados.getSelectionModel().getSelectedItem();
            if (selected != null) {
                investigadoresSeleccionados.remove(selected);								//quita al asociado seleccionado
            } else {
                showAlert(Alert.AlertType.WARNING, "Advertencia", "Seleccione un investigador asociado para quitar.");
            }																				//valida si no seleccionaste a ningun asociado
        });

        btnContinuar.disableProperty().bind(															//deshabilita el boton contiuar si no se llenan los campos
                txtResponsable.textProperty().isEmpty().or(												//tanto el del nombre del investigador
                        javafx.beans.binding.Bindings.size(investigadoresSeleccionados).isNotEqualTo(4)	//como el de seleccioar los 4 investigadores
                )
        );

        btnContinuar.setOnAction(e -> {														//accion boton continuar
            if (investigadoresSeleccionados.size() != 4) {									//valida si son exactamente 4 asociados
                showAlert(Alert.AlertType.WARNING, "Advertencia", "Tiene que seleccionar exactamente 4 investigadores asociados.");
                return;																		//muestra la alerta
            }

            String nombreResponsable = txtResponsable.getText().trim();
            if (nombreResponsable.isEmpty()) {												//valida si no se lleno el campo del nombre
                showAlert(Alert.AlertType.ERROR, "Error", "Debe ingresar el nombre del investigador responsable.");
                return;																		//muestra la alerta
            }

            Investigador1 responsable = investigadores.stream()										//busca al investigador responsable
                    .filter(inv -> inv.getName().equalsIgnoreCase(nombreResponsable))				
                    .findFirst()															
                    .orElseGet(() -> {
                        showAlert(Alert.AlertType.INFORMATION, "Nuevo Investigador", "El investigador responsable '" + nombreResponsable + "' no fue encontrado y será creado como nuevo.");
                        Investigador1 newInv = new Investigador1(nombreResponsable, "N/A", "N/A");	//en caso que no existia previamente
                        investigadores.add(newInv);													//lo agrega con los anteriores
                        return newInv;
                    });


            Proyecto nuevoProyecto;															//crea nuevo proyecto con un booleano
            if (esEmprendimiento) {															//si es proyecto emprendimiento 
                nuevoProyecto = new ProyectoEmprendimiento(nombreProyecto, descripcion, url, monto);
            } else {																		//o de investigacion
                nuevoProyecto = new ProyectoInvestigacion(nombreProyecto, descripcion, url);
            }
            nuevoProyecto.setInvestigadorResponsable(responsable);

            for (Investigador1 inv : investigadoresSeleccionados) {							//agrega los investigadoes asociados
                nuevoProyecto.agregarAsociado(inv);
            }

            abrirVentanaConfirmacion(registroInvStage, nuevoProyecto);						//para ambos casos, al terminar abre
        });																					//la ventana de confirmacion

        VBox layout = new VBox(10);															//visualizacion pestaña registro investigadores
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                lblResponsable, txtResponsable,
                new HBox(10, lblAsociados, btnAddAsociado, btnRemoveAsociado),
                new HBox(20, tvTodosInvestigadores, tvAsociadosSeleccionados),
                btnContinuar
        );
        
        if (investigadoresImage != null) {
            layout.setBackground(createWatermarkBackground(investigadoresImage));
            }

        Scene scene = new Scene(layout, 650, 450);
        registroInvStage.setScene(scene);
        registroInvStage.showAndWait();
    }

    private void abrirVentanaConfirmacion(Stage ownerStage, Proyecto proyectoTemporal) {	//metodo para la ventana confirmacion
        Stage confirmacionStage = new Stage();
        confirmacionStage.setTitle("Confirmación de Proyecto");
        confirmacionStage.initModality(Modality.APPLICATION_MODAL);
        confirmacionStage.initOwner(ownerStage);

        Label lblMensaje = new Label("¿Desea guardar la información del proyecto?");		//label para mostrar toda la informacion
        lblMensaje.setStyle(
                "-fx-text-fill: #333333;" +"-fx-font-size: 18px;" +"-fx-font-weight: bold;" +"-fx-padding: 10px 20px;" +"-fx-background-color: rgba(255, 255, 255, 0.7);" +"-fx-background-radius: 10px;"
            );
        StringBuilder detalle = new StringBuilder();										//recopilada
        detalle.append("Proyecto: ").append(proyectoTemporal.getName()).append("\n");
        detalle.append("Descripción: ").append(proyectoTemporal.getDescripcion()).append("\n");
        detalle.append("URL: ").append(proyectoTemporal.getUrl()).append("\n");
        detalle.append("Responsable: ").append(proyectoTemporal.getResponsable() != null ? proyectoTemporal.getResponsable().getName() : "N/A").append("\n");
        detalle.append("Asociados: ");
        if (!proyectoTemporal.getAsociados().isEmpty()) {
            List<String> asociadosNombres = new ArrayList<>();
            for (Investigador1 inv : proyectoTemporal.getAsociados()) {
                asociadosNombres.add(inv.getName());
            }
            detalle.append(String.join(", ", asociadosNombres));
        } else {
            detalle.append("Ninguno");
        }
        if (proyectoTemporal instanceof ProyectoEmprendimiento) {							//si es proyecto de emprendimiento, agrega el monto
            detalle.append("\nMonto a Financiar: $").append(((ProyectoEmprendimiento) proyectoTemporal).getMontoTotal());
        }

        Label lblDetalleProyecto = new Label(detalle.toString());							//label y botones si o no
        Button btnSi = new Button("Sí");
        btnSi.setStyle(										//estilo del boton
				"-fx-background-color: #FFB6C1;" + "-fx-text-fill:black;" +"-fx-font-weight: bold;" +"-fx-front-size: 14px;" +"-fx-padding: 8 16 8 16;"
				);
        Button btnNo = new Button("No");
        btnNo.setStyle(										//estilo del boton
				"-fx-background-color: #FFB6C1;" + "-fx-text-fill:black;" +"-fx-font-weight: bold;" +"-fx-front-size: 14px;" +"-fx-padding: 8 16 8 16;"
				);

        btnSi.setOnAction(e -> {															//accion boton si
            proyectos.add(proyectoTemporal);												//agrega el proyecto temporal
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Proyecto guardado correctamente.");
            confirmacionStage.close();														//muestra la alerta de confirmacion
            ownerStage.close();
        });

        btnNo.setOnAction(e -> {															//accion boton no
            showAlert(Alert.AlertType.INFORMATION, "Descartado", "El proyecto ha sido descartado.");
            confirmacionStage.close();														//muestra la alerta de descarte
            ownerStage.close();
        });
        
        VBox layout = new VBox(15);															//visualizacion pestaña de confirmacion
        VBox siono = new VBox(10, btnSi, btnNo);
        siono.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(lblMensaje, lblDetalleProyecto, siono);

        Scene scene = new Scene(layout, 500, 300);
        confirmacionStage.setScene(scene);
        confirmacionStage.showAndWait();
    }

    private void abrirVentanaColaborar(Stage stage) {										//metodo para la ventana colaborar
    	Stage colaborarStage = new Stage();
        colaborarStage.setTitle("Colaborar con un Proyecto");
        colaborarStage.initOwner(stage);
        colaborarStage.initModality(Modality.APPLICATION_MODAL);

        //crear un ObservableList para mostrar todos los proyectos
        ObservableList<Proyecto> todosLosProyectos = FXCollections.observableArrayList(proyectos);

        TableView<Proyecto> tvProyectos = new TableView<>(todosLosProyectos);				//TableView para mostrar los proyectos
        tvProyectos.setPlaceholder(new Label("No hay proyectos registrados."));
        tvProyectos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); 

        TableColumn<Proyecto, String> colNombreProyecto = new TableColumn<>("Proyecto");	//columnas del TableView
        colNombreProyecto.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNombreProyecto.setPrefWidth(200);
        colNombreProyecto.setStyle("-fx-alignment: CENTER;");

        TableColumn<Proyecto, String> colDescripcion = new TableColumn<>("Descripción");	//descripciones
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDescripcion.setPrefWidth(350);
        colDescripcion.setStyle("-fx-alignment: CENTER;");

        TableColumn<Proyecto, Double> colMontoTotal = new TableColumn<>("Monto Total");		//columna monto total
        colMontoTotal.setCellValueFactory(cellData -> {										//para el proyecto emprendimiento
            if (cellData.getValue() instanceof ProyectoEmprendimiento) {
                return new SimpleDoubleProperty(((ProyectoEmprendimiento) cellData.getValue()).getMontoTotal()).asObject();
            }
            return new SimpleDoubleProperty(0.0).asObject(); 								//monto = 0.0 en caso de proyecto
        });																					//de investigacion
        colMontoTotal.setPrefWidth(90);
        colMontoTotal.setStyle("-fx-alignment: CENTER;");

        TableColumn<Proyecto, Double> colMontoActual = new TableColumn<>("Monto Financiado");	//columna monto actual
        colMontoActual.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof ProyectoEmprendimiento) {
                return new SimpleDoubleProperty(((ProyectoEmprendimiento) cellData.getValue()).getMontoActual()).asObject();
            }
            return new SimpleDoubleProperty(0.0).asObject(); 									//monto = 0.0 en caso de proyecto
        });																						//de investigacion
        colMontoActual.setPrefWidth(90);
        colMontoActual.setStyle("-fx-alignment: CENTER;");

        //columna de donaciones para los proyectos de emprendimiento
        TableColumn<Proyecto, String> colDonacionesDetalle = new TableColumn<>("Historial de Donaciones");
        colDonacionesDetalle.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof ProyectoEmprendimiento) {
                ProyectoEmprendimiento pe = (ProyectoEmprendimiento) cellData.getValue();
                StringBuilder sb = new StringBuilder();										//String builder para generar las donaciones
                if (pe.getHistorialDonaciones().isEmpty()) {
                    sb.append("Sin donaciones");
                } else {
                    for (Donacion donacion : pe.getHistorialDonaciones()) {					//generacion del string para donaciones
                        sb.append(donacion.getColaborador().getName())
                          .append(": $").append(String.format("%.2f", donacion.getMonto()))
                          .append(" | ");
                    }
                    if (sb.length() > 0 && sb.toString().endsWith(" | ")) {					//para evitar que el ultimo termine con "|"
                        sb.setLength(sb.length() - 3);
                    }
                }
                return new SimpleStringProperty(sb.toString());
            }
            return new SimpleStringProperty("N/A (Investigación)"); 						//devuelve la cadena, para proyectos de investigación
        });
        colDonacionesDetalle.setPrefWidth(250);

        tvProyectos.getColumns().addAll(colNombreProyecto, colDescripcion, colMontoTotal, colMontoActual, colDonacionesDetalle);

        //labels y registro de donaciones
        Label lblInstruccionDonar = new Label("Para donar, ingresa tu nombre y el nombre EXACTO del proyecto:");
        lblInstruccionDonar.setStyle(
                "-fx-text-fill: #333333;" +"-fx-font-size: 16px;" +"-fx-font-weight: bold;" +"-fx-padding: 10px 20px;" +"-fx-background-color: rgba(255, 255, 255, 0.7);" +"-fx-background-radius: 10px;"
            );
        Label lblNombreColaborador = new Label("Tu Nombre:");
        lblNombreColaborador.setStyle(
                "-fx-text-fill: #333333;" +"-fx-font-size: 12px;" +"-fx-font-weight: bold;" +"-fx-padding: 10px 20px;" +"-fx-background-color: rgba(255, 255, 255, 0.7);" +"-fx-background-radius: 10px;"
            );
        TextField txtNombreColaborador = new TextField();
        txtNombreColaborador.setPromptText("Nombre del Colaborador");
        txtNombreColaborador.setStyle(
                "-fx-control-inner-background: rgba(255, 255, 255, 0.6);"+"-fx-background-color: transparent;" +"-fx-border-color: #B0C4DE;" +"-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +"-fx-background-radius: 5px;" +"-fx-padding: 5px 8px;" +"-fx-text-fill: #333333;" +"-fx-prompt-text-fill: #666666;"
            );

        Label lblNombreProyectoDonar = new Label("Nombre del Proyecto a Donar:");
        lblNombreProyectoDonar.setStyle(
                "-fx-text-fill: #333333;" +"-fx-font-size: 12px;" +"-fx-font-weight: bold;" +"-fx-padding: 10px 20px;" +"-fx-background-color: rgba(255, 255, 255, 0.7);" +"-fx-background-radius: 10px;"
            );
        TextField txtNombreProyectoDonar = new TextField();
        txtNombreProyectoDonar.setPromptText("Ej. App de Reciclaje (Debe coincidir)");
        txtNombreProyectoDonar.setStyle(
                "-fx-control-inner-background: rgba(255, 255, 255, 0.6);"+"-fx-background-color: transparent;" +"-fx-border-color: #B0C4DE;" +"-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +"-fx-background-radius: 5px;" +"-fx-padding: 5px 8px;" +"-fx-text-fill: #333333;" +"-fx-prompt-text-fill: #666666;"
            );

        Label lblMontoDonar = new Label("Cantidad a Donar:");
        lblMontoDonar.setStyle(
                "-fx-text-fill: #333333;" +"-fx-font-size: 12px;" +"-fx-font-weight: bold;" +"-fx-padding: 10px 20px;" +"-fx-background-color: rgba(255, 255, 255, 0.7);" +"-fx-background-radius: 10px;"
            );
        TextField txtMontoDonar = new TextField();
        txtMontoDonar.setPromptText("Ej. 50.00");
        txtMontoDonar.setStyle(
                "-fx-control-inner-background: rgba(255, 255, 255, 0.6);"+"-fx-background-color: transparent;" +"-fx-border-color: #B0C4DE;" +"-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +"-fx-background-radius: 5px;" +"-fx-padding: 5px 8px;" +"-fx-text-fill: #333333;" +"-fx-prompt-text-fill: #666666;"
            );

        Button btnDonar = new Button("Donar");
        btnDonar.setStyle(										//estilo del boton
				"-fx-background-color: #FFC107;" + "-fx-text-fill:black;" +"-fx-font-weight: bold;" +"-fx-front-size: 14px;" +"-fx-padding: 8 16 8 16;"
				);
        
        btnDonar.disableProperty().bind(													//deshabilitar el botón hasta que
                txtNombreColaborador.textProperty().isEmpty().or(							//todos los campos estén llenos
                        txtNombreProyectoDonar.textProperty().isEmpty().or(
                                txtMontoDonar.textProperty().isEmpty()
                        )
                )
        );


        btnDonar.setOnAction(e -> {															//accion boton donar
            try {																			
                String nombreColaborador = txtNombreColaborador.getText().trim();
                String nombreProyectoDonar = txtNombreProyectoDonar.getText().trim();
                double montoDonacion = Double.parseDouble(txtMontoDonar.getText());

              //valida que los campos esten llenos
                if (nombreColaborador.isEmpty() || nombreProyectoDonar.isEmpty() || montoDonacion <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Por favor, completa todos los campos y asegúrate que el monto sea positivo.");
                    return;																	//muestra la alerta
                }

                ProyectoEmprendimiento targetProject = null;								//buscar el proyecto
                for (Proyecto p : proyectos) {
                    if (p instanceof ProyectoEmprendimiento && p.getName().equalsIgnoreCase(nombreProyectoDonar)) {
                        targetProject = (ProyectoEmprendimiento) p;
                        break;
                    }
                }

                if (targetProject == null) {												//valida si el proyecto existe
                    showAlert(Alert.AlertType.ERROR, "Proyecto No Encontrado", "No se encontró un proyecto de emprendimiento con el nombre exacto: " + nombreProyectoDonar);
                    return;																	//muestra la alerta
                }

                if (targetProject.getMontoActual() >= targetProject.getMontoTotal()) {		//valida si el monto ya ha sido totalmente financiado
                    showAlert(Alert.AlertType.INFORMATION, "Proyecto Financiado", "El proyecto \"" + targetProject.getName() + "\" ya ha alcanzado su objetivo de financiación.");
                    return;																	//muestra la alerta
                }

                Colaborador colaborador = colaboradores.stream()							//obtiene o crea el colaborador
                        .filter(c -> c.getName().equalsIgnoreCase(nombreColaborador))
                        .findFirst()
                        .orElseGet(() -> {
                            Colaborador newColaborador = new Colaborador(nombreColaborador);
                            colaboradores.add(newColaborador);
                            return newColaborador;
                        });

                double devuelto = targetProject.calcDonacion(colaborador, montoDonacion);	//calcula el vuelto en el metodo

                if (devuelto > 0) {															//mensaje por la donacion en caso que
                    showAlert(Alert.AlertType.INFORMATION, "Donación Completa",				//haya sobrepasado el monto actual
                            "¡Gracias por tu donación! El proyecto \"" + targetProject.getName() +
                            "\" ha sido completamente financiado. Se te han devuelto $" + String.format("%.2f", devuelto) + "."
                    );
                } else {																	//mensaje por doncion normal
                    showAlert(Alert.AlertType.INFORMATION, "Donación Exitosa",
                            "Has donado $" + String.format("%.2f", montoDonacion) + " al proyecto \"" + targetProject.getName() + "\"."
                    );
                }

                txtMontoDonar.clear(); 														//limpiar solo el campo de monto

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error de Formato", "Por favor, ingrese un número válido para la cantidad a donar.");
            }																				//muestra la alerta
        });

        VBox layout = new VBox(15);															//visualizacion pestaña donacion
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setPadding(new Insets(60));
        layout.getChildren().addAll(
                new Label("Proyectos de Emprendimiento Disponibles:"),
                tvProyectos,
                new Separator(), 															//separador visual
                lblInstruccionDonar,
                new HBox(10, lblNombreColaborador, txtNombreColaborador),
                new HBox(10, lblNombreProyectoDonar, txtNombreProyectoDonar),
                new HBox(10, lblMontoDonar, txtMontoDonar),
                btnDonar
        );
        
        if (colaborarImage != null) {
            layout.setBackground(createWatermarkBackground(colaborarImage));
            }

        Scene scene = new Scene(layout, 1100, 800);
        colaborarStage.setScene(scene);
        colaborarStage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {			//metodo para mostrar las alertas
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private Background createWatermarkBackground(Image i) {
        if (i == null) {
            return Background.EMPTY; 														//retorna un fondo vacio si la imagen no se cargo
        }
        BackgroundRepeat repeat = BackgroundRepeat.NO_REPEAT;								//define como se repetira la imagen
        BackgroundPosition position = BackgroundPosition.CENTER;							//define la posicion
        BackgroundSize size = new BackgroundSize(1.0, 1.0, true, true, false, true); 		//define la escala al 100% del contenedor

        BackgroundImage bgImage = new BackgroundImage(
            i,
            repeat, //repetir en X
            repeat, //repetir en Y
            position,
            size
        );

        return new Background(bgImage);
    }

    public static void main(String[] args) {												//main que empieza la compilacion
        launch(args);
    }
}
