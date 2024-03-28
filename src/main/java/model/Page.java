package model;

/**
 * The Page class represents a page object with a title, content, and privacy status.
 */
public class Page {
    private String title; // The title of the page
    private String content; // the address of the txt file of the page.
    private boolean isPrivate; // The privacy status of the page

    /**
     * Constructs a new Page object with the specified title, content, and privacy status.
     *
     * @param title     the title of the page
     * @param content   the address of the txt file of the page.
     * @param isPrivate the privacy status of the page
     */
    public Page(String title, String content, boolean isPrivate) {
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
    }

    /**
     * Returns the title of the page.
     *
     * @return the title of the page
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the page.
     *
     * @param title the new title of the page
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the address of the txt file of the page.
     *
     * @return the address of the txt file of the page.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the address of the txt file of the page.
     *
     * @param content the new content of the page
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the privacy status of the page.
     *
     * @return true if the page is private, false otherwise
     */
    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     * Sets the privacy status of the page.
     *
     * @param isPrivate the new privacy status of the page
     */
    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
}