package SystemTests;// JUnit

import model.Inquiry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

// class imports


class ConsultStaffSystemTests {
    private final TestHelper testHelper = new TestHelper(); // SystemTests.TestHelper class is used to set up the testing environment

    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }

    /**
     * Test case: User is a guest
     * This test verifies that a student can successfully add their email
     * success message is displayed.
     */
    @Test
    void testAddEmail() {
        String validEmail = "test@test.com\n";
        String subject = "Test Inquiry\n";
        String content = "This is a test message.\n";
        testHelper.mockInputOutput(validEmail + subject + content);

        testHelper.getInquirerController().contactStaff();

        List<Inquiry> inquiries = testHelper.getSharedContext().getInquiries();
        assert (!inquiries.isEmpty());
        assert (inquiries.get(0).getInquirerEmail().equals("test@test.com"));
        assert (inquiries.get(0).getSubject().equals("Test Inquiry"));
        assert (inquiries.get(0).getContent().equals("This is a test message."));

    }

    /**
     * Test case: User is a guest
     * This test verifies that a message is displayed when an invalid email is added
     */

    @Test
    void testEmailInvalid() {
        String invalidEmail = "invalidEmail\n";
        String validEmail = "guest@example.com\n";
        String subject = "Test Inquiry\n";
        String content = "This is a test message.\n";
        testHelper.mockInputOutput(invalidEmail + validEmail + subject + content);

        testHelper.getInquirerController().contactStaff();

        String output = testHelper.getOutContent().toString();
        assert (output.contains("Invalid email provided, please enter again using the format, email@domain:"));
    }

    /**
     * Test case: User is a guest
     * This test verifies that a message is displayed when an invalid email is added
     */
    @Test
    void testLoggedIn() {
        testHelper.setUpLoggedInStudent(); // Assuming this method logs in a user correctly

        String subject = "Test Inquiry\n";
        String content = "This is a test message.\n";

        testHelper.mockInputOutput(subject + content);

        testHelper.getInquirerController().contactStaff();

        // Assert an inquiry has been added with the authenticated user's email
        List<Inquiry> inquiries = testHelper.getSharedContext().getInquiries();
        assert (!inquiries.isEmpty());
        // this is the associated email for the setup student
        assert (inquiries.get(0).getInquirerEmail().equals("barb78916@hindenburg.ac.uk"));

    }


}