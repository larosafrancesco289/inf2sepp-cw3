package controller;

import external.*;
import model.*;
import view.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StaffController extends Controller {
    public StaffController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    protected Collection<String> getInquiryTitles(Collection<Inquiry> inquiries) {
        ArrayList<String> titles = new ArrayList<>();
        for (Inquiry inquiry : inquiries) {
            titles.add(inquiry.getSubject());
        }
        return titles;
    }

    protected void respondToInquiry(Inquiry inquiry) {
        String answer;
        do {
            answer = view.getInput("Enter answer to inquiry: ");
            if (answer.isEmpty()) {
                view.displayWarning("Answer cannot be empty");
            }
        } while (answer.isEmpty());

        emailService.sendEmail(SharedContext.ADMIN_STAFF_EMAIL, inquiry.getInquirerEmail(), inquiry.getSubject(), answer);
        view.displaySuccess("Answer sent to " + inquiry.getInquirerEmail());
        sharedContext.getInquiries().remove(inquiry);
    }
}
