import model.Page;
import model.PageSearch;
import model.PageSearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
    private PageSearch pageSearch; // The PageSearch instance to be tested

    /**
     * Sets up the testing environment before each test method. This includes initializing a
     * {@link PageSearch} instance with three predefined pages. Each page is created with
     * a unique title and content sourced from text files located in the test resources directory.
     */
    @BeforeEach
    void setUp() throws IOException {
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

    /**
     * Tests the ability of {@link PageSearch} to return a correct number of search results
     * when searching for a common phrase expected to be found across all indexed documents.
     *
     * @throws Exception If an error occurs during the search operation.
     */
    @Test
    void testSizeOfSearchResultsForCommonPhrase() throws Exception {
        // Execute a search query for a phrase expected to be common across all documents
        Collection<PageSearchResult> results = pageSearch.search("Per aspera ad astra");
        // Assert that the size of the search results matches the number of documents, indicating
        // that the phrase was found in each document.
        assertEquals(3, results.size(), "Expected to match the common phrase in all documents.");
    }

    /**
     * Tests the ability of {@link PageSearch} to return search results containing the searched phrase
     * when searching for a specific phrase that is expected to be found in one of the indexed documents.
     *
     * @throws Exception If an error occurs during the search operation.
     */
    @Test
    void testSearchResultsForSpecificPhrase() throws Exception {
        // Perform a search for a specific phrase enclosed in quotes to ensure an exact match search
        Collection<PageSearchResult> results = pageSearch.search("\"dog, cat, bird\"");
        // Retrieve the first (and in this case, expected to be the only) search result
        PageSearchResult result = results.iterator().next();
        assertTrue(result.getFormattedContent().contains("dog, cat, bird"), "Result should contain the searched phrase.");
    }

    /**
     * Tests the ability of {@link PageSearch} to return no search results when searching for a phrase
     * that is expected not to exist in any of the indexed documents.
     *
     * @throws Exception If an error occurs during the search operation.
     */
    @Test
    void testNoMatchSearchResults() throws Exception {
        // Search for a phrase that is expected not to exist in any of the indexed documents
        Collection<PageSearchResult> results = pageSearch.search("nonexistent phrase");
        assertTrue(results.isEmpty(), "Expected no matches for a nonexistent phrase.");
    }
}
