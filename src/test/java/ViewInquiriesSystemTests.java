// JUnit

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void testAdminView() {
        // add mock inquiry



        testHelper.setUpLoggedInAdminStaff();
        //test for admin user with at least 1 inquiry
    }


}
