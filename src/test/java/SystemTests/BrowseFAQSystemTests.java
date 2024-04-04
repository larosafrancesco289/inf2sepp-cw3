package SystemTests;// JUnit

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

// class imports

class BrowseFAQSystemTests {
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
     * This test verifies that a topic needs to have a non-null string
     */
    @Test
    void testEmptyTopic() {

        testHelper.mockInputOutput("-2" + "\n" + "\n" + "test" + "\n" + "test_q" + "\n" + "test_a"+ "\n" + "-1" + "\n");
        testHelper.getAdminStaffController().manageFAQ();

        assertTrue(testHelper.getOutContent().toString().contains("Topic cannot be empty"));

    }

    /**
     * This test verifies that an error is thrown if an incorrect menu option is submitted
     */
    @Test
    void testInvalidNegativeOption() {

        testHelper.mockInputOutput("-3" + "\n" + "-1" + "\n");
        testHelper.getAdminStaffController().manageFAQ();
        assertTrue(testHelper.getOutContent().toString().contains("Error: Invalid option: -3"));

    }

    /**
     * This test verifies that an error is thrown if an incorrect indexing option is submitted
     */
    @Test
    void testInvalidPositiveOption() {

        testHelper.mockInputOutput("3" + "\n" + "-1" + "\n");
        testHelper.getAdminStaffController().manageFAQ();
        assertTrue(testHelper.getOutContent().toString().contains("Error: Invalid option: 3"));

    }


    /**
     * This test verifies that a new topic is added at root
     */
    @Test
    void testAddSubsection(){
        testHelper.mockInputOutput("-2" + "\n" + "test_topic" + "\n" + "test_q" + "\n" + "test_a"+ "\n" + "-1" + "\n");
        testHelper.getAdminStaffController().manageFAQ();
        assertTrue(testHelper.getOutContent().toString().contains("New section test_topic added!"));

    }




}


