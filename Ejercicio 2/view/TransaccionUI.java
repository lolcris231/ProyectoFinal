package Ejercicio2.view; // Declara el paquete al que pertenece esta clase, 'Ejercicio2.view'.

import Ejercicio2.controller.BancoController; // Importa el controlador del banco para interactuar con la lógica de negocio.
import Ejercicio2.model.*; // Importa todas las clases del paquete model (Cliente, Cuenta, Transaccion, TipoTransaccion, CajaAhorro, Corriente, etc.).
import javafx.application.Application; // Clase base para las aplicaciones JavaFX.
import javafx.beans.property.SimpleDoubleProperty; // Para envolver propiedades de tipo double observable para TableView.
import javafx.beans.property.SimpleStringProperty; // Para envolver propiedades de tipo String observable para TableView.
import javafx.collections.FXCollections; // Utilidad para crear ObservableList.
import javafx.collections.ObservableList; // Tipo de lista que notifica a la UI sobre cambios.
import javafx.geometry.Insets; // Para especificar el relleno alrededor de los nodos.
import javafx.scene.Scene; // Representa el contenido de una ventana.
import javafx.scene.control.*; // Importa todos los controles de JavaFX (Button, Label, TextField, ComboBox, TableView, DatePicker, etc.).
import javafx.scene.layout.*; // Importa todos los layouts de JavaFX (BorderPane, VBox, HBox, etc.).
import javafx.stage.Stage; // Representa la ventana principal o una ventana secundaria.
import java.time.LocalDate; // Para trabajar con fechas sin hora.
import java.time.LocalDateTime; // Para trabajar con fechas y horas.
import java.time.format.DateTimeFormatter; // Para formatear objetos LocalDateTime a String.
import java.time.format.DateTimeParseException; // Excepción para errores de parseo de fecha/hora.
import java.util.List; // Para usar el tipo List.

public class TransaccionUI extends Application { // Define la clase TransaccionUI, que extiende Application para ser una aplicación JavaFX.

    private BancoController banco; // Instancia del controlador del banco para obtener los datos.
    private TableView<TransaccionDetalle> tablaTransacciones; // Tabla para mostrar el historial detallado de transacciones.
    private ComboBox<Cliente> cmbClientes; // ComboBox para filtrar transacciones por cliente.
    private DatePicker dateInicio; // Selector de fecha para el rango de inicio del filtro.
    private DatePicker dateFin; // Selector de fecha para el rango de fin del filtro.
    private Label lblTotalTransacciones; // Etiqueta para mostrar el número total de transacciones.
    private Label lblTotalDepositos; // Etiqueta para mostrar el monto total de depósitos.
    private Label lblTotalRetiros; // Etiqueta para mostrar el monto total de retiros.
    private Label lblPromedioTransaccion; // Etiqueta para mostrar el monto promedio de las transacciones.

    // Clase interna estática para representar una transacción con detalles adicionales para la UI.
    // Esto es útil porque la clase 'Transaccion' original podría no contener el nombre del cliente o el tipo de cuenta.
    public static class TransaccionDetalle {
        private final String cliente; // Nombre del cliente asociado a la transacción.
        private final int numeroCuenta; // Número de cuenta donde ocurrió la transacción.
        private final String tipoCuenta; // Tipo de cuenta (Caja Ahorro o Corriente).
        private final TipoTransaccion tipoTransaccion; // Tipo de transacción (DEPÓSITO o RETIRO).
        private final double monto; // Monto de la transacción.
        private final String fecha; // Fecha y hora de la transacción formateada como String para la tabla.
        private final LocalDateTime fechaTransaccion; // Fecha y hora real de la transacción para lógica de filtrado.

        // Constructor para inicializar un objeto TransaccionDetalle.
        public TransaccionDetalle(String cliente, int numeroCuenta, String tipoCuenta, 
                                TipoTransaccion tipoTransaccion, double monto, String fecha, LocalDateTime fechaTransaccion) {
            this.cliente = cliente; // Asigna el nombre del cliente.
            this.numeroCuenta = numeroCuenta; // Asigna el número de cuenta.
            this.tipoCuenta = tipoCuenta; // Asigna el tipo de cuenta.
            this.tipoTransaccion = tipoTransaccion; // Asigna el tipo de transacción.
            this.monto = monto; // Asigna el monto.
            this.fecha = fecha; // Asigna la fecha formateada.
            this.fechaTransaccion = fechaTransaccion; // Asigna la fecha real.
        }

