package test;

import org.junit.Test;

import buisness.EmailSender;

import static org.junit.Assert.*;

public class SendEmailTest {
    private final String emailSend = "send.1633839@gmail.com";
    private final String emailReceive = "receive.1633839@gmail.com";
    private final String emailSendPwd = "sendpassword";
    private final String emailReceivePwd = "receivepassword";
    private final String emailCC1 = "other.1633839@gmail.com";

    @Test
    public void send(String user, String password, String receiver){
        EmailSender es = new EmailSender(emailSend,emailSendPwd);
        //es.send();
    }
}