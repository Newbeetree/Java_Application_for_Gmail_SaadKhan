package com.saadkhan.buisness;

import com.saadkhan.data.ConfigurationFxBean;
import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;
import com.saadkhan.manager.PropertiesManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Date;
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

import static java.nio.file.Paths.get;

/**
 * This class is meant to take in EmailBeans and convert them into Jodd Email Objects
 * to be sent through to a gmail account
 *
 * @Author: Saad Khan, 1633839
 */
public class EmailSender {

    private final static Logger LOG = LoggerFactory.getLogger(EmailSender.class);
    private String smtpServerName;
    private String userEmail;
    private String userPassword;

    /**
     * Constructs an Email Sender when given proper Usernames and Passwords of a gmail account
     * in order to be able to send emails from said account
     */
    public EmailSender() {
        getConfigValues();
    }

    private void getConfigValues() {
        try {
            PropertiesManager pm = new PropertiesManager();
            Path txtFile = get("", "JAGConfig.properties");
            ConfigurationFxBean cfb = pm.getConfBeanSettings(txtFile);
            this.userEmail = cfb.getUserEmailAddress();
            this.userPassword = cfb.getUserPassword();
            this.smtpServerName = cfb.getSMTPServer();
        } catch (IOException e) {
            LOG.error("file not found");
        }
        this.smtpServerName = "smtp.gmail.com";
    }

    /**
     * Takes an Email bean that the user provides creates a Jodd Email out of said bean.
     * Validates bean as well as checks if the user has requested any atachments to be added to said email
     * Creates a Server and also an Exception before sending the email
     *
     * @param sendingEmail Email Bean which will be validate and turned into a Jodd email for sending
     */
    public void send(EmailBean sendingEmail, boolean noAttachments) {
        // Create am SMTP server object
        SmtpServer smtpServer = MailServer.create()
                .ssl(true)
                .host(smtpServerName)
                .auth(userEmail, userPassword)
                .buildSmtpMailServer();
        //create a valid jodd email object
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
     * Creates a jodd email using a bean and an option to specify if attachments are needed as well
     * as validate if the EmailBean is alright. Throws an error if the EmailBean is invalid.
     *
     * @param sendingEmail the bean of the email that will be validated and turned into a jodd
     * @param option       selects whether or not the email needs attachments that will be added to the email
     * @return Email returns a valid jodd email out of the Eamil Bean
     * @throws IllegalArgumentException In the case of bean being invalid throw the exception
     */
    private Email convertBeanToJodd(EmailBean sendingEmail, boolean option) throws IllegalArgumentException {
        Email email;
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

            email = option ? email : addAtttachments(email, sendingEmail.getAttachments());
        } else {
            LOG.error("Validation error");
            throw new IllegalArgumentException("Email has illegal arguments");
        }
        return email;
    }

    /**
     * Takes an Email bean and validates to, from, name, cc and bcc fields as well as make sure
     * html and message are not null, if they are however throw an error
     *
     * @param bean email bean to be converted
     * @return boolean  if the email is valid returns true or false
     */
    private boolean validateBean(EmailBean bean) throws IllegalArgumentException {
        boolean o = checkEmail(bean.getFrom().getEmail());
        boolean jo = checkListEmail(bean.getTo().toArray(new EmailAddress[0]));
        boolean ho = checkListEmail(bean.getCc().toArray(new EmailAddress[0]));
        boolean g= checkEmailName(bean.getFrom().getPersonalName());
        boolean go = checkListEmail(bean.getBcc().toArray(new EmailAddress[0]));
        boolean fo = bean.getHtmlMessage() != null;
        boolean or=bean.getMessage() != null;
        return checkEmail(bean.getFrom().getEmail()) &&
                checkEmailName(bean.getFrom().getPersonalName()) &&
                checkListEmail(bean.getTo().toArray(new EmailAddress[0])) &&
                checkListEmail(bean.getCc().toArray(new EmailAddress[0])) &&
                checkListEmail(bean.getBcc().toArray(new EmailAddress[0])) &&
                bean.getHtmlMessage() != null &&
                bean.getMessage() != null;
    }


    /**
     * Takes an email, a list of attachments iterates through the selected
     * array and adds the attachments to the jodd
     */
    private Email addAtttachments(Email email, ArrayList<FileAttachmentBean> attachments) {
        for (FileAttachmentBean attachment : attachments) {
            if (!attachment.getType()) {
                email.attachment(EmailAttachment.with().content(attachment.getFile()).name(attachment.getName()));
            } else {
                email.embeddedAttachment(EmailAttachment.with().content(attachment.getFile()).name(attachment.getName()));
            }
        }
        return email;
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

    /**
     * Use a regex expression to make sure name is alright and not strange or has any symbols
     *
     * @return true is OK, false if not
     */
    private boolean checkEmailName(String personalName) {
        String regx ="[A-Za-z0-9]";// "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(personalName);
        return matcher.find();
    }

    /**
     * takes an array of email address and checks if each email has a proper email address and name
     *
     * @param list an array containing email adresses
     * @return true if all are valid
     * @throws IllegalArgumentException throws the exception in case of invalid email
     */
    private boolean checkListEmail(EmailAddress[] list) throws IllegalArgumentException {
        for (EmailAddress email : list) {
            if (!checkEmail(email.getEmail()) || !checkEmailName(email.getPersonalName()))
                throw new IllegalArgumentException("Invalid Email");
        }
        return true;
    }
}
