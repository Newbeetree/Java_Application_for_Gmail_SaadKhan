/**
 * Sample Skeleton for 'mainPage.fxml' Controller Class
 */

package com.saadkhan.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;

public class mainController {

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
        assert dateReTxt != null : "fx:id=\"dateReTxt\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert fileMn != null : "fx:id=\"fileMn\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert fromTxt != null : "fx:id=\"fromTxt\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert editMn != null : "fx:id=\"editMn\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert helpMn != null : "fx:id=\"helpMn\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert subjectTxt != null : "fx:id=\"subjectTxt\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert trashBtn != null : "fx:id=\"trashBtn\" was not injected: check your FXML file 'mainPage.fxml'.";
        assert folderBtn != null : "fx:id=\"folderBtn\" was not injected: check your FXML file 'mainPage.fxml'.";

    }
}
