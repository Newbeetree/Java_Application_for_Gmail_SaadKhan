package com.saadkhan.presentation;

import com.saadkhan.data.EmailBean;
import com.saadkhan.data.FileAttachmentBean;
import com.saadkhan.data.Priority;
import com.saadkhan.persistence.EmailDOA;
import com.saadkhan.persistence.EmailDOAImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDateTime;

import jodd.mail.EmailAddress;

public class main {
    private static EmailDOA emailDOA = new EmailDOAImpl();

    public static void main(String args[]){
        try {
            EmailBean eb = createBasicBean();
            emailDOA.createEmailBean(eb);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static EmailBean createBasicBean() throws IOException {
        EmailBean bean = new EmailBean();
        bean.setFrom(new EmailAddress("user name", "send.1633839@gmail.com"));
        bean.getTo().add(new EmailAddress("receiver", "receive.1633839@gmail.com"));
        bean.getCc().add(new EmailAddress("other", "other.1633839@gmail.com"));
        bean.setSubject("test14");
        bean.setMessage("hello testing 1 2 3 4 5 6");
        bean.setSend(LocalDateTime.now());
        bean.setFolder("Sent");
        bean.setHtmlMessage("<html><META http-equiv=Content-Type "
                + "content=\"text/html; charset=utf-8\">"
                + "<body><h1>Here is my photograph embedded in "
                + "this email.</h1><img src='cid:WindsorKen180.jpg'>"
                + "<h2>I'm flying!</h2></body></html>");
        bean.setPriority(Priority.PRIORITY_NORMAL);
        FileAttachmentBean fa = new FileAttachmentBean();
        fa.setName("WindsorKen180.jpg");
        fa.setFile(Files.readAllBytes(new File("WindsorKen180.jpg").toPath()));
        fa.setType(true);
        bean.getAttachments().add(fa);
        return bean;
    }

}
