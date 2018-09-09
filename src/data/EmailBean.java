package data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import jodd.mail.EmailAddress;

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

    private EmailAddress from;
    private ArrayList<EmailAddress> to;
    private ArrayList<EmailAddress> cc;
    private ArrayList<EmailAddress> bcc;
    private String subject;
    private String message;
    private String htmlMessage;
    private boolean seen;
    private ArrayList<FileAttachment> attachments;
    private ArrayList<FileAttachment> imbedAttachments;
    private String messageType;
    private LocalDateTime send;
    private LocalDateTime recived;
    private String folder;
    private Priority priority;

    public EmailBean() {
        from = null;
        to = new ArrayList<EmailAddress>();
        cc = new ArrayList<EmailAddress>();
        bcc = new ArrayList<EmailAddress>();
        subject = "";
        message = "";
        htmlMessage = "";
        seen = false;
        attachments = new ArrayList<FileAttachment>();
        imbedAttachments = new ArrayList<FileAttachment>();
        messageType = "";
        send = null;
        recived = null;
        folder = "";
        priority = Priority.PRIORITY_NORMAL;
    }

    public EmailAddress getFrom() {
        return from;
    }

    public EmailBean setFrom(EmailAddress from) {
        this.from = from;
        return this;
    }

    public ArrayList<EmailAddress> getTo() {
        return to;
    }

    public EmailBean setTo(ArrayList<EmailAddress> to) {
        this.to = to;
        return this;
    }

    public ArrayList<EmailAddress> getCc() {
        return cc;
    }

    public EmailBean setCc(ArrayList<EmailAddress> cc) {
        this.cc = cc;
        return this;
    }

    public ArrayList<EmailAddress> getBcc() {
        return bcc;
    }

    public EmailBean setBcc(ArrayList<EmailAddress> bcc) {
        this.bcc = bcc;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public EmailBean setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public EmailBean setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getHtmlMessage() {
        return htmlMessage;
    }

    public EmailBean setHtmlMessage(String htmlMessage) {
        this.htmlMessage = htmlMessage;
        return this;
    }

    public boolean isSeen() {
        return seen;
    }

    public EmailBean setSeen(boolean seen) {
        this.seen = seen;
        return this;
    }

    public ArrayList<FileAttachment> getAttachments() {
        return attachments;
    }

    public EmailBean setAttachments(ArrayList<FileAttachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public ArrayList<FileAttachment> getImbedAttachments() {
        return imbedAttachments;
    }

    public EmailBean setImbedAttachments(ArrayList<FileAttachment> imbedAttachments) {
        this.imbedAttachments = imbedAttachments;
        return this;
    }

    public String getMessageType() {
        return messageType;
    }

    public EmailBean setMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public LocalDateTime getSend() {
        return send;
    }

    public EmailBean setSend(LocalDateTime send) {
        this.send = send;
        return this;
    }

    public LocalDateTime getRecived() {
        return recived;
    }

    public EmailBean setRecived(LocalDateTime recived) {
        this.recived = recived;
        return this;
    }

    public String getFolder() {
        return folder;
    }

    public EmailBean setFolder(String folder) {
        this.folder = folder;
        return this;
    }

    public Priority getPriority() {
        return priority;
    }

    public EmailBean setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public Priority setPriority(int priority) {
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
        }
        return this.priority;
    }
}