package com.saadkhan.presentation;

import com.saadkhan.controller.composeController;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final static Logger LOG = LoggerFactory.getLogger(MainApp.class);
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        Scene emailScene = createEmailScene();
        Scene conf = createConfFile(emailScene);
        conf.getStylesheets().add("/styles/confCSS.css");
        emailScene.getStylesheets().add("/styles/emailCSS.css");
        if (checkIfPropertiesExists()) {
            this.stage.setScene(emailScene);
            this.stage.setTitle("JAG: Email Client");
        } else {
            this.stage.setScene(conf);
            this.stage.setTitle("JAG: Configure");
        }
        this.stage.show();
    }

    private boolean checkIfPropertiesExists() {
        boolean result = false;
        ConfigurationFxBean cfb = new ConfigurationFxBean();
        PropertiesManager pm = new PropertiesManager();

        try{
            if(pm.loadTextProperties(cfb,"","JAGConfig")){
                result = true;
            }
        }catch (IOException ex){
            LOG.error("checking properties error", ex);
        }
        return result;
    }

    private Scene createEmailScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/mainPage.fxml"));
        //loader.setResources(ResourceBundle.getBundle("MessagesBundle"));
        Parent root = (AnchorPane) loader.load();

        // rpc = loader.getController();
        //rpc.displayPropertiesInTextArea();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        return scene;
    }

    private Scene createConfFile(Scene emailScene) throws IOException {
        ResourceBundle rb = getMyBundle("En", "conf");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/confPage.fxml"), rb);
        Parent root = (AnchorPane) loader.load();
        //composeController controller = loader.getController();
        //controller.setSceneStageController(scene2, stage, rpc);

        Scene scene = new Scene(root);
        return scene;

    }

    private ResourceBundle getMyBundle(String lang, String spec) {
        if (lang.equals("En")) {
            if (spec.equals("conf")) {
                return ResourceBundle.getBundle("En-confBundle");
            } else if (spec.equals("comp")) {
                return ResourceBundle.getBundle("En-compBundle");
            }
        } else if (lang.equals("Fr")) {
            if (spec.equals("conf")) {
                return ResourceBundle.getBundle("Fr-confBundle");
            } else if (spec.equals("comp")) {
                return ResourceBundle.getBundle("Fr-compBundle");
            }
        }
        return null;
    }
}
