package Ejercicio2.view; // Declara el paquete al que pertenece esta clase.

import Ejercicio2.controller.BancoController; // Importa el controlador del banco para interactuar con la lógica de negocio.
import Ejercicio2.model.*; // Importa todas las clases del paquete model (Cliente, Cuenta, Transaccion, Hora, ValidacionesUtil, etc.).
import javafx.application.Application; // Importa la clase base para las aplicaciones JavaFX.
import javafx.beans.property.SimpleDoubleProperty; // Para envolver propiedades de tipo double observable para TableView.
import javafx.beans.property.SimpleIntegerProperty; // Para envolver propiedades de tipo int observable para TableView.
import javafx.beans.property.SimpleStringProperty; // Para envolver propiedades de tipo String observable para TableView.
import javafx.collections.FXCollections; // Utilidad para crear ObservableList.
import javafx.collections.ObservableList; // Tipo de lista que notifica a la UI sobre cambios.
import javafx.geometry.Insets; // Para especificar el relleno alrededor de los nodos.
import javafx.scene.Scene; // Representa el contenido de una ventana.
import javafx.scene.control.*; // Importa todos los controles de JavaFX (Button, Label, TextField, ComboBox, TableView, TextArea, etc.).
import javafx.scene.layout.*; // Importa todos los layouts de JavaFX (BorderPane, VBox, HBox, etc.).
import javafx.stage.Stage; // Representa la ventana principal de la aplicación.
import java.time.format.DateTimeFormatter; // Para formatear objetos LocalDateTime a String.
import java.util.List; // Para usar el tipo List.

public class CuentaUI extends Application { // Define la clase CuentaUI, que extiende Application para ser una aplicación JavaFX.

    private BancoController banco; // Instancia del controlador del banco para manejar las operaciones bancarias.
    private TableView<Cuenta> tablaCuentas; // Tabla para mostrar las cuentas de un cliente seleccionado.
    private TableView<Transaccion> tablaTransacciones; // Tabla para mostrar las transacciones de una cuenta seleccionada.
    private ComboBox<Cliente> cmbClientes; // ComboBox para seleccionar un cliente.
    private ComboBox<Cuenta> cmbCuentas; // ComboBox para seleccionar una cuenta del cliente elegido.
    private TextField txtMonto; // Campo de texto para ingresar el monto de una transacción.
    private Label lblSaldo; // Etiqueta para mostrar el saldo actual de la cuenta seleccionada.
    private TextArea txtLog; // Área de texto para mostrar un log de operaciones y mensajes.

    // Método para inyectar la dependencia del BancoController.
    // Esto es útil cuando se inicializa la UI desde una clase principal (e.g., AppBanco).
    public void setBancoController(BancoController banco) { 
        this.banco = banco; // Asigna la instancia del BancoController.
    }

    @Override // Anotación que indica que este método sobrescribe el método 'start' de la clase Application.
    public void start(Stage stage) { // Punto de entrada principal para la interfaz de usuario de esta vista.
        BorderPane root = new BorderPane(); // Crea un BorderPane como el contenedor principal, que permite organizar los elementos en regiones (top, bottom, left, right, center).
        root.setPadding(new Insets(10)); // Establece un relleno de 10 píxeles alrededor de los bordes del BorderPane.

        // Panel superior - Título de la vista.
        Label titulo = new Label("💳 GESTIÓN DE CUENTAS Y TRANSACCIONES"); // Crea una etiqueta para el título.
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;"); // Aplica estilos CSS al título.
        root.setTop(titulo); // Coloca el título en la región superior del BorderPane.

        // Panel izquierdo - Controles para crear cuentas y realizar transacciones.
        VBox panelIzquierdo = crearPanelControles(); // Llama a un método privado para construir el panel de controles.
        root.setLeft(panelIzquierdo); // Coloca el panel de controles en la región izquierda del BorderPane.

        // Panel central - Tablas de cuentas y transacciones.
        VBox panelCentral = crearPanelTablas(); // Llama a un método privado para construir el panel de tablas.
        root.setCenter(panelCentral); // Coloca el panel de tablas en la región central del BorderPane.

        // Panel derecho - Área de log de operaciones.
        VBox panelDerecho = crearPanelLog(); // Llama a un método privado para construir el panel del log.
        root.setRight(panelDerecho); // Coloca el panel del log en la región derecha del BorderPane.

        Scene scene = new Scene(root, 1000, 600); // Crea una nueva escena con el contenedor 'root' y dimensiones iniciales.
        stage.setScene(scene); // Asigna la escena a la ventana (stage).
        stage.setTitle("Gestión de Cuentas"); // Establece el título de la ventana.
        stage.show(); // Hace visible la ventana.

        // Llama a este método después de que la escena y el stage estén configurados,
        // para poblar el ComboBox de clientes al inicio.
        actualizarComboClientes(); 
    }

