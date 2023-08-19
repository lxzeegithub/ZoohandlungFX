package com.lukas.zoohandlungfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ZoohandlungApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ZoohandlungApplication.class.getResource("zoohandlung.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setResizable(false);
        stage.setTitle("ZoohandlungFX Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}