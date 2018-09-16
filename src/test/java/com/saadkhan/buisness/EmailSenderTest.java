package com.saadkhan.buisness;

import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;
import com.saadkhan.data.Priority;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;

import jodd.mail.EmailAddress;

public class EmailSenderTest {
    private final String emailSend = "send.1633839@gmail.com";
    private final String emailReceive = "receive.1633839@gmail.com";
    private final String emailSendPwd = "sendpassword";
    private final String emailReceivePwd = "receivepassword";
    private final String emailCC1 = "other.1633839@gmail.com";
    private EmailSender es = new EmailSender(emailSend, emailSendPwd);
    private EmailReceiver re = new EmailReceiver(emailReceive, emailReceivePwd);


    /**
     *
     * @throws IOException
     */
    @Test
    public void sendAndReceiveWithAllFields() throws IOException, IllegalAccessException {
        EmailBean bean = setup();
        bean = addAttachments(bean);
        bean = addImbeddedAttachments(bean);
        es.send(bean, false);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        EmailBean[] rbean = re.receiveEmail();
        Assert.assertTrue(bean.equals(rbean[0]));
    }

    @Test
    public void sendInvalidTo() {
        EmailBean bean = setup();
        bean.setTo(new ArrayList<EmailAddress>());
        bean.getTo().add(new EmailAddress("invalid Email", "hfagdsgjfhakjdsfafdddasd"));
        try{
            es.send(bean, true);
        }catch(IllegalAccessException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void sendInvalidCC() {
        EmailBean bean = setup();
        bean.setCc(new ArrayList<EmailAddress>());
        bean.getCc().add(new EmailAddress("invalid CC Email", "hfagdsgjfhakjdsfafdddasd"));
        try{
            es.send(bean, true);
        }catch(IllegalAccessException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void sendNullSubject() throws IllegalAccessException {
        EmailBean bean = setup();
        bean.setSubject(null);
        es.send(bean,true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        EmailBean[] rbean = re.receiveEmail();
        Assert.assertTrue(bean.equals(rbean[0]));
    }

    @Test
    public void sendOnlyMessage() throws IllegalAccessException {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", emailSend));
        bean.getTo().add(new EmailAddress("receiver", emailReceive));
        bean.setMessage("Hi im only sending a message");
        es.send(bean,true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        EmailBean[] rbean = re.receiveEmail();
        Assert.assertTrue(bean.equals(rbean[0]));
    }

    @Test
    public void sendOnlySubject() throws IllegalAccessException {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", emailSend));
        bean.getTo().add(new EmailAddress("receiver", emailReceive));
        bean.setSubject("hello only this is subject");
        es.send(bean,true);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        EmailBean[] rbean = re.receiveEmail();
        Assert.assertTrue(bean.equals(rbean[0]));
    }

    @Test
    public void sendOnlyHtml() throws IllegalAccessException {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", emailSend));
        bean.getTo().add(new EmailAddress("receiver", emailReceive));
        bean.setHtmlMessage("<html><body><h1>Only a Html message</h1></body></html>");
        es.send(bean,true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        EmailBean[] rbean = re.receiveEmail();
        Assert.assertTrue(bean.equals(rbean[0]));
    }

    @Test
    public void sendOnlyAttachments() throws IllegalAccessException, IOException {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", emailSend));
        bean.getTo().add(new EmailAddress("receiver", emailReceive));
        bean = addAttachments(bean);
        es.send(bean,false);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        EmailBean[] rbean = re.receiveEmail();
        Assert.assertTrue(bean.equals(rbean[0]));
    }

    @Test
    public void sendNothing() {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", emailSend));
        bean.getTo().add(new EmailAddress("receiver", emailReceive));
        try {
            es.send(bean, true);
        }catch (Exception e){
            Assert.assertTrue(true);
        }
    }

















    /**
     *
     * @return
     */
    private EmailBean setup() {
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
     *
     * @param bean
     * @return
     * @throws IOException
     */
    private EmailBean addAttachments(EmailBean bean) throws IOException {
        FileAttachmentBean fa = new FileAttachmentBean();
        FileAttachmentBean fb = new FileAttachmentBean();
        fa.setName("FreeFall.jpg");
        fa.setFile(Files.readAllBytes(new File("FreeFall.jpg").toPath()));
        fb.setName("textFile.txt");
        fb.setFile(Files.readAllBytes(new File("textFile.txt").toPath()));
        bean.getAttachments().add(fa);
        bean.getAttachments().add(fb);
        return bean;
    }

    /**
     *
     * @param bean
     * @return
     * @throws IOException
     */
    private EmailBean addImbeddedAttachments(EmailBean bean) throws IOException {
        FileAttachmentBean fa = new FileAttachmentBean();
        fa.setName("WindsorKen180.jpg");
        fa.setFile(Files.readAllBytes(new File("WindsorKen180.jpg").toPath()));
        bean.getImbedAttachments().add(fa);
        return bean;
    }
}
