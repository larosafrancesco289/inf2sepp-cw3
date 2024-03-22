package model;

/**
 * Represents a search result from the PageSearch class.
 * It encapsulates formatted content retrieved from the search operation.
 */
public class PageSearchResult {
    private String formattedContent; // The formatted content of the search result

    /**
     * Constructs a new PageSearchResult object with the specified formatted content.
     *
     * @param formattedContent the formatted content of the search result
     */
    public PageSearchResult(String formattedContent) {
        this.formattedContent = formattedContent;
    }

    /**
     * Retrieves the formatted content of the search result.
     *
     * @return the formatted content
     */
    public String getFormattedContent() {
        return formattedContent;
    }

    /**
     * Sets the formatted content of the search result.
     *
     * @param formattedContent the new formatted content
     */
    public void setFormattedContent(String formattedContent) {
        this.formattedContent = formattedContent;
    }
}
