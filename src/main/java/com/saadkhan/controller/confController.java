/**
 * Sample Skeleton for 'confPage.fxml' Controller Class
 */

package com.saadkhan.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class confController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="DBNameIn"
    private TextField DBNameIn; // Value injected by FXMLLoader

    @FXML // fx:id="IMAPPortIn"
    private TextField IMAPPortIn; // Value injected by FXMLLoader

    @FXML // fx:id="submitBtn"
    private Button submitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DBPortIn"
    private TextField DBPortIn; // Value injected by FXMLLoader

    @FXML // fx:id="DBURLIn"
    private TextField DBURLIn; // Value injected by FXMLLoader

    @FXML // fx:id="nameIn"
    private TextField nameIn; // Value injected by FXMLLoader

    @FXML // fx:id="DBPassIn"
    private TextField DBPassIn; // Value injected by FXMLLoader

    @FXML // fx:id="SMTPServerIN"
    private TextField SMTPServerIN; // Value injected by FXMLLoader

    @FXML // fx:id="SMTPPortIn"
    private TextField SMTPPortIn; // Value injected by FXMLLoader

    @FXML // fx:id="DBUserIn"
    private TextField DBUserIn; // Value injected by FXMLLoader

    @FXML // fx:id="PasswordIn"
    private TextField PasswordIn; // Value injected by FXMLLoader

    @FXML // fx:id="emailIn"
    private TextField emailIn; // Value injected by FXMLLoader

    @FXML // fx:id="IMapServerIn"
    private TextField IMapServerIn; // Value injected by FXMLLoader

    @FXML
    public void submitCredentials(ActionEvent event) {

    }

    @FXML
    public void initialize() {

    }
}
