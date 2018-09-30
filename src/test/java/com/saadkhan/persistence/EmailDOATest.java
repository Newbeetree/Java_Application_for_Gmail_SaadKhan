package com.saadkhan.persistence;

import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;
import com.saadkhan.data.Priority;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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

import jodd.mail.EmailAddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EmailDOATest {

    private final static Logger LOG = LoggerFactory.getLogger(EmailDOATest.class);
    private EmailDOA emailDOA = new EmailDOAImpl();

    @Test(timeout = 1000)
    public void testCreateEmailAddress() throws SQLException {
        int success = emailDOA.createEmailAddress(new EmailAddress("agdssfd", "rsghjfdgs"));
        assertNotEquals(0, success);
    }

    @Test(timeout = 1000)
    public void testCreateFolder() throws SQLException {
        int success = emailDOA.createFolder("Read");
        assertNotEquals(0, success);
    }

    @Test(timeout = 1000)
    public void testCreateEmailBean() throws SQLException, IOException {
        EmailBean bean = createBasicBean();
        int success = emailDOA.createEmailBean(bean);
        assertNotEquals(0, success);
    }

    @Test(timeout = 10000)
    public void testCreateDuplicateEmailBean() throws SQLException, IOException {
        EmailBean bean = createBasicBean();
        int email_id = emailDOA.createEmailBean(bean);
        int success = emailDOA.createEmailBean(bean);
        assertEquals(email_id, success);
    }

    @Test(timeout = 1000)
    public void testDuplicateSameFolder() throws SQLException {
        int success = emailDOA.createFolder("Inbox");
        assertNotEquals(0, success);
    }

    @Test(timeout = 1000)
    public void testFindAllEmailsBeans() throws SQLException {
        List<EmailBean> emails = emailDOA.findAllEmailBeans();
        assertEquals("# of emails", 25, emails.size());
    }

    @Test(timeout = 1000)
    public void testFindAllFolders() throws SQLException {
        ArrayList<String> folders = emailDOA.findAllFolders();
        assertEquals("# of folders", 6, folders.size());
    }

    @Test(timeout = 1000)
    public void testFindEmailAddress() throws SQLException {
        int success = emailDOA.findEmailAddress(new EmailAddress("user name", "send.1633839@gmail.com"));
        assertEquals("Email not found", 26, success);
    }

    @Test(timeout = 1000)
    public void testFindEmailBean() throws SQLException, IOException {
        int email_id = emailDOA.createEmailBean(createBasicBean());
        int success = emailDOA.findEmailBean(createBasicBean());
        assertEquals("emailbean not found", email_id, success);
    }

    @Test(timeout = 1000)
    public void testFindEmailBeanBean() throws SQLException, IOException {
        int email_id = emailDOA.createEmailBean(createBasicBean());
        EmailBean success = emailDOA.findEmail(email_id);
        assertEquals("emailbean not found", createBasicBean(), success);
    }

    @Test(timeout = 1000)
    public void testFindEmail() throws SQLException, IOException {
        EmailBean basic = createBasicBean();
        emailDOA.createEmailBean(basic);
        EmailBean bean = emailDOA.findEmail(26);
        assertEquals("Beans not Equal", bean, basic);
    }

    @Test(timeout = 1000)
    public void testFindFileAttachments() throws SQLException {
        ArrayList<FileAttachmentBean> fabList = emailDOA.findAllAttachments();
        assertEquals("all files return", 10, fabList.size());
    }

    @Test(timeout = 1000)
    public void testFindFolderID() throws SQLException {
        String folderName = emailDOA.findFolder(1);
        assertEquals("folder not found", "Inbox", folderName);
    }

    @Test(timeout = 1000)
    public void testFindFolderString() throws SQLException {
        int folder_id = emailDOA.findFolder("Inbox");
        assertEquals("folder not found", 1, folder_id);
    }

    @Test(timeout = 1000)
    public void testFindEmailList() throws SQLException {
        ArrayList<EmailAddress> emailaddresslist = emailDOA.findEmailList(12, "To");
        assertEquals("emails not found", 2, emailaddresslist.size());
    }

    @Test(timeout = 1000)
    public void testFindFrom() throws SQLException {
        EmailAddress ea = emailDOA.findFrom(26);
        assertEquals("Found from email", "users name", ea.getPersonalName());
    }

    @Test(timeout = 1000)
    public void testDeleteEmailAddressId() throws SQLException {
        int result = emailDOA.deleteEmailAddress(26);
        assertEquals("message was deleted", 1, result);
    }

    @Test(timeout = 1000)
    public void testDeleteEmailAddressEmail() throws SQLException {
        int result = emailDOA.deleteEmailAddress("send.1633839@gmail.com");
        assertEquals("message was deleted", 1, result);
    }

    @Test(timeout = 1000)
    public void testDeleteFolderId() throws SQLException {
        int result = emailDOA.deleteFolder(1);
        assertEquals("folder not deleted", 1, result);
    }

    @Test(timeout = 1000)
    public void testDeleteFolderName() throws SQLException {
        int result = emailDOA.deleteFolder("Inbox");
        assertEquals("folder not deleted", 1, result);
    }

    @Test(timeout = 1000)
    public void testDeleteEmailBean() throws SQLException {
        int result = emailDOA.deleteEmailBean(1);
        assertEquals("folder not deleted", 1, result);
    }

    @Test(timeout = 1000)
    public void testUpdateFolder() throws SQLException {
        int result = emailDOA.updateFolder(1, "My Inbox");
        assertEquals("folder not deleted", 1, result);
    }

    @Test(timeout = 1000)
    public void testUpdateEmailAddress() throws SQLException {
        int result = emailDOA.updateEmailAddress(26, "user", "other.1633839@gmail.com");
        assertEquals("folder not deleted", 1, result);
    }

    @Before
    public void seedDatabase() {
        LOG.info("Seeding Database");
        final String URL = "jdbc:mysql://localhost:3306/JAG?zeroDateTimeBehavior=CONVERT_TO_NULL&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
        final String user = "auser";
        final String password = "123password";
        final String seedDataScript = loadAsString("./src/test/resources/createEmailSchema.sql");
        final String seedTableScript = loadAsString("./src/test/resources/createEmailDB.sql");
        final String seedEmailTable = loadAsString("./src/test/resources/createEmailTable.sql");
        try (Connection connection = DriverManager.getConnection(URL, user, password)) {
            for (String statement : splitStatements(new StringReader(
                    seedEmailTable), ";")) {
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
        bean.setFrom(new EmailAddress("user name", "send.1633839@gmail.com"));
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
