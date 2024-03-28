// JUnit

import org.junit.jupiter.api.Test;

// class imports


class RedirectEnquirySystemTests {

    @Test
    void testEmptyEmail() {

        //test when email is empty (should not proceed)
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