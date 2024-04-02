// JUnit

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

// class imports


class ViewInquiriesSystemTests {

    private final TestHelper testHelper = new TestHelper(); // TestHelper class is used to set up the testing environment

    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }

    @Test
    void testNoInquiriesAdmin() {
        testHelper.setUpLoggedInAdminStaff();
        //test if no inquiries in list returns error message
        testHelper.mockInputOutput("-1");
        testHelper.getAdminStaffController().manageInquiries();
        // check output message
        assertTrue(testHelper.getOutContent().toString().contains("No inquiries to manage"));

    }

    @Test
    void testNoInquiriesTeaching() {
        testHelper.setUpLoggedInTeachingStaff();
        // test if no inquiries in list returns error message
        testHelper.getTeachingStaffController().manageReceivedInquiries();
        // check output message
        assertTrue(testHelper.getOutContent().toString().contains("Currently no unanswered inquiries!"));
    }


    /**
     * Tests that inquiry system displays inquiries if they exists for admin staff
     * */
    @Test
    void testAdminView() {
        // add mock inquiry
        testHelper.setupMockInquiry(false);

        //test for admin user with at least 1 inquiry
        testHelper.setUpLoggedInAdminStaff();

        testHelper.mockInputOutput("Test");
        testHelper.getAdminStaffController().manageInquiries();

        assertTrue(testHelper.getOutContent().toString().contains("Inquiries to manage:\nTest"));
    }

    @Test
    void testTeachingView() {

        // add mock inquiry
        testHelper.setupMockInquiry(true);

        //test for admin user with at least 1 inquiry
        testHelper.setUpLoggedInTeachingStaff();


        testHelper.mockInputOutput("0" + "\n" + "no" + "\n" + "-1");
        testHelper.getTeachingStaffController().manageReceivedInquiries();


        assertTrue(testHelper.getOutContent().toString().contains("Inquiry number 0"));

    }





}
