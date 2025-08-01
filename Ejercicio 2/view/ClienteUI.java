package Ejercicio2.view; // Declara el paquete al que pertenece esta clase, 'Ejercicio2.view'.

import Ejercicio2.controller.BancoController; // Importa el controlador del banco, que manejará la lógica de negocio.
import Ejercicio2.model.Cliente; // Importa la clase Cliente del modelo.
import Ejercicio2.model.ValidacionesUtil; // Importa la clase de utilidades para validaciones.
import javafx.application.Application; // Importa la clase base para las aplicaciones JavaFX.
import javafx.beans.property.SimpleIntegerProperty; // Importa para envolver propiedades de enteros observables para TableView.
import javafx.beans.property.SimpleStringProperty; // Importa para envolver propiedades de cadenas observables para TableView.
import javafx.geometry.Insets; // Importa para especificar el relleno alrededor de los nodos.
import javafx.scene.Scene; // Importa Scene, que representa el contenido de una ventana.
import javafx.scene.control.*; // Importa todos los controles de JavaFX (Button, Label, TextField, TableView, etc.).
import javafx.scene.layout.*; // Importa todos los layouts de JavaFX (BorderPane, VBox, HBox, etc.).
import javafx.stage.Stage; // Importa Stage, que representa la ventana principal de la aplicación.

public class ClienteUI extends Application { // Define la clase ClienteUI, que extiende Application para ser una aplicación JavaFX.

    private BancoController banco; // Declara una instancia del controlador del banco para interactuar con la lógica.
    private TableView<Cliente> tablaClientes; // Declara una TableView para mostrar la lista de clientes.
    private TextField txtNombre; // Campo de texto para ingresar el nombre del cliente.
    private TextField txtTelefono; // Campo de texto para ingresar el teléfono del cliente.
    private TextField txtBuscar; // Campo de texto para buscar clientes.

    // Método para inyectar la dependencia del BancoController.
    // Esto permite que el controlador sea establecido desde el punto de inicio de la aplicación.
    public void setBancoController(BancoController banco) { 
        this.banco = banco; // Asigna el controlador de banco pasado como parámetro.
    }

    @Override // Anotación que indica que este método sobrescribe un método de la clase padre (Application).
    public void start(Stage stage) { // El método start es el punto de entrada principal para la interfaz de usuario de esta vista.
        BorderPane root = new BorderPane(); // Crea un BorderPane como el nodo raíz de la escena, que permite organizar los componentes en 5 regiones (top, bottom, left, right, center).
        root.setPadding(new Insets(15)); // Establece un relleno de 15 píxeles alrededor de los bordes del BorderPane.

        // Título de la interfaz de usuario
        Label titulo = new Label("👥 GESTIÓN DE CLIENTES"); // Crea una etiqueta (Label) para el título.
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;"); // Establece estilos CSS para el título.
        root.setTop(titulo); // Coloca el título en la región superior del BorderPane.

        // Panel izquierdo - Formulario para agregar/buscar clientes
        VBox panelFormulario = crearPanelFormulario(); // Llama a un método privado para crear el panel del formulario.
        root.setLeft(panelFormulario); // Coloca el panel del formulario en la región izquierda del BorderPane.

        // Panel central - Tabla de clientes
        VBox panelTabla = crearPanelTabla(); // Llama a un método privado para crear el panel de la tabla.
        root.setCenter(panelTabla); // Coloca el panel de la tabla en la región central del BorderPane.

        // Panel derecho - Información detallada del cliente seleccionado
        VBox panelInfo = crearPanelInformacion(); // Llama a un método privado para crear el panel de información.
        root.setRight(panelInfo); // Coloca el panel de información en la región derecha del BorderPane.

        Scene scene = new Scene(root, 900, 600); // Crea una nueva escena con el BorderPane raíz y dimensiones iniciales.
        stage.setScene(scene); // Asigna la escena a la ventana (stage).
        stage.setTitle("Gestión de Clientes"); // Establece el título de la ventana.
        stage.show(); // Hace visible la ventana.
    }

