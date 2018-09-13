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

    public EmailBean receiveEmail() {
        EmailBean bean = new EmailBean();
        ImapServer imapServer = MailServer.create()
                .host(imapServerName)
                .ssl(true)
                .auth(receiveEmail, receivePassword)
                .debugMode(true)
                .buildImapMailServer();
        EmailBean[] beanArr;
        try (ReceiveMailSession session = imapServer.createSession()) {
            session.open();
            LOG.info("Message Count: " + session.getMessageCount());
            ReceivedEmail[] emails = session.receiveEmailAndMarkSeen(EmailFilter.filter().flag(Flags.Flag.SEEN, false));
            beanArr = new EmailBean[emails.length];
            System.out.println("***********************************************************");
            if (emails != null) {
                LOG.info("\n >>>> ReceivedEmail count = " + emails.length);
                for (ReceivedEmail email : emails) {
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
                    bean.setSubject(email.subject());
                    bean.setPriority(email.priority());
                    bean.setSend(LocalDateTime.ofInstant(email.sentDate().toInstant(), ZoneId.systemDefault()));
                    bean.setSend(LocalDateTime.ofInstant(email.receivedDate().toInstant(), ZoneId.systemDefault()));

                    // process messages
                    List<EmailMessage> messages = email.messages();

                    messages.stream().map((msg) -> {
                        LOG.info("------EmailMessage");
                        return msg;
                    }).map((msg) -> {
                        LOG.info("EmailMessage encoding: " + msg.getEncoding());
                        return msg;
                    }).map((msg) -> {
                        LOG.info("EmailMessage mime type " + msg.getMimeType());
                        return msg;
                    }).forEachOrdered((msg) -> {
                        LOG.info("EmailMessage content: " + msg.getContent());
                        if (msg.getMimeType().equals("TEXT/PLAIN"))
                            bean.setMessage(msg.getContent());
                        else
                            bean.setHtmlMessage(msg.getContent());
                    });

                    // process attachments
                    List<EmailAttachment<? extends DataSource>> attachments = email.attachments();
                    FileAttachmentBean fs = new FileAttachmentBean();
                    if (attachments != null) {
                        LOG.info("+++++");
                        attachments.stream().map((attachment) -> {
                            LOG.info("name: " + attachment.getName());
                            fs.setName(attachment.getName());
                            return attachment;
                        }).map((attachment) -> {
                            LOG.info("cid: " + attachment.getContentId());
                            return attachment;
                        }).map((attachment) -> {
                            LOG.info("size: " + attachment.getSize());
                            return attachment;
                        }).forEachOrdered((attachment) -> {
                            fs.setFile(attachment.toByteArray());
                            if (attachment.isEmbedded()) {
                                bean.getImbedAttachments().add(new FileAttachmentBean(fs.getFile(),fs.getName()));
                            }else {
                                bean.getAttachments().add(new FileAttachmentBean(fs.getFile(),fs.getName()));
                            }
                        });
                    }
                }
            }
        }
        return bean;
    }
}
