package SystemTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Login use case.
 */
class LogInSystemTests {
    private final TestHelper testHelper = new TestHelper(); // SystemTests.TestHelper class is used to set up the testing environment

    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }

    /**
     * Test case: Successful login as a student.
     * This test verifies that a student can successfully log in and that the appropriate
     * success message is displayed.
     */
    @Test
    void testSuccessfulLoginStudent() {
        String username = "Barbie";
        String password = "I like pink muffs and I cannot lie";
        testHelper.mockInputOutput(username + "\n" + password + "\n");
        testHelper.getGuestController().login();

        // Assert that the login was successful and the correct role is set.
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Login successful"));
        Assertions.assertEquals("Student", testHelper.getSharedContext().getCurrentUser().getRole());
    }

    /**
     * Test case: Successful login as an admin.
     * This test verifies that an admin can successfully log in and that the appropriate
     * success message is displayed.
     */
    @Test
    void testSuccessfulLoginAdminStaff() {
        String username = "JackTheRipper";
        String password = "catch me if u can";
        testHelper.mockInputOutput(username + "\n" + password + "\n");
        testHelper.getGuestController().login();

        // Assert that the login was successful and the correct role is set.
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Login successful"));
        Assertions.assertEquals("AdminStaff", testHelper.getSharedContext().getCurrentUser().getRole());
    }

    /**
     * Test case: Successful login as a teaching staff.
     * This test verifies that a teaching staff member can successfully log in and that the appropriate
     * success message is displayed.
     */
    @Test
    void testSuccessfulLoginTeachingStaff() {
        String username = "JSON Derulo";
        String password = "Desrouleaux";
        testHelper.mockInputOutput(username + "\n" + password + "\n");
        testHelper.getGuestController().login();

        // Assert that the login was successful and the correct role is set.
        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Login successful"));
        Assertions.assertEquals("TeachingStaff", testHelper.getSharedContext().getCurrentUser().getRole());
    }

    /**
     * Test case: Display of error message upon login failure.
     * This test verifies that an appropriate error message is displayed when login fails.
     */
    @Test
    void testWrongUsernameOrPassword() {
        String username = "Barbie";
        String password = "";
        testHelper.mockInputOutput(username + "\n" + password + "\n");
        testHelper.getGuestController().login();

        Assertions.assertTrue(testHelper.getOutContent().toString().contains("Wrong username or password"));
    }

    // TODO: Add more test cases for email null and role null or different than expected
}
