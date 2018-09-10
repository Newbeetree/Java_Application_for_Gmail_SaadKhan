package data;

import java.io.Serializable;

public class FileAttachmentBean implements Serializable {

    private byte[] file;
    private String name;

    public FileAttachmentBean() {
    }

    public byte[] getFile() {
        return file;
    }

    public FileAttachmentBean setFile(byte[] file) {
        this.file = file;
        return this;
    }

    public String getName() {
        return name;
    }

    public FileAttachmentBean setName(String name) {
        this.name = name;
        return this;
    }
}
