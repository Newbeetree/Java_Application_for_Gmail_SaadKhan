/**
 * Sample Skeleton for 'confPage.fxml' Controller Class
 */

package com.saadkhan.controller;

import com.saadkhan.data.ConfigurationFxBean;
import com.saadkhan.manager.PropertiesManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class confController {
    private final static Logger LOG = LoggerFactory.getLogger(confController.class);

    private final ConfigurationFxBean cfb;
    public confController(){
        cfb = new ConfigurationFxBean();
    }
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="DBPortTxt"
    private Label DBPortTxt; // Value injected by FXMLLoader

    @FXML // fx:id="stmpSIn"
    private TextField stmpSIn; // Value injected by FXMLLoader

    @FXML // fx:id="nameTxt"
    private Label nameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="DBUserTxtIn"
    private TextField DBUserTxtIn; // Value injected by FXMLLoader

    @FXML // fx:id="DBPassIn"
    private PasswordField DBPassIn; // Value injected by FXMLLoader

    @FXML // fx:id="DBNameTxt"
    private Label DBNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="passTxt"
    private Label passTxt; // Value injected by FXMLLoader

    @FXML // fx:id="imapS"
    private Label imapS; // Value injected by FXMLLoader

    @FXML // fx:id="imapPIn"
    private TextField imapPIn; // Value injected by FXMLLoader

    @FXML // fx:id="imapSIn"
    private TextField imapSIn; // Value injected by FXMLLoader

    @FXML // fx:id="DBNameIn"
    private TextField DBNameIn; // Value injected by FXMLLoader

    @FXML // fx:id="submitBtn"
    private Button submitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DBPortIn"
    private TextField DBPortIn; // Value injected by FXMLLoader

    @FXML // fx:id="imapP"
    private Label imapP; // Value injected by FXMLLoader

    @FXML // fx:id="jagTxt"
    private Label jagTxt; // Value injected by FXMLLoader

    @FXML // fx:id="DBUserTxt"
    private Label DBUserTxt; // Value injected by FXMLLoader

    @FXML // fx:id="emailTxt"
    private Label emailTxt; // Value injected by FXMLLoader

    @FXML // fx:id="nameIn"
    private TextField nameIn; // Value injected by FXMLLoader

    @FXML // fx:id="DBUrlTxt"
    private Label DBUrlTxt; // Value injected by FXMLLoader

    @FXML // fx:id="smtpS"
    private Label smtpS; // Value injected by FXMLLoader

    @FXML // fx:id="emailIn"
    private TextField emailIn; // Value injected by FXMLLoader

    @FXML // fx:id="smtpPIn"
    private TextField smtpPIn; // Value injected by FXMLLoader

    @FXML // fx:id="DBUrlIn"
    private TextField DBUrlIn; // Value injected by FXMLLoader

    @FXML // fx:id="DBPassTxt"
    private Label DBPassTxt; // Value injected by FXMLLoader

    @FXML // fx:id="passIn"
    private PasswordField passIn; // Value injected by FXMLLoader

    @FXML // fx:id="smtpP"
    private Label smtpP; // Value injected by FXMLLoader

    @FXML
    void submitCredentials(ActionEvent event) {
        PropertiesManager pm = new PropertiesManager();
        try{
            pm.writeTextProperties("", "JAGConfig", cfb);
            LOG.info("create file");
        }catch (IOException ex){
            LOG.error("error saving", ex);
        }

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        Bindings.bindBidirectional(nameIn.textProperty(), cfb.userNameProperty());
        Bindings.bindBidirectional(emailIn.textProperty(), cfb.userEmailAddressProperty());
        Bindings.bindBidirectional(passIn.textProperty(), cfb.userPasswordProperty());
        Bindings.bindBidirectional(imapSIn.textProperty(), cfb.IMAPServerProperty());
        Bindings.bindBidirectional(stmpSIn.textProperty(), cfb.SMTPServerProperty());
        Bindings.bindBidirectional(imapPIn.textProperty(), cfb.IMAPPortProperty());
        Bindings.bindBidirectional(smtpPIn.textProperty(), cfb.SMTPPortProperty());
        Bindings.bindBidirectional(DBUrlIn.textProperty(), cfb.DBUrlProperty());
        Bindings.bindBidirectional(DBNameIn.textProperty(), cfb.DBNameProperty());
        Bindings.bindBidirectional(DBUserTxtIn.textProperty(), cfb.DBUserProperty());
        Bindings.bindBidirectional(DBPortIn.textProperty(), cfb.DBPortProperty());
        Bindings.bindBidirectional(DBPassIn.textProperty(), cfb.DBPasswordProperty());
    }
}
