module com.example.simulacion2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.graphics;
    requires commons.math3;

    opens com.example.simulacion2 to javafx.fxml;
    exports com.example.simulacion2;
}