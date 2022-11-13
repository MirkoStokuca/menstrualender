module com.example.menstrualender {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.menstrualender to javafx.fxml;
    exports com.example.menstrualender;
    opens com.example.menstrualender.view to javafx.fxml;
    exports com.example.menstrualender.view;

}