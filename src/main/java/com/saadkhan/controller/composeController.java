/**
 * Sample Skeleton for 'composePage.fxml' Controller Class
 */

package com.saadkhan.controller;

import com.saadkhan.data.ConfigurationFxBean;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

public class composeController {

    private final ConfigurationFxBean cfb;

    public composeController(){
        this.cfb = new ConfigurationFxBean();
    }
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="replyTxt"
    private MenuItem replyTxt; // Value injected by FXMLLoader

    @FXML // fx:id="toTxt"
    private Label toTxt; // Value injected by FXMLLoader

    @FXML // fx:id="editMn"
    private Menu editMn; // Value injected by FXMLLoader

    @FXML // fx:id="fwdAllTxt"
    private MenuItem fwdAllTxt; // Value injected by FXMLLoader

    @FXML // fx:id="addBtn"
    private Button addBtn; // Value injected by FXMLLoader

    @FXML // fx:id="ccTxt"
    private Label ccTxt; // Value injected by FXMLLoader

    @FXML // fx:id="bccTxt"
    private Label bccTxt; // Value injected by FXMLLoader

    @FXML // fx:id="fwdTxt"
    private MenuItem fwdTxt; // Value injected by FXMLLoader

    @FXML // fx:id="fileMn"
    private Menu fileMn; // Value injected by FXMLLoader

    @FXML // fx:id="sendBtn"
    private Button sendBtn; // Value injected by FXMLLoader

    @FXML // fx:id="helpMn"
    private Menu helpMn; // Value injected by FXMLLoader

    @FXML // fx:id="subjectTx"
    private Label subjectTx; // Value injected by FXMLLoader

    @FXML // fx:id="sOptionBtn"
    private SplitMenuButton sOptionBtn; // Value injected by FXMLLoader

    @FXML
    public void sendEmail(ActionEvent event) {

    }

    @FXML
    public void addFolder(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        addAllFolders();
        addAllEmails();
    }

    private void addAllEmails() {

    }

    private void addAllFolders() {

    }

    public void goBack(ActionEvent actionEvent) {

    }
}
