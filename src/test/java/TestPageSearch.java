import model.Page;
import model.PageSearch;
import model.PageSearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite for the {@link PageSearch} class, focusing on the functionality of indexing pages
 * and performing search operations.
 */

public class TestPageSearch {
    private PageSearch pageSearch;

    /**
     * Sets up the testing environment before each test method. This includes initializing a
     * {@link PageSearch} instance with three predefined pages. Each page is created with
     * a unique title and content sourced from text files located in the test resources directory.
     */
    @BeforeEach
    void setUp() {
        URL dataPath = getClass().getResource("/examplePage1.txt");
        Page page1 = new Page("Article", dataPath.getPath(), false);

        dataPath = getClass().getResource("/examplePage2.txt");
        Page page2 = new Page("Webpage", dataPath.getPath(), false);

        dataPath = getClass().getResource("/examplePage3.txt");
        Page page3 = new Page("Blog", dataPath.getPath(), false);

        pageSearch = new PageSearch(new HashMap<String, Page>() {{
            put("Article", page1);
            put("Webpage", page2);
            put("Blog", page3);
        }});
    }

    @Test
    void testSizeOfSearchResultsForCommonPhrase() {
        // Execute a search query for a phrase expected to be common across all documents
        Collection<PageSearchResult> results = pageSearch.search("Per aspera ad astra");
        // Assert that the size of the search results matches the number of documents, indicating
        // that the phrase was found in each document.
        assertEquals(3, results.size(), "Expected to match the common phrase in all documents.");
    }

    @Test
    void testSearchResultsForSpecificPhrase() {
        // Perform a search for a specific phrase enclosed in quotes to ensure an exact match search
        Collection<PageSearchResult> results = pageSearch.search("\"dog, cat, bird\"");
        // Retrieve the first (and in this case, expected to be the only) search result
        PageSearchResult result = results.iterator().next();
        assertTrue(result.getFormattedContent().contains("dog, cat, bird"), "Result should contain the searched phrase.");
    }

    @Test
    void testNoMatchSearchResults() {
        // Search for a phrase that is expected not to exist in any of the indexed documents
        Collection<PageSearchResult> results = pageSearch.search("nonexistent phrase");
        assertTrue(results.isEmpty(), "Expected no matches for a nonexistent phrase.");
    }
}
