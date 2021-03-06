/**
 * Sample Skeleton for 'mainPage.fxml' Controller Class
 */

package com.saadkhan.controller;

import com.saadkhan.buisness.EmailReceiver;
import com.saadkhan.data.EmailBean;
import com.saadkhan.data.EmailFxBean;
import com.saadkhan.data.FileAttachmentBean;
import com.saadkhan.persistence.EmailDOA;
import com.saadkhan.persistence.EmailDOAImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jodd.io.FileNameUtil;
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
    @FXML // fx:id="refreshBtn"
    private Button refreshBtn; // Value injected by FXMLLoader
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
    @FXML // fx:id="from"
    private Label from; // Value injected by FXMLLoader
    @FXML // fx:id="attachTxt"
    private Label attachTxt; // Value injected by FXMLLoader
    @FXML // fx:id="webby"
    private WebView webby; // Value injected by FXMLLoader
    private Stage primaryStage;
    private ObservableList<String> folderList;
    private Locale locale;
    private EmailDOA doa;
    private EmailFxBean selectedEmail;
    private EmailReceiver er;
    private String folderName = "";
    private EmailFxBean selected;

    /**
     * constructor for the maincontroller class; sets receiver, DOA and local
     */
    public mainController() {
        this.er = new EmailReceiver();
        this.doa = new EmailDOAImpl();
        this.locale = new Locale("en", "US");
    }

    /**
     * onClick open a trext input dialgo to ask user for the name of the new folder to input
     * then redraw all the folders in the listview
     */
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

    /**
     * onClick move email to the Trash folder if the email is already in the trash folder delter from
     * the Db the email then recreate the window
     */
    @FXML
    public void moveTrash(MouseEvent mouseEvent) {
        try {
            if (selectedEmail != null) {
                LOG.info("moving " + selectedEmail + " to Trash");
                if (selectedEmail.getFolder().equals("Trash")) {
                    LOG.info("deleting permanently email");
                    doa.deleteEmailBean(selectedEmail.getEmailID());
                } else {
                    doa.moveFolder("Trash", selectedEmail);
                }
                recreateWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * initialize method, sets button to disabled by default and populates the the table view with
     * data then attaches the events for drag and drop
     */
    @FXML
    public void initialize() {
        trashBtn.setDisable(true);
        sOptionBtn.setDisable(true);
        fromTxt.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
        dateReTxt.setCellValueFactory(cellData -> cellData.getValue().recivedProperty());
        subjectTxt.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
        emailHolder.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showEmailDetails(newValue));
        emailHolder.setOnDragDetected(e -> {
            selected = emailHolder.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Dragboard drg = emailHolder.startDragAndDrop(TransferMode.ANY);
                ClipboardContent cb = new ClipboardContent();
                cb.put(DataFormat.RTF, selected);
                drg.setContent(cb);
                e.consume();
            }
        });
        emailHolder.setOnDragOver(e -> {
            Dragboard drb = e.getDragboard();
        });
        emailHolder.setOnDragDone(e -> {
            if (selected != null && folderName != null) {
                int email_Id = selectedEmail.getEmailID();
                LOG.info("Moving " + selectedEmail.getSubject() + " To " + folderName);
                doa.moveFolder(folderName, selected);
                recreateWindow();
            }
        });
        addAllFolders();
    }

    /**
     * get emails from server, clear the listview of folders for each folder name in the folder list
     * we add them to the listview then on click display the email, if right click dsiplay
     * delete option or the rename option, then add the drag over event
     */
    private void drawFolders() {
        refreshEmails();
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
            folderHolder.setOnDragOver(e -> {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                folderName = e.getPickResult().getIntersectedNode().getId();
            });
            folderHolder.getItems().add(l);
        }
    }

    /**
     * clear all fieds in the GUI, allow buttons to be clicable and attach correct events to splitmenu
     * set the information from the beans into the correct fields
     *
     * @param email email that has been selected to display
     */
    private void showEmailDetails(EmailFxBean email) {
        if (email != null) {
            clearFields();
            this.selectedEmail = email;
            trashBtn.setDisable(false);
            sOptionBtn.setDisable(false);
            sOptionBtn.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
                if (isNowShowing) {
                    sOptionBtn.getItems().clear();
                    MenuItem m1 = new MenuItem("Reply");
                    MenuItem m2 = new MenuItem("Reply All");
                    MenuItem m3 = new MenuItem("Forward");
                    m1.setOnAction(e -> {
                        sOptionBtn.setText(m1.getText());
                    });
                    m2.setOnAction(e -> {
                        sOptionBtn.setText(m2.getText());
                    });
                    m3.setOnAction(e -> {
                        sOptionBtn.setText(m3.getText());
                    });
                    sOptionBtn.getItems().addAll(m1, m2, m3);
                }
            });
            sOptionBtn.setOnMouseClicked(e -> sOptionSettings());
            from.setText(from.getText() + ": " + email.getFrom());
            ccTxt.setText(ccTxt.getText() + ": " + email.getListInString(email.getCc()));
            subTxt.setText(subTxt.getText() + ": " + email.getSubject());
            drawAttachList(email.getAttachments());
            displayContent();
        }
    }

    /**
     * convert html and message to be displayed properly along with attachments
     */
    private void displayContent() {
        String htmlMessage = selectedEmail.getHtmlMessage();
        String message = selectedEmail.getMessage();
        if (!htmlMessage.isEmpty()) {
            for (FileAttachmentBean fab : selectedEmail.getAttachments()) {
                if (fab.getType()) {
                    createTemp(fab);
                    String temp = System.getProperty("java.io.tmpdir");
                    Path file = Paths.get(temp + fab.getName());
                    String defaultAttach = "<img src=\"cid:" + fab.getName() + "\">";
                    String custumAttach = "<img src='" + file.toUri().toString() + "'/>";
                    htmlMessage = htmlMessage.replace(defaultAttach, custumAttach);
                }
            }
            webby.getEngine().loadContent(htmlMessage);
        } else {
            webby.getEngine().loadContent("<html><p>" + message + "</p><html>");
        }
    }

    /**
     * create file for specified attachment inside the computers temp folder
     */
    private void createTemp(FileAttachmentBean fab) {
        String temp = System.getProperty("java.io.tmpdir");
        try (FileOutputStream stream = new FileOutputStream(temp + fab.getName())) {
            if (fab.getFile() != null) {
                LOG.info("creating temp");
                stream.write(fab.getFile());
            }
        } catch (IOException e) {
            LOG.error("error saving");
            e.printStackTrace();
        }
    }

    /**
     * how to display compose depending on btn click
     */
    private void sOptionSettings() {
        switch (sOptionBtn.getText()) {
            case "Reply":
                optionedCompose(1);
                break;
            case "Reply All":
                optionedCompose(2);
                break;
            case "Forward":
                optionedCompose(3);
                break;
            default:
        }
    }

    /**
     * in a seperate thread use the selected option and create the stage to be dispalyed along with
     * the selected email that was chosen to reply with
     *
     * @param option reply option 1, 2 or 3
     */
    private void optionedCompose(int option) {
        Platform.runLater(() -> {
            try {
                ResourceBundle rb = ResourceBundle.getBundle("Strings", locale);
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/composePage.fxml"), rb);
                Parent root = (AnchorPane) loader.load();
                composeController controller = loader.getController();
                controller.setSceneStageController(primaryStage);
                controller.setData(selectedEmail, option);
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
        });
    }

    /**
     * take an arraylist of attachments and through a new thread attach them as buttons to the
     * attachment holder
     */
    private void drawAttachList(ArrayList<FileAttachmentBean> attachments) {
        Platform.runLater(() -> {
            for (FileAttachmentBean f : attachments) {
                if (!f.getType()) {
                    Button b = new Button(f.getName().length() < 15 ? f.getName() : f.getName().substring(0, 15));
                    b.setId(f.getName().length() < 15 ? f.getName() : f.getName().substring(0, 15) + "Btn");
                    b.setStyle("-fx-background-color: #b0ffb6; ");
                    b.setOnMouseClicked(e -> {
                        if (f.getFile() != null) {
                            LOG.info("file name: " + f.getName());
                            ContextMenu cm = new ContextMenu();
                            MenuItem m1 = new MenuItem(this.resources.getString("download"));
                            m1.setOnAction(event -> {
                                downloadFile(f);
                            });
                            cm.getItems().add(m1);
                            cm.show(b, e.getScreenX(), e.getScreenY());
                        }
                    });
                    attachyHolder.setOrientation(Orientation.HORIZONTAL);
                    attachyHolder.getItems().add(b);
                }
            }
        });
    }

    /**
     * when file is lciked for download display a filechooser with appropiate file extensions
     * and allow the user to save their attachments
     *
     * @param bean The file to be downloaded
     */
    private void downloadFile(FileAttachmentBean bean) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        String ext = FileNameUtil.getExtension(bean.getName());
        FileChooser.ExtensionFilter current = new FileChooser.ExtensionFilter(this.resources.getString("ThisFile"), "*." + ext);
        FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter pdfs = new FileChooser.ExtensionFilter("PDF Files", "*.pdf");
        FileChooser.ExtensionFilter images = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        FileChooser.ExtensionFilter audio = new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav");
        FileChooser.ExtensionFilter html = new FileChooser.ExtensionFilter("Web pages", "*.tpl", "*.html", "*.htm");
        FileChooser.ExtensionFilter all = new FileChooser.ExtensionFilter("All", "*.*");
        fileChooser.getExtensionFilters().addAll(txt, images, pdfs, audio, html, current, all);
        fileChooser.setSelectedExtensionFilter(current);
        fileChooser.setInitialFileName(bean.getName());
        System.out.println(bean.getName());
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try (FileOutputStream stream = new FileOutputStream(file);) {
                stream.write(bean.getFile());
            } catch (IOException e) {
                LOG.error("error saving");
            }
        }
    }

    /**
     * remove the emails from the to,cc and bcc fields
     */
    private void clearFields() {
        from.setText(this.resources.getString("fromTxt"));
        ccTxt.setText(this.resources.getString("ccTxt"));
        subTxt.setText(this.resources.getString("subjectTxt"));
        attachyHolder.getItems().clear();
    }

    /**
     * create contexr menu for delete and rename, however they cannot be used on the mandatory fields
     * Mandatory fields: Inbox, Trash, Spam, Sent
     *
     * @param fName name of the folder that is clicked when event is called
     * @return Contextmenu for rename and delete
     */
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
            if(fName.equals("Trash")) {
                deleteFolder(fName);
                try {
                    LOG.info("deleteing all trash");
                    doa.createFolder(fName);
                    recreateWindow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if (!fName.equals("Inbox") && !fName.equals("Spam") && !fName.equals("Sent")) {
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

    /**
     * remove folder from list of folders
     *
     * @param fName selected folder
     */
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
                recreateWindow();
            } catch (SQLException e) {
                LOG.error("error deleting from DB");
            }
        }
    }

    /**
     * rename the selected folder in the list and update GUI
     *
     * @param fName selected folder
     */
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

    /**
     * display the compose page
     */
    public void showCompose(ActionEvent actionEvent) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Strings", locale);
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

    /**
     * set the primary stage
     */
    public void setSceneStageController(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * add all folders from the db to the list then display them
     */
    private void addAllFolders() {
        try {
            folderList = doa.findAllFolders();
            drawFolders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * recreate the current window
     */
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

    /**
     * in another thread receive emails from the gmail server again then insert each one to the db
     */
    private void refreshEmails() {
        Platform.runLater(() -> {
            try {
                LOG.info("Refreshing");
                EmailBean[] emailBeans = er.receiveEmail();
                for (EmailBean bean : emailBeans) {
                    doa.createEmailBean(bean);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * onclick set the local
     */
    public void changeFrench(ActionEvent actionEvent) {
        this.locale = new Locale("en", "US");
        recreateWindow();
    }

    /**
     * on click set the local
     */
    public void changeEnglish(ActionEvent actionEvent) {
        this.locale = new Locale("fr", "CA");
        recreateWindow();
    }

    /**
     * onclick display page to change file configurations
     */
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

    /**
     * onclick display an error alert which contains the readme file in the more information section
     */
    public void readMeDiag(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(this.resources.getString("readMeTitle"));
        alert.setHeaderText(this.resources.getString("aboutHeaderTxt"));
        alert.setContentText(this.resources.getString("aboutContent"));
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
            Label label = new Label("README:");
            TextArea textArea = new TextArea(str);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);
            alert.getDialogPane().setExpandableContent(expContent);
        } catch (Exception e) {
            LOG.error("Could not Find README File");
        }
        alert.showAndWait();
    }

    /**
     * onclick of the refresh btn disable the button, refresh emails from server and then enable btn
     */
    public void callRefresh(ActionEvent actionEvent) {
        refreshBtn.setDisable(true);
        refreshEmails();
        refreshBtn.setDisable(false);
    }
}
