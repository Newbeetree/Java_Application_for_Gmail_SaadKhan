package buisness;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.Flags;

import data.EmailBean;
import jodd.mail.EmailAddress;
import jodd.mail.EmailAttachment;
import jodd.mail.EmailFilter;
import jodd.mail.EmailMessage;
import jodd.mail.ImapServer;
import jodd.mail.MailServer;
import jodd.mail.ReceiveMailSession;
import jodd.mail.ReceivedEmail;

public class ReceiveEmail {
    private final String imapServerName = "imap.gmail.com";
    private String receiveEmail;
    private String receivePassword;

    public ReceiveEmail(String receiveEmail, String receivePassword) {
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

        try (ReceiveMailSession session = imapServer.createSession()) {
            session.open();
            System.out.println(session.getMessageCount());
            ReceivedEmail[] emails = session.receiveEmailAndMarkSeen(EmailFilter.filter().flag(Flags.Flag.SEEN, false));
            System.out.println("***********************************************************");
            if (emails != null) {
                for (ReceivedEmail email : emails) {
                    System.out.println("\n\n===[" + email.messageNumber() + "]===");

                    // common info
                    System.out.println("FROM:" + email.from());
                    bean.setFrom(email.from());
                    System.out.println("TO:" + email.to()[0]);
                    bean.setTo(new ArrayList<EmailAddress>(Arrays.asList(email.to()[0])));
                    System.out.println("SUBJECT:" + email.subject());
                    bean.setSubject(email.subject());
                    System.out.println("PRIORITY:" + email.priority());
                    bean.setPriority(email.priority());
                    System.out.println("SENT DATE:" + email.sentDate());
                    bean.setSend(LocalDateTime.ofInstant(email.sentDate().toInstant(), ZoneId.systemDefault()));
                    System.out.println("RECEIVED DATE: " + email.receivedDate());
                    bean.setSend(LocalDateTime.ofInstant(email.receivedDate().toInstant(), ZoneId.systemDefault()));

                    // process messages
                    List<EmailMessage> messages = email.messages();

                    messages.stream().map((msg) -> {
                        System.out.println("------");
                        return msg;
                    }).map((msg) -> {
                        System.out.println(msg.getEncoding());
                        return msg;
                    }).map((msg) -> {
                        System.out.println(msg.getMimeType());
                        return msg;
                    }).forEachOrdered((msg) -> {
                        bean.setMessage(msg.getContent());
                        System.out.println(msg.getContent());
                    });

                    // process attachments
                    List<EmailAttachment<? extends DataSource>> attachments = email.attachments();
                    if (attachments != null) {
                        System.out.println("+++++");
                        attachments.stream().map((attachment) -> {
                            System.out.println("name: " + attachment.getName());
                            return attachment;
                        }).map((attachment) -> {
                            System.out.println("cid: " + attachment.getContentId());
                            return attachment;
                        }).map((attachment) -> {
                            System.out.println("size: " + attachment.getSize());
                            return attachment;
                        }).forEachOrdered((attachment) -> {
                            attachment.writeToFile(
                                    new File("c:\\temp", attachment.getName()));
                        });
                    }
                }
            }
        }
        return bean;
    }
}
