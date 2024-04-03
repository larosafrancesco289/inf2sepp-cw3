package SystemTests;

import model.Inquiry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests redirecting Inquiry functionality of {@link controller.AdminStaffController}
 */
class RedirectInquirySystemTests {

    private final TestHelper testHelper = new TestHelper(); // TestHelper class is used to set up the testing environment


    /**
     * Sets up logged in AdminStaff user and adds a mock Inquiry before each test
     */
    @BeforeEach
    void setUp() {
        testHelper.setUpLoggedInAdminStaff();
        testHelper.setupMockInquiry(false);
    }


    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }


    /**
     * Test case: Empty email error message
     * Checks for empty email provided error message when no input is provided when redirecting an inquiry
     */
    @Test
    void testEmptyEmail() {

        testHelper.mockInputOutput("1" + "\n" + "no" + "\n" + "\n" + "email@domain");
        testHelper.getAdminStaffController().manageInquiries();

        assertTrue(testHelper.getOutContent().toString().contains("Invalid email provided, please enter again using the format, email@domain:"));


    }

    /**
     * Test case: Inquiry assigned
     * Checks that inquiries have the correct assigned email address once they have been redirected
     */
    @Test
    void testAssigned() {

        testHelper.mockInputOutput("1" + "\n" + "no" + "\n" + "email@domain");
        testHelper.getAdminStaffController().manageInquiries();

        List<Inquiry> inquiryList = testHelper.getSharedContext().getInquiries();
        assertEquals("email@domain", inquiryList.get(0).getAssignedTo());


    }


    /**
     * Test case: Inquiry assigned message
     * Checks for inquiry assignment success message once an inquiry has been redirected
     */
    @Test
    void testAssignedMessage() {

        testHelper.mockInputOutput("1" + "\n" + "no" + "\n" + "email@domain");
        testHelper.getAdminStaffController().manageInquiries();

        assertTrue(testHelper.getOutContent().toString().contains("Inquiry assigned to email@domain"));

    }


}