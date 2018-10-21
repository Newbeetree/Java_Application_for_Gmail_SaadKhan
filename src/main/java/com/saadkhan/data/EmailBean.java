package com.saadkhan.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import jodd.mail.EmailAddress;

/**
 * EmailBean is a bean class that is serializable and has basic and getters for most functions
 *
 * @Author: Saad Khan 1633839
 */
public class EmailBean implements Serializable {

    private IntegerProperty emailID;
    private ObjectProperty<EmailAddress> from;
    private ArrayList<EmailAddress> to;
    private ArrayList<EmailAddress> cc;
    private ArrayList<EmailAddress> bcc;
    private StringProperty subject;
    private StringProperty message;
    private StringProperty htmlMessage;
    private BooleanProperty seen;
    private ArrayList<FileAttachmentBean> attachments;
    private StringProperty messageType;
    private ObjectProperty<LocalDateTime> send;
    private ObjectProperty<LocalDateTime> recived;
    private StringProperty folder;
    private ObjectProperty<Priority> priority;

    /**
     * Constructor sets all values of the email bean to the defualt values
     */
    public EmailBean() {
        emailID = new SimpleIntegerProperty();
        from = null;
        to = new ArrayList<>();
        cc = new ArrayList<>();
        bcc = new ArrayList<>();
        subject = new SimpleStringProperty();
        message = new SimpleStringProperty();
        htmlMessage = new SimpleStringProperty();
        seen = new SimpleBooleanProperty();
        attachments = new ArrayList<>();
        messageType = new SimpleStringProperty();
        send = new SimpleObjectProperty<>(LocalDateTime.now());
        recived = new SimpleObjectProperty<>(LocalDateTime.now());
        folder = new SimpleStringProperty();
        priority = new SimpleObjectProperty<>(Priority.PRIORITY_NORMAL);
    }

    /**
     * @return email address of from
     */
    public EmailAddress getFrom() {
        return from.get();
    }

    /**
     * @param from email address to set
     */
    public EmailBean setFrom(EmailAddress from) {
        this.from.set(from);
        return this;
    }

    public ObjectProperty<EmailAddress> fromProperty() {
        return from;
    }

    /**
     * @return arraylist of email address
     */
    public ArrayList<EmailAddress> getTo() {
        return to;
    }

    /**
     * @param to Array list of emailadress to set
     */
    public EmailBean setTo(ArrayList<EmailAddress> to) {
        this.to = to;
        return this;
    }

    /**
     * @return Arraylist of email addresses in cc
     */
    public ArrayList<EmailAddress> getCc() {
        return cc;
    }

    /**
     * @param cc sets and arraylist for cc
     */
    public EmailBean setCc(ArrayList<EmailAddress> cc) {
        this.cc = cc;
        return this;
    }

    /**
     * @return Arraylist of Email addresses
     */
    public ArrayList<EmailAddress> getBcc() {
        return bcc;
    }

    /**
     * @param bcc Arraylist of EmailAddress to set
     */
    public EmailBean setBcc(ArrayList<EmailAddress> bcc) {
        this.bcc = bcc;
        return this;
    }

    /**
     * @return string representing subject
     */
    public String getSubject() {
        return subject.get();
    }

