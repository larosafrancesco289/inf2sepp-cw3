package model;

import java.util.Date;

/**
 * Represents an inquiry.
 * An inquiry can be assigned to a user.
 */
public class Inquiry {
    private Date createdAt; // The date when the inquiry was created
    private String inquirerEmail; // The email of the inquirer
    private String subject; // The subject of the inquiry
    private String content; // The content of the inquiry
    private String assignedTo; // The user to whom the inquiry is assigned

    /**
     * Constructs a new Inquiry object with the specified inquirer email, subject, and content.
     *
     * @param inquirerEmail the email of the inquirer
     * @param subject       the subject of the inquiry
     * @param content       the content of the inquiry
     */
    public Inquiry(String inquirerEmail, String subject, String content) {
        this.createdAt = new Date(); // Assuming the current date is the creation date
        this.inquirerEmail = inquirerEmail;
        this.subject = subject;
        this.content = content;
        // assignedTo can be set based on some condition or through a setter
    }

    /**
     * Retrieves the date when the inquiry was created.
     *
     * @return the creation date of the inquiry
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date when the inquiry was created.
     *
     * @param createdAt the new creation date of the inquiry
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the email of the inquirer.
     *
     * @return the email of the inquirer
     */
    public String getInquirerEmail() {
        return inquirerEmail;
    }

    /**
     * Sets the email of the inquirer.
     *
     * @param inquirerEmail the new email of the inquirer
     */
    public void setInquirerEmail(String inquirerEmail) {
        this.inquirerEmail = inquirerEmail;
    }

    /**
     * Retrieves the subject of the inquiry.
     *
     * @return the subject of the inquiry
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject of the inquiry.
     *
     * @param subject the new subject of the inquiry
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Retrieves the content of the inquiry.
     *
     * @return the content of the inquiry
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the inquiry.
     *
     * @param content the new content of the inquiry
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Retrieves the user to whom the inquiry is assigned.
     *
     * @return the user to whom the inquiry is assigned
     */
    public String getAssignedTo() {
        return assignedTo;
    }

    /**
     * Sets the user to whom the inquiry is assigned.
     *
     * @param assignedTo the new user to whom the inquiry is assigned
     */
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

}