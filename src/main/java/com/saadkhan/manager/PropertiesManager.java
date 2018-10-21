package com.kenfogel.properties_demo.manager;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import com.kenfogel.properties_demo.mailbean.MailConfigBean;

/**
 * Example of a class to manager properties
 *
 * @author Ken Fogel
 *
 */
public class PropertiesManager {

    /**
     * Returns a MailConfigBean object with the contents of the properties file
     *
     * @param path Must exist, will not be created
     * @param propFileName Name of the properties file
     * @return The bean loaded with the properties
     * @throws IOException
     */
    public final MailConfigBean loadTextProperties(final String path, final String propFileName) throws IOException {

        Properties prop = new Properties();

        Path txtFile = get(path, propFileName + ".properties");

        MailConfigBean mailConfig = new MailConfigBean();

        // File must exist
        if (Files.exists(txtFile)) {
            try (InputStream propFileStream = newInputStream(txtFile);) {
                prop.load(propFileStream);
            }
            mailConfig.setUserName(prop.getProperty("userName"));
            mailConfig.setPassword(prop.getProperty("password"));
            mailConfig.setUserEmailAddress(prop.getProperty("userEmailAddress"));
        }
        return mailConfig;
    }

    /**
     * Creates a plain text properties file based on the parameters
     *
     * @param path Must exist, will not be created
     * @param propFileName Name of the properties file
     * @param mailConfig The bean to store into the properties
     * @throws IOException
     */
    public final void writeTextProperties(final String path, final String propFileName, final MailConfigBean mailConfig) throws IOException {

        Properties prop = new Properties();

        prop.setProperty("userName", mailConfig.getUserName());
        prop.setProperty("password", mailConfig.getPassword());
        prop.setProperty("userEmailAddress", mailConfig.getUserEmailAddress());

        Path txtFile = get(path, propFileName + ".properties");

        // Creates the file or if file exists it is truncated to length of zero
        // before writing
        try (OutputStream propFileStream = newOutputStream(txtFile)) {
            prop.store(propFileStream, "SMTP Properties");
        }
    }

    /**
     * Returns a MailConfigBean object with the contents of the properties file
     * that is in an XML format
     *
     * @param path Must exist, will not be created. Empty string means root of
     * program folder
     * @param propFileName Name of the properties file
     * @return The bean loaded with the properties
     * @throws IOException
     */
    public final MailConfigBean loadXmlProperties(final String path, final String propFileName) throws IOException {

        Properties prop = new Properties();

        // The path of the XML file
        Path xmlFile = get(path, propFileName + ".xml");

        MailConfigBean mailConfig = new MailConfigBean();

        // File must exist
        if (Files.exists(xmlFile)) {
            try (InputStream propFileStream = newInputStream(xmlFile);) {
                prop.loadFromXML(propFileStream);
            }
            mailConfig.setUserName(prop.getProperty("userName"));
            mailConfig.setPassword(prop.getProperty("password"));
            mailConfig.setUserEmailAddress(prop.getProperty("userEmailAddress"));
        }
        return mailConfig;
    }

    /**
     * Creates an XML properties file based on the parameters
     *
     * @param path Must exist, will not be created
     * @param propFileName Name of the properties file. Empty string means root
     * of program folder
     * @param mailConfig The bean to store into the properties
     * @throws IOException
     */
    public final void writeXmlProperties(final String path, final String propFileName, final MailConfigBean mailConfig) throws IOException {

        Properties prop = new Properties();

        prop.setProperty("userName", mailConfig.getUserName());
        prop.setProperty("password", mailConfig.getPassword());
        prop.setProperty("userEmailAddress", mailConfig.getUserEmailAddress());

        Path xmlFile = get(path, propFileName + ".xml");

        // Creates the file or if file exists it is truncated to length of zero
        // before writing
        try (OutputStream propFileStream = newOutputStream(xmlFile)) {
            prop.storeToXML(propFileStream, "XML SMTP Properties");
        }
    }

    /**
     * Retrieve the properties file. This syntax rather than normal File I/O is
     * employed because the properties file is inside the jar. The technique
     * shown here will work in both Java SE and Java EE environments. A similar
     * technique is used for loading images.
     *
     * In a Maven project all configuration files, images and other files go
     * into src/main/resources. The files and folders placed there are moved to
     * the root of the project when it is built.
     *
     * @param propertiesFileName : Name of the properties file
     * @throws IOException : Error while reading the file
     * @throws NullPointerException : File not found
     */
    public final MailConfigBean loadJarTextProperties(final String propertiesFileName) throws IOException, NullPointerException {

        Properties prop = new Properties();
        MailConfigBean mailConfig = new MailConfigBean();

        // There is no exists method for files in a jar so we try to get the
        // resource and if its not there it returns a null
        if (this.getClass().getResource("/" + propertiesFileName) != null) {
            // Assumes that the properties file is in the root of the
            // project/jar.
            // Include a path from the root if required.
            try (InputStream stream = this.getClass().getResourceAsStream("/" + propertiesFileName);) {
                prop.load(stream);
            }
            mailConfig.setUserName(prop.getProperty("userName"));
            mailConfig.setPassword(prop.getProperty("password"));
            mailConfig.setUserEmailAddress(prop.getProperty("userEmailAddress"));
        }
        return mailConfig;
    }
}
