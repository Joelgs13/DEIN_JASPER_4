module joel.dein.ejercicio4_jasper {
    requires javafx.controls;
    requires javafx.fxml;
    requires jasperreports;
    requires java.sql;


    opens joel.dein.ejercicio4_jasper to javafx.fxml;
    exports joel.dein.ejercicio4_jasper;
}