    // Método privado para crear el panel con los controles para la gestión de cuentas y transacciones.
    private VBox crearPanelControles() {
        VBox panel = new VBox(10); // Crea un VBox con un espaciado de 10 píxeles entre sus hijos.
        panel.setPadding(new Insets(10)); // Establece un relleno interno de 10 píxeles.
        panel.setPrefWidth(300); // Establece el ancho preferido del panel.
        panel.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 1;"); // Aplica estilos de fondo y borde.

        // Sección de selección de cliente.
        Label lblCliente = new Label("Cliente:"); // Etiqueta para el ComboBox de clientes.
        lblCliente.setStyle("-fx-font-weight: bold;"); // Estilo de negrita.
        cmbClientes = new ComboBox<>(); // Crea el ComboBox para seleccionar clientes.
        cmbClientes.setPrefWidth(Double.MAX_VALUE); // Hace que el ComboBox ocupe el ancho máximo disponible.
        // Define la acción a ejecutar cuando se selecciona un cliente en el ComboBox.
        // Llama a 'actualizarComboCuentas' para poblar el ComboBox de cuentas de ese cliente.
        cmbClientes.setOnAction(e -> actualizarComboCuentas()); 

        // Sección para crear nueva cuenta.
        Label lblNuevaCuenta = new Label("Crear Nueva Cuenta:"); // Título para la sección de creación de cuentas.
        lblNuevaCuenta.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;"); // Estilos para el título.

        TextField txtSaldoInicial = new TextField(); // Campo de texto para ingresar el saldo inicial.
        txtSaldoInicial.setPromptText("Saldo inicial"); // Texto de sugerencia.

        Button btnCuentaCorriente = new Button("Crear Cuenta Corriente"); // Botón para crear una cuenta corriente.
        btnCuentaCorriente.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;"); // Estilos del botón.
        btnCuentaCorriente.setPrefWidth(Double.MAX_VALUE); // Ancho máximo.
        // Define la acción para crear una cuenta corriente al hacer clic.
        btnCuentaCorriente.setOnAction(e -> crearCuentaCorriente(txtSaldoInicial)); 

        TextField txtMovAnuales = new TextField(); // Campo de texto para ingresar el límite de movimientos anuales (para Caja de Ahorro).
        txtMovAnuales.setPromptText("Movimientos anuales (ej: 12)"); // Texto de sugerencia.

        Button btnCajaAhorro = new Button("Crear Caja de Ahorro"); // Botón para crear una caja de ahorro.
        btnCajaAhorro.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;"); // Estilos del botón.
        btnCajaAhorro.setPrefWidth(Double.MAX_VALUE); // Ancho máximo.
        // Define la acción para crear una caja de ahorro al hacer clic.
        btnCajaAhorro.setOnAction(e -> crearCajaAhorro(txtSaldoInicial, txtMovAnuales)); 

        // Sección para realizar transacciones.
        Separator sep1 = new Separator(); // Separador visual.
        Label lblTransacciones = new Label("Realizar Transacciones:"); // Título para la sección de transacciones.
        lblTransacciones.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;"); // Estilos del título.

        Label lblCuenta = new Label("Cuenta:"); // Etiqueta para el ComboBox de cuentas.
        cmbCuentas = new ComboBox<>(); // ComboBox para seleccionar la cuenta en la que realizar la transacción.
        cmbCuentas.setPrefWidth(Double.MAX_VALUE); // Ancho máximo.
        // Define la acción a ejecutar cuando se selecciona una cuenta en el ComboBox.
        // Llama a 'actualizarSaldo' para mostrar el saldo de la cuenta seleccionada y sus transacciones.
        cmbCuentas.setOnAction(e -> actualizarSaldo()); 

        lblSaldo = new Label("Saldo: $0.00"); // Etiqueta para mostrar el saldo.
        lblSaldo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #28a745;"); // Estilos para el saldo.

        txtMonto = new TextField(); // Campo de texto para ingresar el monto de la transacción.
        txtMonto.setPromptText("Monto"); // Texto de sugerencia.

        Button btnDeposito = new Button("DEPÓSITO"); // Botón para realizar un depósito.
        btnDeposito.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;"); // Estilos del botón.
        btnDeposito.setPrefWidth(Double.MAX_VALUE); // Ancho máximo.
        btnDeposito.setOnAction(e -> realizarDeposito()); // Define la acción para realizar un depósito.

        Button btnRetiro = new Button("RETIRO"); // Botón para realizar un retiro.
        btnRetiro.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;"); // Estilos del botón.
        btnRetiro.setPrefWidth(Double.MAX_VALUE); // Ancho máximo.
        btnRetiro.setOnAction(e -> realizarRetiro()); // Define la acción para realizar un retiro.

        // Añade todos los componentes a este panel en el orden deseado.
        panel.getChildren().addAll(
            lblCliente, cmbClientes,
            new Separator(), // Separador visual.
            lblNuevaCuenta,
            txtSaldoInicial, btnCuentaCorriente,
            txtMovAnuales, btnCajaAhorro,
            sep1, // Separador visual.
            lblTransacciones,
            lblCuenta, cmbCuentas,
            lblSaldo,
            txtMonto,
            btnDeposito, btnRetiro
        );

        return panel; // Retorna el panel VBox configurado.
    }

