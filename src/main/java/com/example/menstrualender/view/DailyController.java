package com.example.menstrualender.view;

import com.example.menstrualender.MensApplication;
import com.example.menstrualender.model.Cycles;
import com.example.menstrualender.model.Db;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;


public class DailyController implements Initializable {

    //initialize FXML
    @FXML
    private DatePicker dailyDatePicker;
    @FXML
    private ChoiceBox<String> chooseMood;
    @FXML
    private ChoiceBox<String> chooseSlime;
    @FXML
    private ChoiceBox<String> chooseBlood;
    @FXML
    private MensApplication mensApp;
    @FXML
    private TextField dailyTemperature;
    @FXML
    private Button dayReturn, saveButton;
    @FXML
    private TextField dailyComments;
    @FXML ToggleButton eisprung;

    //initialize String Array for drop Down
    private String[] outflow = {"Trocken","Pampig","Durchsichtig"};
    private String[] mood = {"ängstlich","gereizt","aufgestellt","niedergeschlagen","motiviert","lustlos","hoffnungsvoll"};
    private String [] blood = {"+","++","+++"};
    public void setMainApp(MensApplication mensApp) {
        this.mensApp = mensApp;
    }

    /**
     * Constructor
     */
    public DailyController() {
    }

    /**
     * initializes DailyController and loads the String arrays into the Dropdown menus
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseSlime.getItems().addAll(outflow);
        chooseMood.getItems().addAll(mood);
        chooseBlood.getItems().addAll(blood);
    }

    /**
     * reads the input data, transforms it and sends them to the Data Base
     */
    public void dailySave() {
        //read Input Data by the User
        System.out.println("Funktion: Speichern");
        String outflowChoice = chooseSlime.getValue();
        String moodChoice = chooseMood.getValue();
        String dailyTemp = dailyTemperature.getText();
        String commentsDaily = dailyComments.getText();
        String bloodChoice = chooseBlood.getValue();

        //interpret Dropdown menu input and transforms them into index Numbers (easier to handle)
        int outflowChoiceInt = Arrays.asList(outflow).indexOf(outflowChoice) + 1; //Trocken = 1, Pampig = 2, Durchsichtig = 3
        int moodChoiceInt = Arrays.asList(mood).indexOf(moodChoice) + 1;  //ängstlich = 1, gereizt = 2, aufgestellt = 3, niedergeschlagen = 4, motiviert = 5, lustlos = 6, hoffnungsvoll =7
        int bloodChoiceInt  = Arrays.asList(blood).indexOf(bloodChoice)+1;

        // Todo: AddBleeding
        // Todo: AddOvulation (Eisprung) Datum

        //saves Data into DB
        if(outflowChoiceInt != 0) {
            this.mensApp.zyklus.addOutflow(outflowChoiceInt);
        }
        if(moodChoiceInt != 0) {
            this.mensApp.zyklus.addMood(moodChoiceInt);
        }
        if (!dailyTemp.equals("")) {
            double dailyTempDouble = Double.parseDouble(dailyTemp);
            this.mensApp.zyklus.addTemp(String.valueOf(dailyTempDouble));
        }
        if(!commentsDaily.equals("")) {
            this.mensApp.zyklus.addComments(commentsDaily);
        }
       //ToDo @Julia @Mirko
        if (bloodChoiceInt != 0){
            mensApp.zyklus.addBleeding(bloodChoiceInt);
        }
        if (eisprung.isSelected()){
            mensApp.zyklus.addOvulation(dailyDatePicker.getValue());
        }

/*
        System.out.println(outflowChoiceInt);
        System.out.println(moodChoiceInt);
        System.out.println("Ausgabe Temperatur, unverändert: " + dailyTemp);
        System.out.println(commentsDaily);
        System.out.println(dailyDate);

 */
    }

    /**
     * Closes Daily Stage
     */
    public void dailyReturn() {
        Stage dayStage = (Stage) dayReturn.getScene().getWindow();
        dayStage.close();
    }
}