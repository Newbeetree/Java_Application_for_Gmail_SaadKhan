package test;

import org.junit.Test;

import buisness.EmailSender;
import buisness.ReceiveEmail;
import data.EmailBean;
import data.Priority;
import jodd.mail.EmailAddress;

import static org.junit.Assert.*;

public class SendEmailTest {
    private final String emailSend = "send.1633839@gmail.com";
    private final String emailReceive = "receive.1633839@gmail.com";
    private final String emailSendPwd = "sendpassword";
    private final String emailReceivePwd = "receivepassword";
    private final String emailCC1 = "other.1633839@gmail.com";

    @Test
    public void send(){
      //  EmailSender es = new EmailSender(emailSend,emailSendPwd);
      //  EmailBean bean = setup();
      //  es.send(bean);
        ReceiveEmail re = new ReceiveEmail(emailReceive,emailReceivePwd);
        re.receiveEmail();
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
                + "this email.</h1>"
                + "<h2>I'm flying!</h2></body></html>");
        bean.setSubject("test4");
        bean.setPriority(Priority.PRIORITY_NORMAL);
        return bean;
    }
}