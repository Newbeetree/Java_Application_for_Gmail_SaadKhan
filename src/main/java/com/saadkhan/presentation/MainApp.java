package com.saadkhan.presentation;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    EmailForm ef = new EmailForm();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ef.checkProperties();
        ef.displayEmailForm();
    }
}
