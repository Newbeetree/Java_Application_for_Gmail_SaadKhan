package com.saadkhan.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jodd.mail.EmailAddress;

/**
 * EmailFxBean is a bean class that is serializable and has basic and getters for most functions
 *
 * @Author: Saad Khan 1633839
 */
public class EmailFxBean implements Serializable {

    private IntegerProperty emailID;
    private ObjectProperty<EmailAddress> from;
    private ListProperty<EmailAddress> to;
    private ListProperty<EmailAddress> cc;
    private ListProperty<EmailAddress> bcc;
    private StringProperty subject;
    private StringProperty message;
    private StringProperty htmlMessage;
    private BooleanProperty seen;
    private ListProperty<FileAttachmentBean> attachments;
    private StringProperty messageType;
    private ObjectProperty<LocalDateTime> send;
    private ObjectProperty<LocalDateTime> recived;
    private StringProperty folder;
    private ObjectProperty<Priority> priority;

    /**
     * Constructor sets all values of the email bean to the defualt values
     */
    public EmailFxBean() {
        emailID = new SimpleIntegerProperty();
        from = new SimpleObjectProperty<>();
        to = new SimpleListProperty<>();
        cc = new SimpleListProperty<>();
        bcc = new SimpleListProperty<>();
        subject = new SimpleStringProperty();
        message = new SimpleStringProperty();
        htmlMessage = new SimpleStringProperty();
        seen = new SimpleBooleanProperty();
        attachments = new SimpleListProperty<>();
        messageType = new SimpleStringProperty();
        send = new SimpleObjectProperty<>(LocalDateTime.now());
        recived = new SimpleObjectProperty<>(LocalDateTime.now());
        folder = new SimpleStringProperty();
        priority = new SimpleObjectProperty<>(Priority.PRIORITY_NORMAL);
    }

    /**
     * create EmailFXbean using and email bean and converting all appropriate fields
     *
     * @param bean bean to be converted
     */
    public EmailFxBean(EmailBean bean) {
        emailID = new SimpleIntegerProperty(bean.getEmailID());
        from = new SimpleObjectProperty<>(bean.getFrom());
        to = new SimpleListProperty<>(FXCollections.observableArrayList(bean.getTo()));
        cc = new SimpleListProperty<>(FXCollections.observableArrayList(bean.getCc()));
        bcc = new SimpleListProperty<>(FXCollections.observableArrayList(bean.getBcc()));
        subject = new SimpleStringProperty(bean.getSubject());
        message = new SimpleStringProperty(bean.getMessage());
        htmlMessage = new SimpleStringProperty(bean.getHtmlMessage());
        seen = new SimpleBooleanProperty(bean.isSeen());
        attachments = new SimpleListProperty<>(FXCollections.observableArrayList(bean.getAttachments()));
        messageType = new SimpleStringProperty(bean.getMessageType());
        send = new SimpleObjectProperty<>(bean.getSend());
        recived = new SimpleObjectProperty<>(bean.getRecived());
        folder = new SimpleStringProperty(bean.getFolder());
        priority = new SimpleObjectProperty<>(bean.getPriority());
    }

    /**
     * convert EmailFxbean to an emailBean
     *
     * @param efb emailfxbean to convert
     * @return Email bean with all appropriate fields set
     */
    public EmailBean toBean(EmailFxBean efb) {
        EmailBean eb = new EmailBean();
        eb.setFrom(efb.getFrom());
        if (!efb.getTo().isEmpty())
            eb.setTo(efb.getTo());
        if (!efb.getCc().isEmpty())
            eb.setCc(efb.getCc());
        if (!efb.getBcc().isEmpty())
            eb.setBcc(efb.getBcc());
        if (!efb.getAttachments().isEmpty())
            eb.setAttachments(efb.getAttachments());
        if (efb.getSubject() != null)
            eb.setSubject(efb.getSubject());
        if (efb.getMessage() != null)
            eb.setMessage(efb.getMessage());
        if (efb.getHtmlMessage() != null)
            eb.setHtmlMessage(efb.getHtmlMessage());
        eb.setFolder("Sent");
        return eb;
    }

