package controller;

import external.*;
import model.*;
import view.*;
public class AdminStaffController extends StaffController {
    public AdminStaffController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void addPage(Page page) {
        sharedContext.addPage(page);
    }

    public void manageFAQ() {
    }

    private void addFAQItem(FAQSection section) {
        sharedContext.getFaq().getSections().add(section);
    }

    public void viewAllPages() {
    }

    public void manageInquiries() {
    }

    private void redirectInquiry(Inquiry inquiry) {
    }
}
