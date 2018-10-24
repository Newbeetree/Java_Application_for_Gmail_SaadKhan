/**
 * Sample Skeleton for 'mainPage.fxml' Controller Class
 */

package com.saadkhan.controller;

import com.saadkhan.data.EmailBean;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class mainController {

    @FXML
    public ListView folderHolder;
    @FXML
    public TableView emailHolder;
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
    private Stage primaryStage;
    private ArrayList<String> folderList;
    public mainController() {
    }

    @FXML
    public void addFolder(ActionEvent event) {
        TextInputDialog folderDiag = new TextInputDialog("Folder Name");
        folderDiag.setTitle("Add Folder");
        folderDiag.setHeaderText("Enter Folder Name");
        Optional<String> result = folderDiag.showAndWait();
        result.ifPresent(name -> {
            folderList.add(name);
        });
        drawFolders();
    }

    private void drawFolders() {
        folderHolder.getItems().clear();
        for (String fName : folderList) {
            Label b = new Label(fName);
            b.setMaxWidth(folderHolder.getPrefWidth());
            folderHolder.getItems().add(b);
        }
    }

    @FXML
    public void moveTrash(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            String id = mouseEvent.getPickResult().getIntersectedNode().getId();
            //find email bean with this id then set the bean folder id to trash
        }
    }

    @FXML
    public void initialize() {
        addAllEmails();
        addAllFolders();
    }

    public void showCompose(ActionEvent actionEvent) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Strings");
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/composePage.fxml"), rb);
            Parent root = (AnchorPane) loader.load();
            composeController controller = loader.getController();
            controller.setSceneStageController(primaryStage);
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

    public void setSceneStageController(Stage stage) {
        this.primaryStage = stage;
    }


    private void addAllEmails() {
        ArrayList<EmailBean> emailList = getEmailsDB();
        drawEmails(emailList);
    }

    private void drawEmails(ArrayList<EmailBean> emailList) {
        folderHolder.getItems().clear();
        for (EmailBean eb : emailList) {
            //onDragMethod drag over the folders
            //onDoubleClickMethod doubl click to display the emails
        }
    }

    private ArrayList<EmailBean> getEmailsDB() {
        ArrayList<EmailBean> list = new ArrayList<>();
        list.add(new EmailBean());
        return list;
    }

    private void addAllFolders() {
        folderList = getFolderDB();
        drawFolders();
    }

    private ArrayList<String> getFolderDB() {
        ArrayList<String> s = new ArrayList<>();
        s.add("test1");
        s.add("test2");
        s.add("test3");
        return s;
    }

    public void changeFrench(ActionEvent actionEvent) {

    }

    public void changeEnglish(ActionEvent actionEvent) {
    }


}
