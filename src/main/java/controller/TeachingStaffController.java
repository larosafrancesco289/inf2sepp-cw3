package controller;

import external.AuthenticationService;
import external.EmailService;
import model.AuthenticatedUser;
import model.Inquiry;
import model.SharedContext;
import model.User;
import view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class specific to teaching staff functionalities.
 * Extends the StaffController to provide functionalities such as viewing and responding to redirected inquiries.
 */
public class TeachingStaffController extends StaffController {
    /**
     * Constructs a TeachingStaffController with the specified shared context, view, authentication service, and email service.
     * Initializes the controller with necessary services and context for its operation, focusing on teaching staff roles.
     *
     * @param sharedContext The shared context for application-wide state management.
     * @param view          The interface for user interaction.
     * @param authService   The service for authenticating users.
     * @param emailService  The service for handling email operations.
     */
    public TeachingStaffController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    /**
     * Allows teaching staff to manage inquiries that have been redirected to them.
     * Teaching staff can view a list of inquiries assigned to them, choose one to respond to,
     * and upon responding, the inquiry is removed from the list of active inquiries.
     * If there are no redirected inquiries, a message indicating the absence of inquiries is displayed.
     */
    public void manageReceivedInquiries() {

        List<Inquiry> allInquiries = sharedContext.getInquiries();

        // if there are any inquiries, look for redirected inquiries
        if (!allInquiries.isEmpty()) {

            // Get list of all unanswered inquiries
            List<Inquiry> redirectedInquiries = new ArrayList<>();
            // Get current teachingStaff details
            User currentUser = sharedContext.getCurrentUser();
            String teachingStaffUser = ((AuthenticatedUser) currentUser).getEmail();

            // loop through inquiry list, add to redirected list if redirected
            for (Inquiry currInquiry : allInquiries) {

                if (currInquiry.getAssignedTo().equals(teachingStaffUser)) {

                    redirectedInquiries.add(currInquiry);
                }
            }

            // Loops until there are no redirected inquiries left OR user chooses to exit
            boolean answering = true;
            while (answering && !redirectedInquiries.isEmpty()) {

                // display inquiries available to answer
                int i = 0;

                for (Inquiry currReInquiry : redirectedInquiries) {

                    String inqNo = String.format("Inquiry number %d", i);
                    view.displayInfo(inqNo);
                    view.displayInquiry(currReInquiry);
                    i += 1;
                }

                // Get inquiry number to select OR exit code from user
                boolean valid = false;
                int answerNo = 0;
                String userInput = view.getInput("Enter inquiry number to manage, or enter -1 to exit:");

                while (!valid) {
                    // check provided value is in range of inquiries available to select
                    int inputParsed = Integer.parseInt(userInput);
                    if (inputParsed <= i) {
                        answerNo = inputParsed;
                        valid = true;
                    } else {
                        String inputPrompt = String.format("Invalid inquiry number, please enter any number up to %d, or -1 to exit:%n", i);
                        userInput = view.getInput(inputPrompt);
                    }

                }

                //process answer
                if (answerNo > -1) {

                    // Display selected inquiry
                    Inquiry answerInquiry = redirectedInquiries.get(answerNo);
                    String inquiryDetail = String.format("Inquiry number %d", answerNo);

                    view.displayInfo(inquiryDetail);
                    view.displayInfo(answerInquiry.getSubject());
                    view.displayInfo(answerInquiry.getContent());

                    // choose whether to answer inquiry
                    if (view.getYesNoInput("Would you like to answer this inquiry?")) {
                        // send email to inquirer with answer
                        super.respondToInquiry(answerInquiry);
                        // remove inquiry from unanswered list and redirected list, then update shared context list
                        redirectedInquiries.remove(answerInquiry);
                        allInquiries.remove(answerInquiry);
                        sharedContext.setInquiries(allInquiries);
                    }

                } else {
                    answering = false;
                }

            }

            // if no unanswered inquiries
        } else {
            view.displaySuccess("Currently no unanswered inquiries!");
        }
    }


}

