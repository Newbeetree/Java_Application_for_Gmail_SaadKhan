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
    private final static Logger LOG = LoggerFactory.getLogger(EmailDOAImpl.class);

    // This information should be coming from a Properties file
    private final static String URL = "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private final static String USER = "root";
    private final static String PASSWORD = "123password";

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

        // Using try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             // You must use PreparedStatements to guard against SQL
             // Injection
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
        bean.setFrom(getEmail(resultSet.getInt("Email_From")));
        bean.setTo(getEmailList(resultSet.getInt("Bean_Id"), "To"));
        bean.setCc(getEmailList(resultSet.getInt("Bean_Id"), "CC"));
        bean.setBcc(getEmailList(resultSet.getInt("Bean_Id"), "BCC"));
        bean.setSubject(resultSet.getString("Email_Subject"));
        bean.setMessage(resultSet.getString("Message"));
        bean.setHtmlMessage(resultSet.getString("HTML"));
        bean.setSend(resultSet.getTimestamp("Send_Date").toLocalDateTime());
        bean.setRecived(resultSet.getTimestamp("Receive_Date").toLocalDateTime());
        bean.setPriority(resultSet.getInt("Priority"));
        bean.setFolder(getFolder(resultSet.getInt("Folder_Id")));
        bean.getAttachments().add(checkForFileAttachments(resultSet.getInt("Bean_Id")));
        return bean;
    }

    private FileAttachmentBean checkForFileAttachments(int bean_id) {
        return null;
    }

    private String getFolder(int folder_id) throws SQLException {
        String selectQuery = "SELECT Folder_Name FROM Folder WHERE Folder_Id = ?";
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement(selectQuery);
            ps.setInt(1, folder_id);
            resultSet = ps.executeQuery();
        }
        return resultSet.getString("Folder_Name");
    }

    private ArrayList<EmailAddress> getEmailList(int bean_id, String type) throws SQLException {
        ArrayList<EmailAddress> emailList = new ArrayList<>();
        String selectQuery = "SELECT distinct EA.Name, EA.Address from emailaddresses EA join emailbeanadresses eba on EA.Email_Id = eba.Email_Id join emailbean eb on eba.Bean_Id = eb.Bean_Id where eba.Email_Type = ? AND eb.Bean_Id = ?";
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
            PreparedStatement ps = connection.prepareStatement(selectQuery);
            ps.setString(1, type);
            ps.setInt(2, bean_id);
            resultSet = ps.executeQuery();
        }
        while (resultSet.next()){
            emailList.add(new EmailAddress(resultSet.getString("Name"),resultSet.getString("Address")));
        }
        return emailList;
    }

    private EmailAddress getEmail(int email_from) throws SQLException {
        String selectQuery = "SELECT Name, Address FROM EmailAddresses WHERE Email_Id = ?";
        ResultSet resultSet;
        EmailAddress email = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement(selectQuery);
            ps.setInt(1, email_from);
            resultSet = ps.executeQuery();
        }
        while(resultSet.next()){
            email = new EmailAddress(resultSet.getString("Name"), resultSet.getString("Address"));
        }
        return email;
    }

    @Override
    public EmailBean findID(int id) throws SQLException {
        return null;
    }

    @Override
    public int update(EmailBean Email) throws SQLException {
        return 0;
    }

    @Override
    public int delete(int ID) throws SQLException {
        return 0;
    }

    @Override
    public int create(EmailBean Email) throws SQLException {
        return 0;
    }

}
