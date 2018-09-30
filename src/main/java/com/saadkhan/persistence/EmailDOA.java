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

    /**
     * create an entry in the emailAddress table using a jodd emailAddress,
     * if the email is already in the table simply return the emailAddress id
     *
     * @param email email address field that contains both name and email address
     * @return int that represents the id of the email address in the table
     */
    int createEmailAddress(EmailAddress email) throws SQLException;

    /**
     * create an entry in the emailBean table using emailBean bean,
     * if the email bean is already in the table return the emailBean id
     *
     * @param bean bean that will be inserted into the table
     * @return int that represents the id of the email bean in the table
     */
    int createEmailBean(EmailBean bean) throws SQLException;

    /**
     * create an entry in the emailBeanAddress table,
     * the bridging table between emailbean and email address,
     *
     * @param email_id the email_id of the email address in the emailaddress table
     * @param bean_id  the bean_id of the email bean in the emailbean table
     * @param type     the type of the address for the email bean either: To, Bcc, Cc
     */
    void createEmailBeanAddress(int email_id, int bean_id, String type) throws SQLException;

    /**
     * create an entry in the folder table if folder already exists return the folder id
     *
     * @return int repressenting the folder id of the folder that is returned
     */
    int createFolder(String folderName) throws SQLException;

    /**
     * create an entry in the attachments folder
     *
     * @param fabList an arraylist of all the file attachments beans to add
     * @param email_id  id of the email bean the attachment belongs to
     */
    void createAttachments(ArrayList<FileAttachmentBean> fabList, int email_id) throws SQLException;


    // Read

    /**
     * Find all emails in the database using the email bean table and creating an array list of emailbeans
     *
     * @return list of all emailbeans in the database
     */
    ArrayList<EmailBean> findAllEmailBeans() throws SQLException;

    /**
     * find all the email address and names in the datavase and create an array list of email addresses
     *
     * @return List of email address in the database
     */
    ArrayList<EmailAddress> findAllEmailAddresses() throws SQLException;

    /**
     * find name of all folders in the database and create an arraylist of folders
     *
     * @return list of all folder names
     */
    ArrayList<String> findAllFolders() throws SQLException;

    /**
     * find name of all attachments in the database and create an arraylist  of FileAttachmentBeans
     *
     * @return list of all fileattachment beans
     */
    ArrayList<FileAttachmentBean> findAllAttachments() throws SQLException;

    /**
     * find email address id using this specific jodd email
     *
     * @return int the email id of the email address
     */
    int findEmailAddress(EmailAddress emailAddress) throws SQLException;

    /**
     * find bean_id of an entry in the emailbean able using the email bean
     *
     * @param bean the email which will be searched for in the table
     * @return int of bean_id
     */
    int findEmailBean(EmailBean bean) throws SQLException;

    /**
     * find email bean using the bean_id and return a fully formed bean
     *
     * @return email bean of the with the correct email
     */
    EmailBean findEmail(int bean_id) throws SQLException;

    /**
     * find the file attachemnts of a specific email
     *
     * @param email_id the id for the email whose atttachments we are looking for
     * @return return a list of attachments that we've found associated with that email id
     */
    ArrayList<FileAttachmentBean> findFileAttachments(int email_id) throws SQLException;

    /**
     * Find name of folder that is associated with that email_id
     *
     * @param email_id id of the email
     * @return String name of the folder
     */
    String findFolder(int email_id) throws SQLException;

    /**
     * find the id of folder that is associated with that specific name
     *
     * @param folderName name of the folder
     * @return int id of the folder
     */
    int findFolder(String folderName) throws SQLException;

    /**
     * create a list of email address of a specific type associated with a email bean
     * and its type either: To, Bcc, Cc
     *
     * @param bean_id id of the email bean we are looking for
     * @param type string type of the email addresses in the table: To,Bcc, Cc
     */
    ArrayList<EmailAddress> findEmailList(int bean_id, String type) throws SQLException;

    /**
     * find the email address for the from field
     * @param email_from the email_id in the email address table
     * @return emailaddress from of the sender in the email address table
     */
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
