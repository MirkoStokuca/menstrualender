package com.example.menstrualender;

import com.example.menstrualender.model.Cycles;
import com.example.menstrualender.util.DateUtil;
import com.example.menstrualender.view.DailyController;
import com.example.menstrualender.view.LoginController;
import com.example.menstrualender.view.MensController;
import com.example.menstrualender.view.MonthlyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MensApplication extends Application {

    public final static String PATH_TO_FILE = "src/artificialData.csv";
    private Stage defaultStage;

    @Override
    public void start(Stage defaultStage) throws IOException {

        //Setup defaultStage
        this.defaultStage = defaultStage;
        this.defaultStage.setTitle("Menstrualender");
        this.defaultStage.getIcons().add(new Image(MensApplication.class.getResourceAsStream("images/mensicon.png")));
        this.defaultStage.setResizable(false);
        Group root = new Group();
        Scene scene = new Scene(root);
        defaultStage.setScene(scene);
        defaultStage.show();
        //Loads login Window
        loginWindow();

    }

    /**
     * sets up login Window Scene
     */
    public void loginWindow(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MensApplication.class.getResource("view/login.fxml"));
            AnchorPane loginView = (AnchorPane) loader.load();
            Scene loginScene = new Scene(loginView);
            defaultStage.setScene(loginScene);

            LoginController controller = loader.getController();
            controller.setMainApp(this);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * sets up Default Scene
     */
    public void showDefaultWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MensApplication.class.getResource("view/mensView.fxml"));
            AnchorPane defaultView = (AnchorPane) loader.load();Scene defaultScene = new Scene(defaultView);
            defaultStage.setScene(defaultScene);


            MensController controller = loader.getController();
            controller.setMainApp(this);

            controller.loadData();
            controller.showInfos();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDailyWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MensApplication.class.getResource("view/dayView.fxml"));
            AnchorPane defaultView = (AnchorPane) loader.load();
            Scene defaultScene = new Scene(defaultView);
            Stage dayStage = new Stage();
            dayStage.setScene(defaultScene);

            DailyController controller = loader.getController();
            controller.setMainApp(this);
            dayStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMonthlyWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MensApplication.class.getResource("view/monthView.fxml"));
            AnchorPane defaultView = (AnchorPane) loader.load();
            Scene defaultScene = new Scene(defaultView);
            Stage monthStage = new Stage();
            monthStage.setScene(defaultScene);

            MonthlyController controller = loader.getController();
            controller.setMainApp(this);
            monthStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);
    }
}