        // Métodos 'getter' para acceder a las propiedades del detalle de la transacción.
        public String getCliente() { return cliente; }
        public int getNumeroCuenta() { return numeroCuenta; }
        public String getTipoCuenta() { return tipoCuenta; }
        public String getTipoTransaccion() { return tipoTransaccion != null ? tipoTransaccion.toString() : "DESCONOCIDO"; } // Asegura que no sea nulo.
        public double getMonto() { return monto; }
        public String getFecha() { return fecha; }
        public LocalDateTime getFechaTransaccion() { return fechaTransaccion; }
    }

    // Método para inyectar la dependencia del BancoController.
    public void setBancoController(BancoController banco) {
        this.banco = banco; // Asigna la instancia del BancoController.
    }

    @Override // Anotación que indica que este método sobrescribe el método 'start' de la clase Application.
    public void start(Stage stage) { // Punto de entrada principal para la interfaz de usuario de esta vista.
        BorderPane root = new BorderPane(); // Crea un BorderPane como el nodo raíz de la escena.
        root.setPadding(new Insets(15)); // Establece un relleno de 15 píxeles alrededor de los bordes.

        // Título de la interfaz de usuario.
        Label titulo = new Label("📊 REPORTE DE TRANSACCIONES"); // Crea una etiqueta para el título.
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;"); // Aplica estilos CSS al título.

        // Panel de filtros.
        HBox panelFiltros = crearPanelFiltros(); // Llama a un método privado para construir el panel de filtros.
        VBox topPanel = new VBox(10, titulo, panelFiltros); // Crea un VBox para contener el título y el panel de filtros.
        root.setTop(topPanel); // Coloca este VBox en la región superior del BorderPane.

        // Tabla de transacciones.
        VBox panelTabla = crearPanelTabla(); // Llama a un método privado para construir el panel de la tabla.
        root.setCenter(panelTabla); // Coloca el panel de la tabla en la región central del BorderPane.

        // Panel de estadísticas.
        VBox panelEstadisticas = crearPanelEstadisticas(); // Llama a un método privado para construir el panel de estadísticas.
        root.setRight(panelEstadisticas); // Coloca el panel de estadísticas en la región derecha del BorderPane.

        Scene scene = new Scene(root, 1200, 700); // Crea una nueva escena con el BorderPane raíz y dimensiones iniciales.
        stage.setScene(scene); // Asigna la escena a la ventana (stage).
        stage.setTitle("Reporte de Transacciones"); // Establece el título de la ventana.
        stage.show(); // Hace visible la ventana.

        cargarDatos(); // Llama a este método para poblar los datos iniciales al arrancar la UI.
    }

    // Método privado para crear el panel con los controles de filtro.
    private HBox crearPanelFiltros() {
        HBox panel = new HBox(15); // Crea un HBox con un espaciado de 15 píxeles entre sus hijos.
        panel.setPadding(new Insets(10)); // Establece un relleno interno de 10 píxeles.
        panel.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 1;"); // Aplica estilos de fondo y borde.

        Label lblCliente = new Label("Cliente:"); // Etiqueta para el filtro de cliente.
        cmbClientes = new ComboBox<>(); // ComboBox para seleccionar clientes.
        cmbClientes.setPrefWidth(200); // Ancho preferido del ComboBox.

        Label lblFechaInicio = new Label("Desde:"); // Etiqueta para el selector de fecha de inicio.
        dateInicio = new DatePicker(); // Selector de fecha.

        Label lblFechaFin = new Label("Hasta:"); // Etiqueta para el selector de fecha de fin.
        dateFin = new DatePicker(); // Selector de fecha.

        Button btnFiltrar = new Button("🔍 Filtrar"); // Botón para aplicar los filtros.
        btnFiltrar.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;"); // Estilos del botón.
        btnFiltrar.setOnAction(e -> aplicarFiltros()); // Define la acción al hacer clic (llama al método aplicarFiltros).

        Button btnLimpiar = new Button("🗑️ Limpiar"); // Botón para limpiar los filtros.
        btnLimpiar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;"); // Estilos del botón.
        btnLimpiar.setOnAction(e -> limpiarFiltros()); // Define la acción al hacer clic (llama al método limpiarFiltros).

        // Añade todos los componentes de filtro al panel HBox.
        panel.getChildren().addAll(lblCliente, cmbClientes, lblFechaInicio, dateInicio, 
                                 lblFechaFin, dateFin, btnFiltrar, btnLimpiar);
        return panel; // Retorna el panel HBox configurado.
    }

