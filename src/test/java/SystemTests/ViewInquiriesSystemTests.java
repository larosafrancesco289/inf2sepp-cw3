package SystemTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests viewing of inquiries for both {@link controller.AdminStaffController} and {@link controller.TeachingStaffController}
 */
class ViewInquiriesSystemTests {

    private final TestHelper testHelper = new TestHelper(); // TestHelper class is used to set up the testing environment

    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }

    // No Inquiries

    /**
     * Test case: No Inquiries
     * Checks for no inquiry message when a user of type AdminStaff manages inquiries
     */
    @Test
    void testNoInquiriesAdmin() {
        testHelper.setUpLoggedInAdminStaff();
        //test if no inquiries in list returns error message
        testHelper.mockInputOutput("-1");
        testHelper.getAdminStaffController().manageInquiries();
        // check output message
        assertTrue(testHelper.getOutContent().toString().contains("No inquiries to manage"));

    }

    /**
     * Test case: No Inquiries
     * Checks for no inquiry message when a user of type TeachingStaff manages redirected inquiries
     */
    @Test
    void testNoInquiriesTeaching() {
        testHelper.setUpLoggedInTeachingStaff();
        // test if no inquiries in list returns error message
        testHelper.getTeachingStaffController().manageReceivedInquiries();
        // check output message
        assertTrue(testHelper.getOutContent().toString().contains("Currently no unanswered inquiries!"));
    }


    // Inquiry exists

    /**
     * Test case: Display Inquiries
     * Checks that inquiries are displayed to user of type AdminStaff when managing inquiries
     */
    @Test
    void testAdminView() {
        // add mock inquiry
        testHelper.setupMockInquiry(false);

        //test for admin user with at least 1 inquiry
        testHelper.setUpLoggedInAdminStaff();

        testHelper.mockInputOutput("-1");
        testHelper.getAdminStaffController().manageInquiries();

        assertTrue(testHelper.getOutContent().toString().contains("Inquiries to manage:"));
    }

    /**
     * Test case: Display Inquiries
     * Checks that redirected inquiries are displayed to user of type TeachingStaff when managing redirected inquiries
     */
    @Test
    void testTeachingView() {
        // adds mock inquiry, redirected to teaching staff member
        testHelper.setupMockInquiry(true);

        testHelper.setUpLoggedInTeachingStaff();
        testHelper.mockInputOutput("0" + "\n" + "0" + "\n" + "yes" + "\n" + "ok");
        testHelper.getTeachingStaffController().manageReceivedInquiries();

        assertTrue(testHelper.getOutContent().toString().contains("Inquiry number 0\nSubject: TestInquiry"));

    }


}