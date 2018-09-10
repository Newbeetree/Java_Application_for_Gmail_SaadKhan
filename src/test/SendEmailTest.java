package test;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import buisness.EmailSender;
import buisness.ReceiveEmail;
import data.EmailBean;
import data.FileAttachmentBean;
import data.Priority;
import jodd.mail.EmailAddress;

public class SendEmailTest {
    private final String emailSend = "send.1633839@gmail.com";
    private final String emailReceive = "receive.1633839@gmail.com";
    private final String emailSendPwd = "sendpassword";
    private final String emailReceivePwd = "receivepassword";
    private final String emailCC1 = "other.1633839@gmail.com";

    @Test
    public void send() throws IOException {
        EmailSender es = new EmailSender(emailSend,emailSendPwd);
        EmailBean bean = setup();
        bean = setupAndAttachments(bean);
        es.send(bean,true);
    }
    @Test
    public void sendAndReceive() throws IOException {
        EmailSender es = new EmailSender(emailSend,emailSendPwd);
        EmailBean bean = setup();
        bean = setupAndAttachments(bean);
        bean = setupAndImbeddedAttachments(bean);
        es.send(bean,true);

        ReceiveEmail re = new ReceiveEmail(emailReceive,emailReceivePwd);
        EmailBean rbean = re.receiveEmail();
    }

    @Test
    public void sendWithAttachments() throws IOException {
        EmailSender es = new EmailSender(emailSend,emailSendPwd);
        EmailBean bean = setup();
        bean = setupAndAttachments(bean);
        bean = setupAndImbeddedAttachments(bean);
        es.send(bean,false);
    }

    private EmailBean setup() {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name",emailSend));
        bean.getTo().add(new EmailAddress("receiver",emailReceive));
        bean.getCc().add(new EmailAddress("other", emailCC1));
        bean.setMessage("hello testing 1 2 3");
        bean.setHtmlMessage("<html><META http-equiv=Content-Type "
                + "content=\"text/html; charset=utf-8\">"
                + "<body><h1>Here is my photograph embedded in "
                + "this email.</h1><img src='cid:WindsorKen180.jpg'>"
                + "<h2>I'm flying!</h2></body></html>");
        bean.setSubject("test7");
        bean.setPriority(Priority.PRIORITY_NORMAL);
        return bean;
    }

    private EmailBean setupAndAttachments(EmailBean bean) throws IOException {
        FileAttachmentBean fa = new FileAttachmentBean();
        fa.setName("FreeFall.jpg");
        fa.setFile(Files.readAllBytes(new File("FreeFall.jpg").toPath()));
        bean.getAttachments().add(fa);
        return bean;
    }
    private EmailBean setupAndImbeddedAttachments(EmailBean bean) throws IOException {
        FileAttachmentBean fa = new FileAttachmentBean();
        fa.setName("WindsorKen180.jpg");
        fa.setFile(Files.readAllBytes(new File("WindsorKen180.jpg").toPath()));
        bean.getImbedAttachments().add(fa);
        return bean;
    }
}