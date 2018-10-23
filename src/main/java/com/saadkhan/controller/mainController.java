/**
 * Sample Skeleton for 'mainPage.fxml' Controller Class
 */

package com.saadkhan.controller;

import com.saadkhan.data.ConfigurationFxBean;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class mainController {

    private String language;
    private ConfigurationFxBean cfb;

    public mainController(){}
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="dateReTxt"
    private TableColumn<?, ?> dateReTxt; // Value injected by FXMLLoader

    @FXML // fx:id="fileMn"
    private Menu fileMn; // Value injected by FXMLLoader

    @FXML // fx:id="fromTxt"
    private TableColumn<?, ?> fromTxt; // Value injected by FXMLLoader

    @FXML // fx:id="editMn"
    private Menu editMn; // Value injected by FXMLLoader

    @FXML // fx:id="helpMn"
    private Menu helpMn; // Value injected by FXMLLoader

    @FXML // fx:id="subjectTxt"
    private TableColumn<?, ?> subjectTxt; // Value injected by FXMLLoader

    @FXML // fx:id="trashBtn"
    private Button trashBtn; // Value injected by FXMLLoader

    @FXML // fx:id="folderBtn"
    private Button folderBtn; // Value injected by FXMLLoader

    @FXML
    public void addFolder(ActionEvent event) {

    }

    @FXML
    public void moveTrash(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
    }

    public mainController(String lang) {
        this.cfb = new ConfigurationFxBean();
        this.language = lang;
    }

    public void showCompose(ActionEvent actionEvent) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Strings");
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/composePage.fxml"), rb);
            Parent root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("JAG: Email Create");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