    // Método privado para crear el panel del formulario de entrada de datos y búsqueda.
    private VBox crearPanelFormulario() {
        VBox panel = new VBox(15); // Crea un VBox con un espaciado de 15 píxeles entre sus hijos.
        panel.setPadding(new Insets(10)); // Establece un relleno interno de 10 píxeles.
        panel.setPrefWidth(250); // Establece el ancho preferido del panel.
        panel.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 1;"); // Establece estilos de fondo y borde.

        Label tituloForm = new Label("Nuevo Cliente"); // Etiqueta para el título de la sección "Nuevo Cliente".
        tituloForm.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // Estilos para el título del formulario.

        Label lblNombre = new Label("Nombre completo:"); // Etiqueta para el campo de nombre.
        lblNombre.setStyle("-fx-font-weight: bold;"); // Estilo de negrita para la etiqueta.
        txtNombre = new TextField(); // Campo de texto para el nombre.
        txtNombre.setPromptText("Ej: Juan Pérez"); // Texto de sugerencia cuando el campo está vacío.

        Label lblTelefono = new Label("Teléfono:"); // Etiqueta para el campo de teléfono.
        lblTelefono.setStyle("-fx-font-weight: bold;"); // Estilo de negrita para la etiqueta.
        txtTelefono = new TextField(); // Campo de texto para el teléfono.
        txtTelefono.setPromptText("Ej: 123-456-789"); // Texto de sugerencia para el teléfono.

        Button btnAgregar = new Button("➕ Agregar Cliente"); // Botón para agregar un nuevo cliente.
        btnAgregar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;"); // Estilos para el botón "Agregar".
        btnAgregar.setPrefWidth(Double.MAX_VALUE); // Establece el ancho preferido al máximo disponible.
        btnAgregar.setOnAction(e -> agregarCliente()); // Define la acción a ejecutar cuando se hace clic en el botón (llama al método agregarCliente).

        Button btnLimpiar = new Button("🗑️ Limpiar Campos"); // Botón para limpiar los campos del formulario.
        btnLimpiar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;"); // Estilos para el botón "Limpiar".
        btnLimpiar.setPrefWidth(Double.MAX_VALUE); // Establece el ancho preferido al máximo disponible.
        btnLimpiar.setOnAction(e -> limpiarCampos()); // Define la acción a ejecutar (llama al método limpiarCampos).

        Separator sep = new Separator(); // Crea un separador visual.

        Label lblBuscar = new Label("Buscar cliente:"); // Etiqueta para la sección de búsqueda.
        lblBuscar.setStyle("-fx-font-weight: bold;"); // Estilo de negrita para la etiqueta.
        txtBuscar = new TextField(); // Campo de texto para ingresar el término de búsqueda.
        txtBuscar.setPromptText("Escriba nombre..."); // Texto de sugerencia para la búsqueda.
        // Añade un 'listener' a la propiedad de texto del campo de búsqueda.
        // Cada vez que el texto cambie, se llamará al método buscarCliente con el nuevo valor.
        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> buscarCliente(newVal)); 

