package joel.dein.ejercicio4_jasper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class InterfazController {

    @FXML
    private Button btGenerar;

    @FXML
    private Button btLimpiar;

    @FXML
    private Button btSalir;

    @FXML
    private TextArea txaTratamiento;

    @FXML
    private TextField txtCodMedico;

    @FXML
    private TextField txtDirPaciente;

    @FXML
    private TextField txtEspMedico;

    @FXML
    private TextField txtNomMedico;

    @FXML
    private TextField txtNomPaciente;

    @FXML
    private TextField txtNumPaciente;

    /**
     * Se ejecuta al hacer clic en el botón "Generar informe".
     * Realiza la validación de los campos y muestra un alert con los errores.
     * Si es válido, se genera el informe.
     */
    @FXML
    void generarInforme(ActionEvent event) {
        StringBuilder errores = new StringBuilder();

        // Validación del campo "Número de paciente"
        if (!esNumeroEntero(txtNumPaciente.getText())) {
            errores.append("El 'Número de paciente' debe ser un número entero.\n");
        }

        // Validación del campo "Código del médico"
        if (!esNumeroEntero(txtCodMedico.getText())) {
            errores.append("El 'Código del médico' debe ser un número entero.\n");
        }

        // Validación del campo "Nombre del paciente"
        if (txtNomPaciente.getText().trim().isEmpty()) {
            errores.append("El 'Nombre del paciente' no puede estar vacío.\n");
        }

        // Validación del campo "Nombre del médico"
        if (txtNomMedico.getText().trim().isEmpty()) {
            errores.append("El 'Nombre del médico' no puede estar vacío.\n");
        }

        // Validación del campo "Especialidad del médico"
        if (txtEspMedico.getText().trim().isEmpty()) {
            errores.append("La 'Especialidad del médico' no puede estar vacía.\n");
        }

        // Validación del campo "Tratamiento"
        if (txaTratamiento.getText().trim().isEmpty()) {
            errores.append("El 'Tratamiento' no puede estar vacío.\n");
        }

        // Si hay errores, mostramos el alert con todos los errores
        if (errores.length() > 0) {
            mostrarError("Errores en el formulario", errores.toString());
        } else {
            // Obtener los valores de los campos del formulario
            String numPaciente = txtNumPaciente.getText();
            String nomPaciente = txtNomPaciente.getText();
            String dirPaciente = txtDirPaciente.getText();
            String codMedico = txtCodMedico.getText();
            String nomMedico = txtNomMedico.getText();
            String espMedico = txtEspMedico.getText();
            String tratamiento = txaTratamiento.getText();

            // Crear un mapa de parámetros para pasar al informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("NUM_PACIENTE", Integer.parseInt(numPaciente));
            parameters.put("NOM_PACIENTE", nomPaciente);
            parameters.put("DIR_PACIENTE", dirPaciente);
            parameters.put("COD_MEDICO", Integer.parseInt(codMedico));
            parameters.put("NOM_MEDICO", nomMedico);
            parameters.put("ESP_MEDICO", espMedico);
            parameters.put("TRATAMIENTO", tratamiento);
            parameters.put("IMAGE_PATH", getClass().getResource("/img/").toString());

            // Generar el informe con los parámetros
            generarReporte("/JasperReport/formularioMedico.jasper", parameters);
        }
    }

    private void generarReporte(String reportePath, Map<String, Object> parameters) {
        try {
            // Conectar a la base de datos (en este caso, no estamos usando base de datos, así que utilizamos JREmptyDataSource)
            JREmptyDataSource dataSource = new JREmptyDataSource();

            // Cargar el archivo JasperReport
            InputStream reportStream = getClass().getResourceAsStream(reportePath);

            if (reportStream == null) {
                System.out.println("El archivo no está allí: " + reportePath);
                return;
            }

            // Cargar el reporte
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

            // Llenar el reporte con los datos (parámetros y dataSource vacío)
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters,dataSource);

            // Mostrar el reporte
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (JRException e) {
            e.printStackTrace();
            mostrarError("Error al generar el informe", "No se pudo generar el informe.");
        }
    }

    /**
     * Valida si el texto ingresado es un número entero.
     *
     * @param texto El texto a validar.
     * @return true si el texto es un número entero, false si no lo es.
     */
    private boolean esNumeroEntero(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de error.
     *
     * @param titulo El título del cuadro de diálogo.
     * @param mensaje El mensaje a mostrar en el cuadro de diálogo.
     */
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null); // No queremos un encabezado
        alert.setContentText(mensaje); // El mensaje que queremos mostrar
        alert.showAndWait(); // Mostrar el mensaje y esperar a que el usuario lo cierre
    }

    /**
     * Se ejecuta al hacer clic en el botón "Limpiar".
     * Limpia todos los campos del formulario.
     */
    @FXML
    void limpiarFormulario(ActionEvent event) {
        txtNumPaciente.clear();
        txtNomPaciente.clear();
        txtDirPaciente.clear();
        txtCodMedico.clear();
        txtNomMedico.clear();
        txtEspMedico.clear();
        txaTratamiento.clear();
    }

    /**
     * Se ejecuta al hacer clic en el botón "Salir".
     * Cierra la aplicación.
     */
    @FXML
    void salir(ActionEvent event) {
        // Cerrar la aplicación
        System.exit(0);
    }

}
