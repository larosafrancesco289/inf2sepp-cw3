package model;

import java.util.Date;

public class Inquiry {
    private Date createdAt;
    private String inquirerEmail;
    private String subject;
    private String content;
    private String assignedTo;

    public Inquiry(String inquirerEmail, String subject, String content) {
        this.createdAt = new Date(); // Assuming the current date is the creation date
        this.inquirerEmail = inquirerEmail;
        this.subject = subject;
        this.content = content;
        // assignedTo can be set based on some condition or through a setter
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getInquirerEmail() {
        return inquirerEmail;
    }

    public void setInquirerEmail(String inquirerEmail) {
        this.inquirerEmail = inquirerEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
