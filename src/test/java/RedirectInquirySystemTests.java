// JUnit

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;

// class imports


class RedirectInquirySystemTests {

    private final TestHelper testHelper = new TestHelper(); // TestHelper class is used to set up the testing environment

    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }

    @Test
    void testEmptyEmail() {


    }

    @Test
    void testAssigned() {
        //test if inquiry has been assigned to staff?
    }


}


//private void redirectInquiry(Inquiry inquiry) {
//    String teachingStaffEmail;
//    do {
//        teachingStaffEmail = view.getInput("Enter the email of the staff member to whom this inquiry should be redirected: ");
//        if (teachingStaffEmail.isEmpty()) {
//            view.displayWarning("Assignee email cannot be empty");
//        }
//    } while (teachingStaffEmail.isEmpty()); // Need more validation here
//    inquiry.setAssignedTo(teachingStaffEmail);
//    emailService.sendEmail(SharedContext.ADMIN_STAFF_EMAIL, teachingStaffEmail, inquiry.getSubject(), "Inquiry assigned to you");
//    view.displaySuccess("Inquiry assigned to " + teachingStaffEmail);
//}