module com.example.menstrualender {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.menstrualender to javafx.fxml;
    exports com.example.menstrualender;
}