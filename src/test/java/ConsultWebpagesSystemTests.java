import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * System tests for the Consult Webpages use case.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is used to ensure that the setUp method is run only once
class ConsultWebpagesSystemTests {
    private final TestHelper testHelper = new TestHelper(); // TestHelper class is used to set up the testing environment

    /**
     * Sets up the testing environment before all tests by adding the pages.
     */
    @BeforeAll
    void setUp() {
        testHelper.setUpPages();
    }

    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }

    /**
     * Tests for a query present in the pages.
     */
    @Test
    void testCommonQuery() {
        // Search for pages
        String searchQuery = "Per aspera ad astra";
        testHelper.mockInputOutput(searchQuery + "\n");
        testHelper.getInquirerController().searchPages();

        // Verify that the search results are displayed
        assertTrue(testHelper.getOutContent().toString().contains("Per aspera ad astra"));
    }

    /**
     * Tests for a query not present in the pages.
     */
    @Test
    void testNoMatch() {
        // Search for pages
        String searchQuery = "Superman";
        testHelper.mockInputOutput(searchQuery + "\n");
        testHelper.getInquirerController().searchPages();

        // Verify that no results are found
        assertTrue(testHelper.getOutContent().toString().contains("No results found for query: " + searchQuery));
    }

    /**
     * Tests for a query present in a private page, but the user is a guest.
     */
    @Test
    void testPrivatePageGuest() {
        // Log out
        testHelper.setUpGuest();

        // Search for pages
        String searchQuery = "Per aspera ad astra";
        testHelper.mockInputOutput(searchQuery + "\n");
        testHelper.getInquirerController().searchPages();

        // Verify that only two pages are displayed
        assertTrue(testHelper.getOutContent().toString().contains("Article"));
        assertTrue(testHelper.getOutContent().toString().contains("Webpage"));

        // Blog is a private page, so it should not be displayed
        assertFalse(testHelper.getOutContent().toString().contains("Blog"));
    }

    /**
     * Tests for a query present in a private page, but the user is a logged in user.
     */
    @Test
    void testPrivatePageLoggedInUser() {
        // Log in as a student
        testHelper.setUpLoggedInStudent();

        // Search for pages
        String searchQuery = "Per aspera ad astra";
        testHelper.mockInputOutput(searchQuery + "\n");
        testHelper.getInquirerController().searchPages();

        // Verify that all three pages are displayed
        assertTrue(testHelper.getOutContent().toString().contains("Article"));
        assertTrue(testHelper.getOutContent().toString().contains("Webpage"));

        // Blog is a private page, but the user is logged in, so it should be displayed
        assertTrue(testHelper.getOutContent().toString().contains("Blog"));
    }
}
