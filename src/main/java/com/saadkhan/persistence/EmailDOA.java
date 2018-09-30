package com.saadkhan.persistence;

import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jodd.mail.EmailAddress;

/**
 * Interface for CRUD methods
 *
 * @author Saad Khan
 */
public interface EmailDOA {

    // Create
    int createEmailAddress(EmailAddress email) throws SQLException;

    int createEmailBean(EmailBean bean) throws SQLException;

    void createEmailBeanAddress(int email_id, int id, String type) throws SQLException;

    int createFolder(String folderName) throws SQLException;

    void createAttachments(ArrayList<FileAttachmentBean> fab, int id) throws SQLException;


    // Read
    List<EmailBean> findAllEmails() throws SQLException;

    ArrayList<EmailAddress> findAllEmailAddresses() throws SQLException;

    ArrayList<String> findAllFolders() throws SQLException;

    ArrayList<FileAttachmentBean> findAllAttachments() throws SQLException;

    ArrayList<EmailBean> findAllEmailBeans() throws SQLException;

    int findEmailAddress(EmailAddress emailAddress) throws SQLException;

    int findEmailBean(EmailBean bean) throws SQLException;

    EmailBean findEmail(int bean_id) throws SQLException;

    ArrayList<FileAttachmentBean> findFileAttachments(int email_id) throws SQLException;

    String findFolder(int email_id) throws SQLException;

    int findFolder(String folderName) throws SQLException;

    ArrayList<EmailAddress> findEmailList(int bean_id, String type) throws SQLException;

    EmailAddress findFrom(int email_from) throws SQLException;


    // Delete

    /**
     * Deletes an EmailAddress and Name from the EmailAddress table using the email id
     *
     * @param email_Id Id for email in EmailAddress Table
     * @return int being 0 for failure of delete and 1 be success
     */
    int deleteEmailAddress(int email_Id) throws SQLException;

    /**
     * Deletes an EmailAddress and Name from the EmailAddress table using the email address
     *
     * @param email_Address Id for email in EmailAddress Table
     * @return int being 0 for failure of delete and 1 be success
     */
    int deleteEmailAddress(String email_Address) throws SQLException;

    /**
     * Deletes a Folder from the Folder table using the folder id
     *
     * @param folder_Id Id for email in EmailAddress Table
     * @return int being 0 for failure of delete and 1 be success
     */
    int deleteFolder(int folder_Id) throws SQLException;

    /**
     * Deletes a Folder from the Folder table using the folder name
     *
     * @param folder_Name name of the folder in the table
     * @return int being 0 for failure and 1 for success
     */
    int deleteFolder(String folder_Name) throws SQLException;

    /**
     * Deletes an Email Bean from the email bean table using the email bean id
     *
     * @param email_Id id for the email inside the emailbean table
     * @return int being 0 for failure and 1 for success
     */
    int deleteEmailBean(int email_Id) throws SQLException;

    /**
     * makes updates in the folder table regarding folder name
     *
     * @param folder_Id id of the folder that is to be changed
     * @param newName   new name of file
     * @return 0 for failure and 1 for success
     */
    int updateFolder(int folder_Id, String newName) throws SQLException;

    /**
     * makes updates in the emailaddress table changing the email address
     *
     * @param email_Id id of the email to change
     * @return 0 for failure and 1 for success
     */
     int updateEmailAddress(int email_Id, String Name, String Address) throws SQLException;

}
