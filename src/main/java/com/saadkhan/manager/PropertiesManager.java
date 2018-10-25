package com.saadkhan.manager;

import com.saadkhan.data.ConfigurationFxBean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.Paths.get;

/**
 * class to manager properties
 *
 * @author Saad Khan
 */
public class PropertiesManager {

    public final boolean loadTextProperties(ConfigurationFxBean bean, String path, String fileName) throws IOException {

        boolean found = false;
        Path txtFile = get(path, fileName + ".properties");
        try {
            if (Files.exists(txtFile)) {
                bean = getConfBeanSettings(txtFile);
                found = true;
            }
        } catch (NullPointerException e) {
            found = false;
        }
        return found;
    }

    public ConfigurationFxBean getConfBeanSettings(Path txtFile) throws NullPointerException, IOException {
        Properties prop = new Properties();
        try (InputStream propFileStream = newInputStream(txtFile);) {
            prop.load(propFileStream);
        }
        ConfigurationFxBean bean = new ConfigurationFxBean();
        bean.setUserName(prop.getProperty("name"));
        bean.setUserEmailAddress(prop.getProperty("email"));
        bean.setUserPassword(prop.getProperty("password"));
        bean.setIMAPServer(prop.getProperty("IMAPS"));
        bean.setSMTPServer(prop.getProperty("SMTPS"));
        bean.setIMAPPort(prop.getProperty("IMAPP"));
        bean.setSMTPPort(prop.getProperty("SMTPP"));
        bean.setDBUrl(prop.getProperty("DBUrl"));
        bean.setDBName(prop.getProperty("DBName"));
        bean.setDBUser(prop.getProperty("DBUser"));
        bean.setDBPort(prop.getProperty("DBPort"));
        bean.setDBPassword(prop.getProperty("DBPassword"));
        return bean;
    }

    public void writeTextProperties(String path, String fileName, ConfigurationFxBean bean) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("name", bean.getUserName());
        properties.setProperty("email", bean.getUserEmailAddress());
        properties.setProperty("password", bean.getUserPassword());
        properties.setProperty("IMAPS", bean.getIMAPServer());
        properties.setProperty("SMTPS", bean.getSMTPServer());
        properties.setProperty("IMAPP", bean.getIMAPPort());
        properties.setProperty("SMTPP", bean.getSMTPPort());
        properties.setProperty("DBUrl", bean.getDBUrl());
        properties.setProperty("DBName", bean.getDBName());
        properties.setProperty("DBUser", bean.getDBUser());
        properties.setProperty("DBPort", bean.getDBPort());
        properties.setProperty("DBPassword", bean.getDBPassword());

        Path file = get(path, fileName + ".properties");
        try (OutputStream propFileStream = newOutputStream(file)) {
            properties.store(propFileStream, "configuration properties");
        }

    }
}
