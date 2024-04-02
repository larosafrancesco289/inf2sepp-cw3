import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedirectInquirySystemTests {

    private final TestHelper testHelper = new TestHelper(); // TestHelper class is used to set up the testing environment

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


    // Guest


    @Test
    void testEmptyEmail() {

        testHelper.mockInputOutput("TestInquiry" + "\n" + "no" + "\n" + "" + "\n" + "email@domain");
        testHelper.getAdminStaffController().manageInquiries();

        assertTrue(testHelper.getOutContent().toString().contains("Invalid email provided, please enter again using the format, email@domain:"));


    }

    @Test
    void testAssigned() {

    }


}
