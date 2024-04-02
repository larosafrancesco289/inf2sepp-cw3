package controller;

import external.AuthenticationService;
import external.EmailService;
import model.*;
import view.View;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AdminStaffController extends StaffController {
    public AdminStaffController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    /**
     * Adds webpage to the system. Allows for admin staff member to enter a title and page content (path to txt file).
     * Page is also set to either public or private, with private allowing logged-in users only to view
     **/
    public void addPage() {
        String title = view.getInput("Enter page title: ");
        String content = view.getInput("Enter page content: ");
        Boolean isPrivate = view.getYesNoInput("Should this page be private? ");

        // assert title != null : "Title cannot be null";
        // assert content != null : "Content cannot be null";
        // assert isPrivate != null : "isPrivate cannot be null";

        HashMap<String, Page> availablePages = sharedContext.getPages();
        Boolean titleExists = availablePages.containsKey(title);
        if (titleExists) {
            Boolean overwrite = view.getYesNoInput("Page " + title + " already exists. Overwrite with new page?");
            if (!overwrite) {
                view.displayInfo("Cancelled adding new page");
                return;
            }
        }

        Page newPage = new Page(title, content, isPrivate);
        sharedContext.addPage(newPage);

        // assert sharedContext.getPages().containsKey(title) : "New page should have been added";

        // Get the current user
        AuthenticatedUser currentUser = (AuthenticatedUser) sharedContext.getCurrentUser();

        int status = emailService.sendEmail(currentUser.getEmail(), SharedContext.ADMIN_STAFF_EMAIL, title, "New page added");
        if (status == EmailService.STATUS_SUCCESS) {
            view.displaySuccess("Added page " + title);
        } else {
            view.displayWarning("Added page " + title + " but failed to send email notification!");
        }
    }

    /**
     * m
     **/
    public void manageFAQ() {
        FAQ faq;
        List<FAQSection> sections;
        FAQSection currentSection = null;
        FAQSection parent = null;
        User currentUser = sharedContext.getCurrentUser();
        String topic;
        Collection<String> subscribers;
        String userEmail = null;

        if (currentUser instanceof AuthenticatedUser) {
            userEmail = ((AuthenticatedUser) currentUser).getEmail();
        }

        int optionNo = -1;
        while (currentSection == null && optionNo == -1) {
            if (currentSection == null) {
                faq = sharedContext.getFaq();
                view.displayFAQ(faq, currentUser instanceof Guest);
                view.displayInfo("[-1] to return to the main menu");
            } else {
                view.displayFAQSection(currentSection, currentUser instanceof Guest);
                parent = currentSection.getParent();

                if (parent == null) {
                    view.displayInfo("[-1] to return to the main menu");
                } else {
                    topic = parent.getTopic();
                    view.displayInfo("[-1] to return to " + topic);
                }

            }
            try {
                optionNo = Integer.parseInt(view.getInput("Please choose an option: "));
                topic = currentSection.getTopic();


                if (currentSection != null && optionNo == -1) {
                    parent = currentSection.getParent();
                    currentSection = parent;
                } else if (optionNo == -1) {
                    if (currentSection == null) {
                        faq = sharedContext.getFaq();
                        sections = faq.getSections();
                    } else {
                        sections = parent.getSubsections();
                    }

                    // if optionNo out of section bounds
                    if (optionNo > sections.size()) {
                        view.displayError("Invalid option: " + optionNo);
                    } else {
                        currentSection = sections.get(optionNo);
                    }
                }
            } catch (NumberFormatException e) {
                view.displayError("Invalid option: " + optionNo);
                return;
            }
        }
    }

    /**
     * Adds FAQ topic to system <br>
     * Lets admin staff enter a topic title
     **/
    private void addFAQItem(FAQSection section) {
        if (section.getParent() == null) {
            String topic;
            do {
                topic = view.getInput("Enter topic");
                if (topic.isEmpty()) {
                    view.displayWarning("Topic cannot be empty");
                }
            } while (topic.isEmpty());
            FAQSection newSection = new FAQSection(topic);
            section.addSubsection(newSection);
        }

        Boolean addSubtopic = view.getYesNoInput("Do you want to provide a new subtopic?");
        if (addSubtopic) {
            String subtopic = view.getInput("Enter subtopic");
            FAQSection newSection = new FAQSection(subtopic);
            section.addSubsection(newSection);
            addFAQItem(newSection);
        }

        String question;
        String answer;
        do {
            question = view.getInput("Enter question");
            answer = view.getInput("Enter answer");
            if (question.isEmpty()) {
                view.displayWarning("Question cannot be empty");
            } else if (answer.isEmpty()) {
                view.displayWarning("Answer cannot be empty");
            }
        } while (question.isEmpty() || answer.isEmpty());
        section.addItem(question, answer);
        emailService.sendEmail(SharedContext.ADMIN_STAFF_EMAIL, SharedContext.ADMIN_STAFF_EMAIL, question, "New FAQ question added");
        view.displaySuccess("Added FAQ question: " + question);
    }

    public void viewAllPages() {
        HashMap<String, Page> pages = sharedContext.getPages();
        for (Page page : pages.values()) {
            view.displayInfo(page.getTitle() + ": " + page.getContent());
        }

        Boolean addPage = view.getYesNoInput("Do you want to add a new page?");
        if (addPage) {
            addPage();
        }
    }

    public void manageInquiries() {
        Collection<String> inquiryTitles = super.getInquiryTitles(sharedContext.getInquiries());
        if (inquiryTitles.isEmpty()) {
            view.displayDivider();
            view.displayInfo("No inquiries to manage");
            // selectFromMenu(null, "return to main menu");
            return;
        }
        view.displayDivider();
        view.displayInfo("Inquiries to manage:");
        for (String title : inquiryTitles) {
            view.displayInfo(title);
        }

        String inquiryTitle = view.getInput("Enter the title of the inquiry you want to manage: ");
        Inquiry inquiry = sharedContext.getInquiries().stream().filter(i -> i.getSubject().equals(inquiryTitle)).findFirst().orElse(null);
        if (inquiry == null) {
            view.displayWarning("Inquiry not found");
            return;
        }
        // print the content of the inquiry
        view.displayInquiry(inquiry);
        // need to add alternative scenarios here
        Boolean respond = view.getYesNoInput("Do you want to respond to this inquiry?");
        if (respond) {
            respondToInquiry(inquiry);
        } else {
            redirectInquiry(inquiry);
        }
    }

    private void redirectInquiry(Inquiry inquiry) {
        String teachingStaffEmail;
        do {
            teachingStaffEmail = view.getInput("Enter the email of the staff member to whom this inquiry should be redirected: ");
            if (teachingStaffEmail.isEmpty()) {
                view.displayWarning("Assignee email cannot be empty");
            }
        } while (teachingStaffEmail.isEmpty()); // Need more validation here
        inquiry.setAssignedTo(teachingStaffEmail);
        emailService.sendEmail(SharedContext.ADMIN_STAFF_EMAIL, teachingStaffEmail, inquiry.getSubject(), "Inquiry assigned to you");
        view.displaySuccess("Inquiry assigned to " + teachingStaffEmail);
    }
}
