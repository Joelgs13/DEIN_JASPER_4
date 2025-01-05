package joel.dein.ejercicio4_jasper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación que inicia la interfaz gráfica.
 */
public class LanzarInforme extends Application {

    /**
     * Configura y muestra la ventana principal de la aplicación.
     *
     * @param stage la ventana principal (Stage) proporcionada por JavaFX.
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LanzarInforme.class.getResource("/fxml/interfaz.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Formulario medico!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Punto de entrada principal de la aplicación.
     *
     * @param args argumentos de la línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        launch();
    }
}
