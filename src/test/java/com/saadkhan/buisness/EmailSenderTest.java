package com.saadkhan.buisness;

import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;
import com.saadkhan.data.Priority;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;

import jodd.mail.EmailAddress;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * Class for testing emails sending and receiving
 */
public class EmailSenderTest {
    private final String emailSend = "send.1633839@gmail.com";
    private final String emailReceive = "receive.1633839@gmail.com";
    private final String emailSendPwd = "sendpassword";
    private final String emailReceivePwd = "receivepassword";
    private final String emailCC1 = "other.1633839@gmail.com";
    private EmailSender es = new EmailSender(emailSend, emailSendPwd, "smtp.gmail.com");
    private EmailReceiver re = new EmailReceiver(emailReceive, emailReceivePwd);


    /**
     * Send an email with all fields to, from, cc,bcc,subject, attachments, imbued, message and html
     */
    @Test
    public void sendAndReceiveWithAllFields() throws IOException {
        EmailBean bean = createBasicBean();
        bean = addAttachments(bean);
        bean = addImbeddedAttachments(bean);
        es.send(bean, false);
        delay();
        EmailBean[] rbean = re.receiveEmail();
        assertEquals(bean, rbean[0]);
    }

    /**
     * Send an email with an invalid to address and catches the error
     */
    @Test(expected = IllegalArgumentException.class)
    public void sendInvalidTo() {
        EmailBean bean = createBasicBean();
        bean.setTo(new ArrayList<EmailAddress>());
        bean.getTo().add(new EmailAddress("invalid Email", "hfagdsgjfhakjdsfafdddasd"));
        es.send(bean, true);
        fail("Sent Invalid TO");
    }

    /**
     * Send an Email with an invalid CC and catch the error
     */
    @Test(expected = IllegalArgumentException.class)
    public void sendInvalidCC() {
        EmailBean bean = createBasicBean();
        bean.setCc(new ArrayList<EmailAddress>());
        bean.getCc().add(new EmailAddress("invalid CC Email", "hfagdsgjfhakjdsfafdddasd"));
        es.send(bean, true);
        fail("Sent Invalid CC");
    }

    /**
     * Send an amail with a null subject and recive it to make sure it is correct
     */
    @Test
    public void sendNullSubject() {
        EmailBean bean = createBasicBean();
        bean.setSubject(null);
        es.send(bean, true);
        delay();
        EmailBean[] rbean = re.receiveEmail();
        assertEquals(bean, rbean[0]);
    }

    /**
     * send and receive an email with only a message set
     */
    @Test
    public void sendOnlyMessage() {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", emailSend));
        bean.getTo().add(new EmailAddress("receiver", emailReceive));
        bean.setMessage("Hi im only sending a message");
        es.send(bean, true);
        delay();
        EmailBean[] rbean = re.receiveEmail();
        assertEquals(bean, rbean[0]);
    }

    /**
     * send and receive an email with only html set
     */
    @Test
    public void sendOnlyHtml() {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", emailSend));
        bean.getTo().add(new EmailAddress("receiver", emailReceive));
        bean.setHtmlMessage("<html><body><h1>Only a Html message</h1></body></html>");
        es.send(bean, true);
        delay();
        EmailBean[] rbean = re.receiveEmail();
        assertEquals(bean, rbean[0]);
    }

    /**
     * send and receive an email with only attachments set
     */
    @Test
    public void sendOnlyAttachments() throws IOException {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", emailSend));
        bean.getTo().add(new EmailAddress("receiver", emailReceive));
        bean = addAttachments(bean);
        es.send(bean, false);
        delay();
        EmailBean[] rbean = re.receiveEmail();
        assertEquals(bean, rbean[0]);
    }

    /**
     * send an email with nothing set
     */
    @Test
    public void sendNothing() {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", emailSend));
        bean.getTo().add(new EmailAddress("receiver", emailReceive));
        es.send(bean, true);
        delay();
        EmailBean[] rbean = re.receiveEmail();
        assertEquals(bean, rbean[0]);
    }

    /**
     * send an email with html field set to null
     */
    @Test(expected = IllegalArgumentException.class)
    public void sendHtmlNull() {
        EmailBean bean = createBasicBean();
        bean.setHtmlMessage(null);
        delay();
        es.send(bean, true);
        fail("Sent null html");
    }

    /**
     * send and receive an email with the message field set to null
     */
    @Test(expected = IllegalArgumentException.class)
    public void sendMessageNull() {
        EmailBean bean = createBasicBean();
        bean.setMessage(null);
        delay();
        es.send(bean, true);
        fail("Sent null message");
    }

    //Need to speak to Ken about this one
    @Test
    public void sendOnlyHtmlAndText() {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", emailSend));
        bean.getTo().add(new EmailAddress("receiver", emailReceive));
        bean.setMessage("heres some text and html");
        bean.setHtmlMessage("<html><body><h1>Here's some html and text</h1></body></html>");
        es.send(bean, true);
        delay();
        EmailBean[] rbean = re.receiveEmail();
        assertEquals(bean, rbean[0]);
    }


    @After
    public void getAllEmails() {
        EmailBean[] rbean = re.receiveEmail();
    }

    /**
     * @return Emailbean with all fields set bar the attachments
     */
    private EmailBean createBasicBean() {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", emailSend));
        bean.getTo().add(new EmailAddress("receiver", emailReceive));
        bean.getCc().add(new EmailAddress("other", emailCC1));
        bean.setSubject("test12");
        bean.setMessage("hello testing 1 2 3");
        bean.setSend(LocalDateTime.now());
        bean.setHtmlMessage("<html><META http-equiv=Content-Type "
                + "content=\"text/html; charset=utf-8\">"
                + "<body><h1>Here is my photograph embedded in "
                + "this email.</h1><img src='cid:WindsorKen180.jpg'>"
                + "<h2>I'm flying!</h2></body></html>");
        bean.setPriority(Priority.PRIORITY_NORMAL);
        return bean;
    }

    /**
     * This method is to add attachments to the emailbean and return it
     *
     * @param bean original emailbean
     * @return emailbean with the attachments added
     */
    private EmailBean addAttachments(EmailBean bean) throws IOException {
        FileAttachmentBean fa = new FileAttachmentBean();
        FileAttachmentBean fb = new FileAttachmentBean();
        FileAttachmentBean fc = new FileAttachmentBean();
        fa.setName("FreeFall.jpg");
        fa.setFile(Files.readAllBytes(new File("FreeFall.jpg").toPath()));
        fa.setType(false);
        fb.setName("textFile.txt");
        fb.setFile(Files.readAllBytes(new File("textFile.txt").toPath()));
        fb.setType(false);
        fc.setName("FreeFall.jpg");
        fc.setFile(Files.readAllBytes(new File("FreeFall.jpg").toPath()));
        fc.setType(false);
        bean.getAttachments().add(fa);
        bean.getAttachments().add(fb);
        bean.getAttachments().add(fc);
        return bean;
    }

    /**
     * This method is to add imbeddedattachments to the emailbean and return it
     *
     * @param bean original emailbean
     * @return emailbean with the imbedded attachments added
     */
    private EmailBean addImbeddedAttachments(EmailBean bean) throws IOException {
        FileAttachmentBean fa = new FileAttachmentBean();
        fa.setName("WindsorKen180.jpg");
        fa.setFile(Files.readAllBytes(new File("WindsorKen180.jpg").toPath()));
        fa.setType(true);
        bean.getAttachments().add(fa);
        return bean;
    }

    /**
     * delays code for 5 secounds before executing
     */
    private void delay() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
    }
}
