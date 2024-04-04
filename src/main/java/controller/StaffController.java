package controller;

import external.AuthenticationService;
import external.EmailService;
import model.Inquiry;
import model.SharedContext;
import view.View;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Base controller class for staff functionalities.
 * Provides common staff actions such as handling inquiries, which can be extended by specific staff roles.
 */
public class StaffController extends Controller {
    /**
     * Constructs a StaffController with the specified shared context, view, authentication service, and email service.
     * Initializes the controller with necessary services and context for its operation.
     *
     * @param sharedContext The shared application context for managing application state.
     * @param view          The interface for interacting with the user.
     * @param authService   The service for authenticating users.
     * @param emailService  The service for sending emails.
     */
    public StaffController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    /**
     * Retrieves a collection of inquiry titles from a given collection of inquiries.
     * This method is useful for displaying inquiry titles to staff for selection.
     *
     * @param inquiries A collection of Inquiry objects from which titles will be extracted.
     * @return A collection of strings representing the titles of the inquiries.
     */
    protected ArrayList<String> getInquiryTitles(Collection<Inquiry> inquiries) {
        ArrayList<String> titles = new ArrayList<>();
        for (Inquiry inquiry : inquiries) {
            // assert inquiry.getSubject() != null : "Inquiry subject cannot be null";g
            titles.add(inquiry.getSubject());
        }
        return titles;
    }

    /**
     * Allows staff to respond to an inquiry. Prompts the staff member for a response, sends the response to the inquirer via email,
     * and removes the inquiry from the shared context.
     *
     * @param inquiry The inquiry to which the staff member will respond.
     */
    protected void respondToInquiry(Inquiry inquiry) {
        String answer;
        do {
            answer = view.getInput("Enter answer to inquiry: ");
            if (answer.isEmpty()) {
                view.displayWarning("Answer cannot be empty");
            }
        } while (answer.isEmpty());
        view.displayInfo("\033[H\033[2J");


        String emailSubject = String.format("A staff have responded to your inquiry %s", inquiry.getSubject());
        String emailBody = String.format("Answer to your in inquiry: \n%s", answer);

        emailService.sendEmail(sharedContext.getCurrentUser().getEmail(), inquiry.getInquirerEmail(), emailSubject,emailBody);
        view.displayDivider();
        view.displaySuccess("Answer sent to " + inquiry.getInquirerEmail());
        sharedContext.getInquiries().remove(inquiry);
    }
}
