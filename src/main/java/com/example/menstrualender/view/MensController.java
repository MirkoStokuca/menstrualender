package com.example.menstrualender.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mvp.Helper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class MensController {
    @FXML
    private Label kalenderAusgabe;
    @FXML
    private DatePicker datePicker;

    public void gedrueckt(ActionEvent e){
        //System.out.println("ye");
        kalenderAusgabe.setText("ye");
    }

    public void getDate(ActionEvent event) {
        LocalDate myDate = datePicker.getValue();
        String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        kalenderAusgabe.setText(myFormattedDate);

    }
    @FXML
    public void switchToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToHello(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}