    // Método privado para crear el panel que contiene las tablas de cuentas y transacciones.
    private VBox crearPanelTablas() {
        VBox panel = new VBox(10); // Crea un VBox con espaciado de 10 píxeles.
        panel.setPadding(new Insets(10)); // Establece un relleno interno.

        // Sección de tabla de cuentas.
        Label lblCuentas = new Label("Cuentas del Cliente:"); // Etiqueta para la tabla de cuentas.
        lblCuentas.setStyle("-fx-font-weight: bold;"); // Estilo de negrita.

        tablaCuentas = new TableView<>(); // Crea una nueva TableView para mostrar objetos Cuenta.
        
        // Columna 'Número' para la tabla de cuentas.
        TableColumn<Cuenta, Integer> colNumero = new TableColumn<>("Número"); 
        // Define cómo obtener el valor de la propiedad 'numero' de una Cuenta.
        colNumero.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNumero()).asObject()); 
        colNumero.setPrefWidth(80); // Ancho preferido.

        // Columna 'Tipo' para la tabla de cuentas (muestra si es "Caja Ahorro" o "Corriente").
        TableColumn<Cuenta, String> colTipo = new TableColumn<>("Tipo"); 
        // Define cómo obtener el tipo de cuenta. Usa 'instanceof' para determinar el tipo concreto.
        colTipo.setCellValueFactory(data -> { 
            String tipo = data.getValue() instanceof CajaAhorro ? "Caja Ahorro" : "Corriente";
            return new SimpleStringProperty(tipo); // Retorna una SimpleStringProperty con el tipo de cuenta.
        });
        colTipo.setPrefWidth(100); // Ancho preferido.

        // Columna 'Saldo' para la tabla de cuentas.
        TableColumn<Cuenta, Double> colSaldo = new TableColumn<>("Saldo"); 
        // Define cómo obtener el valor de la propiedad 'saldo' de una Cuenta.
        colSaldo.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSaldo()).asObject()); 
        // Define una fábrica de celdas personalizada para formatear el saldo como moneda.
        colSaldo.setCellFactory(col -> new TableCell<Cuenta, Double>() { 
            @Override // Sobrescribe el método updateItem para personalizar cómo se muestra el ítem en la celda.
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty); // Llama al método de la superclase.
                if (empty || item == null) { // Si la celda está vacía o el ítem es nulo.
                    setText(null); // No muestra texto.
                } else { // Si hay un valor.
                    setText("$" + String.format("%.2f", item)); // Formatea el saldo a dos decimales con un '$' y lo establece como texto de la celda.
                }
            }
        });
        colSaldo.setPrefWidth(100); // Ancho preferido.

        // Añade todas las columnas a la tabla de cuentas.
        tablaCuentas.getColumns().addAll(colNumero, colTipo, colSaldo); 
        tablaCuentas.setPrefHeight(200); // Establece la altura preferida de la tabla.

        // Sección de tabla de transacciones.
        Label lblTransacciones = new Label("Historial de Transacciones:"); // Etiqueta para la tabla de transacciones.
        lblTransacciones.setStyle("-fx-font-weight: bold;"); // Estilo de negrita.

        tablaTransacciones = new TableView<>(); // Crea una nueva TableView para mostrar objetos Transaccion.

        // Columna 'Tipo' para la tabla de transacciones.
        TableColumn<Transaccion, String> colTipoTrans = new TableColumn<>("Tipo"); 
        // Define cómo obtener el tipo de transacción (DEPOSITO/RETIRO) como String.
        colTipoTrans.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipo().toString())); 
        colTipoTrans.setPrefWidth(80); // Ancho preferido.

        // Columna 'Monto' para la tabla de transacciones.
        TableColumn<Transaccion, Double> colMontoTrans = new TableColumn<>("Monto"); 
        // Define cómo obtener el monto de la transacción.
        colMontoTrans.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMonto()).asObject()); 
        // Define una fábrica de celdas personalizada para formatear el monto como moneda.
        colMontoTrans.setCellFactory(col -> new TableCell<Transaccion, Double>() { 
            @Override // Sobrescribe el método updateItem.
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("$" + String.format("%.2f", item)); // Formatea el monto.
                }
            }
        });
        colMontoTrans.setPrefWidth(100); // Ancho preferido.

        // Columna 'Fecha' para la tabla de transacciones.
        TableColumn<Transaccion, String> colFechaTrans = new TableColumn<>("Fecha"); 
        // Define cómo obtener la fecha de la transacción y formatearla a una cadena específica.
        colFechaTrans.setCellValueFactory(data -> new SimpleStringProperty(
            data.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) // Formato de fecha y hora.
        ));
        colFechaTrans.setPrefWidth(120); // Ancho preferido.

        // Añade todas las columnas a la tabla de transacciones.
        tablaTransacciones.getColumns().addAll(colTipoTrans, colMontoTrans, colFechaTrans); 
        tablaTransacciones.setPrefHeight(200); // Establece la altura preferida de la tabla.

        // Añade las etiquetas y las tablas a este panel.
        panel.getChildren().addAll(lblCuentas, tablaCuentas, lblTransacciones, tablaTransacciones); 
        return panel; // Retorna el panel VBox configurado.
    }

    // Método privado para crear el panel del log de operaciones.
    private VBox crearPanelLog() {
        VBox panel = new VBox(10); // Crea un VBox con espaciado.
        panel.setPadding(new Insets(10)); // Establece un relleno.
        panel.setPrefWidth(250); // Ancho preferido.

        Label titulo = new Label("Log de Operaciones:"); // Etiqueta para el título del log.
        titulo.setStyle("-fx-font-weight: bold;"); // Estilo de negrita.

        txtLog = new TextArea(); // Crea un TextArea para mostrar los mensajes del log.
        txtLog.setEditable(false); // Hace que el TextArea no sea editable por el usuario.
        txtLog.setPrefRowCount(20); // Establece el número de filas preferido.
        txtLog.setStyle("-fx-font-family: monospace; -fx-font-size: 10px;"); // Estilos para la fuente del log.

        Button btnLimpiarLog = new Button("Limpiar Log"); // Botón para limpiar el contenido del log.
        btnLimpiarLog.setOnAction(e -> txtLog.clear()); // Define la acción para limpiar el TextArea.

        // Añade el título, el TextArea y el botón al panel del log.
        panel.getChildren().addAll(titulo, txtLog, btnLimpiarLog); 
        return panel; // Retorna el panel VBox configurado.
    }

    // Método para actualizar los ítems del ComboBox de clientes.
    private void actualizarComboClientes() {
        if (banco != null && banco.getClientes() != null) { // Comprueba que el controlador y la lista de clientes no sean nulos.
            cmbClientes.setItems(banco.getClientes()); // Establece la lista observable de clientes del controlador como ítems del ComboBox.
        }
    }

    // Método para actualizar los ítems del ComboBox de cuentas y la tabla de cuentas basándose en el cliente seleccionado.
    private void actualizarComboCuentas() {
        Cliente clienteSeleccionado = cmbClientes.getValue(); // Obtiene el cliente actualmente seleccionado en el ComboBox.
        if (clienteSeleccionado != null && banco != null) { // Si hay un cliente seleccionado y el controlador no es nulo.
            ObservableList<Cuenta> cuentas = banco.getCuentasDeCliente(clienteSeleccionado.getId()); // Obtiene las cuentas del cliente desde el controlador.
            if (cuentas != null) { // Si la lista de cuentas no es nula.
                cmbCuentas.setItems(cuentas); // Establece las cuentas como ítems del ComboBox de cuentas.
                tablaCuentas.setItems(cuentas); // Establece las mismas cuentas como ítems de la tabla de cuentas.
                // Limpia la selección en el combo de cuentas para evitar un estado inconsistente
                cmbCuentas.getSelectionModel().clearSelection(); 
                // Limpia la tabla de transacciones al cambiar de cliente/cuentas
                tablaTransacciones.setItems(FXCollections.observableArrayList());
                lblSaldo.setText("Saldo: $0.00"); // Reinicia el saldo
            }
        }
    }

    // Método para actualizar la etiqueta de saldo y la tabla de transacciones cuando se selecciona una cuenta.
    private void actualizarSaldo() {
        Cuenta cuentaSeleccionada = cmbCuentas.getValue(); // Obtiene la cuenta actualmente seleccionada en el ComboBox de cuentas.
        if (cuentaSeleccionada != null) { // Si hay una cuenta seleccionada.
            // Actualiza la etiqueta de saldo con el saldo formateado de la cuenta seleccionada.
            lblSaldo.setText("Saldo: $" + String.format("%.2f", cuentaSeleccionada.getSaldo())); 
            
            // Mostrar transacciones de la cuenta
            ObservableList<Transaccion> transacciones = FXCollections.observableArrayList(); // Crea una nueva ObservableList para las transacciones.
            try {
                // *** AVISO: Esta parte usa reflexión para acceder a un campo 'protected' de la clase Cuenta. ***
                // Es preferible que la clase Cuenta tenga un método público 'getTransacciones()' para acceder a ellas.
                java.lang.reflect.Field field = Cuenta.class.getDeclaredField("transacciones"); // Obtiene el campo 'transacciones' por reflexión.
                field.setAccessible(true); // Hace el campo accesible (incluso si es privado/protegido).
                @SuppressWarnings("unchecked") // Suprime advertencias de tipo no seguro para la conversión.
                List<Transaccion> lista = (List<Transaccion>) field.get(cuentaSeleccionada); // Obtiene la lista de transacciones del objeto cuenta.
                if (lista != null) { // Si la lista no es nula.
                    transacciones.addAll(lista); // Añade todas las transacciones a la ObservableList.
                }
            } catch (NoSuchFieldException | IllegalAccessException e) { // Captura excepciones si el campo no existe o no es accesible.
                log("Error al obtener transacciones: " + e.getMessage()); // Registra el error en el log.
            }
            tablaTransacciones.setItems(transacciones); // Establece las transacciones en la tabla de transacciones.
        }
    }

    // Método para manejar la creación de una nueva cuenta corriente.
    private void crearCuentaCorriente(TextField txtSaldoInicial) {
        Cliente cliente = cmbClientes.getValue(); // Obtiene el cliente seleccionado.
        if (cliente == null) { // Si no hay cliente seleccionado.
            mostrarError("Seleccione un cliente"); // Muestra un error.
            return; // Sale del método.
        }

        if (banco == null) { // Si el controlador del banco no está inicializado.
            mostrarError("Controller del banco no inicializado"); // Muestra un error.
            return; // Sale del método.
        }

        try {
            // Intenta parsear el saldo inicial. Si el campo está vacío, usa 0.0.
            double saldoInicial = txtSaldoInicial.getText().isEmpty() ? 0.0 : Double.parseDouble(txtSaldoInicial.getText()); 
            int numeroCuenta = generarNumeroCuenta(); // Genera un nuevo número de cuenta.
            
            Corriente cuenta = new Corriente(numeroCuenta); // Crea una nueva instancia de CuentaCorriente.
            if (saldoInicial > 0) { // Si el saldo inicial es positivo.
                cuenta.deposito(saldoInicial, cliente); // Realiza un depósito inicial en la cuenta (usando el método de la cuenta).
            }
            
            // CORREGIDO: Asegurarse de agregar la cuenta al cliente.
            // Aunque el método ya existe en Cliente, es fundamental llamarlo.
            // El diagrama UML indica que el cliente 'tiene' las cuentas.
            cliente.agregarCuenta(cuenta); 
            actualizarComboCuentas(); // Actualiza los ComboBox de cuentas y las tablas para reflejar la nueva cuenta.
            txtSaldoInicial.clear(); // Limpia el campo de texto del saldo inicial.
            
            log("Cuenta Corriente creada: N° " + numeroCuenta + " para " + cliente.getNombre()); // Registra la operación en el log.
            mostrarExito("Cuenta Corriente creada exitosamente"); // Muestra un mensaje de éxito.
            
        } catch (NumberFormatException e) { // Captura si el saldo inicial no es un número válido.
            mostrarError("Saldo inicial debe ser un número válido"); // Muestra un error.
        } catch (Exception e) { // Captura cualquier otra excepción.
            mostrarError("Error al crear cuenta: " + e.getMessage()); // Muestra un error al usuario.
            log("ERROR: " + e.getMessage()); // Registra el error en el log.
        }
    }

    // Método para manejar la creación de una nueva caja de ahorro.
    private void crearCajaAhorro(TextField txtSaldoInicial, TextField txtMovAnuales) {
        Cliente cliente = cmbClientes.getValue(); // Obtiene el cliente seleccionado.
        if (cliente == null) { // Si no hay cliente seleccionado.
            mostrarError("Seleccione un cliente"); // Muestra un error.
            return; // Sale del método.
        }

        if (banco == null) { // Si el controlador del banco no está inicializado.
            mostrarError("Controller del banco no inicializado"); // Muestra un error.
            return; // Sale del método.
        }

        try {
            // Intenta parsear el saldo inicial (0.0 si está vacío).
            double saldoInicial = txtSaldoInicial.getText().isEmpty() ? 0.0 : Double.parseDouble(txtSaldoInicial.getText()); 
            // Intenta parsear los movimientos anuales (12 por defecto si está vacío).
            int movAnuales = txtMovAnuales.getText().isEmpty() ? 12 : Integer.parseInt(txtMovAnuales.getText()); 
            int numeroCuenta = generarNumeroCuenta(); // Genera un nuevo número de cuenta.
            
            CajaAhorro cuenta = new CajaAhorro(numeroCuenta, movAnuales); // Crea una nueva instancia de CajaAhorro.
            if (saldoInicial > 0) { // Si el saldo inicial es positivo.
                cuenta.deposito(saldoInicial, cliente); // Realiza un depósito inicial en la cuenta.
            }
            
            cliente.agregarCuenta(cuenta); // Agrega la cuenta al cliente.
            actualizarComboCuentas(); // Actualiza los ComboBox y tablas.
            txtSaldoInicial.clear(); // Limpia el campo de saldo inicial.
            txtMovAnuales.clear(); // Limpia el campo de movimientos anuales.
            
            log("Caja de Ahorro creada: N° " + numeroCuenta + " para " + cliente.getNombre() + " (Max mov: " + movAnuales + ")"); // Registra en el log.
            mostrarExito("Caja de Ahorro creada exitosamente"); // Muestra un mensaje de éxito.
            
        } catch (NumberFormatException e) { // Captura si los valores numéricos son inválidos.
            mostrarError("Valores numéricos inválidos"); // Muestra un error.
        } catch (Exception e) { // Captura cualquier otra excepción.
            mostrarError("Error al crear cuenta: " + e.getMessage()); // Muestra un error al usuario.
            log("ERROR: " + e.getMessage()); // Registra el error.
        }
    }

    // Método para manejar la operación de depósito.
    private void realizarDeposito() {
        try {
            Cliente cliente = cmbClientes.getValue(); // Obtiene el cliente seleccionado.
            Cuenta cuenta = cmbCuentas.getValue(); // Obtiene la cuenta seleccionada.
            String montoStr = txtMonto.getText(); // Obtiene el monto del campo de texto.

            if (cliente == null || cuenta == null || montoStr.isEmpty()) { // Validaciones básicas.
                mostrarError("Complete todos los campos"); // Muestra un error si faltan campos.
                return; // Sale del método.
            }

            double monto = Double.parseDouble(montoStr); // Convierte el monto a double.
            // Realiza el depósito llamando al método 'deposito' del cliente, que delega a la cuenta.
            double nuevoSaldo = cliente.deposito(cuenta, monto); 

            actualizarSaldo(); // Actualiza el saldo mostrado y las transacciones.
            txtMonto.clear(); // Limpia el campo del monto.

            // Registra la operación en el log.
            log("DEPÓSITO: $" + String.format("%.2f", monto) + " en cuenta " + cuenta.getNumero() + 
                " de " + cliente.getNombre() + ". Nuevo saldo: $" + String.format("%.2f", nuevoSaldo));
            mostrarExito("Depósito realizado exitosamente"); // Muestra un mensaje de éxito.

        } catch (NumberFormatException e) { // Captura si el monto no es un número válido.
            mostrarError("Monto debe ser un número válido"); // Muestra un error.
        } catch (RuntimeException e) { // Captura excepciones lanzadas por la lógica de negocio (e.g., por la cuenta).
            mostrarError("Error: " + e.getMessage()); // Muestra el mensaje de la excepción.
            log("ERROR DEPÓSITO: " + e.getMessage()); // Registra el error en el log.
        }
    }

    // Método para manejar la operación de retiro.
    private void realizarRetiro() {
        try {
            Cliente cliente = cmbClientes.getValue(); // Obtiene el cliente seleccionado.
            Cuenta cuenta = cmbCuentas.getValue(); // Obtiene la cuenta seleccionada.
            String montoStr = txtMonto.getText(); // Obtiene el monto del campo de texto.

            if (cliente == null || cuenta == null || montoStr.isEmpty()) { // Validaciones básicas.
                mostrarError("Complete todos los campos"); // Muestra un error.
                return; // Sale del método.
            }

            double monto = Double.parseDouble(montoStr); // Convierte el monto a double.
            // Realiza el retiro llamando al método 'retiro' del cliente, que delega a la cuenta.
            double nuevoSaldo = cliente.retiro(cuenta, monto); 

            if (nuevoSaldo == -1) { // Si el retiro retorna -1, significa que la cuenta no pertenece al cliente (según la implementación de Cliente.retiro).
                mostrarError("La cuenta no pertenece al cliente seleccionado"); // Muestra un error específico.
                return; // Sale del método.
            }

            actualizarSaldo(); // Actualiza el saldo mostrado y las transacciones.
            txtMonto.clear(); // Limpia el campo del monto.

            // Registra la operación en el log.
            log("RETIRO: $" + String.format("%.2f", monto) + " de cuenta " + cuenta.getNumero() + 
                " de " + cliente.getNombre() + ". Nuevo saldo: $" + String.format("%.2f", nuevoSaldo));
            mostrarExito("Retiro realizado exitosamente"); // Muestra un mensaje de éxito.

        } catch (NumberFormatException e) { // Captura si el monto no es un número válido.
            mostrarError("Monto debe ser un número válido"); // Muestra un error.
        } catch (RuntimeException e) { // Captura excepciones lanzadas por la lógica de negocio (e.g., saldo insuficiente, límite de movimientos).
            mostrarError("Error: " + e.getMessage()); // Muestra el mensaje de la excepción al usuario.
            log("ERROR RETIRO: " + e.getMessage()); // Registra el error en el log.
        }
    }

    // Método privado para generar un número de cuenta aleatorio (entre 1000 y 9999).
    private int generarNumeroCuenta() {
        return (int) (Math.random() * 9000) + 1000; // Genera un número entero aleatorio.
    }

    // Método privado para añadir un mensaje al log de operaciones con la hora actual.
    private void log(String mensaje) {
        if (txtLog != null) { // Comprueba que el TextArea del log esté inicializado.
            // Añade la hora actual formateada, un guion y el mensaje, seguido de una nueva línea.
            txtLog.appendText(java.time.LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("HH:mm:ss")) + " - " + mensaje + "\n");
        }
    }

    // Método privado para mostrar una ventana de alerta de tipo ERROR.
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Crea una alerta de tipo ERROR.
        alert.setTitle("Error"); // Establece el título de la alerta.
        alert.setHeaderText(null); // No muestra un encabezado.
        alert.setContentText(mensaje); // Establece el mensaje de contenido.
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre.
    }

    // Método privado para mostrar una ventana de alerta de tipo INFORMATION (éxito).
    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Crea una alerta de tipo INFORMATION.
        alert.setTitle("Éxito"); // Establece el título.
        alert.setHeaderText(null); // No muestra un encabezado.
        alert.setContentText(mensaje); // Establece el mensaje de contenido.
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre.
    }
}