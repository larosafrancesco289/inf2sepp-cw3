package SystemTests;

import org.junit.jupiter.api.*;

/**
 * System tests for the Consult Webpages use case.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is used to ensure that the setUp method is run only once
class ConsultWebpagesSystemTests {
    private final TestHelper testHelper = new TestHelper(); // SystemTests.TestHelper class is used to set up the testing environment

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
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Per aspera ad astra"));
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
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("No results found for query: " + searchQuery));
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
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Article"));
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Webpage"));

        // Blog is a private page, so it should not be displayed
        Assertions.assertFalse(testHelper.getOutContent().toString().contains("Blog"));
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
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Article"));
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Webpage"));

        // Blog is a private page, but the user is logged in, so it should be displayed
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Blog"));
    }

    /**
     * Tests for an empty query.
     */
    @Test
    void testEmptyQuery() {
        // Search for pages
        String searchQuery = "\n";
        // Enter an empty query followed by a valid query
        testHelper.mockInputOutput(searchQuery + "\n" + "Per aspera ad astra" + "\n");
        testHelper.getInquirerController().searchPages();

        // Verify that no results are found
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Please enter a valid search query: "));
    }

    @Test
    void testInvalidQuery() {
        // Search for pages
        // Invalid query: missing closing parenthesis
        String searchQuery = "title:(One Two OR AND text:";
        // Enter an invalid query followed by a valid query
        testHelper.mockInputOutput(searchQuery + "\n" + "Per aspera ad astra" + "\n");
        testHelper.getInquirerController().searchPages();

        // Verify that no results are found
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Invalid search query. Please try again."));
    }
}
