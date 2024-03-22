import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import external.MockEmailService;

import static org.junit.jupiter.api.Assertions.*;

import model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Collection;

public class TestPageSearch {
    private PageSearch pageSearch;

    // Initialize some pages for testing
    @BeforeEach
    void setUp() {
        Page page1 = new Page("Page 1", "This is the content for page 1", false);
        Page page2 = new Page("Page 2", "This is the content for page 2", false);
        Page page3 = new Page("Page 3", "This is the content for page 3", false);
        Page page4 = new Page("Page 4", "This is the content for page 4", false);
        Page page5 = new Page("Page 5", "This is the content for page 5", false);
        Page unique1 = new Page("Unique 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", false);
        Page unique2 = new Page("Unique 2", "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", false);
        pageSearch = new PageSearch(new HashMap<String, Page>() {{
            put("Page 1", page1);
            put("Page 2", page2);
            put("Page 3", page3);
            put("Page 4", page4);
            put("Page 5", page5);
            put("Unique 1", unique1);
            put("Unique 2", unique2);
        }});
    }

    @Test
    void testSizeOfSearchResults() {
        Collection<PageSearchResult> results = pageSearch.search("content");
        assertEquals(5, results.size(), "All pages should match the search query");
    }

    @Test
    void testSearchResultsFormatting() {
        Collection<PageSearchResult> results = pageSearch.search("content");
        for (PageSearchResult result : results) {
            assertNotNull(result.getFormattedContent(), "Search results should have formatted content");
        }
    }

    @Test
    void testUniqueResult() {
        Collection<PageSearchResult> results = pageSearch.search("lorem");
        assertEquals(1, results.size(), "Only one page should match the search query");
    }
}
