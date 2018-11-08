package com.saadkhan.buisness;

import com.saadkhan.data.ConfigurationFxBean;
import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;
import com.saadkhan.manager.PropertiesManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.Flags;

import jodd.mail.EmailAddress;
import jodd.mail.EmailAttachment;
import jodd.mail.EmailFilter;
import jodd.mail.EmailMessage;
import jodd.mail.ImapServer;
import jodd.mail.MailServer;
import jodd.mail.ReceiveMailSession;
import jodd.mail.ReceivedEmail;

import static java.nio.file.Paths.get;

/**
 * This class is meant to check gmail servers for unread emails and  return all that are unread
 *
 * @Author: Saad Khan, 1633839
 */
public class EmailReceiver {
    private final static Logger LOG = LoggerFactory.getLogger(EmailSender.class);
    private String receiveEmail;
    private String receivePassword;

    /**
     * Constructs an EmailReciever when given proper Usernames and Passwords of a gmail account
     * that received emails in order to be able to check all unread emails
     */
    public EmailReceiver() {
        getConfigValues();
    }

    /**
     * create a Property manager object and retain and set values
     */
    private void getConfigValues() {
        try {
            PropertiesManager pm = new PropertiesManager();
            Path txtFile = get("", "JAGConfig.properties");
            ConfigurationFxBean cfb = pm.getConfBeanSettings(txtFile);
            this.receiveEmail = cfb.getUserEmailAddress();
            this.receivePassword = cfb.getUserPassword();
        } catch (IOException e) {
            LOG.error("file not found");
        }
    }

    /**
     * Opens a server connection with gmail and recieves all emails marking those that are
     * unread as read and returns an array of said unread emails
     *
     * @return EmailBean[] returns an array of emails that were recieved from the server
     */
    public EmailBean[] receiveEmail() {
        String imapServerName = "imap.gmail.com";
        ImapServer imapServer = MailServer.create()
                .host(imapServerName)
                .ssl(true)
                .auth(receiveEmail, receivePassword)
                .buildImapMailServer();

        ArrayList<EmailBean> beanArrayList = new ArrayList<>();
        EmailBean bean;

        try (ReceiveMailSession session = imapServer.createSession()) {
            session.open();
            LOG.info("Message Count: " + session.getMessageCount());
            ReceivedEmail[] emails = session.receiveEmailAndMarkSeen(EmailFilter.filter().flag(Flags.Flag.SEEN, false));
            if (emails != null) {
                LOG.info("\n >>>> ReceivedEmail count = " + emails.length);
                for (ReceivedEmail email : emails) {
                    bean = setBean(email);
                    beanArrayList.add(bean);
                }
            }
        }
        return beanArrayList.toArray(new EmailBean[0]);
    }

    /**
     * Useing a received email set and return and email bean
     */
    private EmailBean setBean(ReceivedEmail email) {
        EmailBean bean = new EmailBean();
        LOG.info("===[" + email.messageNumber() + "]===");

        // common info
        setCommonFields(bean, email);

        // process messages
        setMessages(bean, email);

        // process attachments
        setAttachments(bean, email);

        return bean;
    }

    /**
     * takes the bean and email sets from, to, subject, priority and date from email into a bean
     */
    private void setCommonFields(EmailBean bean, ReceivedEmail email) {
        LOG.info("FROM:" + email.from());
        LOG.info("TO:" + email.to()[0]);
        LOG.info("SUBJECT:" + email.subject());
        LOG.info("PRIORITY:" + email.priority());
        LOG.info("SENT DATE:" + email.sentDate());
        LOG.info("RECEIVED DATE: " + email.receivedDate());

        bean.setFrom(email.from());
        bean.setTo(new ArrayList<EmailAddress>(Arrays.asList(email.to()[0])));
        if (email.cc().length > 0)
            bean.setCc(new ArrayList<EmailAddress>(Arrays.asList(email.cc()[0])));
        bean.setSubject(email.subject());
        bean.setPriority(email.priority());
        bean.setSend(LocalDateTime.ofInstant(email.sentDate().toInstant(), ZoneId.systemDefault()));
        bean.setRecived(LocalDateTime.ofInstant(email.receivedDate().toInstant(), ZoneId.systemDefault()));
    }

    /**
     * takes a bean and email and setts the beans attachment field by iterating through the attachments
     * and setting the File atachment bean based on type
     */
    private void setAttachments(EmailBean bean, ReceivedEmail email) {
        List<EmailAttachment<? extends DataSource>> attachments = email.attachments();
        FileAttachmentBean fs = new FileAttachmentBean();
        if (attachments != null) {
            for (EmailAttachment<? extends DataSource> attachment : attachments) {
                LOG.info("+++++");
                LOG.info("name: " + attachment.getName());
                LOG.info("size: " + attachment.getSize());
                fs.setName(attachment.getName());
                fs.setFile(attachment.toByteArray());
                fs.setType(attachment.isEmbedded());
                bean.getAttachments().add(new FileAttachmentBean(fs.getAttachID(), fs.getFile(), fs.getName(), fs.getType()));
            }
        }
    }

    /**
     * iterates through the email and sets the beans html or message fields depending on the the mime type
     */
    private void setMessages(EmailBean bean, ReceivedEmail email) {
        List<EmailMessage> messages = email.messages();
        for (EmailMessage message : messages) {
            LOG.info("EmailMessage content: " + message.getContent());
            if (message.getMimeType().equals("TEXT/PLAIN"))
                bean.setMessage(message.getContent());
            else
                bean.setHtmlMessage(message.getContent());
        }
    }

}