    // Método privado para crear el panel que contiene la tabla de transacciones.
    private VBox crearPanelTabla() {
        VBox panel = new VBox(10); // Crea un VBox con un espaciado de 10 píxeles.
        panel.setPadding(new Insets(10)); // Establece un relleno interno.

        tablaTransacciones = new TableView<>(); // Crea una nueva instancia de TableView para objetos TransaccionDetalle.

        // Configuración de las columnas de la tabla.

        // Columna 'Cliente'.
        TableColumn<TransaccionDetalle, String> colCliente = new TableColumn<>("Cliente");
        // Define cómo obtener el valor para esta columna (el nombre del cliente).
        colCliente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCliente()));
        colCliente.setPrefWidth(150); // Ancho preferido.

        // Columna 'N° Cuenta'.
        TableColumn<TransaccionDetalle, String> colCuenta = new TableColumn<>("N° Cuenta");
        // Define cómo obtener el número de cuenta (convertido a String).
        colCuenta.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNumeroCuenta())));
        colCuenta.setPrefWidth(80); // Ancho preferido.

        // Columna 'Tipo Cuenta'.
        TableColumn<TransaccionDetalle, String> colTipoCuenta = new TableColumn<>("Tipo Cuenta");
        // Define cómo obtener el tipo de cuenta.
        colTipoCuenta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoCuenta()));
        colTipoCuenta.setPrefWidth(100); // Ancho preferido.

        // Columna 'Transacción' (tipo de transacción).
        TableColumn<TransaccionDetalle, String> colTipoTrans = new TableColumn<>("Transacción");
        // Define cómo obtener el tipo de transacción.
        colTipoTrans.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoTransaccion()));
        colTipoTrans.setPrefWidth(100); // Ancho preferido.

        // Columna 'Monto'.
        TableColumn<TransaccionDetalle, Double> colMonto = new TableColumn<>("Monto");
        // Define cómo obtener el monto de la transacción.
        colMonto.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMonto()).asObject());
        // Define una fábrica de celdas personalizada para formatear el monto y colorearlo.
        colMonto.setCellFactory(col -> new TableCell<TransaccionDetalle, Double>() {
            @Override // Sobrescribe el método updateItem para personalizar la visualización de la celda.
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty); // Llama al método de la superclase.
                if (empty || item == null) { // Si la celda está vacía o el ítem es nulo.
                    setText(null); // No muestra texto.
                    setStyle(""); // Limpia cualquier estilo previo.
                } else { // Si hay un valor.
                    setText("$" + String.format("%.2f", item)); // Formatea el monto como moneda a dos decimales.
                    // Colorear el texto de la celda según el tipo de transacción.
                    TableRow<TransaccionDetalle> row = getTableRow(); // Obtiene la fila actual.
                    if (row != null && row.getItem() != null) { // Si la fila y su ítem no son nulos.
                        TransaccionDetalle detalle = row.getItem(); // Obtiene el objeto TransaccionDetalle de la fila.
                        String tipoTransaccion = detalle.getTipoTransaccion(); // Obtiene el tipo de transacción.
                        if ("DEPOSITO".equals(tipoTransaccion)) { // Si es un depósito.
                            setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;"); // Color verde y negrita.
                        } else if ("RETIRO".equals(tipoTransaccion)) { // Si es un retiro.
                            setStyle("-fx-text-fill: #dc3545; -fx-font-weight: bold;"); // Color rojo y negrita.
                        } else { // Para cualquier otro tipo (o desconocido).
                            setStyle("-fx-text-fill: #6c757d; -fx-font-weight: bold;"); // Color gris y negrita.
                        }
                    }
                }
            }
        });
        colMonto.setPrefWidth(100); // Ancho preferido.

        // Columna 'Fecha y Hora'.
        TableColumn<TransaccionDetalle, String> colFecha = new TableColumn<>("Fecha y Hora");
        // Define cómo obtener la fecha formateada de la transacción.
        colFecha.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFecha()));
        colFecha.setPrefWidth(150); // Ancho preferido.

        // Añade todas las columnas definidas a la tabla de transacciones.
        tablaTransacciones.getColumns().addAll(colCliente, colCuenta, colTipoCuenta, colTipoTrans, colMonto, colFecha);

        Label labelTabla = new Label("Historial de Transacciones:"); // Etiqueta para el título de la tabla.
        labelTabla.setStyle("-fx-font-weight: bold;"); // Estilo de negrita.
        
        panel.getChildren().addAll(labelTabla, tablaTransacciones); // Añade la etiqueta y la tabla al panel.
        return panel; // Retorna el panel VBox configurado.
    }

    // Método privado para crear el panel que muestra las estadísticas de las transacciones.
    private VBox crearPanelEstadisticas() {
        VBox panel = new VBox(15); // Crea un VBox con espaciado de 15 píxeles.
        panel.setPadding(new Insets(10)); // Establece un relleno interno.
        panel.setPrefWidth(250); // Ancho preferido.
        panel.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 1;"); // Estilos de fondo y borde.

        Label titulo = new Label("📈 Estadísticas"); // Etiqueta para el título de las estadísticas.
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // Estilos.

        // Inicializar las etiquetas que mostrarán las estadísticas.
        lblTotalTransacciones = new Label("Total Transacciones: 0");
        lblTotalDepositos = new Label("Total Depósitos: $0.00");
        lblTotalRetiros = new Label("Total Retiros: $0.00");
        lblPromedioTransaccion = new Label("Promedio: $0.00");

        // Añade el título, un separador y las etiquetas de estadísticas al panel.
        panel.getChildren().addAll(titulo, new Separator(), 
                                 lblTotalTransacciones, lblTotalDepositos, 
                                 lblTotalRetiros, lblPromedioTransaccion);
        return panel; // Retorna el panel VBox configurado.
    }

    // Método para cargar los datos iniciales al abrir la ventana.
    private void cargarDatos() {
        if (banco != null && banco.getClientes() != null) { // Si el controlador y la lista de clientes no son nulos.
            cmbClientes.setItems(banco.getClientes()); // Pobla el ComboBox de clientes.
            aplicarFiltros(); // Aplica los filtros para cargar todas las transacciones inicialmente.
        }
    }

    // Método principal para aplicar los filtros seleccionados y actualizar la tabla.
    private void aplicarFiltros() {
        ObservableList<TransaccionDetalle> transacciones = FXCollections.observableArrayList(); // Lista mutable para los detalles de las transacciones.
        
        // Comprobación de seguridad para evitar NullPointerException.
        if (banco == null || banco.getClientes() == null) { 
            tablaTransacciones.setItems(transacciones); // Si no hay datos, muestra una tabla vacía.
            actualizarEstadisticas(transacciones); // Actualiza las estadísticas a cero.
            return; // Sale del método.
        }
        
        Cliente clienteSeleccionado = cmbClientes.getValue(); // Obtiene el cliente seleccionado en el ComboBox.
        LocalDate fechaInicioFiltro = dateInicio.getValue(); // Obtiene la fecha de inicio del filtro.
        LocalDate fechaFinFiltro = dateFin.getValue(); // Obtiene la fecha de fin del filtro.
        
        // Itera sobre todos los clientes del banco.
        for (Cliente cliente : banco.getClientes()) {
            // Si hay un cliente seleccionado en el filtro y el cliente actual no coincide, se salta al siguiente cliente.
            if (clienteSeleccionado != null && !cliente.equals(clienteSeleccionado)) {
                continue;
            }
            
            // Si el cliente no tiene cuentas, se salta al siguiente cliente.
            if (cliente.getCuentas() == null) {
                continue;
            }
            
            // Itera sobre cada cuenta del cliente.
            for (Cuenta cuenta : cliente.getCuentas()) {
                // Determina el tipo de cuenta (Caja Ahorro o Corriente).
                String tipoCuenta = cuenta instanceof CajaAhorro ? "Caja Ahorro" : "Corriente";
                
                // *** ATENCIÓN: Esta parte del código usa reflexión para acceder a un campo privado/protegido. ***
                // Es una solución temporal si la clase 'Cuenta' no tiene un método público `getTransacciones()`.
                // En un diseño ideal, 'Cuenta' debería exponer sus transacciones de forma segura con un getter.
                try {
                    java.lang.reflect.Field field = Cuenta.class.getDeclaredField("transacciones"); // Obtiene el campo "transacciones" por reflexión.
                    field.setAccessible(true); // Hace el campo accesible (ignora si es privado/protegido).
                    @SuppressWarnings("unchecked") // Suprime la advertencia de tipo no seguro para la conversión.
                    List<Transaccion> listaTransacciones = (List<Transaccion>) field.get(cuenta); // Obtiene la lista de transacciones de la cuenta.
                    
                    if (listaTransacciones != null) { // Si la lista de transacciones no es nula.
                        for (Transaccion trans : listaTransacciones) { // Itera sobre cada transacción.
                            if (trans == null || trans.getFecha() == null) { // Si la transacción o su fecha son nulas, se salta.
                                continue;
                            }
                            
                            try {
                                // Obtiene y convierte la fecha de la transacción a LocalDate para la comparación.
                                LocalDateTime fechaTransaccion = trans.getFecha();
                                LocalDate fechaTransaccionDate = fechaTransaccion.toLocalDate();
                                
                                // Filtrar por rango de fechas si se han especificado.
                                if (fechaInicioFiltro != null && fechaTransaccionDate.isBefore(fechaInicioFiltro)) {
                                    continue; // Si la transacción es anterior a la fecha de inicio, se salta.
                                }
                                
                                if (fechaFinFiltro != null && fechaTransaccionDate.isAfter(fechaFinFiltro)) {
                                    continue; // Si la transacción es posterior a la fecha de fin, se salta.
                                }
                                
                                // Formatear la fecha de la transacción para mostrarla en la tabla.
                                String fechaFormateada;
                                try {
                                    fechaFormateada = fechaTransaccion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")); // Formato específico.
                                } catch (DateTimeParseException e) {
                                    fechaFormateada = fechaTransaccion.toString(); // Si falla el formato, usa el toString por defecto.
                                }
                                
                                // Crea un nuevo objeto TransaccionDetalle con toda la información relevante.
                                TransaccionDetalle detalle = new TransaccionDetalle(
                                    cliente.getNombre() != null ? cliente.getNombre() : "Cliente sin nombre", // Nombre del cliente (maneja nulos).
                                    cuenta.getNumero(), // Número de cuenta.
                                    tipoCuenta, // Tipo de cuenta.
                                    trans.getTipo(), // Tipo de transacción (DEPÓSITO/RETIRO).
                                    trans.getMonto(), // Monto de la transacción.
                                    fechaFormateada, // Fecha formateada para la UI.
                                    fechaTransaccion // Fecha y hora completa para la lógica de filtrado.
                                );
                                transacciones.add(detalle); // Añade el detalle a la lista de transacciones a mostrar.
                                
                            } catch (Exception e) { // Captura excepciones internas durante el procesamiento de una transacción.
                                System.err.println("Error al procesar transacción: " + e.getMessage());
                                // Se puede optar por registrar esto en el log de la UI si se considera crítico.
                                // La ejecución continúa con la siguiente transacción.
                            }
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) { // Captura excepciones si no se encuentra el campo 'transacciones' o no se puede acceder.
                    System.err.println("Error al obtener transacciones de la cuenta " + cuenta.getNumero() + ": " + e.getMessage());
                } catch (Exception e) { // Captura cualquier otra excepción inesperada.
                    System.err.println("Error inesperado al procesar cuenta " + cuenta.getNumero() + ": " + e.getMessage());
                }
            }
        }
        
        tablaTransacciones.setItems(transacciones); // Actualiza los ítems de la tabla con la lista filtrada.
        actualizarEstadisticas(transacciones); // Actualiza las estadísticas basadas en las transacciones filtradas.
    }

    // Método para actualizar las etiquetas de estadísticas.
    private void actualizarEstadisticas(ObservableList<TransaccionDetalle> transacciones) {
        int totalTransacciones = transacciones.size(); // Número total de transacciones en la lista actual.
        double totalDepositos = 0.0; // Suma de todos los depósitos.
        double totalRetiros = 0.0; // Suma de todos los retiros.
        
        // Itera sobre las transacciones para calcular los totales.
        for (TransaccionDetalle detalle : transacciones) {
            String tipoTransaccion = detalle.getTipoTransaccion(); // Obtiene el tipo de transacción como String.
            if ("DEPOSITO".equals(tipoTransaccion)) { // Si es un depósito.
                totalDepositos += detalle.getMonto(); // Suma al total de depósitos.
            } else if ("RETIRO".equals(tipoTransaccion)) { // Si es un retiro.
                totalRetiros += detalle.getMonto(); // Suma al total de retiros.
            }
        }
        
        // Calcula el promedio. Evita división por cero.
        double promedio = totalTransacciones > 0 ? (totalDepositos + totalRetiros) / totalTransacciones : 0.0;
        
        // Actualiza el texto de las etiquetas con los valores calculados.
        lblTotalTransacciones.setText("Total Transacciones: " + totalTransacciones);
        lblTotalDepositos.setText("Total Depósitos: $" + String.format("%.2f", totalDepositos)); // Formatea a dos decimales.
        lblTotalRetiros.setText("Total Retiros: $" + String.format("%.2f", totalRetiros)); // Formatea a dos decimales.
        lblPromedioTransaccion.setText("Promedio: $" + String.format("%.2f", promedio)); // Formatea a dos decimales.
    }

    // Método para limpiar los filtros y recargar todos los datos.
    private void limpiarFiltros() {
        cmbClientes.setValue(null); // Deselecciona cualquier cliente en el ComboBox.
        dateInicio.setValue(null); // Borra la fecha de inicio.
        dateFin.setValue(null); // Borra la fecha de fin.
        aplicarFiltros(); // Vuelve a aplicar los filtros (sin ninguno, cargará todo).
    }
}