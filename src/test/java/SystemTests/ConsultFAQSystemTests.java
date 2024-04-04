package SystemTests;// JUnit

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// class imports

// CONSULT IS FOR USERS
class ConsultFAQSystemTests {
    private final TestHelper testHelper = new TestHelper(); // SystemTests.TestHelper class is used to set up the testing environment

     /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }

    /**
     * This test verifies that -1 is the only acceptable input if no QA for guest
     */
    @Test
    void testEmptyQAGuest() {
        testHelper.getInquirerController().consultFAQ();
        testHelper.mockInputOutput("-1"+"\n");
        assertTrue(testHelper.getOutContent().toString().contains("[-1] to return to the main menu"));  
    }

}
