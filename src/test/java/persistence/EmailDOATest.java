package persistence;

import com.saadkhan.data.EmailBean;
import com.saadkhan.persistence.EmailDOA;
import com.saadkhan.persistence.EmailDOAImpl;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

import jodd.mail.EmailAddress;

import static org.junit.Assert.assertEquals;

public class EmailDOATest {

    private final String URL = "jdbc:mysql://localhost:3306/jag?zeroDateTimeBehavior=CONVERT_TO_NULL&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private final String user = "root";
    private final String password = "123password";

    private final static Logger LOG = LoggerFactory.getLogger(EmailDOATest.class);

    /**
     * This will test if the expected number of records are in the database
     *
     * @throws SQLException
     */
    @Test(timeout = 1000)
    public void testFindAllEmails() throws SQLException {
        EmailDOA emailDOA = new EmailDOAImpl();
        List<EmailBean> emails = emailDOA.findAllEmails();
        // Nothing to do with the test
        displayAll(emails);

        assertEquals("# of emails", 25, emails.size());
    }

    /**
     * This will test if the expected number of records are in the database
     *
     * @throws SQLException
     */
    @Test(timeout = 1000)
    public void testFindAllFolders() throws SQLException {
        EmailDOA emailDOA = new EmailDOAImpl();
        List<String> folders = emailDOA.findAllFolders();
        // Nothing to do with the test

        assertEquals("# of folders", 6, folders.size());
    }

    @Test(timeout = 1000)
    public void testCreateFolder() throws SQLException{
        EmailDOA emailDOA = new EmailDOAImpl();
        int success = emailDOA.createFolder("Read");
        assertEquals(1, success);
    }

    @Test(timeout = 1000)
    public void testCreateSameFolder() throws SQLException{
        EmailDOA emailDOA = new EmailDOAImpl();
        int success = emailDOA.createFolder("Inbox");
        assertEquals(0, success);
    }

    @Test
    public void testCreateEmailAddress() throws SQLException{
        EmailDOA emailDOA = new EmailDOAImpl();
        int success = emailDOA.createEmailAddress(new EmailAddress("agdssfd","rsghjfdgs"));
        assertEquals(0, success);
    }

    private void displayAll(List<EmailBean> emails) {
        for (EmailBean eb :
                emails) {
            LOG.info(eb.getTo().toString());
        }
    }
}
