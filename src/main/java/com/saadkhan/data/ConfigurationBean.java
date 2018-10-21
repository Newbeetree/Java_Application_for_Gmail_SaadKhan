package com.saadkhan.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConfigurationBean {
    private final StringProperty userName;
    private final StringProperty userEmailAddress;
    private final StringProperty userPassword;
    private final StringProperty IMAPServer;
    private final StringProperty SMTPServer;
    private final StringProperty IMAPPort;
    private final StringProperty SMTPPort;
    private final StringProperty DBUrl;
    private final StringProperty DBName;
    private final StringProperty DBPort;
    private final StringProperty DBUser;
    private final StringProperty DBPassword;

    public ConfigurationBean(String userName, String userEmailAddress,
                             String userPassword, String IMAPServer,
                             String SMTPServer, String IMAPPort,
                             String SMTPPort, String DBUrl,
                             String DBName, String DBPort,
                             String DBUser, String DBPassword) {
        this.userName = new SimpleStringProperty(userName);
        this.userEmailAddress = new SimpleStringProperty(userEmailAddress);
        this.userPassword = new SimpleStringProperty(userPassword);
        this.IMAPServer = new SimpleStringProperty(IMAPServer);
        this.SMTPServer = new SimpleStringProperty(SMTPServer);
        this.IMAPPort = new SimpleStringProperty(IMAPPort);
        this.SMTPPort = new SimpleStringProperty(SMTPPort);
        this.DBUrl = new SimpleStringProperty(DBUrl);
        this.DBName = new SimpleStringProperty(DBName);
        this.DBPort = new SimpleStringProperty(DBPort);
        this.DBUser = new SimpleStringProperty(DBUser);
        this.DBPassword = new SimpleStringProperty(DBPassword);
    }

    public ConfigurationBean(){
        this("","","","","","",
                "","","","","","");
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getUserEmailAddress() {
        return userEmailAddress.get();
    }

    public StringProperty userEmailAddressProperty() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress.set(userEmailAddress);
    }

    public String getUserPassword() {
        return userPassword.get();
    }

    public StringProperty userPasswordProperty() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword.set(userPassword);
    }

    public String getIMAPServer() {
        return IMAPServer.get();
    }

    public StringProperty IMAPServerProperty() {
        return IMAPServer;
    }

    public void setIMAPServer(String IMAPServer) {
        this.IMAPServer.set(IMAPServer);
    }

    public String getSMTPServer() {
        return SMTPServer.get();
    }

    public StringProperty SMTPServerProperty() {
        return SMTPServer;
    }

    public void setSMTPServer(String SMTPServer) {
        this.SMTPServer.set(SMTPServer);
    }

    public String getIMAPPort() {
        return IMAPPort.get();
    }

    public StringProperty IMAPPortProperty() {
        return IMAPPort;
    }

    public void setIMAPPort(String IMAPPort) {
        this.IMAPPort.set(IMAPPort);
    }

    public String getSMTPPort() {
        return SMTPPort.get();
    }

    public StringProperty SMTPPortProperty() {
        return SMTPPort;
    }

    public void setSMTPPort(String SMTPPort) {
        this.SMTPPort.set(SMTPPort);
    }

    public String getDBUrl() {
        return DBUrl.get();
    }

    public StringProperty DBUrlProperty() {
        return DBUrl;
    }

    public void setDBUrl(String DBUrl) {
        this.DBUrl.set(DBUrl);
    }

    public String getDBName() {
        return DBName.get();
    }

    public StringProperty DBNameProperty() {
        return DBName;
    }

    public void setDBName(String DBName) {
        this.DBName.set(DBName);
    }

    public String getDBPort() {
        return DBPort.get();
    }

    public StringProperty DBPortProperty() {
        return DBPort;
    }

    public void setDBPort(String DBPort) {
        this.DBPort.set(DBPort);
    }

    public String getDBUser() {
        return DBUser.get();
    }

    public StringProperty DBUserProperty() {
        return DBUser;
    }

    public void setDBUser(String DBUser) {
        this.DBUser.set(DBUser);
    }

    public String getDBPassword() {
        return DBPassword.get();
    }

    public StringProperty DBPasswordProperty() {
        return DBPassword;
    }

    public void setDBPassword(String DBPassword) {
        this.DBPassword.set(DBPassword);
    }
}
