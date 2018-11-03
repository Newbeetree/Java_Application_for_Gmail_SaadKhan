/**
 * Sample Skeleton for 'mainPage.fxml' Controller Class
 */

package com.saadkhan.controller;

import com.mysql.cj.xdevapi.SqlDataResult;
import com.saadkhan.data.EmailBean;
import com.saadkhan.data.EmailFxBean;
import com.saadkhan.persistence.EmailDOA;
import com.saadkhan.persistence.EmailDOAImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.lang.Object.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jodd.mail.EmailAddress;

import static java.nio.file.Paths.get;

public class mainController {

    private final static Logger LOG = LoggerFactory.getLogger(mainController.class);
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="replyTxt"
    private MenuItem replyTxt; // Value injected by FXMLLoader

    @FXML // fx:id="toTxt"
    private Label toTxt; // Value injected by FXMLLoader

    @FXML // fx:id="reAllTxt"
    private MenuItem reAllTxt; // Value injected by FXMLLoader

    @FXML // fx:id="attachyHolder"
    private ListView attachyHolder; // Value injected by FXMLLoader

    @FXML // fx:id="subjectTxt"
    private Label subTxt; // Value injected by FXMLLoader

    @FXML // fx:id="folderHolder"
    private ListView folderHolder; // Value injected by FXMLLoader

    @FXML // fx:id="composeBtn"
    private Button composeBtn; // Value injected by FXMLLoader

    @FXML // fx:id="ccTxt"
    private Label ccTxt; // Value injected by FXMLLoader

    @FXML // fx:id="bccTxt"
    private Label bccTxt; // Value injected by FXMLLoader

    @FXML // fx:id="fwdTxt"
    private MenuItem fwdTxt; // Value injected by FXMLLoader

    @FXML // fx:id="helpMn"
    private Menu helpMn; // Value injected by FXMLLoader

    @FXML // fx:id="langMn"
    private Menu langMn; // Value injected by FXMLLoader

    @FXML // fx:id="sOptionBtn"
    private SplitMenuButton sOptionBtn; // Value injected by FXMLLoader

    @FXML // fx:id="trashBtn"
    private Button trashBtn; // Value injected by FXMLLoader

    @FXML // fx:id="folderBtn"
    private Button folderBtn; // Value injected by FXMLLoader

    @FXML // fx:id="dateReTxt"
    private TableColumn<EmailFxBean, LocalDateTime> dateReTxt; // Value injected by FXMLLoader

    @FXML // fx:id="subjectTxt"
    private TableColumn<EmailFxBean, String> subjectTxt; // Value injected by FXMLLoader

    @FXML // fx:id="fromTxt"
    private TableColumn<EmailFxBean, EmailAddress> fromTxt; // Value injected by FXMLLoader

    @FXML // fx:id="emailHolder"
    private TableView<EmailFxBean> emailHolder; // Value injected by FXMLLoader

    @FXML // fx:id="attachTxt"
    private Label attachTxt; // Value injected by FXMLLoader


    private Stage primaryStage;
    private ObservableList<String> folderList;
    private Locale locale;
    private EmailDOA doa;
    private ArrayList<EmailBean> emailList;

    public mainController() {
        this.doa = new EmailDOAImpl();
        this.locale = new Locale("en", "US");
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

    @FXML
    public void moveTrash(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            String id = mouseEvent.getPickResult().getIntersectedNode().getId();
            //find email bean with this id then set the bean folder id to trash
        }
    }

    @FXML
    public void initialize() {
        trashBtn.setDisable(true);
        sOptionBtn.setDisable(true);
        fromTxt.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
        dateReTxt.setCellValueFactory(cellData -> cellData.getValue().recivedProperty());
        subjectTxt.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
        emailHolder
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) -> showEmailDetails(newValue));
        addAllFolders();
    }

    private void drawFolders() {
        folderHolder.getItems().clear();
        for (String fName : folderList) {
            Label l = new Label(fName);
            l.setId(fName);
            l.setOnMouseClicked(e -> {
                try {
                    Label label = (Label) e.getSource();
                    String folderName = label.getId();
                    int folderId = doa.findFolder(folderName);
                    ObservableList<EmailFxBean> emailFxList = doa.findAllEmailBeansByFolderFx(folderId);
                    emailHolder.setItems(emailFxList);
                    dateReTxt.setSortType(TableColumn.SortType.DESCENDING);
                    emailHolder.getSortOrder().add(dateReTxt);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
            l.setOnContextMenuRequested(e -> {
                openContext(fName).show(l, e.getScreenX(), e.getScreenY());
            });
            l.setMaxWidth(folderHolder.getPrefWidth());
            folderHolder.getItems().add(l);

        }
    }

    private ContextMenu openContext(String fName) {
        ContextMenu cm = new ContextMenu();
        MenuItem m1 = new MenuItem("Rename");
        m1.setOnAction(event -> {
            if (!fName.equals("Inbox") && !fName.equals("Trash") && !fName.equals("Spam") && !fName.equals("Sent")) {
                renameFolder(fName);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Sorry Cannot Rename This Folder");
                alert.showAndWait();
            }
        });
        MenuItem m2 = new MenuItem("Delete");
        m2.setOnAction(event -> {
            if (!fName.equals("Inbox") && !fName.equals("Trash") && !fName.equals("Spam") && !fName.equals("Sent")) {
                deleteFolder(fName);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Sorry Cannot Delete This Folder");
                alert.showAndWait();
            }
        });
        cm.getItems().addAll(m1, m2);
        return cm;
    }

    private void deleteFolder(String fName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Current project is modified");
        alert.setContentText("Remove Folder " + fName);
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(okButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == okButton) {
            try {
                LOG.info("removing " + fName);
                folderList.remove(fName);
                folderHolder.getItems().remove(fName);
                doa.deleteFolder(fName);
                drawFolders();
            } catch (SQLException e) {
                LOG.error("error deleting from DB");
            }
        }
    }

    private void renameFolder(String fName) {
        TextInputDialog folderDiag = new TextInputDialog(this.resources.getString("folderName"));//"Folder Name");
        folderDiag.setTitle(this.resources.getString("renameFolder"));
        folderDiag.setHeaderText(this.resources.getString("enterFolderName"));
        Optional<String> result = folderDiag.showAndWait();
        result.ifPresent(name -> {
            try {
                LOG.info("changing file name to " + name);
                Collections.replaceAll(folderList, fName, name);
                doa.updateFolder(doa.findFolder(fName), name);
            } catch (SQLException e) {
                LOG.error("error updating DB");
            }
        });
        drawFolders();
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


    private void addAllFolders() {
        try {
            folderList = doa.findAllFolders();
            drawFolders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            controller.setData();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/emailCSS.css");
            primaryStage.setTitle("JAG: Configure");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public void readMeDiag(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        Path filePath = get("", "README.md");
        File file = filePath.toFile();
        try {
            List<String> lines = Files.readAllLines(filePath,
                    Charset.defaultCharset());
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line).append('\n');
            }
            String str = sb.toString();
            alert.setContentText(str);
        } catch (Exception e) {

        }
        alert.showAndWait();
    }
}
