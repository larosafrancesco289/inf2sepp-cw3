package model;

public class PageSearchResult {
    private String formattedContent;

    public PageSearchResult(String formattedContent) {
        this.formattedContent = formattedContent;
    }

    public String getFormattedContent() {
        return formattedContent;
    }

    public void setFormattedContent(String formattedContent) {
        this.formattedContent = formattedContent;
    }
}
