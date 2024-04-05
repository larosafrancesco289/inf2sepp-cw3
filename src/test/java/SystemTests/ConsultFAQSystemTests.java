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
     * Setup the faq section Topic1,
     * Topic1 contain Subtopic1.1, and QA pair a_1.1 and q_1.1
     * Subtopic1.1 contain q_1.1.1 and a_1.1.1
     */
    @BeforeEach
    void setUp() {
        testHelper.setUpLoggedInAdminStaff();
        testHelper.mockInputOutput("-2\nTopic1\nq_1.1\na_1.1\n1\n-2\nyes\nSubtopic1.1\nq_1.1.1\na_1.1.1\n-1\n-1\n");
        testHelper.getAdminStaffController().manageFAQ();
        testHelper.setUpGuest();
    }

    /**
     * This test verifies that -1 is the acceptable input if no QA was added
     */
    @Test
    void testFallBackQA() {

        testHelper.mockInputOutput("-1" + "\n");
        testHelper.getInquirerController().consultFAQ();
        assertTrue(!testHelper.getOutContent().toString().contains("Invalid"));
    }

    /**
     * This test verifies that an error is thrown if an incorrect negative menu option is submitted at the root level of the QA hierarchy
     * -2 is the invalid negative menu option
     * -1 returns to main menu
     */
    
    @Test
    void testNegativeAtRoot() {
        testHelper.mockInputOutput("-2" + "\n" + "-1" + "\n");
        testHelper.getInquirerController().consultFAQ();
        assertTrue(testHelper.getOutContent().toString().contains("Invalid"));
    }
    
    /**
     * This test verifies that an error is thrown if an incorrect positive menu option is submitted at the root level of the QA hierarchy
     * 100 is the invalid positive menu option
     * -1 returns to main menu
     */
    
     @Test
     void testPositiveAtRoot() {
         testHelper.mockInputOutput("100" + "\n" + "-1" + "\n");
         testHelper.getInquirerController().consultFAQ();
         assertTrue(testHelper.getOutContent().toString().contains("Invalid"));
     }
     
    /**
     * This test verifies that an error is thrown if 0 is submitted at the root level of the QA hierarchy
     * 0 is the invalid  menu option
     * -1 returns to main menu
     */
    
     @Test
     void testZeroAtRoot() {
         testHelper.mockInputOutput("0" + "\n" + "-1" + "\n");
         testHelper.getInquirerController().consultFAQ();
         assertTrue(testHelper.getOutContent().toString().contains("Invalid"));
     }
     
    /**
     * This test verifies that an error is thrown if a non integer string is submitted at the root level of the QA hierarchy
     * "hello word" is the invalid  menu option
     * -1 returns to main menu
     */
    
     @Test
     void testStringAtRoot() {
         testHelper.mockInputOutput("hello word" + "\n" + "-1" + "\n");
         testHelper.getInquirerController().consultFAQ();
         assertTrue(testHelper.getOutContent().toString().contains("Invalid"));
     }
     
    /**
     * This test verifies that an error is thrown if a non integer decimal number is submitted at the root level of the QA hierarchy
     * "-1.14" is the invalid  menu option
     * -1 returns to main menu
     */
    
     @Test
     void testDecimalAtRoot() {
         testHelper.mockInputOutput("-1.14" + "\n" + "-1" + "\n");
         testHelper.getInquirerController().consultFAQ();
         assertTrue(testHelper.getOutContent().toString().contains("Invalid"));
     }

    /**
     * This test verifies that an error is thrown no input is submitted at the root level of the QA hierarchy
     * "" is the invalid  menu option
     * -1 returns to main menu
     */
     @Test
     void testEmptyAtRoot() {
         testHelper.mockInputOutput("" + "\n" + "-1" + "\n");
         testHelper.getInquirerController().consultFAQ();
         assertTrue(testHelper.getOutContent().toString().contains("Invalid"));
     }

     /**
     * This test verifies that user can select a section to enter
     * "1" ts the option to go into "Topic1"
     * -1 returns to main FAQ menu
     * -1 returns to main menu
     * if the anwser of the QA pair 1.1 was in the output, means the user have seccfully entered Topic1
     * and the system should offer options to request updates and unsubscribe for guest
     */

     @Test
     void testPickSection() {
         testHelper.mockInputOutput("1\n-1\n-1\n");
         testHelper.getInquirerController().consultFAQ();
         assertTrue(testHelper.getOutContent().toString().contains("A: a_1.1")
                 && !testHelper.getOutContent().toString().contains("invalid")
                 && testHelper.getOutContent().toString().contains("[-2] to request updates on this topic")
                 && testHelper.getOutContent().toString().contains("[-3] to stop receiving updates on this topic"));
     }
     
     /**
     * This test verifies that user can select a section to enter
     * "1" ts the option to go into "Topic1"
     * -1 returns to main FAQ menu
     * -1 returns to main menu
     * if the anwser of the QA pair 1.1 was in the output, means the user have seccfully entered Topic1
     * and the system should offer options to request updates "but not" unsubscribe for Student if ther nit alread subs
     */

     @Test
     void testPickSectionStudent() {
         testHelper.setUpLoggedInStudent();
         testHelper.mockInputOutput("1\n-1\n-1\n");
         testHelper.getInquirerController().consultFAQ();
         assertTrue(testHelper.getOutContent().toString().contains("A: a_1.1")
                 && !testHelper.getOutContent().toString().contains("invalid")
                 && testHelper.getOutContent().toString().contains("[-2] to request updates on this topic")
                 && !testHelper.getOutContent().toString().contains("[-3] to stop receiving updates on this topic"));
     }
     
    /**
     * This test verifies that "guests" can request update
     * 1 is the option to go into "Topic1"
     * -2 is the option to reques update
     * guest@gmail.com is an sample guest email
     * -1 returns to main FAQ menu
     * -1 returns to main menu
     * if Successfully registered was in the output, means the user have seccfully registered to topic update
     */

     @Test
     void testSubs() {
         testHelper.setUpLoggedInStudent();
         testHelper.mockInputOutput("1\n-2\n"+"guest@gmail.com"+"\n-1\n-1\n");
         testHelper.getInquirerController().consultFAQ();
         assertTrue(testHelper.getOutContent().toString().contains("Successfully registered"));
     }

     /**
     * This test verifies that "guests" can stop request update
     * "1" ts the option to go into "Topic1"
     * -2 is the option to  request updates
     * guest@gmail.com is an example email
     * -3 is the option to  stop reciving updates
     * guest@gmail.com is an example email
     * -1 returns to main FAQ menu
     * -1 returns to main menu
     * * if Successfully unregistered was in the output, means the user have seccfully unregistered to topic update
     */
     
    @Test
     void testUnSubs() {
        testHelper.mockInputOutput("1\n-2\n"+"guest@gmail.com\n-3\n"+"guest@gmail.com"+"\n-1\n-1\n");
        testHelper.getInquirerController().consultFAQ();
        assertTrue(testHelper.getOutContent().toString().contains("Successfully unregistered"));
    }

     /**
     * This test verifies that students can request update
     * 1 is the option to go into "Topic1"
     * -2 is the option to reques update
     * -1 returns to main FAQ menu
     * -1 returns to main menu
     * * if Successfully registered was in the output, means the user have seccfully registered to topic update
     */

     @Test
     void testSubsStudent() {
         testHelper.setUpLoggedInStudent();
         testHelper.mockInputOutput("1\n-2\n-1\n-1\n");
         testHelper.getInquirerController().consultFAQ();
         assertTrue(testHelper.getOutContent().toString().contains("Successfully registered")
                 && testHelper.getOutContent().toString().contains("[-2] to stop receiving updates on this topic"));
     }

     /**
     * This test verifies that students can stop request update
     * "1" ts the option to go into "Topic1"
     * -2 is the option to request updates
     * -2 now  become the option to stop reciving updates
     * -1 returns to main FAQ menu
     * -1 returns to main menu
     * if the anwser of the QA pair 1.1 was in the output, means the user have seccfully entered Topic1
     */
     
    @Test
    void testUnSubsStudent() {
        testHelper.setUpLoggedInStudent();
        testHelper.mockInputOutput("1\n-2\n-2\n-1\n-1\n");
        testHelper.getInquirerController().consultFAQ();
        assertTrue(testHelper.getOutContent().toString().contains("Successfully unregistered"));
    }

    /**
     * This test verifies that user can select a subtopic to enter
     * 1 ts the option to go into "Topic1"
     * 1 ts the option to go into "Subtopic1.1"
     * -1 returns to Topic1
     * -1 returns to main FAQ menu
     * -1 returns to main menu
     * if the anwser of the QA pair 1.1.1 was in the output, means the user have seccfully entered Subtopic1.1
     * Also check if the directory "Topic1 / Subtopic1.1" was present in the out put
     * and the system should offer options to request updates and unsubscribe for guest
     */
    @Test
    void testPickSubtopic() {
        testHelper.setUpLoggedInStudent();
        testHelper.mockInputOutput("1\n1\n-1\n-1\n-1\n");
        testHelper.getInquirerController().consultFAQ();
        assertTrue(testHelper.getOutContent().toString().contains("a_1.1.1")
        &&testHelper.getOutContent().toString().contains("Topic1 / Subtopic1.1"));
    }

}
