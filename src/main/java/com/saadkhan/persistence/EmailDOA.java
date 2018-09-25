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
    public int create(EmailBean Email) throws SQLException;

    // Read
    public List<EmailBean> findAll() throws SQLException;
    public ArrayList<FileAttachmentBean> checkForFileAttachments(int bean_id) throws SQLException;

    public String findFolder(int folder_id) throws SQLException;
    public ArrayList<EmailAddress> findEmailList(int bean_id, String type) throws SQLException;
    public EmailAddress findFrom(int email_from) throws SQLException;

        // Update
    public int update(EmailBean Email) throws SQLException;

    // Delete
    public int delete(int ID) throws SQLException;
}
