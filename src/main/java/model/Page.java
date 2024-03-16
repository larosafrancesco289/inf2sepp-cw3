package model;

public class Page {
    private String title;
    private String content;
    private boolean isPrivate;

    public Page(String title, String content, boolean isPrivate) {
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