        // Añade todos los componentes al panel del formulario en el orden especificado.
        panel.getChildren().addAll(tituloForm, lblNombre, txtNombre, lblTelefono, txtTelefono,
                                 btnAgregar, btnLimpiar, sep, lblBuscar, txtBuscar);
        return panel; // Retorna el panel VBox configurado.
    }

    // Método privado para crear el panel que contiene la tabla de clientes.
    private VBox crearPanelTabla() {
        VBox panel = new VBox(10); // Crea un VBox con espaciado de 10 píxeles.
        panel.setPadding(new Insets(10)); // Establece un relleno interno de 10 píxeles.

        Label tituloTabla = new Label("Lista de Clientes:"); // Etiqueta para el título de la tabla.
        tituloTabla.setStyle("-fx-font-weight: bold;"); // Estilo de negrita para la etiqueta.

        tablaClientes = new TableView<>(); // Crea una nueva instancia de TableView para mostrar objetos Cliente.
        tablaClientes.setItems(banco.getClientes()); // Establece la lista de clientes del controlador como la fuente de datos de la tabla.

        // Define la columna 'ID'.
        TableColumn<Cliente, Integer> colId = new TableColumn<>("ID"); 
        // Define cómo obtener el valor para esta columna de un objeto Cliente.
        // SimpleIntegerProperty envuelve un int para hacerlo observable.
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject()); 
        colId.setPrefWidth(50); // Establece el ancho preferido de la columna.

        // Define la columna 'Nombre'.
        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre"); 
        // Define cómo obtener el valor de la propiedad 'nombre' de un Cliente.
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre())); 
        colNombre.setPrefWidth(200); // Establece el ancho preferido.

        // Define la columna 'Teléfono'.
        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono"); 
        // Define cómo obtener el valor de la propiedad 'telefono' de un Cliente.
        colTelefono.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefono())); 
        colTelefono.setPrefWidth(120); // Establece el ancho preferido.

        // Define la columna 'N° Cuentas'.
        TableColumn<Cliente, Integer> colCuentas = new TableColumn<>("N° Cuentas"); 
        // Define cómo obtener el número de cuentas de un Cliente.
        colCuentas.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCuentas().size()).asObject()); 
        colCuentas.setPrefWidth(80); // Establece el ancho preferido.

        // Añade todas las columnas definidas a la tabla.
        tablaClientes.getColumns().addAll(colId, colNombre, colTelefono, colCuentas); 

        // Añade un 'listener' a la propiedad de selección de la tabla.
        // Este listener se activa cuando la fila seleccionada cambia.
        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) { // Si hay una nueva selección (no es nula, es decir, se seleccionó un cliente).
                mostrarInformacionCliente(newSelection); // Llama al método para mostrar la información detallada del cliente seleccionado.
            }
        });

        // Añade el título de la tabla y la tabla misma al panel.
        panel.getChildren().addAll(tituloTabla, tablaClientes); 
        return panel; // Retorna el panel VBox configurado.
    }

    // Método privado para crear el panel que muestra la información detallada del cliente.
    private VBox crearPanelInformacion() {
        VBox panel = new VBox(10); // Crea un VBox con espaciado de 10 píxeles.
        panel.setPadding(new Insets(10)); // Establece un relleno interno.
        panel.setPrefWidth(200); // Establece el ancho preferido del panel.
        panel.setStyle("-fx-background-color: #e9ecef; -fx-border-color: #ced4da; -fx-border-width: 1;"); // Estilos de fondo y borde.

        Label titulo = new Label("Información del Cliente"); // Etiqueta para el título del panel de información.
        titulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;"); // Estilos para el título.

        Label lblInfo = new Label("Seleccione un cliente de la tabla para ver su información detallada."); // Mensaje inicial.
        lblInfo.setWrapText(true); // Permite que el texto se ajuste a varias líneas si es necesario.
        lblInfo.setStyle("-fx-text-fill: #6c757d;"); // Estilo para el texto de información.

        // Añade el título, un separador y el mensaje inicial al panel.
        panel.getChildren().addAll(titulo, new Separator(), lblInfo); 
        return panel; // Retorna el panel VBox configurado.
    }

    // Método para manejar la lógica de agregar un nuevo cliente.
    private void agregarCliente() {
        String nombre = ValidacionesUtil.limpiarTexto(txtNombre.getText()); // Obtiene el texto del campo nombre y lo limpia usando ValidacionesUtil.
        String telefono = ValidacionesUtil.limpiarTexto(txtTelefono.getText()); // Obtiene el texto del campo teléfono y lo limpia.

        // Validaciones de entrada de datos
        if (!ValidacionesUtil.esNombreValido(nombre)) { // Comprueba si el nombre es válido usando ValidacionesUtil.
            mostrarError("El nombre debe tener al menos 2 caracteres"); // Muestra un mensaje de error si no es válido.
            txtNombre.requestFocus(); // Pone el foco en el campo de nombre.
            return; // Sale del método.
        }

        if (!ValidacionesUtil.esTelefonoValido(telefono)) { // Comprueba si el teléfono es válido.
            mostrarError("El teléfono debe tener un formato válido (ej: 123-456-789)"); // Muestra un mensaje de error.
            txtTelefono.requestFocus(); // Pone el foco en el campo de teléfono.
            return; // Sale del método.
        }

        try { // Inicia un bloque try-catch para manejar posibles excepciones durante la creación del cliente.
            int nuevoId = generarNuevoId(); // Genera un nuevo ID para el cliente.
            Cliente nuevoCliente = new Cliente(nuevoId, nombre, telefono); // Crea una nueva instancia de Cliente.
            banco.agregarCliente(nuevoCliente); // Llama al método del controlador para agregar el nuevo cliente.

            limpiarCampos(); // Limpia los campos del formulario después de agregar el cliente.
            mostrarExito("Cliente agregado exitosamente con ID: " + nuevoId); // Muestra un mensaje de éxito.

            // Seleccionar el nuevo cliente en la tabla para que su información aparezca.
            tablaClientes.getSelectionModel().select(nuevoCliente); 

        } catch (Exception e) { // Captura cualquier excepción que pueda ocurrir.
            mostrarError("Error al agregar cliente: " + e.getMessage()); // Muestra un mensaje de error con el detalle de la excepción.
        }
    }

    // Método para limpiar todos los campos de texto del formulario.
    private void limpiarCampos() {
        txtNombre.clear(); // Borra el texto del campo nombre.
        txtTelefono.clear(); // Borra el texto del campo teléfono.
        txtBuscar.clear(); // Borra el texto del campo de búsqueda.
    }

    // Método para filtrar la lista de clientes en la tabla basándose en el texto de búsqueda.
    private void buscarCliente(String textoBusqueda) {
        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) { // Si el texto de búsqueda es nulo o vacío.
            tablaClientes.setItems(banco.getClientes()); // Restablece la tabla para mostrar todos los clientes.
            return; // Sale del método.
        }

        // Filtra la lista original de clientes.
        var clientesFiltrados = banco.getClientes().filtered(cliente -> 
            // Comprueba si el nombre del cliente (ignorando mayúsculas/minúsculas) contiene el texto de búsqueda,
            // o si el ID del cliente (convertido a String) contiene el texto de búsqueda,
            // o si el teléfono del cliente contiene el texto de búsqueda.
            cliente.getNombre().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
            String.valueOf(cliente.getId()).contains(textoBusqueda) ||
            cliente.getTelefono().contains(textoBusqueda)
        );

        tablaClientes.setItems(clientesFiltrados); // Actualiza la tabla con la lista de clientes filtrados.
    }

    // Método para mostrar la información detallada de un cliente seleccionado en el panel derecho.
    private void mostrarInformacionCliente(Cliente cliente) {
        // Obtiene una referencia al panel de información del cliente (el VBox en la región derecha del BorderPane).
        VBox panelInfo = (VBox) ((BorderPane) tablaClientes.getScene().getRoot()).getRight(); 
        panelInfo.getChildren().clear(); // Limpia todos los elementos hijos actuales del panel de información.

        Label titulo = new Label("Información del Cliente"); // Vuelve a añadir el título.
        titulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;"); // Estilos para el título.

        Label lblId = new Label("ID: " + cliente.getId()); // Muestra el ID del cliente.
        lblId.setStyle("-fx-font-weight: bold;"); // Estilo de negrita.

        Label lblNombre = new Label("Nombre: " + cliente.getNombre()); // Muestra el nombre del cliente.
        Label lblTelefono = new Label("Teléfono: " + cliente.getTelefono()); // Muestra el teléfono del cliente.
        Label lblCuentas = new Label("Número de cuentas: " + cliente.getCuentas().size()); // Muestra la cantidad de cuentas.

        // Información de cuentas detallada
        Label lblDetalleCuentas = new Label("Detalle de cuentas:"); // Etiqueta para el detalle de cuentas.
        lblDetalleCuentas.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;"); // Estilos.

        VBox detalleCuentas = new VBox(5); // Crea un VBox para contener los detalles de cada cuenta con espaciado.
        if (cliente.getCuentas().isEmpty()) { // Si el cliente no tiene cuentas.
            Label sinCuentas = new Label("Sin cuentas asociadas"); // Muestra un mensaje.
            sinCuentas.setStyle("-fx-text-fill: #6c757d; -fx-font-style: italic;"); // Estilos.
            detalleCuentas.getChildren().add(sinCuentas); // Añade el mensaje al VBox de detalle de cuentas.
        } else { // Si el cliente tiene cuentas.
            for (int i = 0; i < cliente.getCuentas().size(); i++) { // Itera sobre cada cuenta del cliente.
                var cuenta = cliente.getCuentas().get(i); // Obtiene la cuenta actual.
                String tipoCuenta = cuenta.getClass().getSimpleName(); // Obtiene el nombre simple de la clase de la cuenta (ej. "Corriente", "CajaAhorro").
                // Formatea la información de la cuenta.
                String info = String.format("• %s N°%d - Saldo: %s", 
                    tipoCuenta, cuenta.getNumero(), ValidacionesUtil.formatearMonto(cuenta.getSaldo()));
                
                Label lblCuenta = new Label(info); // Crea una etiqueta con la información formateada de la cuenta.
                lblCuenta.setStyle("-fx-font-size: 11px;"); // Estilo para la etiqueta de la cuenta.
                detalleCuentas.getChildren().add(lblCuenta); // Añade la etiqueta de la cuenta al VBox de detalle de cuentas.
            }
        }

        // Añade todos los componentes actualizados al panel de información del cliente.
        panelInfo.getChildren().addAll(titulo, new Separator(), lblId, lblNombre, 
                                     lblTelefono, lblCuentas, new Separator(),
                                     lblDetalleCuentas, detalleCuentas);
    }

    // Método privado para generar un nuevo ID simple basado en el número de clientes existentes.
    // Esto es un enfoque simplista y podría no ser robusto en un sistema real (e.g., al eliminar clientes).
    private int generarNuevoId() {
        return banco.getClientes().size() + 1; // Retorna el número actual de clientes + 1.
    }

    // Método privado para mostrar una ventana de alerta de error.
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Crea una alerta de tipo ERROR.
        alert.setTitle("Error"); // Establece el título de la alerta.
        alert.setHeaderText(null); // No muestra un encabezado.
        alert.setContentText(mensaje); // Establece el mensaje de contenido.
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre.
    }

    // Método privado para mostrar una ventana de alerta de éxito/información.
    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Crea una alerta de tipo INFORMATION.
        alert.setTitle("Éxito"); // Establece el título.
        alert.setHeaderText(null); // No muestra un encabezado.
        alert.setContentText(mensaje); // Establece el mensaje de contenido.
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre.
    }
}