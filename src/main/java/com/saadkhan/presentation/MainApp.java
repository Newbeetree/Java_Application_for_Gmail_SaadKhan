package com.saadkhan.presentation;

import com.saadkhan.controller.confController;
import com.saadkhan.controller.mainController;
import com.saadkhan.data.ConfigurationFxBean;
import com.saadkhan.manager.PropertiesManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final static Logger LOG = LoggerFactory.getLogger(MainApp.class);
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * method to display primary stage
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        Scene emailScene = createEmailScene();
        Scene conf = createConfFile(emailScene);
        conf.getStylesheets().add("/styles/emailCSS.css");
        emailScene.getStylesheets().add("/styles/emailCSS.css");
        if (checkIfPropertiesExists()) {
            this.stage.setScene(emailScene);
            this.stage.setTitle("JAG: Email Client");
        } else {
            this.stage.setScene(conf);
            this.stage.setTitle("JAG: Configure");
        }
        this.stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/frown.png")));
        this.stage.show();
    }

    /**
     * check if the properties file exists
     * @return if they exist
     */
    private boolean checkIfPropertiesExists() {
        boolean result = false;
        ConfigurationFxBean cfb = new ConfigurationFxBean();
        PropertiesManager pm = new PropertiesManager();
        try {
            if (pm.loadTextProperties(cfb, "", "JAGConfig")) {
                result = true;
            }
        } catch (IOException ex) {
            LOG.error("checking properties error", ex);
        }
        return result;
    }

    /**
     * if the properties file exists display the main emailGUI
     * @return Scene of the main scene
     * @throws IOException
     */
    private Scene createEmailScene() throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("Strings");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/mainPage.fxml"), rb);
        Parent root = (AnchorPane) loader.load();
        mainController controller = loader.getController();
        controller.setSceneStageController(stage);
        Scene scene = new Scene(root);
        return scene;
    }

    /**
     * if property file doesnt exist open config page to allow user to write it
     * @param emailScene
     * @return properties scene
     * @throws IOException
     */
    private Scene createConfFile(Scene emailScene) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("Strings");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/confPage.fxml"), rb);
        Parent root = (AnchorPane) loader.load();
        confController controller = loader.getController();
        controller.setSceneStageController(stage);
        Scene scene = new Scene(root);
        return scene;
    }
}