    public EmailAddress getFrom() {
        return from.get();
    }

    public void setFrom(EmailAddress from) {
        this.from.set(from);
    }

    /**
     * convert a list of email addresses to a string
     *
     * @param list email addresss
     * @return string of all email addresses together
     */
    public String getListInString(ArrayList<EmailAddress> list) {
        String emailString = "";
        for (EmailAddress email : list) {
            emailString += email.toString() + ", ";
        }
        return emailString;
    }

    /**
     * convert a string of email address into a list property of emailaddress
     *
     * @param emailString string of all emails
     * @return list of email addresses
     */
    public ListProperty<EmailAddress> getStringToList(String emailString) {
        emailString = emailString.replace('[', ' ');
        emailString = emailString.replace(']', ' ');
        String[] emailList = emailString.split(", ");
        ArrayList<EmailAddress> emailAddresses = new ArrayList<>();
        for (int i = 0; i < emailList.length; i++) {
            emailAddresses.add(EmailAddress.of(emailList[i]));
        }
        return new SimpleListProperty<>(FXCollections.observableArrayList(emailAddresses));
    }

    public ObjectProperty<EmailAddress> fromProperty() {
        return from;
    }

    /**
     * retrun a new array if to has nothing or return it with proper email addresses
     */
    public ArrayList<EmailAddress> getTo() {
        return to.size() > 0 ? new ArrayList<>(to.get()) : new ArrayList<>();
    }

    public void setTo(ObservableList<EmailAddress> to) {
        this.to.set(to);
    }

    public ListProperty<EmailAddress> toProperty() {
        return to;
    }

    /**
     * return a new array if cc has nothing or return it with proper email addresses
     */
    public ArrayList<EmailAddress> getCc() {
        return cc.size() > 0 ? new ArrayList<>(cc.get()) : new ArrayList<>();
    }

    public void setCc(ObservableList<EmailAddress> cc) {
        this.cc.set(cc);
    }

    public ListProperty<EmailAddress> ccProperty() {
        return cc;
    }

    /**
     * s     * retrun a new array if bcc has nothing or return it with proper email addresses
     */
    public ArrayList<EmailAddress> getBcc() {
        return bcc.size() > 0 ? new ArrayList<>(bcc.get()) : new ArrayList<>();
    }

    public void setBcc(ObservableList<EmailAddress> bcc) {
        this.bcc.set(bcc);
    }

