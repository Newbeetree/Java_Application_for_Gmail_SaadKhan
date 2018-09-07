package data;

import java.io.File;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

/*
Question: [This question has come up more than once]
Also, for the E-mail JavaBeans, I would like to know if there is any reason why we are not using
those provided by jodd.mail. Is it because we want to be as independent from the library as possible (I am not sure)?

Answer:
The Email and CommonEmail classes in Jodd are not suitable for use as beans to represent a message
that will be stored in a database. This is why we need to read from and write to the Jodd classes from our beans.

Your email bean should contain the minimum data to represent an email. What we have seen so far is:
Email Addresses as Strings:
1 From
0…n To
0..n CC
0…n BCC
1 Subject
0..1 Test Message
0..1 HTML Message
0…n Attachments
0…n Embedded Attachments

1 and 0…1 means a single variable
0…n means an array

In addition you will need fields that allow you to identify what type of message, new, reply or
forward and fields that link to other messages when required. You will need send and receive times.

You will need the folder name the message belongs to. The message priority is another field.
There may be more and its your job to ensure that you have identified everything you will need.

In most cases the value for fields is defined by Jodd but remember that you cannot store objects in
the database so some Jodd objects may need to become strings or numbers.*/
public class EmailBean implements Serializable {

    private String from;
    private ArrayList<String> to;
    private ArrayList<String> cc;
    private ArrayList<String> bcc;
    private String subject;
    private String message;
    private String htmlMessage;
    private boolean seen;
    private ArrayList<byte[]> attachments;
    private ArrayList<byte[]> imbedAttachments;
    private String messageType;
    private Date send;
    private Date recived;
    private String folder;
    private Priority priority;

    public EmailBean() {
        from = "";
        to = new ArrayList<>();
        cc = new ArrayList<>();
        bcc = new ArrayList<>();
        subject = "";
        message = "";
        htmlMessage = "";
        seen = false;
        attachments = new ArrayList<>();
        imbedAttachments = new ArrayList<>();
        messageType = "";
        send = null;
        recived = null;
        folder = "";
        priority= Priority.LOW;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public ArrayList<String> getCc() {
        return cc;
    }

    public void setCc(ArrayList<String> cc) {
        this.cc = cc;
    }

    public ArrayList<String> getBcc() {
        return bcc;
    }

    public void setBcc(ArrayList<String> bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHtmlMessage() {
        return htmlMessage;
    }

    public void setHtmlMessage(String htmlMessage) {
        this.htmlMessage = htmlMessage;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public ArrayList<byte[]> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<byte[]> attachments) {
        this.attachments = attachments;
    }

    public ArrayList<byte[]> getImbedAttachments() {
        return imbedAttachments;
    }

    public void setImbedAttachments(ArrayList<byte[]> imbedAttachments) {
        this.imbedAttachments = imbedAttachments;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Date getSend() {
        return send;
    }

    public void setSend(Date send) {
        this.send = send;
    }

    public Date getRecived() {
        return recived;
    }

    public void setRecived(Date recived) {
        this.recived = recived;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}