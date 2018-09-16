package com.saadkhan.buisness;

import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jodd.mail.Email;
import jodd.mail.EmailAddress;
import jodd.mail.EmailAttachment;
import jodd.mail.MailServer;
import jodd.mail.RFC2822AddressParser;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;

public class EmailSender {

    private final static Logger LOG = LoggerFactory.getLogger(EmailSender.class);

    private final String smtpServerName = "smtp.gmail.com";
    private String userEmail;
    private String userPassword;

    /**
     *
     * @param userEmail
     * @param userPassword
     */
    public EmailSender(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    /**
     * @param noAttachments : option indicates whether or not we want to include attachments
     */
    public void send(EmailBean sendingEmail, boolean noAttachments) throws IllegalAccessException, IllegalAccessException {
        // Create am SMTP server object
        SmtpServer smtpServer = MailServer.create()
                .ssl(true)
                .host(smtpServerName)
                .auth(userEmail, userPassword)
                .debugMode(true)
                .buildSmtpMailServer();

        Email email = convertBeanToJodd(sendingEmail, noAttachments);
        try (
                // A session is the object responsible for communicating with the server
                SendMailSession session = smtpServer.createSession()) {

            // open the session, send the message and close the session
            session.open();
            session.sendMail(email);
            LOG.info("Email sent");
        }
    }

    /**
     * creates a jodd email using a bean and an option to specify if attachments are needed
     *
     * @return Email
     */
    private Email convertBeanToJodd(EmailBean sendingEmail, boolean option) throws IllegalAccessException {
        Email email = null;
        if (validateBean(sendingEmail)) {
            email = Email.create()
                    .from(sendingEmail.getFrom())
                    .to(sendingEmail.getTo().toArray(new EmailAddress[0]))
                    .cc(sendingEmail.getCc().toArray(new EmailAddress[0]))
                    .bcc(sendingEmail.getBcc().toArray(new EmailAddress[0]))
                    .subject(sendingEmail.getSubject())
                    .sentDate(Date.from(sendingEmail.getSend().toInstant(ZoneOffset.UTC)))
                    .textMessage(sendingEmail.getMessage())
                    .htmlMessage(sendingEmail.getHtmlMessage())
                    .priority(sendingEmail.getPriority().getValue());

            email = option ? email : addAtttachments(email, sendingEmail.getAttachments(), true);
            email = option ? email : addAtttachments(email, sendingEmail.getImbedAttachments(), false);
        } else {
            LOG.error("Validation error");
            throw new IllegalAccessException("Email has illegal arguements");
        }
        return email;
    }

    /**
     *
     * @param email
     * @param attachments
     * @param attachmentType
     * @return
     */
    private Email addAtttachments(Email email, ArrayList<FileAttachmentBean> attachments, boolean attachmentType) {
        if (attachmentType) {
            for (FileAttachmentBean fab : attachments) {
                email.attachment(EmailAttachment.with().content(fab.getFile()).name(fab.getName()));
            }
        } else {
            for (FileAttachmentBean fab : attachments) {
                email.embeddedAttachment(EmailAttachment.with().content(fab.getFile()).name(fab.getName()));
            }
        }
        return email;
    }


    private boolean validateBean(EmailBean bean) throws IllegalAccessException {
        if (checkEmail(bean.getFrom().getEmail()) &&
                checkEmailName(bean.getFrom().getPersonalName()) &&
                checkListEmail(bean.getTo().toArray(new EmailAddress[0])) &&
                checkListEmail(bean.getCc().toArray(new EmailAddress[0])) &&
                checkListEmail(bean.getBcc().toArray(new EmailAddress[0])) &&
                bean.getHtmlMessage() != null &&
                bean.getMessage() != null) {
            return true;
        } else
            return false;
    }

    /**
     * Use the RFC2822AddressParser to validate that the email string could be a
     * valid address
     *
     * @return true is OK, false if not
     */
    private boolean checkEmail(String address) {
        return RFC2822AddressParser.STRICT.parseToEmailAddress(address) != null;
    }

    private boolean checkEmailSentDate(LocalDateTime date) {
        return (!date.equals(null));
    }

    private boolean checkEmailName(String personalName) {
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(personalName);
        return matcher.find();
    }

    private boolean checkListEmail(EmailAddress[] list) throws IllegalAccessException {
        for (EmailAddress email : list) {
            if (!checkEmail(email.getEmail()) || !checkEmailName(email.getPersonalName()))
                throw new IllegalAccessException("Invalid Email");
        }
        return true;
    }
}
