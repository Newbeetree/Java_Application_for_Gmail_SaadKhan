package buisness;

import data.EmailBean;
import jodd.mail.Email;
import jodd.mail.EmailAddress;
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

        Email email = convertBeanToJodd(sendingEmail);
        System.out.println(email);
        try ( // A session is the object responsible for communicating with the server
              SendMailSession session = smtpServer.createSession()) {
            // Like a file we open the session, send the message and close the
            // session
            session.open();
            session.sendMail(email);
        }
    }

    private Email convertBeanToJodd(EmailBean sendingEmail) {
        Email email = Email.create()
                .from(sendingEmail.getFrom())
                .to(sendingEmail.getTo().toArray(new EmailAddress[0]))
                .cc(sendingEmail.getCc().toArray(new EmailAddress[0]))
                .bcc(sendingEmail.getBcc().toArray(new EmailAddress[0]))
                .subject(sendingEmail.getSubject())
                .textMessage(sendingEmail.getMessage())
                .htmlMessage("<html><META http-equiv=Content-Type "
                        + "content=\"text/html; charset=utf-8\">"
                        + "<body><h1>Here is my photograph embedded in "
                        + "this email.</h1>"
                        + "<h2>I'm flying!</h2></body></html>")
                .priority(sendingEmail.getPriority().getValue());
        return email;
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
