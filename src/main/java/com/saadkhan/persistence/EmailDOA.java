package com.saadkhan.persistence;

import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jodd.mail.Email;
import jodd.mail.EmailAddress;

/**
 * Interface for CRUD methods
 *
 * @author Saad Khan
 */
public interface EmailDOA {

    // Create
    public int createEmailAddress(EmailAddress email) throws SQLException;
    public int createEmailBean(EmailBean bean) throws SQLException;
    public int createEmailBeanAddress()throws SQLException;
    public int createFolder(String folderName) throws SQLException;
    public int createAttachments(FileAttachmentBean fab) throws SQLException;

    // Read
    public List<EmailBean> findAllEmails() throws SQLException;
    public EmailBean findEmail(int bean_id) throws SQLException;
    public ArrayList<FileAttachmentBean> findFileAttachments(int email_id) throws SQLException;
    public String findFolder(int email_id) throws SQLException;
    public ArrayList<String> findAllFolders() throws SQLException;
    public ArrayList<EmailAddress> findEmailList(int bean_id, String type) throws SQLException;
    public EmailAddress findFrom(int email_from) throws SQLException;

    // Delete
    public int delete(int ID) throws SQLException;
}
