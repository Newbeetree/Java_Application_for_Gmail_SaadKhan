package data;

import java.io.Serializable;
import java.util.List;

public class EmailBean implements Serializable {

    private String from;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String message;
    private String  htmlMessage;
    private List<?> attachments;
    private List<?> imbedAttachments;

    public EmailBean(){}


    public String getFrom() {
        return from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(final String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(final String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(final String[] bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getHtmlMessage() {
        return htmlMessage;
    }

    public void setHtmlMessage(final String htmlMessage) {
        this.htmlMessage = htmlMessage;
    }

    public List<?> getAttachments() {
        return attachments;
    }

    public void setAttachments(final List<?> attachments) {
        this.attachments = attachments;
    }

    public List<?> getImbedAttachments() {
        return imbedAttachments;
    }

    public void setImbedAttachments(final List<?> imbedAttachments) {
        this.imbedAttachments = imbedAttachments;
    }
}
