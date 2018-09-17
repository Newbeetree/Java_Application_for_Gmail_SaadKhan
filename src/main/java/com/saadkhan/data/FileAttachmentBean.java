package com.saadkhan.data;

import java.io.Serializable;

/**
 * FileAttachmentBean is serializable and has getters and setters for files and
 * the names of said files to be used by the EmailBean
 *
 * @Author: Saad Khan 1633839
 */
public class FileAttachmentBean implements Serializable {

    private byte[] file;
    private String name;

    /**
     * constructor for default FileAttachmentBean
     */
    public FileAttachmentBean() {
    }

    /**
     * onstructor for file attachments with some paramenters
     *
     * @param file the byte array of the file
     * @param name the name of said file
     */
    public FileAttachmentBean(byte[] file, String name) {
        this.file = file;
        this.name = name;
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
        return name;
    }

    /**
     * @param name name of file to be set
     */
    public FileAttachmentBean setName(String name) {
        this.name = name;
        return this;
    }
}
