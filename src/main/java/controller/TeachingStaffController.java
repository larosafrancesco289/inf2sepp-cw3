package controller;

import external.*;
import model.*;
import view.*;
public class TeachingStaffController extends StaffController{
    public TeachingStaffController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void manageReceivedInquiries() {
        selectFromMenu(null, "Return to main menu");
    }
}
