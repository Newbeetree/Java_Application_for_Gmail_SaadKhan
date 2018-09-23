package com.saadkhan.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import jodd.mail.EmailAddress;

/**
 * EmailBean is a bean class that is serializable and has basic and getters for most functions
 *
 * @Author: Saad Khan 1633839
 */
public class EmailBean implements Serializable {

    private EmailAddress from;
    private ArrayList<EmailAddress> to;
    private ArrayList<EmailAddress> cc;
    private ArrayList<EmailAddress> bcc;
    private String subject;
    private String message;
    private String htmlMessage;
    private boolean seen;
    private ArrayList<FileAttachmentBean> attachments;
    private String messageType;
    private LocalDateTime send;
    private LocalDateTime recived;
    private String folder;
    private Priority priority;

    /**
     * Constructor sets all values of the email bean to the defualt values
     */
    public EmailBean() {
        from = null;
        to = new ArrayList<>();
        cc = new ArrayList<>();
        bcc = new ArrayList<>();
        subject = "";
        message = "";
        htmlMessage = "";
        seen = false;
        attachments = new ArrayList<>();
        messageType = "";
        send = LocalDateTime.now();
        recived = LocalDateTime.now();
        folder = "";
        priority = Priority.PRIORITY_NORMAL;
    }

    /**
     * @return email address of from
     */
    public EmailAddress getFrom() {
        return from;
    }

    /**
     * @param from email address to set
     */
    public EmailBean setFrom(EmailAddress from) {
        this.from = from;
        return this;
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
        return subject;
    }

    /**
     * @param subject set the string for subject
     */
    public EmailBean setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * @return string for the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message message to set
     */
    public EmailBean setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * @return html message to get
     */
    public String getHtmlMessage() {
        return htmlMessage;
    }

    /**
     * @param htmlMessage html to set
     */
    public EmailBean setHtmlMessage(String htmlMessage) {
        this.htmlMessage = htmlMessage;
        return this;
    }

    /**
     * @return boolean representing if the email has been seen
     */
    public boolean isSeen() {
        return seen;
    }

    /**
     * @param seen sets if the mail has been seen or not
     */
    public EmailBean setSeen(boolean seen) {
        this.seen = seen;
        return this;
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
        return messageType;
    }

    /**
     * @param messageType string messgae type to set
     */
    public EmailBean setMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    /**
     * @return LocalDateTime of when object was sent
     */
    public LocalDateTime getSend() {
        return send;
    }

    /**
     * @param send LocalDateTime of the send to set
     */
    public void setSend(LocalDateTime send) {
        this.send = send;
    }

    /**
     * @return LocalDateTime of when object was recieved
     */
    public LocalDateTime getRecived() {
        return recived;
    }

    /**
     * @param recived set LocalDateTime of when object was received
     */
    public void setRecived(LocalDateTime recived) {
        this.recived = recived;
    }

    /**
     * @return string representing which folder email belonged to
     */
    public String getFolder() {
        return folder;
    }

    /**
     * @param folder string representing the name of folder to set
     */
    public EmailBean setFolder(String folder) {
        this.folder = folder;
        return this;
    }

    /**
     * @return Priorty of email
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param priority set the priority of email
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * @param priority set priority of email using an integer
     */
    public void setPriority(int priority) {
        switch (priority) {
            case 0:
                this.priority = Priority.PRIORITY_LOWEST;
                break;
            case 1:
                this.priority = Priority.PRIORITY_LOW;
                break;
            case 2:
                this.priority = Priority.PRIORITY_NORMAL;
                break;
            case 3:
                this.priority = Priority.PRIORITY_HIGH;
                break;
            case 4:
                this.priority = Priority.PRIORITY_HIGHEST;
                break;
            default:
                this.priority = Priority.PRIORITY_NORMAL;
        }
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
        boolean work = Objects.equals(from.getEmail(), emailBean.from.getEmail());
        boolean work2 = emailEqual(to, emailBean.to);
        boolean work3 = emailEqual(cc, emailBean.cc);
        boolean work4 = subjectEquals(subject, emailBean.subject);
        boolean work5 = Objects.equals(message, emailBean.message);
        boolean work6 = Objects.equals(htmlMessage, emailBean.htmlMessage);
        boolean work7 = attachEqual(attachments, emailBean.attachments);
        return Objects.equals(from.getEmail(), emailBean.from.getEmail()) &&
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
    private boolean subjectEquals(String toSubject, String fromSubject) {
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
                getRecived(), getFolder(), getPriority());
    }
}