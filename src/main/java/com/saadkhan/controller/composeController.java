/**
 * Sample Skeleton for 'composePage.fxml' Controller Class
 */

package com.saadkhan.controller;

import com.saadkhan.buisness.EmailSender;
import com.saadkhan.data.ConfigurationFxBean;
import com.saadkhan.data.EmailBean;
import com.saadkhan.data.EmailFxBean;
import com.saadkhan.data.FileAttachmentBean;
import com.saadkhan.manager.PropertiesManager;
import com.saadkhan.persistence.EmailDOA;
import com.saadkhan.persistence.EmailDOAImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jodd.mail.EmailAddress;

import static java.nio.file.Paths.get;

public class composeController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="toTxt"
    private Label toTxt; // Value injected by FXMLLoader

    @FXML // fx:id="closeBtn"
    private Button closeBtn; // Value injected by FXMLLoader

    @FXML // fx:id="attachyHolder"
    private ListView attachyHolder; // Value injected by FXMLLoader

    @FXML // fx:id="ccIn"
    private TextField ccIn; // Value injected by FXMLLoader

    @FXML // fx:id="addBtn"
    private Button addBtn; // Value injected by FXMLLoader

    @FXML // fx:id="ccTxt"
    private Label ccTxt; // Value injected by FXMLLoader

    @FXML // fx:id="bccTxt"
    private Label bccTxt; // Value injected by FXMLLoader

    @FXML // fx:id="bccIn"
    private TextField bccIn; // Value injected by FXMLLoader

    @FXML // fx:id="sendBtn"
    private Button sendBtn; // Value injected by FXMLLoader

    @FXML // fx:id="toIn"
    private TextField toIn; // Value injected by FXMLLoader

    @FXML // fx:id="helpMn"
    private Menu helpMn; // Value injected by FXMLLoader

    @FXML // fx:id="subjectIn"
    private TextField subjectIn; // Value injected by FXMLLoader

    @FXML // fx:id="subjectTx"
    private Label subjectTx; // Value injected by FXMLLoader

    @FXML // fx:id="addImage"
    private Button addImage; // Value injected by FXMLLoader

    @FXML // fx:id="langMn"
    private Menu langMn; // Value injected by FXMLLoader

    @FXML // fx:id="editor"
    private HTMLEditor editor; // Value injected by FXMLLoader


    private Stage primaryStage;
    private final EmailFxBean efb;

    private final static Logger LOG = LoggerFactory.getLogger(confController.class);
    private Locale locale;
    private ArrayList<FileAttachmentBean> list = new ArrayList<>();
    private EmailDOA doa;
    private EmailSender es;
    private String userEmail;
    private String name;

    public composeController() {
        getConfigValues();
        this.efb = new EmailFxBean();
        this.doa = new EmailDOAImpl();
        this.es = new EmailSender();
    }

    private void getConfigValues() {
        try {
            PropertiesManager pm = new PropertiesManager();
            Path txtFile = get("", "JAGConfig.properties");
            ConfigurationFxBean cfb = pm.getConfBeanSettings(txtFile);
            this.userEmail = cfb.getUserEmailAddress();
            this.name = cfb.getUserName();
        } catch (IOException e) {
            LOG.error("cannot find properties");
        }
    }

    @FXML
    public void sendEmail(ActionEvent event) {
        try {
            efb.setFrom(new EmailAddress(name, userEmail));
            efb.setTo(efb.getStringToList(toIn.textProperty().getValue()));
            efb.setCc(efb.getStringToList(ccIn.textProperty().getValue()));
            efb.setBcc(efb.getStringToList(bccIn.textProperty().getValue()));
            efb.setSubject(subjectIn.textProperty().getValue());
            drawAttachList(false);
            if (list != null)
                efb.setAttachments(FXCollections.observableArrayList(list));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sent");
            alert.setContentText("Your Email has been succesfully sent");
            EmailBean emailBean = efb.toBean(efb);
            doa.createEmailBean(emailBean);
            boolean noAttachments = (efb.getAttachments().isEmpty());
            es.send(emailBean, noAttachments);
            alert.showAndWait();
            close(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void addFolder(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        try {
            convertFileBean((fileChooser.showOpenMultipleDialog((Stage) closeBtn.getScene().getWindow())), false);
            drawAttachList(false);
        } catch (NullPointerException e) {
            LOG.info(" File explorer closed unexpectedly");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void convertFileBean(List<File> files, boolean b) throws IOException {
        if (!files.isEmpty()) {
            for (File f : files) {
                FileAttachmentBean bean = new FileAttachmentBean();
                bean.setName(f.getName());
                bean.setFile(Files.readAllBytes(f.toPath()));
                bean.setType(b);
                list.add(bean);
            }
        }
    }

    private void drawAttachList(boolean coverter) {
        attachyHolder.getItems().clear();
        if (list != null) {
            for (FileAttachmentBean f : list) {
                if (!f.getType()) {
                    Button b = new Button(f.getName().length() < 15 ? f.getName() : f.getName().substring(0, 15));
                    b.setId(f.getName().length() < 15 ? f.getName() : f.getName().substring(0, 15) + "Btn");
                    b.setStyle("-fx-background-color: #b2c3ff; ");
                    b.setOnMouseClicked(e -> {
                        int spot = attachyHolder.getItems().indexOf(b);
                        attachyHolder.getItems().remove(spot);
                        this.list = new ArrayList<>(list);
                        list.remove(spot);
                    });
                    attachyHolder.setOrientation(Orientation.HORIZONTAL);
                    attachyHolder.getItems().add(b);
                } else {
                    String temp = System.getProperty("java.io.tmpdir");
                    Path file = Paths.get(temp + f.getName());
                    String defaultAttach = "<img src=\"cid:" + f.getName() + "\">";
                    String custumAttach = "<img src=\"" + file.toUri().toString() + "\">";
                    if (coverter) {
                        String html = editor.getHtmlText().replaceAll(defaultAttach, custumAttach);
                        editor.setHtmlText(html);
                    } else {
                        LOG.info(editor.getHtmlText().replace(custumAttach, defaultAttach));
                        String gmail = editor.getHtmlText();
                        gmail = gmail.replaceAll(custumAttach, defaultAttach);
                        efb.setHtmlMessage(gmail);
                    }
                }
            }
            if(list.isEmpty())
            efb.setHtmlMessage(editor.getHtmlText());
        } else {
            efb.setHtmlMessage(editor.getHtmlText());
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        this.locale = new Locale("en", "US");
        Bindings.bindBidirectional(efb.getStringToList(toIn.textProperty().toString()), efb.toProperty());
        Bindings.bindBidirectional(efb.getStringToList(toIn.textProperty().toString()), efb.toProperty());
        Bindings.bindBidirectional(efb.getStringToList(toIn.textProperty().toString()), efb.toProperty());
        Bindings.bindBidirectional(subjectIn.textProperty(), efb.subjectProperty());
    }

    public void close(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
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
            e.printStackTrace();
        }
    }

    public void setSceneStageController(Stage stage) {
        this.primaryStage = stage;
    }

    public void changeFrench(ActionEvent actionEvent) {
        this.locale = new Locale("en", "US");
        recreateWindow();
    }

    public void changeEnglish(ActionEvent actionEvent) {
        this.locale = new Locale("fr", "CA");
        recreateWindow();
    }

    private void recreateWindow() {
        try {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
            ResourceBundle rb = ResourceBundle.getBundle("Strings", locale);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/composePage.fxml"), rb);
            Parent root = (AnchorPane) loader.load();
            composeController controller = loader.getController();
            controller.setSceneStageController(primaryStage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/emailCSS.css");
            Stage main = new Stage();
            main.setScene(scene);
            main.initModality(Modality.APPLICATION_MODAL);
            main.setTitle("JAG: Email Create");
            main.initStyle(StageStyle.UNDECORATED);
            main.show();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public void changeSettings(ActionEvent actionEvent) {
        try {
            close(null);
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

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setData(EmailFxBean selectedEmail, int option) {
        switch (option) {
            case 1:
                toIn.setText(selectedEmail.getFrom().toString());
                subjectIn.setText("Re: " + selectedEmail.getSubject());
                break;
            case 2:
                toIn.setText(selectedEmail.getFrom().toString());
                ccIn.setText(selectedEmail.getListInString(selectedEmail.getCc()));
                subjectIn.setText("Re: " + selectedEmail.getSubject());
                break;
            case 3:
                subjectIn.setText("FWD: " + selectedEmail.getSubject());
                break;
            default:
                LOG.error("this should never occur");
        }
        efb.setMessage(selectedEmail.getMessage());
        editor.setHtmlText("<br><hr>" + selectedEmail.getHtmlMessage());
        this.list = selectedEmail.getAttachments();
        drawAttachList(true);
    }

    public void addImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        try {
            List<File> files = fileChooser.showOpenMultipleDialog((Stage) closeBtn.getScene().getWindow());
            convertFileBean(files, true);
            if (!files.isEmpty()) {
                for (File f : files) {
                    String temp = System.getProperty("java.io.tmpdir");
                    try (FileOutputStream stream = new FileOutputStream(temp + f.getName())) {
                        stream.write(Files.readAllBytes(f.toPath()));
                        f = new File(temp + f.getName());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    String h = "<img src=\"cid:" + f.getName() + "\">" + editor.getHtmlText();
                    editor.setHtmlText("<img src=\"cid:" + f.getName() + "\">" + editor.getHtmlText());
                }
                drawAttachList(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            LOG.info("closed Explorer");
        }
    }
}
