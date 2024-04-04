package SystemTests;// JUnit

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

// class imports

//THIS ONE IS UNFINISHED

class AddFAQQASystemTests {
    private final TestHelper testHelper = new TestHelper(); // SystemTests.TestHelper class is used to set up the testing environment

    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }
    @BeforeEach
    void setUp() {
        testHelper.setUpLoggedInAdminStaff();
    }


    /**
     * Test for adding a question in an established section
     * -2 selects addFAQ
     * -1 selects main menu or back
     *  test is the topic
     */
    @Test
    void testQuestionAnswerProvided() {
        testHelper.mockInputOutput("-2" + "\n" + "test" + "\n" + "test_q" + "\n" + "test_a" + "\n" + "-1" + "\n");
        testHelper.getAdminStaffController().manageFAQ();

        assertTrue(testHelper.getOutContent().toString().contains("Added FAQ question: test_q"));

    }



    /**
     * Test for ensuring email notification is sent to other admin staff
     * -2 selects addFAQ
     * -1 selects main menu or back
     *  test is the topic
     */
    @Test
    void testEmailNotificationSentToStaff() {
        testHelper.mockInputOutput("-2" + "\n" + "test" + "\n" + "test_q" + "\n" + "test_a" + "\n" + "-1" + "\n");
        testHelper.getAdminStaffController().manageFAQ();

        assertTrue(testHelper.getOutContent().toString().contains("Emails sent to admins"));

    }

}
