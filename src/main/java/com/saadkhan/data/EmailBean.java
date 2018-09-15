package com.saadkhan.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import jodd.mail.EmailAddress;

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
    private ArrayList<FileAttachmentBean> imbedAttachments;
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
        attachments = new ArrayList<FileAttachmentBean>();
        imbedAttachments = new ArrayList<FileAttachmentBean>();
        messageType = "";
        send = LocalDateTime.now();
        recived = LocalDateTime.now();
        folder = "";
        priority = Priority.PRIORITY_NORMAL;
    }

    public boolean emailEqual(ArrayList<EmailAddress> to, ArrayList<EmailAddress> from){
        for (int i = 0; i < to.size(); i++) {
            if(!(to.get(i).getEmail().equals(from.get(i).getEmail()))){
                return false;
            }
        }
        return true;
    }
    public boolean imbedEqual(ArrayList<FileAttachmentBean> to, ArrayList<FileAttachmentBean> from){
        for (int i = 0; i < to.size(); i++) {
            if(!(Arrays.equals(to.get(0).getFile(),from.get(0).getFile()))){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailBean emailBean = (EmailBean) o;
        boolean work = Objects.equals(from.getEmail(), emailBean.from.getEmail());
        boolean work2 = emailEqual(to,emailBean.to);
        boolean work3 = emailEqual(cc,emailBean.cc) ;
        boolean work4 = Objects.equals(subject, emailBean.subject);
        boolean work5=  Objects.equals(message, emailBean.message);
        boolean work6=  Objects.equals(htmlMessage, emailBean.htmlMessage);
        boolean work7 = imbedEqual(attachments,emailBean.attachments);
        boolean work8  = imbedEqual(imbedAttachments, emailBean.imbedAttachments);
        return Objects.equals(from.getEmail(), emailBean.from.getEmail()) &&
                emailEqual(to,emailBean.to) &&
                emailEqual(cc,emailBean.cc) &&
                Objects.equals(subject, emailBean.subject) &&
                Objects.equals(message, emailBean.message) &&
                Objects.equals(htmlMessage, emailBean.htmlMessage) &&
                imbedEqual(attachments, emailBean.attachments) &&
                imbedEqual(imbedAttachments, emailBean.imbedAttachments);
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

    public ArrayList<FileAttachmentBean> getAttachments() {
        return attachments;
    }

    public EmailBean setAttachments(ArrayList<FileAttachmentBean> attachments) {
        this.attachments = attachments;
        return this;
    }


    public ArrayList<FileAttachmentBean> getImbedAttachments() {
        return imbedAttachments;
    }

    public EmailBean setImbedAttachments(ArrayList<FileAttachmentBean> imbedAttachments) {
        this.imbedAttachments = imbedAttachments;
        return this;
    }

    public void addImbedAttatchments(FileAttachmentBean attachment) {
        this.imbedAttachments.add(attachment);
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