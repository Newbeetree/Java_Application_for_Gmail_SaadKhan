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
import java.util.ArrayList;
import java.util.List;

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
        return 0;
    }

    @Override
    public int createEmailBean(EmailBean bean) throws SQLException {
        return 0;
    }

    @Override
    public int createEmailBeanAddress() throws SQLException {
        return 0;
    }

    @Override
    public int createFolder(String folderName) throws SQLException {
        return 0;
    }

    @Override
    public int createAttachments(FileAttachmentBean fab) throws SQLException {
        return 0;
    }

    /**
     * Retrieve all the records for the given table and returns the data as an
     * arraylist of EmailBean objects
     *
     * @return The arraylist of EmailBean objects
     */
    @Override
    public List<EmailBean> findAllEmails() throws SQLException {

        List<EmailBean> rows = new ArrayList<>();
        String selectQuery = "SELECT Bean_Id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id FROM EmailBean";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                rows.add(getEmailBean(resultSet));
            }
        }
        LOG.info("# of records found : " + rows.size());
        return rows;
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
                }
            }
        }
        return fabList;
    }

    /**
     * Private method that creates an object of type EmailBean from the current
     * record in the ResultSet
     */
    private EmailBean getEmailBean(ResultSet resultSet) throws SQLException {
        EmailBean bean = new EmailBean();
        bean.setFrom(findFrom(resultSet.getInt("Email_From")));
        bean.setTo(findEmailList(resultSet.getInt("Bean_Id"), "To"));
        bean.setCc(findEmailList(resultSet.getInt("Bean_Id"), "CC"));
        bean.setBcc(findEmailList(resultSet.getInt("Bean_Id"), "BCC"));
        bean.setSubject(resultSet.getString("Email_Subject"));
        bean.setMessage(resultSet.getString("Message"));
        bean.setHtmlMessage(resultSet.getString("HTML"));
        bean.setSend(resultSet.getTimestamp("Send_Date").toLocalDateTime());
        bean.setRecived(resultSet.getTimestamp("Receive_Date").toLocalDateTime());
        bean.setPriority(resultSet.getInt("Priority"));
        bean.setFolder(findFolder(resultSet.getInt("Folder_Id")));
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


    @Override
    public int delete(int ID) {
        return 0;
    }

}
