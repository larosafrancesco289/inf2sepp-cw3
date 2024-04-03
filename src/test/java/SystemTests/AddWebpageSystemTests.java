package SystemTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

/**
 * System tests for the add webpage use case.
 */
class AddWebpageSystemTests {
    private final TestHelper testHelper = new TestHelper(); // SystemTests.TestHelper class is used to set up the testing environment

    /**
     * Sets up the testing environment before each test by logging in as an admin staff member.
     */
    @BeforeEach
    void setUp() {
        testHelper.setUpLoggedInAdminStaff();
    }

    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }

    /**
     * Tests adding a new page to the system.
     */
    @Test
    void testAddNewPage() {
        // Add a new page
        String title = "Article";
        URL dataPath = getClass().getResource("/examplePage1.txt");
        String content = dataPath.getPath();
        String isPrivate = "no";
        testHelper.mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n");
        testHelper.getAdminStaffController().addPage();

        // Verify that the page was added
        assert (testHelper.getSharedContext().getPages().containsKey(title));
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Added page " + title));
    }

    /**
     * Tests the overwrite functionality when adding a page with the same title as an existing page.
     */
    @Test
    void testAddExistingPageOverwrite() {
        // Add a new page
        String title = "Article";
        URL dataPath = getClass().getResource("/examplePage1.txt");
        String content = dataPath.getPath();
        String isPrivate = "no";
        testHelper.mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n");
        testHelper.getAdminStaffController().addPage();

        // Add another page with the same title, overwriting the existing page
        content = getClass().getResource("/examplePage2.txt").getPath();
        testHelper.mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n" + "yes");
        testHelper.getAdminStaffController().addPage();

        // Check if sharedContext contains the new page by the content
        Assertions.assertTrue(testHelper.getSharedContext().getPages().containsKey(title));
    }

    /**
     * Tests adding a page with the same title as an existing page without overwriting the existing page.
     */
    @Test
    void testAddExistingPageNoOverwrite() {
        // Add a new page
        String title = "Article";
        URL dataPath = getClass().getResource("/examplePage1.txt");
        String content = dataPath.getPath();
        String isPrivate = "no";
        testHelper.mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n");
        testHelper.getAdminStaffController().addPage();

        // Add the same page again, not overwriting the existing page
        testHelper.mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n" + "no");
        testHelper.getAdminStaffController().addPage();

        // Confirm that the page was not overwritten
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Cancelled adding new page"));
    }
}
