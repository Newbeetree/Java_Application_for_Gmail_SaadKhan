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

    public EmailDOAImpl() {
        super();
    }

    /**
     * Retrieve all the records for the given table and returns the data as an
     * arraylist of EmailBean objects
     *
     * @return The arraylist of EmailBean objects
     */
    @Override
    public List<EmailBean> findAll() throws SQLException {

        List<EmailBean> rows = new ArrayList<>();
        String selectQuery = "SELECT Bean_Id, Email_From, Email_Subject, Message, HTML, Send_Date, Receive_Date, Priority, Folder_Id FROM EmailBean";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                rows.add(createEmailBean(resultSet));
            }
        }
        LOG.info("# of records found : " + rows.size());
        return rows;
    }

    /**
     * Private method that creates an object of type EmailBean from the current
     * record in the ResultSet
     */
    private EmailBean createEmailBean(ResultSet resultSet) throws SQLException {
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
        bean.setAttachments(checkForFileAttachments(resultSet.getInt("Bean_Id")));
        return bean;
    }

    public ArrayList<FileAttachmentBean> checkForFileAttachments(int bean_id) throws SQLException {
        String selectQuery = "SELECT File_Name, File_Attach, File_Type From Attachments WHERE Email_Id = ?";
        ArrayList<FileAttachmentBean> fileList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(selectQuery);) {
            ps.setInt(1, bean_id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    boolean file_type = resultSet.getInt("File_Type") == 1;
                    fileList.add(new FileAttachmentBean(resultSet.getBytes("File_Attach"), resultSet.getString("File_Name"), file_type));
                }
                return fileList;
            }
        }
    }

    @Override
    public String findFolder(int folder_id) throws SQLException {
        String selectQuery = "SELECT Folder_Name FROM Folder WHERE Folder_Id = ?";
        String folder = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement(selectQuery);
            ps.setInt(1, folder_id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    folder = resultSet.getString("Folder_Name");
                }
            }
        }
        return folder;
    }

    @Override
    public ArrayList<EmailAddress> findEmailList(int bean_id, String type) throws
            SQLException {
        ArrayList<EmailAddress> emailList = new ArrayList<>();
        String selectQuery = "SELECT distinct EA.Name, EA.Address from emailaddresses EA join emailbeanadresses eba on EA.Email_Id = eba.Email_Id join emailbean eb on eba.Bean_Id = eb.Bean_Id where eba.Email_Type = ? AND eb.Bean_Id = ?";
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
    public int update(EmailBean Email) {
        return 0;
    }

    @Override
    public int delete(int ID) {
        return 0;
    }

    @Override
    public int create(EmailBean Email) {
        return 0;
    }

}
