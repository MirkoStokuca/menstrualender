package com.example.menstrualender.view;

import com.example.menstrualender.MensApplication;
import com.example.menstrualender.model.Cycles;
import com.example.menstrualender.model.Db;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;


public class DailyController implements Initializable {
    @FXML
    private DatePicker dailyDatePicker;
    @FXML
    private ChoiceBox<String> chooseMood;
    @FXML
    private ChoiceBox<String> chooseSlime;
    @FXML
    private MensApplication mensApp;
    @FXML
    private TextField dailyTemperature;
    @FXML
    private Button dayReturn;
    @FXML
    private TextField dailyComments;
    @FXML
    Db db = new Db();
    @FXML
    Cycles zyklus = new Cycles(db);

    private String[] slime = {"Trocken","Pampig","Durchsichtig"};
    private String[] mood = {"ängstlich","provozierend","aufgestellt","niedergeschlagen","motiviert","lustlos"};
    public void setMainApp(MensApplication mensApp) {
        this.mensApp = mensApp;
    }

    /**
     * Constructor
     */
    public DailyController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseSlime.getItems().addAll(slime);
        chooseMood.getItems().addAll(mood);
    }

    public void dailySave(ActionEvent actionEvent) {
        String slimeChoice = chooseSlime.getValue();
        String moodChoice = chooseMood.getValue();
        String dailyTemp = dailyTemperature.getText();
        LocalDate dailyDate = dailyDatePicker.getValue();
        String commentsDaily = dailyComments.getText();

        int slimeChoiceInt = Arrays.asList(slime).indexOf(slimeChoice)+1; //Trocken = 1, Pampig = 2, Durchsichtig = 3
        int moodChoiceInt = Arrays.asList(mood).indexOf(moodChoice)+1;  //ängstlich = 1, provozierend = 2, aufgestellt = 3, niedergeschlagen = 4, motiviert = 5, lustlos = 6
        double dailyTempDouble = Double.parseDouble(dailyTemp);        // returns double primitive

        DecimalFormat df = new DecimalFormat("#.00");

        /*
        zyklus.addSlime(slimeChoiceInt);
        zyklus.addMood(moodChoiceInt);
        zyklus.addTemp(df.format(dailyTempDouble));
        zyklus.addComments(commentsDaily);
        zyklus.addDate(dailyDate);
        */
        System.out.println(slimeChoiceInt);
        System.out.println(moodChoiceInt);
        System.out.println(df.format(dailyTempDouble));
        System.out.println(commentsDaily);
        System.out.println(dailyDate);

    }

    public void dailyReturn(ActionEvent actionEvent) {
        Stage dayStage = (Stage) dayReturn.getScene().getWindow();
        dayStage.close();
    }
}