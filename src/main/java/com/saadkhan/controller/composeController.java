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

    private final static Logger LOG = LoggerFactory.getLogger(confController.class);
    private final EmailFxBean efb;
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
    private Locale locale;
    private ArrayList<FileAttachmentBean> list = new ArrayList<>();
    private EmailDOA doa;
    private EmailSender es;
    private String userEmail;
    private String name;

    /**
     * Basic Constructor for composeController;
     * gets config, creates new emailfxbean, EmailDOA, and EmailSender Objects
     */
    public composeController() {
        getConfigValues();
        this.efb = new EmailFxBean();
        this.doa = new EmailDOAImpl();
        this.es = new EmailSender();
    }

    /**
     * get configurations from config properties file and set username and name
     */
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

    /**
     * On Click set and check fields of the emailFxbean then convert the bean
     */
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
            if (es.validateBean(emailBean)) {
                doa.createEmailBean(emailBean);
                boolean noAttachments = (efb.getAttachments().isEmpty());
                es.send(emailBean, noAttachments);
                alert.showAndWait();
                close(null);
            } else {
                toIn.setStyle("-fx-border-color: #ff3d38;");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * on click open File chooser and select which email to attach to this email
     */
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

    /**
     * Taking a list of files and if they are imbedded and converting them to File Attachment Beans
     * and adding them to the privatte list of File Attachment Beans
     */
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

    /**
     * clear where all the attachments are held, then after checking if list is not null iterate
     * through the list of attachments depending on their type either add them as buttons or
     * insert them as html as embedded attachments. Converter field tells the code of how the
     * paths should be formatted, then setthe html text in message
     */
    private void drawAttachList(boolean converter) {
        attachyHolder.getItems().clear();
        if (list != null) {
            for (FileAttachmentBean f : list) {
                if (!f.getType()) {
                    attachFileToHolder(f);
                } else {
                    attachImbed(f, converter);
                }
            }
            if (list.isEmpty())
                efb.setHtmlMessage(editor.getHtmlText());
        } else {
            efb.setHtmlMessage(editor.getHtmlText());
        }
    }

    /**
     * Saves files to temp directory and convertes the html to properly set the html
     *
     * @param f         the imbed attach to set
     * @param converter how the file should be converted
     */
    private void attachImbed(FileAttachmentBean f, boolean converter) {
        String temp = System.getProperty("java.io.tmpdir");
        Path file = Paths.get(temp + f.getName());
        String defaultAttach = "<img src=\"cid:" + f.getName() + "\">";
        String custumAttach = "<img src=\"" + file.toUri().toString() + "\">";
        if (converter) {
            String html = editor.getHtmlText().replaceAll(defaultAttach, custumAttach);
            editor.setHtmlText(html);
        } else {
            String gmail = editor.getHtmlText();
            gmail = gmail.replaceAll(custumAttach, defaultAttach);
            efb.setHtmlMessage(gmail);
        }
    }

    /**
     * Attach the file to the atachment holder
     *
     * @param f the fileattachmentbean to attach to the email
     */
    private void attachFileToHolder(FileAttachmentBean f) {
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
    }

    /**
     * Initializer method sets bindings for bean
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        this.locale = new Locale("en", "US");
        Bindings.bindBidirectional(efb.getStringToList(toIn.textProperty().toString()), efb.toProperty());
        Bindings.bindBidirectional(efb.getStringToList(toIn.textProperty().toString()), efb.toProperty());
        Bindings.bindBidirectional(efb.getStringToList(toIn.textProperty().toString()), efb.toProperty());
        Bindings.bindBidirectional(subjectIn.textProperty(), efb.subjectProperty());
    }

    /**
     * when close button is clicked recreate main page with proper local and close the current stage
     */
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

    /**
     * when controller is set specify the primary stage and put examples of input into the Text Fields
     */
    public void setSceneStageController(Stage stage) {
        this.primaryStage = stage;
        toIn.setPromptText(name + " <" + userEmail + ">");
        ccIn.setPromptText(name + " <" + userEmail + ">, " + name + "<" + userEmail + ">, ");
        bccIn.setPromptText(name + " <" + userEmail + ">, " + name + "<" + userEmail + ">, ");
    }

    /**
     * change local on btn click and recreate window
     */
    public void changeFrench(ActionEvent actionEvent) {
        this.locale = new Locale("en", "US");
        recreateWindow();
    }

    /**
     * change local on btn click and recreate window
     */
    public void changeEnglish(ActionEvent actionEvent) {
        this.locale = new Locale("fr", "CA");
        recreateWindow();
    }

    /**
     * recreate compose controller window
     */
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

    /**
     * on setting button click open settings config stage
     */
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

    /**
     * set the local of the application from another stage
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Get an EmailFxBean from other stage and int representing setData Option;
     * 1 being reply
     * 2 being reply all
     * 3 being forward
     * displays properly all the data from previous window
     *
     * @param selectedEmail EmailFxBean from other window
     * @param option        type of reply
     */
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

    /**
     * add an embbeded image using a file chooser, converts the files selected and adds them to the
     * private list then goes about adding it the temp file with proper path and displaying it
     */
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
