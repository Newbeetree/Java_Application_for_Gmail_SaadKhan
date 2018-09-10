package buisness;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.Flags;

import data.EmailBean;
import data.FileAttachmentBean;
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
                    bean.setFrom(email.from());
                    bean.setTo(new ArrayList<EmailAddress>(Arrays.asList(email.to()[0])));
                    bean.setSubject(email.subject());
                    bean.setPriority(email.priority());
                    bean.setSend(LocalDateTime.ofInstant(email.sentDate().toInstant(), ZoneId.systemDefault()));
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
                        if (msg.getMimeType().equals("TEXT/PLAIN"))
                            bean.setMessage(msg.getContent());
                        else
                            bean.setHtmlMessage(msg.getContent());
                    });

                    // process attachments
                    List<EmailAttachment<? extends DataSource>> attachments = email.attachments();
                    FileAttachmentBean fs = new FileAttachmentBean();
                    if (attachments != null) {
                        System.out.println("+++++");
                        attachments.stream().map((attachment) -> {
                            System.out.println("name: " + attachment.getName());
                            fs.setName(attachment.getName());
                            return attachment;
                        }).map((attachment) -> {
                            System.out.println("cid: " + attachment.getContentId());
                            return attachment;
                        }).map((attachment) -> {
                            System.out.println("size: " + attachment.getSize());
                            return attachment;
                        }).forEachOrdered((attachment) -> {
                            fs.setFile(attachment.toByteArray());
                            if (attachment.isEmbedded())
                                bean.addImbedAttatchments(fs);
                            else
                                bean.addAttachments(fs);
                        });
                    }
                }
            }
        }
        return bean;
    }
}
