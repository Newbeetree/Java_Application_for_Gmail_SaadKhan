package com.saadkhan.buisness;

import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class EmailReceiver {
    private final static Logger LOG = LoggerFactory.getLogger(EmailSender.class);
    private final String imapServerName = "imap.gmail.com";
    private String receiveEmail;
    private String receivePassword;

    public EmailReceiver(String receiveEmail, String receivePassword) {
        this.receiveEmail = receiveEmail;
        this.receivePassword = receivePassword;
    }

    public EmailBean[] receiveEmail() {
        ImapServer imapServer = MailServer.create()
                .host(imapServerName)
                .ssl(true)
                .auth(receiveEmail, receivePassword)
                .debugMode(true)
                .buildImapMailServer();

        ArrayList<EmailBean> beanArrayList = new ArrayList<>();
        EmailBean bean;

        try (ReceiveMailSession session = imapServer.createSession()) {
            session.open();
            LOG.info("Message Count: " + session.getMessageCount());
            ReceivedEmail[] emails = session.receiveEmailAndMarkSeen(EmailFilter.filter().flag(Flags.Flag.SEEN, false));
            System.out.println("***********************************************************");
            if (emails != null) {
                LOG.info("\n >>>> ReceivedEmail count = " + emails.length);
                for (ReceivedEmail email : emails) {
                    bean = new EmailBean();
                    LOG.info("===[" + email.messageNumber() + "]===");

                    // common info
                    LOG.info("FROM:" + email.from());
                    LOG.info("TO:" + email.to()[0]);
                    LOG.info("SUBJECT:" + email.subject());
                    LOG.info("PRIORITY:" + email.priority());
                    LOG.info("SENT DATE:" + email.sentDate());
                    LOG.info("RECEIVED DATE: " + email.receivedDate());
                    bean.setFrom(email.from());
                    bean.setTo(new ArrayList<EmailAddress>(Arrays.asList(email.to()[0])));
                    bean.setCc(new ArrayList<EmailAddress>(Arrays.asList(email.cc()[0])));
                    bean.setSubject(email.subject());
                    bean.setPriority(email.priority());
                    bean.setSend(LocalDateTime.ofInstant(email.sentDate().toInstant(), ZoneId.systemDefault()));
                    bean.setSend(LocalDateTime.ofInstant(email.receivedDate().toInstant(), ZoneId.systemDefault()));

                    // process messages
                    List<EmailMessage> messages = email.messages();
                    for (EmailMessage message : messages) {
                        LOG.info("EmailMessage content: " + message.getContent());
                        if (message.getMimeType().equals("TEXT/PLAIN"))
                            bean.setMessage(message.getContent());
                        else
                            bean.setHtmlMessage(message.getContent());
                    }

                    // process attachments
                    List<EmailAttachment<? extends DataSource>> attachments = email.attachments();
                    FileAttachmentBean fs = new FileAttachmentBean();
                    if (attachments != null) {
                        for (EmailAttachment<? extends DataSource> attachment : attachments) {
                            LOG.info("+++++");
                            LOG.info("name: " + attachment.getName());
                            fs.setName(attachment.getName());
                            LOG.info("cid: " + attachment.getContentId());
                            LOG.info("size: " + attachment.getSize());
                            fs.setFile(attachment.toByteArray());
                            if (attachment.isEmbedded()) {
                                bean.getImbedAttachments().add(new FileAttachmentBean(fs.getFile(), fs.getName()));
                            } else {
                                bean.getAttachments().add(new FileAttachmentBean(fs.getFile(), fs.getName()));
                            }
                        }
                    }
                    beanArrayList.add(bean);
                }
            }
        }
        return beanArrayList.toArray(new EmailBean[0]);
    }
}
