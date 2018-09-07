package buisness;

import data.EmailBean;
import jodd.mail.Email;
import jodd.mail.MailServer;
import jodd.mail.RFC2822AddressParser;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;

public class EmailSender {
    private final String smtpServerName = "smtp.gmail.com";
    private String userEmail;
    private String userPassword;

    public EmailSender(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public void send(EmailBean sendingEmail) {
        // Create am SMTP server object
        SmtpServer smtpServer = MailServer.create()
                .ssl(true)
                .host(smtpServerName)
                .auth(userEmail, userPassword)
                .debugMode(true)
                .buildSmtpMailServer();

        Email email = convertBeantoJodd(sendingEmail);
        try ( // A session is the object responsible for communicating with the server
              SendMailSession session = smtpServer.createSession()) {
            // Like a file we open the session, send the message and close the
            // session
            session.open();
            session.sendMail(email);
        }
    }

    private Email convertBeantoJodd(EmailBean sendingEmail) {
        Email email = Email.create()
                .from(sendingEmail.getFrom())
                .to(sendingEmail.getTo().toArray(new String[0]))
                .cc(sendingEmail.getCc().toArray(new String[0]))
                .bcc(sendingEmail.getBcc().toArray(new String[0]))
                .subject(sendingEmail.getSubject())
                .message(sendingEmail.getMessage()));

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
