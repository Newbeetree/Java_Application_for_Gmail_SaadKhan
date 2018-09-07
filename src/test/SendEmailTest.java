package test;

import org.junit.Test;

import buisness.EmailSender;
import data.EmailBean;
import data.Priority;

import static org.junit.Assert.*;

public class SendEmailTest {
    private final String emailSend = "send.1633839@gmail.com";
    private final String emailReceive = "receive.1633839@gmail.com";
    private final String emailSendPwd = "sendpassword";
    private final String emailReceivePwd = "receivepassword";
    private final String emailCC1 = "other.1633839@gmail.com";

    @Test
    public void send(){
        EmailSender es = new EmailSender(emailSend,emailSendPwd);
        EmailBean bean = setup();
        es.send(bean);
    }

    private EmailBean setup() {
        EmailBean bean = new EmailBean();
        bean.setFrom(emailSend);
        bean.getTo().add(emailReceive);
        bean.getCc().add(emailCC1);
        bean.setMessage("Mother fucker work us stupid bitch");
        bean.setSubject("test");
        bean.setPriority(Priority.PRIORITY_LOW);
        return bean;
    }
}