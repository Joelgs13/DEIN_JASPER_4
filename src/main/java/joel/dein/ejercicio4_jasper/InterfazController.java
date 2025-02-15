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
     * Acción del botón "Generar informe". Valida los campos del formulario y, si son correctos, genera el informe Jasper.
     * Si hay errores, muestra un mensaje con los problemas detectados.
     */
    @FXML
    void generarInforme(ActionEvent event) {
        StringBuilder errores = new StringBuilder();

        if (!esNumeroEntero(txtNumPaciente.getText())) {
            errores.append("El 'Número de paciente' debe ser un número entero.\n");
        }

        if (!esNumeroEntero(txtCodMedico.getText())) {
            errores.append("El 'Código del médico' debe ser un número entero.\n");
        }

        if (txtNomPaciente.getText().trim().isEmpty()) {
            errores.append("El 'Nombre del paciente' no puede estar vacío.\n");
        }

        if (txtNomMedico.getText().trim().isEmpty()) {
            errores.append("El 'Nombre del médico' no puede estar vacío.\n");
        }

        if (txtEspMedico.getText().trim().isEmpty()) {
            errores.append("La 'Especialidad del médico' no puede estar vacía.\n");
        }

        if (txaTratamiento.getText().trim().isEmpty()) {
            errores.append("El 'Tratamiento' no puede estar vacío.\n");
        }

        if (errores.length() > 0) {
            mostrarError("Errores en el formulario", errores.toString());
        } else {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("NUM_PACIENTE", Integer.parseInt(txtNumPaciente.getText()));
            parameters.put("NOM_PACIENTE", txtNomPaciente.getText());
            parameters.put("DIR_PACIENTE", txtDirPaciente.getText());
            parameters.put("COD_MEDICO", Integer.parseInt(txtCodMedico.getText()));
            parameters.put("NOM_MEDICO", txtNomMedico.getText());
            parameters.put("ESP_MEDICO", txtEspMedico.getText());
            parameters.put("TRATAMIENTO", txaTratamiento.getText());
            parameters.put("IMAGE_PATH", getClass().getResource("/img/").toString());

            generarReporte("/JasperReport/formularioMedico.jasper", parameters);
        }
    }

    /**
     * Genera y muestra un informe Jasper usando el archivo y los parámetros proporcionados.
     *
     * @param reportePath ruta del archivo Jasper.
     * @param parameters  mapa de parámetros del informe.
     */
    private void generarReporte(String reportePath, Map<String, Object> parameters) {
        try {
            InputStream reportStream = getClass().getResourceAsStream(reportePath);

            if (reportStream == null) {
                System.out.println("El archivo no está allí: " + reportePath);
                return;
            }

            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
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
     * @param texto texto a validar.
     * @return true si es un número entero, false en caso contrario.
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
     * Muestra un cuadro de diálogo de error con el título y mensaje especificados.
     *
     * @param titulo  título del cuadro de diálogo.
     * @param mensaje mensaje que describe el error.
     */
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Acción del botón "Limpiar". Borra todos los campos del formulario.
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
     * Acción del botón "Salir". Cierra la aplicación.
     */
    @FXML
    void salir(ActionEvent event) {
        System.exit(0);
    }
}
