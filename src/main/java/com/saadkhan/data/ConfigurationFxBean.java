package com.saadkhan.data;

import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConfigurationFxBean {
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

    public ConfigurationFxBean(String userName, String userEmailAddress,
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

    public ConfigurationFxBean() {
        this("", "", "", "", "", "",
                "", "", "", "", "", "");
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public String getUserEmailAddress() {
        return userEmailAddress.get();
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress.set(userEmailAddress);
    }

    public StringProperty userEmailAddressProperty() {
        return userEmailAddress;
    }

    public String getUserPassword() {
        return userPassword.get();
    }

    public void setUserPassword(String userPassword) {
        this.userPassword.set(userPassword);
    }

    public StringProperty userPasswordProperty() {
        return userPassword;
    }

    public String getIMAPServer() {
        return IMAPServer.get();
    }

    public void setIMAPServer(String IMAPServer) {
        this.IMAPServer.set(IMAPServer);
    }

    public StringProperty IMAPServerProperty() {
        return IMAPServer;
    }

    public String getSMTPServer() {
        return SMTPServer.get();
    }

    public void setSMTPServer(String SMTPServer) {
        this.SMTPServer.set(SMTPServer);
    }

    public StringProperty SMTPServerProperty() {
        return SMTPServer;
    }

    public String getIMAPPort() {
        return IMAPPort.get();
    }

    public void setIMAPPort(String IMAPPort) {
        this.IMAPPort.set(IMAPPort);
    }

    public StringProperty IMAPPortProperty() {
        return IMAPPort;
    }

    public String getSMTPPort() {
        return SMTPPort.get();
    }

    public void setSMTPPort(String SMTPPort) {
        this.SMTPPort.set(SMTPPort);
    }

    public StringProperty SMTPPortProperty() {
        return SMTPPort;
    }

    public String getDBUrl() {
        return DBUrl.get();
    }

    public void setDBUrl(String DBUrl) {
        this.DBUrl.set(DBUrl);
    }

    public StringProperty DBUrlProperty() {
        return DBUrl;
    }

    public String getDBName() {
        return DBName.get();
    }

    public void setDBName(String DBName) {
        this.DBName.set(DBName);
    }

    public StringProperty DBNameProperty() {
        return DBName;
    }

    public String getDBPort() {
        return DBPort.get();
    }

    public void setDBPort(String DBPort) {
        this.DBPort.set(DBPort);
    }

    public StringProperty DBPortProperty() {
        return DBPort;
    }

    public String getDBUser() {
        return DBUser.get();
    }

    public void setDBUser(String DBUser) {
        this.DBUser.set(DBUser);
    }

    public StringProperty DBUserProperty() {
        return DBUser;
    }

    public String getDBPassword() {
        return DBPassword.get();
    }

    public void setDBPassword(String DBPassword) {
        this.DBPassword.set(DBPassword);
    }

    public StringProperty DBPasswordProperty() {
        return DBPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationFxBean that = (ConfigurationFxBean) o;
        return Objects.equals(getUserName(), that.getUserName()) &&
                Objects.equals(getUserEmailAddress(), that.getUserEmailAddress()) &&
                Objects.equals(getUserPassword(), that.getUserPassword()) &&
                Objects.equals(getIMAPServer(), that.getIMAPServer()) &&
                Objects.equals(getSMTPServer(), that.getSMTPServer()) &&
                Objects.equals(getIMAPPort(), that.getIMAPPort()) &&
                Objects.equals(getSMTPPort(), that.getSMTPPort()) &&
                Objects.equals(getDBUrl(), that.getDBUrl()) &&
                Objects.equals(getDBName(), that.getDBName()) &&
                Objects.equals(getDBPort(), that.getDBPort()) &&
                Objects.equals(getDBUser(), that.getDBUser()) &&
                Objects.equals(getDBPassword(), that.getDBPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getUserEmailAddress(), getUserPassword(), getIMAPServer(), getSMTPServer(), getIMAPPort(), getSMTPPort(), getDBUrl(), getDBName(), getDBPort(), getDBUser(), getDBPassword());
    }
}
