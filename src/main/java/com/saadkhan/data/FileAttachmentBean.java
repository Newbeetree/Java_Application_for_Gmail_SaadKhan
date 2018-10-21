package com.saadkhan.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * FileAttachmentBean is serializable and has getters and setters for files and
 * the names of said files to be used by the EmailBean
 *
 * @Author: Saad Khan 1633839
 */
public class FileAttachmentBean implements Serializable {

    private IntegerProperty attachID;
    private byte[] file;
    private StringProperty name;
    private BooleanProperty type;

    /**
     * constructor for default FileAttachmentBean
     */
    public FileAttachmentBean() {
        this(-1, null, "", false);
    }

    /**
     * onstructor for file attachments with some paramenters
     *
     * @param file the byte array of the file
     * @param name the name of said file
     */
    public FileAttachmentBean(final int id, final byte[] file, final String name, final boolean type) {
        super();
        this.attachID = new SimpleIntegerProperty(id);
        this.file = file;
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleBooleanProperty(type);
    }

    public int getAttachID() {
        return attachID.get();
    }

    public FileAttachmentBean setAttachID(final int attachID) {
        this.attachID.set(attachID);
        return this;
    }

    public IntegerProperty AttachIDPropertty() {
        return attachID;
    }

    /**
     * @return byte[] of file
     */
    public byte[] getFile() {
        return file;
    }

    /**
     * @param file byte[] of file to set
     */
    public FileAttachmentBean setFile(byte[] file) {
        this.file = file;
        return this;
    }

    /**
     * @return String name of file
     */
    public String getName() {
        return name.get();
    }

    /**
     * @param name name of file to be set
     */
    public FileAttachmentBean setName(String name) {
        this.name.set(name);
        return this;
    }

    public StringProperty nameProperty(){
        return name;
    }

    /**
     * @return type of file
     */
    public boolean getType() {
        return type.get();
    }

    /**
     * @param type type of the file to attach, either imbedded or attached
     *             true for imbed
     *             false for attach
     */
    public FileAttachmentBean setType(boolean type) {
        this.type.set(type);
        return this;
    }

    public BooleanProperty typeProperty(){
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileAttachmentBean that = (FileAttachmentBean) o;
        return getType() == that.getType() &&
                Arrays.equals(getFile(), that.getFile()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(getName(), getType());
        result = 31 * result + Arrays.hashCode(getFile());
        return result;
    }
}