    public ListProperty<EmailAddress> bccProperty() {
        return bcc;
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    public String getMessage() {
        return message.get();
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public StringProperty messageProperty() {
        return message;
    }

    public String getHtmlMessage() {
        return htmlMessage.get();
    }

    public void setHtmlMessage(String htmlMessage) {
        this.htmlMessage.set(htmlMessage);
    }

    public StringProperty htmlMessageProperty() {
        return htmlMessage;
    }

    public boolean isSeen() {
        return seen.get();
    }

    public void setSeen(boolean seen) {
        this.seen.set(seen);
    }

    public BooleanProperty seenProperty() {
        return seen;
    }

    /**
     * retrun a new array if attachments has nothing or return it with proper email addresses
     */
    public ArrayList<FileAttachmentBean> getAttachments() {
        return attachments.size() > 0 ? new ArrayList<>(attachments.get()) : new ArrayList<>();
    }

    public void setAttachments(ObservableList<FileAttachmentBean> attachments) {
        this.attachments.set(attachments);
    }

    public ListProperty<FileAttachmentBean> attachmentsProperty() {
        return attachments;
    }

    public String getMessageType() {
        return messageType.get();
    }

    public void setMessageType(String messageType) {
        this.messageType.set(messageType);
    }

    public StringProperty messageTypeProperty() {
        return messageType;
    }

    public LocalDateTime getSend() {
        return send.get();
    }

    public void setSend(LocalDateTime send) {
        this.send.set(send);
    }

    public ObjectProperty<LocalDateTime> sendProperty() {
        return send;
    }

    public LocalDateTime getRecived() {
        return recived.get();
    }

    public void setRecived(LocalDateTime recived) {
        this.recived.set(recived);
    }

    public ObjectProperty<LocalDateTime> recivedProperty() {
        return recived;
    }

    public String getFolder() {
        return folder.get();
    }

    public void setFolder(String folder) {
        this.folder.set(folder);
    }

    public StringProperty folderProperty() {
        return folder;
    }

    public Priority getPriority() {
        return priority.get();
    }

    public void setPriority(Priority priority) {
        this.priority.set(priority);
    }

    public ObjectProperty<Priority> priorityProperty() {
        return priority;
    }

    /**
     * Checks if two email objects are the same
     *
     * @param o Email bean that is being used to compare with other EmailFxBean
     * @return boolean true if same, false if wrong
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailFxBean EmailFxBean = (EmailFxBean) o;
        boolean work = Objects.equals(from.get().getEmail(), EmailFxBean.from.get().getEmail());
        boolean work2 = emailEqual(to, EmailFxBean.to);
        boolean work3 = emailEqual(cc, EmailFxBean.cc);
        boolean work4 = subjectEquals(subject, EmailFxBean.subject);
        boolean work5 = Objects.equals(message, EmailFxBean.message);
        boolean work6 = Objects.equals(htmlMessage, EmailFxBean.htmlMessage);
        boolean work7 = attachEqual(attachments, EmailFxBean.attachments);
        return Objects.equals(from.get().getEmail(), EmailFxBean.from.get().getEmail()) &&
                emailEqual(to, EmailFxBean.to) &&
                emailEqual(cc, EmailFxBean.cc) &&
                subjectEquals(subject, EmailFxBean.subject) &&
                Objects.equals(message, EmailFxBean.message) &&
                Objects.equals(htmlMessage, EmailFxBean.htmlMessage) &&
                attachEqual(attachments, EmailFxBean.attachments);
    }

    /**
     * checks if two subjects are the same taking into account if one is unknown
     *
     * @param toSubject   subject that was in the bean
     * @param fromSubject subject that we received from gmail server
     * @return true if same, false if wrong
     */
    private boolean subjectEquals(StringProperty toSubject, StringProperty fromSubject) {
        if (Objects.equals(toSubject, fromSubject))
            return true;
        else return toSubject.equals("") && fromSubject == null;
    }

    /**
     * takes both arraylists and checks if they are equal
     *
     * @param to   Arraylist of email addresses from EmailFxBean
     * @param from Arraylist of email addresses from gmail
     * @return true if same, false if wronge
     */
    private boolean emailEqual(ListProperty<EmailAddress> to, ListProperty<EmailAddress> from) {
        if (to.size() == 0 && from.size() == 0)
            return true;
        if (to.size() != from.size())
            return false;
        for (int i = 0; i < to.size(); i++) {
            if (!(to.get(i).getEmail().equals(from.get(i).getEmail()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * takes both arraylists of attachments and checks if they are equal
     *
     * @param to   Arraylist of email addresses from EmailFxBean
     * @param from Arraylist of email addresses from gmail
     * @return true if same, false if wronge
     */
    private boolean attachEqual(ListProperty<FileAttachmentBean> to, ListProperty<FileAttachmentBean> from) {
        if (to.size() != from.size())
            return false;
        to.sort(Comparator.comparing(FileAttachmentBean::getName));
        from.sort(Comparator.comparing(FileAttachmentBean::getName));
        for (int i = 0; i < to.size(); i++) {
            if (!(Arrays.equals(to.get(0).getFile(), from.get(0).getFile()))) {
                return false;
            }
        }
        return true;
    }


    @Override
    public int hashCode() {
        return Objects.hash(getFrom(), getTo(), getCc(), getBcc(), getSubject(), getMessage(),
                getHtmlMessage(), isSeen(), getAttachments(), getMessageType(), getSend(),
                getRecived(), getFolder(), getPriority(), getEmailID());
    }

    public int getEmailID() {
        return emailID.get();
    }

    public EmailFxBean setEmailID(int emailID) {
        this.emailID.set(emailID);
        return this;
    }

    public IntegerProperty emailIDProperty() {
        return emailID;
    }
}