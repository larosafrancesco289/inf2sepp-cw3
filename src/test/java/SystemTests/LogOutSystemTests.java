package SystemTests;

import model.Guest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Logout use case.
 */
class LogOutSystemTests {
    private final TestHelper testHelper = new TestHelper(); // SystemTests.TestHelper class is used to set up the testing environment

    /**
     * Sets up the testing environment before each test by logging in.
     */
    @BeforeEach
    void setUp() {
        testHelper.setUpLoggedInStudent();
    }

    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }

    /**
     * Test case: Successful logout.
     * This test verifies that an authenticated user can successfully log out and that the appropriate
     * success message is displayed.
     */
    @Test
    void testLoggedOut() {
        // Log out
        testHelper.getAuthenticatedUserController().logout();

        // Assert that the logout was successful and the current user is now a guest.
        assert (testHelper.getSharedContext().getCurrentUser() instanceof Guest);
        assert (testHelper.getOutContent().toString().contains("You have been logged out"));
    }
}
