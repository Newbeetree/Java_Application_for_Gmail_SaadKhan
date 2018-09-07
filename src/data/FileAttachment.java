package data;

import java.io.Serializable;

public class FileAttachment implements Serializable {

    private byte[] file;
    private String name;

    public FileAttachment() {
    }

    public byte[] getFile() {
        return file;
    }

    public FileAttachment setFile(byte[] file) {
        this.file = file;
        return this;
    }

    public String getName() {
        return name;
    }

    public FileAttachment setName(String name) {
        this.name = name;
        return this;
    }
}
