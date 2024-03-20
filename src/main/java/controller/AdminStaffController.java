package controller;

import external.*;
import model.*;
import view.*;

import java.util.HashMap;
import java.util.List;

public class AdminStaffController extends StaffController {
    public AdminStaffController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void addPage(Page page) {
        String title = view.getInput("Enter page title");
        String content = view.getInput("Enter page content");
        Boolean isPrivate = view.getYesNoInput("Should this page be private?");

        HashMap<String, Page> availablePages = sharedContext.getPages();
        Boolean titleExists = availablePages.containsKey(title);
        if (titleExists) {
            Boolean overwrite = view.getYesNoInput("Page " + title + " already exists. Overwrite with new page?");
            if (overwrite == false) {
                view.displayInfo("Cancelled adding new page");
                return;
            }
        }

        Page newPage = new Page(title, content, isPrivate);
        sharedContext.addPage(newPage);

        // Get the current user
        AuthenticatedUser currentUser = (AuthenticatedUser) sharedContext.getCurrentUser();

        int status = emailService.sendEmail(currentUser.getEmail(), SharedContext.ADMIN_STAFF_EMAIL, title, "New page added");
        if (status == EmailService.STATUS_SUCCESS) {
            view.displaySuccess("Added page " + title + "");
        } else {
            view.displayWarning("Added page " + title + " but failed to send email notification!");
        }
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
