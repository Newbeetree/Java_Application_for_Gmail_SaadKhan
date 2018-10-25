/**
 * Sample Skeleton for 'mainPage.fxml' Controller Class
 */

package com.saadkhan.controller;

import com.saadkhan.buisness.EmailSender;
import com.saadkhan.data.EmailBean;
import com.saadkhan.data.EmailFxBean;
import com.saadkhan.persistence.EmailDOA;
import com.saadkhan.persistence.EmailDOAImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
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
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jodd.mail.EmailAddress;

public class mainController {

    private final static Logger LOG = LoggerFactory.getLogger(mainController.class);
    @FXML
    public ListView folderHolder;
    @FXML
    public TableView emailHolder;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="dateReTxt"
    private TableColumn<EmailFxBean, LocalDateTime> dateReTxt; // Value injected by FXMLLoader
    @FXML // fx:id="fileMn"
    private Menu fileMn; // Value injected by FXMLLoader
    @FXML // fx:id="fromTxt"
    private TableColumn<EmailFxBean, EmailAddress> fromTxt; // Value injected by FXMLLoader
    @FXML // fx:id="editMn"
    private Menu editMn; // Value injected by FXMLLoader
    @FXML // fx:id="helpMn"
    private Menu helpMn; // Value injected by FXMLLoader
    @FXML // fx:id="subjectTxt"
    private TableColumn<EmailFxBean, String> subjectTxt; // Value injected by FXMLLoader
    @FXML // fx:id="trashBtn"
    private Button trashBtn; // Value injected by FXMLLoader
    @FXML // fx:id="folderBtn"
    private Button folderBtn; // Value injected by FXMLLoader
    @FXML
    private WebView webViewer;

    private Stage primaryStage;
    private ArrayList<String> folderList;
    private Locale locale;
    private EmailDOA doa;
    private ArrayList<EmailBean> emailList;

    public mainController() {
        this.doa = new EmailDOAImpl();
        this.locale = new Locale("en","US");
    }

    @FXML
    public void addFolder(ActionEvent event) {
        TextInputDialog folderDiag = new TextInputDialog(this.resources.getString("folderName"));//"Folder Name");
        folderDiag.setTitle(this.resources.getString("addFolder"));
        folderDiag.setHeaderText(this.resources.getString("enterFolderName"));
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
            b.setId(fName);
            b.setOnMouseClicked(e -> {
                try {
                    String folderName = e.getPickResult().getIntersectedNode().getId();
                    int folderId = doa.findFolder(folderName);
                    ObservableList<EmailFxBean> emailFxList = doa.findAllEmailBeansByFolderFx(folderId);
                    emailHolder.setItems(emailFxList);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
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
        fromTxt.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
        dateReTxt.setCellValueFactory(cellData -> cellData.getValue().recivedProperty());
        subjectTxt.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
        emailHolder
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) -> showEmailDetails(newValue));
        try {
            addAllFolders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showEmailDetails(Object newValue) {
        //webViewer.getEngine().loadContent( newValue);
    }

    public void showCompose(ActionEvent actionEvent) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Strings");
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/composePage.fxml"), rb);
            Parent root = (AnchorPane) loader.load();
            composeController controller = loader.getController();
            controller.setSceneStageController(primaryStage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/emailCSS.css");
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("JAG: Email Create");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSceneStageController(Stage stage) {
        this.primaryStage = stage;
    }


    private void findEmails() throws SQLException {
        ArrayList<EmailBean> emailList = doa.findAllEmailBeans();
        drawEmails(emailList);
    }

    private void drawEmails(ArrayList<EmailBean> emailList) {
        folderHolder.getItems().clear();
        for (EmailBean eb : emailList) {
            //onDragMethod drag over the folders
            //onDoubleClickMethod doubl click to display the emails
        }
    }


    private void addAllFolders() throws SQLException {
        folderList = doa.findAllFolders();
        folderList.remove(folderList.size() - 1);
        drawFolders();
    }

    private void recreateWindow() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Strings", locale);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/mainPage.fxml"), rb);
            Parent root = (AnchorPane) loader.load();
            mainController controller = loader.getController();
            controller.setSceneStageController(primaryStage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/emailCSS.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }
    public void changeFrench(ActionEvent actionEvent) {
        this.locale = new Locale("en", "US");
        recreateWindow();
    }

    public void changeEnglish(ActionEvent actionEvent) {
        this.locale = new Locale("fr", "CA");
        recreateWindow();
    }

    public void changeSettings(ActionEvent actionEvent) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Strings", locale);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/confPage.fxml"), rb);
            Parent root = (AnchorPane) loader.load();
            confController controller = loader.getController();
            controller.setSceneStageController(primaryStage);
            //controller.setData();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/emailCSS.css");
            primaryStage.setTitle("JAG: Configure");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }
}
