package com.example.menstrualender;

import com.example.menstrualender.view.MensController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mvp.Cycle;

import java.io.IOException;

public class MensApplication extends Application {

    private Stage defaultStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage defaultStage) throws IOException {

        this.defaultStage = defaultStage;
        this.defaultStage.setTitle("Menstrualender");
        this.defaultStage.getIcons().add(new Image(MensApplication.class.getResourceAsStream("images/icon.png")));

        initRootLayout();
        showDefaultWindow();
    }

    public void showDefaultWindow() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MensApplication.class.getResource("view/hello-view.fxml"));
            AnchorPane defaultView = (AnchorPane) loader.load();

            rootLayout.setCenter(defaultView);

            MensController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MensApplication.class.getResource("view/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            defaultStage.setScene(scene);
            defaultStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);

    }
}