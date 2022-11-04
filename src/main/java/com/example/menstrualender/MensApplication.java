package com.example.menstrualender;

import com.example.menstrualender.model.Cycles;
import com.example.menstrualender.view.MensController;
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

        //Setup Stage
        this.defaultStage = defaultStage;
        this.defaultStage.setTitle("Menstrualender");
        this.defaultStage.getIcons().add(new Image(MensApplication.class.getResourceAsStream("images/icon.png")));
        this.defaultStage.setResizable(true);
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

            MensController controller = loader.getController();
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


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);

    }
}