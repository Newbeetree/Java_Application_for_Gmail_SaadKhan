package com.saadkhan.persistence;

import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import jodd.mail.EmailAddress;

public class EmailDOAImpl implements EmailDOA {

    private final Logger LOG = LoggerFactory.getLogger(EmailDOAImpl.class);

    private final String URL = "jdbc:mysql://localhost:3306/jag?zeroDateTimeBehavior=CONVERT_TO_NULL&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private final String USER = "root";
    private final String PASSWORD = "123password";

    /**
     * Default Constructor
     */
    public EmailDOAImpl() {
        super();
    }

    @Override
    public int createEmailAddress(EmailAddress email) throws SQLException {
        int id = 0;
        if (checkIfEmailAddressExists(email)) {
            String insertQuery = "INSERT INTO EmailAddresses (Name, Address) values (?, ?)";
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);) {
                pStatement.setString(1, email.getPersonalName());
                pStatement.setString(2, email.getEmail());
                pStatement.executeUpdate();
                try (ResultSet rs = pStatement.getGeneratedKeys();) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                    LOG.debug("New record ID is " + id);
                }
            }
        } else {
            id = findEmailAddress(email);
        }
        return id;
    }

    @Override
    public int findEmailAddress(EmailAddress emailAddress) throws SQLException {
        String selectQuery = "SELECT Email_Id FROM EmailAddresses WHERE Address = ?";
        int email = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement(selectQuery);
            ps.setString(1, emailAddress.getEmail());
            //ps.setString(2, emailAddress.getPersonalName());
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    email = resultSet.getInt("Email_Id");
                }
            }
        }
        return email;
    }

    /**
     * Deletes an EmailAddress and Name from the EmailAddress table using the email id
     *
     * @param Email_Id Id for email in EmailAddress Table
     * @return int being 0 for failure of delete and 1 be success
     */
    @Override
    public int deleteEmailAddress(int Email_Id) throws SQLException {
        return 0;
    }

    /**
     * Deletes an EmailAddress and Name from the EmailAddress table using the email address
     *
     * @param Email_Address Id for email in EmailAddress Table
     * @return int being 0 for failure of delete and 1 be success
     */
    @Override
    public int deleteEmailAddress(String Email_Address) throws SQLException {
        return 0;
    }

    /**
     * Deletes a Folder from the Folder table using the folder id
     *
     * @param Folder_Id Id for email in EmailAddress Table
     * @return int being 0 for failure of delete and 1 be success
     */
    @Override
    public int deleteFolder(int Folder_Id) throws SQLException {
        return 0;
    }

    /**
     * Deletes a Folder from the Folder table using the folder name
     *
     * @param Folder_Name name of the folder in the table
     * @return int being 0 for failure and 1 for success
     */
    @Override
    public int deleteFolder(String Folder_Name) throws SQLException {
        return 0;
    }

    /**
     * Deletes an Email Bean from the email bean table using the email bean id
     *
     * @param Email_Id id for the email inside the emailbean table
     * @return int being 0 for failure and 1 for success
     */
    @Override
    public int deleteEmailBean(int Email_Id) throws SQLException {
        return 0;
    }

    /**
     * makes updates in the folder table regarding folder name
     *
     * @param folder_Id id of the folder that is to be changed
     * @return 0 for failure and 1 for success
     */
    @Override
    public int updateFolder(int folder_Id) throws SQLException {
        return 0;
    }

    /**
     * makes updates in the folder table regarding folder name
     *
     * @param folder_name name of the folder that is to be changed
     * @return 0 for failure and 1 for success
     */
    @Override
    public int updateFolder(String folder_name) throws SQLException {
        return 0;
    }

    /**
     * makes updates in the emailaddress table changing the email address
     *
     * @param email_Id id of the email to change
     * @return 0 for failure and 1 for success
     */
    @Override
    public int updateEmailAddressAddress(int email_Id) throws SQLException {
        return 0;
    }

    /**
     * makes updates in the emailaddress table changing the email address
     *
     * @param emailaddress name of the email we want to change
     * @return 0 for failure and 1 for success
     */
    @Override
    public int updateEmailAddressAddress(String emailaddress) throws SQLException {
        return 0;
    }

    /**
     * makes updates in the email address table changing the name
     *
     * @param email_id id of the email to change
     * @return 0 for failure and 1 for success
     */
    @Override
    public int updateEmailAddressName(int email_id) throws SQLException {
        return 0;
    }

    /**
     * makes updates in the email address the table changing the name
     *
     * @param name name of the email to change
     * @return 0 for failure and 1 for success
     */
    @Override
    public int updateEmailAddressName(String name) throws SQLException {
        return 0;
    }

    private boolean checkIfEmailAddressExists(EmailAddress email) throws SQLException {
        ArrayList<EmailAddress> listEmailAdresses = findAllEmailAddresses();
        for (EmailAddress listedEmail : listEmailAdresses) {
            if (listedEmail.getEmail().equals(email.getEmail())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<EmailAddress> findAllEmailAddresses() throws SQLException {
        ArrayList<EmailAddress> emailList = new ArrayList<>();
        String selectQuery = "SELECT Name, Address From EmailAddresses";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                EmailAddress email = new EmailAddress(resultSet.getString("Name"), resultSet.getString("Address"));
                emailList.add(email);
            }
        }
        LOG.info("# of emailaddresses found : " + emailList.size());
        return emailList;
    }

    @Override
    public int createEmailBean(EmailBean bean) throws SQLException {
        int id = 0;
        if (checkIfEmailBeanExists(bean)) {
            String insertQuery = "INSERT INTO EmailBean (Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id) VALUES (?,?,?,?,?,?,?,?)";
            int from_id = createEmailAddress(bean.getFrom());
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);) {
                pStatement.setInt(1, from_id);
                pStatement.setString(2, bean.getSubject());
                pStatement.setString(3, bean.getMessage());
                pStatement.setString(4, bean.getHtmlMessage());
                pStatement.setDate(5, java.sql.Date.valueOf(bean.getSend().toLocalDate()));
                pStatement.setDate(6, java.sql.Date.valueOf(bean.getRecived().toLocalDate()));
                pStatement.setInt(7, createFolder(bean.getFolder()));
                pStatement.setInt(8, bean.getPriority().getValue());
                pStatement.executeUpdate();
                try (ResultSet rs = pStatement.getGeneratedKeys();) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                        bean.setEmailID(id);
                    }
                    LOG.debug("New record ID is " + id);
                }
            }
        } else {
            id = findEmailBean(bean);
        }
        createEmailsToSend(bean.getTo(), "To", id);
        createEmailsToSend(bean.getCc(), "Cc", id);
        createEmailsToSend(bean.getBcc(), "Bcc", id);
        createAttachments(bean.getAttachments(), id);
        return id;
    }

    @Override
    public int findEmailBean(EmailBean bean) throws SQLException {
        int id = 0;
        ArrayList<EmailBean> emails = findAllEmailBeans();
        for (EmailBean eb : emails) {
            if (bean.equals(eb)) {
                id = eb.getEmailID();
            }
        }
        return id;
    }

    private void createEmailsToSend(ArrayList<EmailAddress> emailList, String type, int bean_id) throws SQLException {
        if (emailList.size() > 0) {
            for (EmailAddress email : emailList) {
                int email_id = createEmailAddress(email);
                createEmailBeanAddress(email_id, bean_id, type);
            }
        }
    }

    private boolean checkIfEmailBeanExists(EmailBean bean) throws SQLException {
        ArrayList<EmailBean> beanList = findAllEmailBeans();
        for (EmailBean cannedBean : beanList) {
            if (cannedBean.equals(bean)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<EmailBean> findAllEmailBeans() throws SQLException {
        ArrayList<EmailBean> beanList = new ArrayList<>();
        String selectQuery = "SELECT Bean_Id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id FROM EmailBean";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                beanList.add(getEmailBean(resultSet));
            }
        }
        ArrayList<FileAttachmentBean> fablist = findAllAttachments();
        LOG.info("# of email beans found : " + beanList.size());
        return beanList;
    }

    @Override
    public void createEmailBeanAddress(int email_id, int bean_id, String type) throws SQLException {
        if (checkIfEmailBeanAddressExists(email_id, bean_id, type)) {
            String insertQuery = "INSERT INTO EmailBeanAdresses (Bean_Id, Email_Id, Email_Type) values (?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);) {
                pStatement.setInt(1, bean_id);
                pStatement.setInt(2, email_id);
                pStatement.setString(3, type);
                pStatement.executeUpdate();
            }
        }
    }

    private boolean checkIfEmailBeanAddressExists(int email_id, int bean_id, String type) throws SQLException {
        String selectQuery = "SELECT Bean_Id,Email_Id, Email_Type FROM EmailBeanAdresses";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                if (email_id == resultSet.getInt("Email_Id") &&
                        bean_id == resultSet.getInt("Bean_Id") &&
                        type.equals(resultSet.getString("Email_Type"))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int createFolder(String folderName) throws SQLException {
        int id = 0;
        if (checkIfFolderExists(folderName)) {
            String insertQuery = "INSERT INTO Folder (Folder_Name) values (?)";
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);) {
                pStatement.setString(1, folderName);
                pStatement.executeUpdate();
                try (ResultSet rs = pStatement.getGeneratedKeys();) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                    LOG.debug("New record ID is " + id);
                }
            }
        } else {
            id = findFolder(folderName);
        }
        return id;
    }

    private boolean checkIfFolderExists(String folderName) throws SQLException {
        ArrayList<String> listFiles = findAllFolders();
        for (String filename : listFiles) {
            if (filename.equals(folderName)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void createAttachments(ArrayList<FileAttachmentBean> fabList, int email_id) throws
            SQLException {
        for (FileAttachmentBean fab : fabList) {
            if (checkIfAttachmentExists(fab, email_id)) {
                String insertQuery = "INSERT INTO Attachments (Email_Id, File_Name, File_Attach, File_Type) values (?, ?, ?, ?)";
                try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                     PreparedStatement pStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);) {
                    pStatement.setInt(1, email_id);
                    pStatement.setString(2, fab.getName());
                    pStatement.setBlob(3, new SerialBlob(fab.getFile()));
                    pStatement.setBoolean(4, fab.getType());
                    pStatement.executeUpdate();
                    try (ResultSet rs = pStatement.getGeneratedKeys();) {
                        int id = 0;
                        if (rs.next()) {
                            id = rs.getInt(1);
                        }
                        fab.setAttachID(id);
                        LOG.debug("New record ID is " + id);
                    }
                }
            }
        }
    }

    private boolean checkIfAttachmentExists(FileAttachmentBean fab, int email_id) throws
            SQLException {
        ArrayList<FileAttachmentBean> listEmailAdresses = findAllAttachments();
        for (FileAttachmentBean listedEmail : listEmailAdresses) {
            if (listedEmail.equals(fab)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<FileAttachmentBean> findAllAttachments() throws SQLException {
        ArrayList<FileAttachmentBean> attachList = new ArrayList<>();
        String selectQuery = "SELECT Email_Id, FILE_NAME, File_Attach, FILE_TYPE FROM Attachments";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                FileAttachmentBean fab = new FileAttachmentBean(
                        resultSet.getBytes("File_Attach"),
                        resultSet.getString("File_Name"),
                        resultSet.getBoolean("File_Type"));
                fab.setAttachID(resultSet.getInt("Email_Id"));
                attachList.add(fab);
            }
        }
        LOG.info("# of attachments found : " + attachList.size());
        return attachList;
    }

    @Override
    public List<EmailBean> findAllEmails() throws SQLException {

        ArrayList<EmailBean> emails = new ArrayList<>();
        String selectQuery = "SELECT Bean_Id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id FROM EmailBean";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                emails.add(getEmailBean(resultSet));
            }
        }
        LOG.info("# of emails found : " + emails.size());
        return emails;
    }

    @Override
    public EmailBean findEmail(int bean_id) throws SQLException {
        String selectQuery = "SELECT Bean_Id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id FROM EmailBean WHERE Bean_Id = ?";
        EmailBean bean = new EmailBean();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pStatement = connection.prepareStatement(selectQuery)) {
            pStatement.setInt(1, bean_id);
            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    bean = getEmailBean(resultSet);
                }
            }
        }
        return bean;
    }

    @Override
    public ArrayList<FileAttachmentBean> findFileAttachments(int email_id) throws SQLException {
        ArrayList<FileAttachmentBean> fabList = new ArrayList<>();
        String selectQuery = "SELECT Attach_Id, File_Name, File_Attach, File_Type FROM Attachments WHERE Email_Id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(selectQuery);) {
            ps.setInt(1, email_id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    FileAttachmentBean fab = new FileAttachmentBean();
                    fab.setFile(resultSet.getBytes("File_Attach"));
                    fab.setName(resultSet.getString("File_Name"));
                    fab.setType(resultSet.getBoolean("File_Type"));
                    fab.setAttachID(resultSet.getInt("Attach_Id"));
                    fabList.add(fab);
                }
            }
        }
        return fabList;
    }

    private EmailBean getEmailBean(ResultSet resultSet) throws SQLException {
        EmailBean bean = new EmailBean();
        bean.setEmailID(resultSet.getInt("Bean_Id"));
        bean.setFrom(findFrom(resultSet.getInt("Email_From")));
        bean.setSubject(resultSet.getString("Email_Subject"));
        bean.setMessage(resultSet.getString("Message"));
        bean.setHtmlMessage(resultSet.getString("HTML"));
        bean.setSend(resultSet.getTimestamp("Send_Date").toLocalDateTime());
        bean.setRecived(resultSet.getTimestamp("Receive_Date").toLocalDateTime());
        bean.setPriority(resultSet.getInt("Priority"));
        bean.setFolder(findFolder(resultSet.getInt("Folder_Id")));
        bean.setTo(findEmailList(resultSet.getInt("Bean_Id"), "To"));
        bean.setBcc(findEmailList(resultSet.getInt("Bean_Id"), "Bcc"));
        bean.setCc(findEmailList(resultSet.getInt("Bean_Id"), "Cc"));
        bean.setAttachments(findFileAttachments(resultSet.getInt("Bean_Id")));
        return bean;
    }

    @Override
    public String findFolder(int email_id) throws SQLException {
        String selectQuery = "SELECT Folder_Name FROM Folder WHERE Folder_Id = ?";
        String folder = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement(selectQuery);
            ps.setInt(1, email_id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    folder = resultSet.getString("Folder_Name");
                }
            }
        }
        return folder;
    }

    @Override
    public int findFolder(String folderName) throws SQLException {
        String selectQuery = "SELECT Folder_Id FROM Folder WHERE Folder_Name = ?";
        int folder = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement(selectQuery);
            ps.setString(1, folderName);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    folder = resultSet.getInt("Folder_Id");
                }
            }
        }
        return folder;
    }

    @Override
    public ArrayList<String> findAllFolders() throws SQLException {
        ArrayList<String> folderList = new ArrayList<>();
        String selectQuery = "SELECT Folder_Name From Folder";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                folderList.add(resultSet.getString("Folder_Name"));
            }
        }
        LOG.info("# of folders found : " + folderList.size());
        return folderList;
    }

    @Override
    public ArrayList<EmailAddress> findEmailList(int bean_id, String type) throws SQLException {
        ArrayList<EmailAddress> emailList = new ArrayList<>();
        String selectQuery = "SELECT distinct EA.Name, EA.Address from emailaddresses EA" +
                " join emailbeanadresses eba on EA.Email_Id = eba.Email_Id" +
                " join emailbean eb on eba.Bean_Id = eb.Bean_Id" +
                " where eba.Email_Type = ? AND eb.Bean_Id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(selectQuery);) {
            ps.setString(1, type);
            ps.setInt(2, bean_id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    emailList.add(new EmailAddress(resultSet.getString("Name"), resultSet.getString("Address")));
                }
                return emailList;
            }
        }
    }

    @Override
    public EmailAddress findFrom(int email_from) throws SQLException {
        String selectQuery = "SELECT Name, Address FROM EmailAddresses WHERE Email_Id = ?";
        EmailAddress email = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(selectQuery);) {
            ps.setInt(1, email_from);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    email = new EmailAddress(resultSet.getString("Name"), resultSet.getString("Address"));
                }
            }
        }
        return email;
    }


}