    /**
     * @param subject set the string for subject
     */
    public EmailBean setSubject(String subject) {
        this.subject.set(subject);
        return this;
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    /**
     * @return string for the message
     */
    public String getMessage() {
        return message.get();
    }

    /**
     * @param message message to set
     */
    public EmailBean setMessage(String message) {
        this.message.set(message);
        return this;
    }

    public StringProperty messageProperty() {
        return message;
    }

    /**
     * @return html message to get
     */
    public String getHtmlMessage() {
        return htmlMessage.get();
    }

    /**
     * @param htmlMessage html to set
     */
    public EmailBean setHtmlMessage(String htmlMessage) {
        this.htmlMessage.set(htmlMessage);
        return this;
    }

    public StringProperty htmlProperty() {
        return htmlMessage;
    }

    /**
     * @return boolean representing if the email has been seen
     */
    public boolean isSeen() {
        return seen.get();
    }

    /**
     * @param seen sets if the mail has been seen or not
     */
    public EmailBean setSeen(boolean seen) {
        this.seen.set(seen);
        return this;
    }

    public BooleanProperty seenProperty() {
        return seen;
    }

    /**
     * @return gets an arraylist of attachments
     */
    public ArrayList<FileAttachmentBean> getAttachments() {
        return attachments;
    }

    /**
     * @param attachments arraylist of attachments to send
     */
    public EmailBean setAttachments(ArrayList<FileAttachmentBean> attachments) {
        this.attachments = attachments;
        return this;
    }

    /**
     * @return string of message type
     */
    public String getMessageType() {
        return messageType.get();
    }

    /**
     * @param messageType string messgae type to set
     */
    public EmailBean setMessageType(String messageType) {
        this.messageType.set(messageType);
        return this;
    }

    /**
     * @return LocalDateTime of when object was sent
     */
    public LocalDateTime getSend() {
        return send.get();
    }

    /**
     * @param send LocalDateTime of the send to set
     */
    public void setSend(LocalDateTime send) {
        this.send.set(send);
    }

    public ObjectProperty<LocalDateTime> sendProperty() {
        return send;
    }

    /**
     * @return LocalDateTime of when object was recieved
     */
    public LocalDateTime getRecived() {
        return recived.get();
    }

    /**
     * @param recived set LocalDateTime of when object was received
     */
    public void setRecived(LocalDateTime recived) {
        this.recived.set(recived);
    }

    /**
     * @return string representing which folder email belonged to
     */
    public String getFolder() {
        return folder.get();
    }

    /**
     * @param folder string representing the name of folder to set
     */
    public EmailBean setFolder(String folder) {
        this.folder.set(folder);
        return this;
    }

    public StringProperty folderProperty() {
        return folder;
    }

    /**
     * @return Priorty of email
     */
    public Priority getPriority() {
        return priority.get();
    }

    /**
     * @param priority set priority of email using an integer
     */
    public void setPriority(int priority) {
        switch (priority) {
            case 0:
                this.priority.set(Priority.PRIORITY_LOWEST);
                break;
            case 1:
                this.priority.set(Priority.PRIORITY_LOW);
                break;
            case 2:
                this.priority.set(Priority.PRIORITY_NORMAL);
                break;
            case 3:
                this.priority.set(Priority.PRIORITY_HIGH);
                break;
            case 4:
                this.priority.set(Priority.PRIORITY_HIGHEST);
                break;
            default:
                this.priority.set(Priority.PRIORITY_NORMAL);
        }
    }

    /**
     * @param priority set the priority of email
     */
    public void setPriority(Priority priority) {
        this.priority.set(priority);
    }

    /**
     * Checks if two email objects are the same
     *
     * @param o Email bean that is being used to compare with other emailbean
     * @return boolean true if same, false if wrong
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailBean emailBean = (EmailBean) o;
        boolean work = Objects.equals(from.get().getEmail(), emailBean.from.get().getEmail());
        boolean work2 = emailEqual(to, emailBean.to);
        boolean work3 = emailEqual(cc, emailBean.cc);
        boolean work4 = subjectEquals(subject, emailBean.subject);
        boolean work5 = Objects.equals(message, emailBean.message);
        boolean work6 = Objects.equals(htmlMessage, emailBean.htmlMessage);
        boolean work7 = attachEqual(attachments, emailBean.attachments);
        return Objects.equals(from.get().getEmail(), emailBean.from.get().getEmail()) &&
                emailEqual(to, emailBean.to) &&
                emailEqual(cc, emailBean.cc) &&
                subjectEquals(subject, emailBean.subject) &&
                Objects.equals(message, emailBean.message) &&
                Objects.equals(htmlMessage, emailBean.htmlMessage) &&
                attachEqual(attachments, emailBean.attachments);
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
     * @param to   Arraylist of email addresses from emailbean
     * @param from Arraylist of email addresses from gmail
     * @return true if same, false if wronge
     */
    private boolean emailEqual(ArrayList<EmailAddress> to, ArrayList<EmailAddress> from) {
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
     * @param to   Arraylist of email addresses from emailbean
     * @param from Arraylist of email addresses from gmail
     * @return true if same, false if wronge
     */
    private boolean attachEqual(ArrayList<FileAttachmentBean> to, ArrayList<FileAttachmentBean> from) {
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

    public EmailBean setEmailID(int emailID) {
        this.emailID.set(emailID);
        return this;
    }

    public IntegerProperty emailIDProperty() {
        return emailID;
    }
}