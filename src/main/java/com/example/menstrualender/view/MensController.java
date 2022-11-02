package com.example.menstrualender.view;

import com.example.menstrualender.model.Cycles;
import javafx.event.ActionEvent;
import com.example.menstrualender.MensApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class MensController {
    @FXML
    private Label kalenderAusgabe;
    @FXML
    private DatePicker datePicker;

    @FXML
    private MensApplication mensApp;
    Cycles zyklus = new Cycles();

    private Stage stage;
    private Scene scene;
    private Parent root;


    public MensController() {
    }

    public void gedrueckt(ActionEvent e) {
        //System.out.println("ye");
        kalenderAusgabe.setText("");
    }

    public void loadData(ActionEvent event) {
        zyklus.readData();
    }

    public void getDate(ActionEvent event) {
        LocalDate myDate = datePicker.getValue();
        myDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        zyklus.addDate(myDate);
    }

    public void setMainApp(MensApplication mensApp) {
        this.mensApp = mensApp;
    }

    public void saveData(){
        zyklus.saveData();
    }




    @FXML
    public void switchToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        /*
        @FXML
        public void switchToHello (ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("view/hello-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
         */

}