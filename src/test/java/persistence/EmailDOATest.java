package persistence;

import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;
import com.saadkhan.data.Priority;
import com.saadkhan.persistence.EmailDOA;
import com.saadkhan.persistence.EmailDOAImpl;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import jodd.mail.EmailAddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EmailDOATest {

    private final static Logger LOG = LoggerFactory.getLogger(EmailDOATest.class);

    /**
     * This will test if the expected number of records are in the database
     */
    @Test(timeout = 1000)
    public void testFindAllEmails() throws SQLException {
        EmailDOA emailDOA = new EmailDOAImpl();
        List<EmailBean> emails = emailDOA.findAllEmails();
        assertEquals("# of emails", 25, emails.size());
    }

    /**
     * This will test if the expected number of records are in the database
     */
    @Test(timeout = 1000)
    public void testFindAllFolders() throws SQLException {
        EmailDOA emailDOA = new EmailDOAImpl();
        ArrayList<String> folders = emailDOA.findAllFolders();
        // Nothing to do with the test

        assertEquals("# of folders", 6, folders.size());
    }

    @Test(timeout = 1000)
    public void testFindAllEmailBeans() throws SQLException {
        EmailDOA emailDOA = new EmailDOAImpl();
        List<EmailBean> folders = emailDOA.findAllEmailBeans();
        // Nothing to do with the test

        assertEquals("# of folders", 25, folders.size());
    }

    @Test(timeout = 1000)
    public void testCreateFolder() throws SQLException {
        EmailDOA emailDOA = new EmailDOAImpl();
        int success = emailDOA.createFolder("Read");
        assertNotEquals(0, success);
    }

    @Test(timeout = 100000)
    public void testCreateEmailBean() throws SQLException, IOException {
        EmailDOA emailDOA = new EmailDOAImpl();
        EmailBean bean = createBasicBean();
        int success = emailDOA.createEmailBean(bean);
        assertNotEquals(0, success);
    }

    @Test(timeout = 100000)
    public void testCreateDuplicateEmailBean() throws SQLException, IOException {
        EmailDOA emailDOA = new EmailDOAImpl();
        EmailBean bean = createBasicBean();
        int email_id = emailDOA.createEmailBean(bean);
        int success = emailDOA.createEmailBean(bean);
        assertEquals(email_id, success);
    }

    @Test(timeout = 1000)
    public void testCreateSameFolder() throws SQLException {
        EmailDOA emailDOA = new EmailDOAImpl();
        int success = emailDOA.createFolder("Inbox");
        assertNotEquals(0, success);
    }

    @Test(timeout = 1000)
    public void testCreateEmailAddress() throws SQLException {
        EmailDOA emailDOA = new EmailDOAImpl();
        int success = emailDOA.createEmailAddress(new EmailAddress("agdssfd", "rsghjfdgs"));
        assertNotEquals(0, success);
    }

    @Test(timeout = 1000)
    public void testFindEmailAddress() throws SQLException {
        EmailDOA emailDOA = new EmailDOAImpl();
        int success = emailDOA.findEmailAddress(new EmailAddress("user name", "send.1633839@gmail.com"));
        assertEquals("Email found", 26, success);
    }

    private void displayAll(List<EmailBean> emails) {
        for (EmailBean eb :
                emails) {
            LOG.info(eb.getTo().toString());
        }
    }

    @Before
    public void seedDatabase() {
        LOG.info("Seeding Database");
        String URL = "jdbc:mysql://localhost:3306/jag?zeroDateTimeBehavior=CONVERT_TO_NULL&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "123password";
        final String seedDataScript = loadAsString("./src/test/resources/createEmailSchema.sql");
        final String seedTableScript = loadAsString("./src/test/resources/createEmailDB.sql");
        try (Connection connection = DriverManager.getConnection(URL, user, password)) {
            for (String statement : splitStatements(new StringReader(
                    seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
            for (String statement : splitStatements(new StringReader(
                    seedTableScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed seeding database", e);
        }
    }

    private String loadAsString(final String path) {
        String fileString = null;
        try {
            fileString = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (IOException e1) {
            LOG.error("File could not be read");
        }
        return fileString;
    }

    private List<String> splitStatements(Reader reader, String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }

    private EmailBean createBasicBean() throws IOException {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("name", "send.1633839@gmail.com"));
        bean.getTo().add(new EmailAddress("receiver", "receive.1633839@gmail.com"));
        bean.getCc().add(new EmailAddress("other", "other.1633839@gmail.com"));
        bean.setSubject("test14");
        bean.setMessage("hello testing 1 2 3 4 5 6");
        bean.setSend(LocalDateTime.now());
        bean.setHtmlMessage("<html><META http-equiv=Content-Type "
                + "content=\"text/html; charset=utf-8\">"
                + "<body><h1>Here is my photograph embedded in "
                + "this email.</h1><img src='cid:WindsorKen180.jpg'>"
                + "<h2>I'm flying!</h2></body></html>");
        bean.setPriority(Priority.PRIORITY_NORMAL);
        FileAttachmentBean fa = new FileAttachmentBean();
        fa.setName("WindsorKen180.jpg");
        fa.setFile(Files.readAllBytes(new File("WindsorKen180.jpg").toPath()));
        fa.setType(true);
        bean.getAttachments().add(fa);
        return bean;
    }
}
