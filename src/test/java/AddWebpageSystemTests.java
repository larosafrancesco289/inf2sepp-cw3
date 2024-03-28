import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddWebpageSystemTests {
    private final TestHelper testHelper = new TestHelper(); // TestHelper class is used to set up the testing environment

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
        assertTrue(testHelper.getOutContent().toString().contains("Added page " + title));
    }

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
        assertTrue(testHelper.getSharedContext().getPages().containsKey(title));
    }

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
        assertTrue(testHelper.getOutContent().toString().contains("Cancelled adding new page"));
    }

    // TODO: Don't know how to test email service failing
    @Test
    void emailServiceFail() {
        //testing email service failing

    }

}
