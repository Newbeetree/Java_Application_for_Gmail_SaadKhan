package com.saadkhan.persistence;

import com.saadkhan.data.EmailBean;

import java.sql.SQLException;
import java.util.List;

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

    public EmailBean findID(int id) throws SQLException;

    // Update
    public int update(EmailBean Email) throws SQLException;

    // Delete
    public int delete(int ID) throws SQLException;
}
