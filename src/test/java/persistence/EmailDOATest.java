package persistence;

import com.saadkhan.data.EmailBean;
import com.saadkhan.persistence.EmailDOA;
import com.saadkhan.persistence.EmailDOAImpl;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

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
    public void testFindAll() throws SQLException {
        EmailDOA emailDOA = new EmailDOAImpl();
        List<EmailBean> emails = emailDOA.findAll();
        // Nothing to do with the test
        displayAll(emails);

        assertEquals("# of emails", 25, emails.size());
    }

    private void displayAll(List<EmailBean> emails) {
        for (EmailBean eb :
                emails) {
            LOG.info(eb.getTo().toString());
        }
    }
}
