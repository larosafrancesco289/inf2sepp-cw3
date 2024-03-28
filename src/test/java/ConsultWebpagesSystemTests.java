import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsultWebpagesSystemTests {
    private final TestHelper testHelper = new TestHelper(); // TestHelper class is used to set up the testing environment

    /**
     * Sets up the testing environment before each test by logging in as an admin staff member.
     */
    @BeforeEach
    void setUp() {
        // Log in as an admin staff member
        testHelper.setUpPage();
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

    // TODO: Add more system tests
}
