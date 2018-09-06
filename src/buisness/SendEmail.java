package buisness;

import java.time.LocalDateTime;

import jodd.mail.Email;
import jodd.mail.MailServer;
import jodd.mail.RFC2822AddressParser;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;

public class SendEmail {
    private final String smtpServerName = "smtp.gmail.com";

    public void send(String emailSender, String emailSendPwd, String emailReceiver) {
        if (checkEmail(emailSender) && checkEmail(emailReceiver)) {
            // Create am SMTP server object
            SmtpServer smtpServer = MailServer.create()
                    .ssl(true)
                    .host(smtpServerName)
                    .auth(emailSender, emailSendPwd)
                    .debugMode(true)
                    .buildSmtpMailServer();

            Email email = Email.create().from(emailSender)
                    .to(emailReceiver)
                    .subject("Jodd Test")
                    .textMessage("Hello from plain text email: " + LocalDateTime.now());
            try ( // A session is the object responsible for communicating with the server
                  SendMailSession session = smtpServer.createSession()) {
                // Like a file we open the session, send the message and close the
                // session
                session.open();
                session.sendMail(email);
            }
        }
    }

    /**
     * Use the RFC2822AddressParser to validate that the email string could be a
     * valid address
     *
     * @return true is OK, false if not
     */
    private boolean checkEmail(String address) {
        return RFC2822AddressParser.STRICT.parseToEmailAddress(address) != null;
    }
}
