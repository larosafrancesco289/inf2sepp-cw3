package SystemTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests answering inquiry functionality for both {@link controller.AdminStaffController} and {@link controller.TeachingStaffController}
 */
class AnswerInquirySystemTests {

    private final TestHelper testHelper = new TestHelper(); // TestHelper class is used to set up the testing environment


    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }


    /**
     * Test case: Empty Answer error message
     * Checks for an empty answer error message when a user of type AdminStaff is answering an inquiry
     */
    @Test
    void testEmptyAnswerAdmin() {
        //test if answer is empty
        //should return "Answer cannot be empty"

        // add mock inquiry
        testHelper.setupMockInquiry(false);

        //test for admin user with at least 1 inquiry
        testHelper.setUpLoggedInAdminStaff();

        testHelper.mockInputOutput("1" + "\n" + "yes" + "\n" + "\n" + "ok");
        testHelper.getAdminStaffController().manageInquiries();

        assertTrue(testHelper.getOutContent().toString().contains("Answer cannot be empty"));

    }

    /**
     * Test case: Empty Answer error message
     * Checks for an empty answer error message when a user of type TeachingStaff is answering an inquiry
     */
    @Test
    void testEmptyAnswerTeaching() {
        //test if answer is empty
        //should return "Answer cannot be empty"

        // add mock inquiry
        testHelper.setupMockInquiry(true);

        //test for admin user with at least 1 inquiry
        testHelper.setUpLoggedInTeachingStaff();

        testHelper.mockInputOutput("0" + "\n" + "yes" + "\n" + "\n" + "ok" + "\n" + "-1");
        testHelper.getTeachingStaffController().manageReceivedInquiries();

        assertTrue(testHelper.getOutContent().toString().contains("Answer cannot be empty"));

    }

    /**
     * Test case: Answer Inquiry success message
     * Checks for success message when a user of type AdminStaff answers an inquiry
     */
    @Test
    void successAdmin() {
        //test success scenario, should display success message

        // add mock inquiry
        testHelper.setupMockInquiry(false);

        //test for admin user with at least 1 inquiry
        testHelper.setUpLoggedInAdminStaff();

        testHelper.mockInputOutput("1" + "\n" + "yes" + "\n" + "ok");
        testHelper.getAdminStaffController().manageInquiries();

        assertTrue(testHelper.getOutContent().toString().contains("Answer sent to test@test.com"));

    }

    /**
     * Test case: Answer Inquiry success message
     * Checks for success message when a user of type TeachingStaff answers an inquiry
     */
    @Test
    void successTeaching() {
        //test success scenario, should display success message

        //Setup
        testHelper.setupMockInquiry(true);
        testHelper.setUpLoggedInTeachingStaff();

        // run test
        testHelper.mockInputOutput("0" + "\n" + "yes" + "\n" + "\n" + "hi");
        testHelper.getTeachingStaffController().manageReceivedInquiries();

        // check for message
        assertTrue(testHelper.getOutContent().toString().contains("Answer sent to test@test.com"));

    }

    /**
     * Test case: Remove Inquiry after answered
     * Checks that the inquiry is removed from the list in SharedContext after answered by a user of type AdminStaff
     */
    @Test
    void testInquiryRemovedAdmin() {
        //test if inquiry successfully removed from SharedContext Inquiry list

        // add mock inquiry
        testHelper.setupMockInquiry(false);

        //test for admin user with at least 1 inquiry
        testHelper.setUpLoggedInAdminStaff();

        testHelper.mockInputOutput("1" + "\n" + "yes" + "\n" + "ok");
        testHelper.getAdminStaffController().manageInquiries();

        assertTrue(testHelper.getSharedContext().getInquiries().isEmpty());

    }


    /**
     * Test case: Remove Inquiry after answered
     * Checks that the inquiry is removed from the list in SharedContext after answered by a user of type TeachingStaff
     */
    @Test
    void testInquiryRemovedTeaching() {
        //test if inquiry successfully removed from SharedContext Inquiry list

        //Setup
        testHelper.setupMockInquiry(true);
        testHelper.setUpLoggedInTeachingStaff();

        // run test
        testHelper.mockInputOutput("0" + "\n" + "yes" + "\n" + "\n" + "hi");
        testHelper.getTeachingStaffController().manageReceivedInquiries();

        // check for message
        assertTrue(testHelper.getSharedContext().getInquiries().isEmpty());

    }


}