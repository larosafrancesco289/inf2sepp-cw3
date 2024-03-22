package controller;

import external.*;
import model.*;
import view.*;

import java.util.ArrayList;
import java.util.List;

public class TeachingStaffController extends StaffController {
    public TeachingStaffController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void manageReceivedInquiries() {
        selectFromMenu(null, null);

        List<Inquiry> allInquiries = sharedContext.getInquiries();

        // if there are any inquiries, look for redirected inquiries
        if (!allInquiries.isEmpty()) {

            List<Inquiry> redirectedInquiries = new ArrayList<>();
            User currentUser = sharedContext.getCurrentUser();
            String teachingStaffUser = ((AuthenticatedUser) currentUser).getEmail();
            // loop through inquiry list, add to redirected list if redirected
            for (Inquiry currInquiry : allInquiries){

                if (currInquiry.getAssignedTo().equals(teachingStaffUser)){

                    redirectedInquiries.add(currInquiry);
                }
            }

            // if there are any redirected inquiries, display them to the user

            if (!redirectedInquiries.isEmpty()) {
                int i = 0;

                for (Inquiry currReInquiry : redirectedInquiries) {

                    String inqNo = String.format("Inquiry number %d%n", i);
                    view.displayInfo(inqNo);
                    view.displayInquiry(currReInquiry);
                    i += 1;
                }

                // allow user to input inquiry number to answer
                // TODO decide to loop while unanswered inquiries or just do 1 at a time

                // TODO add validation for integer
                int answerNo = Integer.parseInt(view.getInput("Enter inquiry number to answer, or -1 to exit:"));
                //process answer
                if (answerNo > -1){

                    // TODO add validation for answer number range

                    Inquiry answerInquiry = redirectedInquiries.get(answerNo);
                    String prompt = String.format("Inquiry number %d%n:", answerNo);

                    view.displayInfo(prompt);
                    view.displayInfo(answerInquiry.getSubject());
                    view.displayInfo(answerInquiry.getContent());

                    if (view.getYesNoInput("Would you like to answer this inquiry?")){
                        // send email to inquirer with answer
                        super.respondToInquiry(answerInquiry);
                        // remove inquiry from unanswered list, then update shared context list
                        allInquiries.remove(answerInquiry);
                        sharedContext.setInquiries(allInquiries);

                    }

                }

            } else {
                view.displayWarning("Currently no redirected inquiries!");
            }
        } else {
            view.displayWarning("Currently no unanswered inquiries!");
        }



    }
}
