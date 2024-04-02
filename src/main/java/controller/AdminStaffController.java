package controller;

import external.AuthenticationService;
import external.EmailService;
import model.*;
import view.View;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Controller class for admin staff functionalities.
 * This class extends StaffController and includes additional methods specific to admin staff roles,
 * such as managing web pages, FAQ sections, viewing all pages, and managing inquiries.
 */

public class AdminStaffController extends StaffController {
    /**
     * Constructs an AdminStaffController with the specified shared context, view, authentication service, and email service.
     *
     * @param sharedContext The shared application context.
     * @param view          The view used for input and output operations.
     * @param authService   The authentication service for user authentication.
     * @param emailService  The email service for sending notifications.
     */
    public AdminStaffController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    /**
     * Adds a new webpage to the system with a specified title, content (path to txt file), and visibility (public/private).
     * Prompts the admin staff member to enter the page details and determines if the page should be overwritten
     * if a page with the same title already exists.
     */
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
     * Manages the Frequently Asked Questions (FAQ) section. Allows navigating through FAQ sections and sub-sections,
     * displaying them accordingly based on the user's role (guest or authenticated user).
     */
    public void manageFAQ() {
        FAQ faq;
        List<FAQSection> sections;
        FAQSection currentSection = null, parent = null;
        User currentUser = sharedContext.getCurrentUser();
        Collection<String> subscribers;
        String topic, section = null;
        int optionNo = 0;


        while (!(currentSection == null && optionNo == -1)) {
            if (currentSection == null) {
                faq = sharedContext.getFaq();
                view.displayFAQ(faq, currentUser instanceof AuthenticatedUser);
                view.displayInfo("[-1] to return to the main menu");
                Boolean addFAQSection = view.getYesNoInput("Do you want to add a new FAQSection?");
                if (addFAQSection) {
                    do {
                        section = view.getInput("Enter section: ");
                        if (section.isEmpty()) {
                            view.displayWarning("section cannot be empty");
                        }
                    } while (section.isEmpty());
                    FAQSection newSection = new FAQSection(section);
                    faq.addSectionItems(newSection);
                    currentSection = newSection;
                }

                Boolean addFAQItem = view.getYesNoInput("Do you want to add a new FAQItem?");
                if (addFAQItem) {
                    addFAQItem(currentSection);
                }
            } else {
                view.displayFAQSection(currentSection, currentUser instanceof AuthenticatedUser);
                parent = currentSection.getParent();

                if (parent == null) {
                    view.displayInfo("[-1] to return to the main menu");
                    Boolean addFAQItem = view.getYesNoInput("Do you want to add a new FAQItem?");
                    if (addFAQItem) {
                        addFAQItem(currentSection);
                    }
                } else{
                    topic = parent.getTopic();
                    view.displayInfo("[-1] to return to " + topic);
                    Boolean addFAQItem = view.getYesNoInput("Do you want to add a new FAQItem?");
                    if (addFAQItem) {
                        addFAQItem(currentSection);
                    }
                }


            }
            String userIntput = view.getInput("Please choose an option: ");

            try {
                optionNo = Integer.parseInt(userIntput);
                //topic = currentSection.getTopic();

                // Handling the negative options

                if (currentSection == null) {
                    if (optionNo < -1) {
                        throw new NumberFormatException(); //Not in the sequence diagram but required
                    }
                } else {
                    switch (optionNo) {
                        case -1:
                            parent = currentSection.getParent();
                            currentSection = parent;

                    }
                }
                //update the view
                System.out.println("HERE");
                if (optionNo != -1) {
                    if (currentSection == null) {
                        faq = sharedContext.getFaq();
                        sections = faq.getSections();
                    } else {
                        sections = currentSection.getSubsections();
                    }
                    // if optionNo out of section bounds
                    if ((optionNo > sections.size()) || (optionNo == 0)) {
                        view.displayError("Invalid option: " + userIntput);
                    } else {
                        currentSection = sections.get(optionNo);
                    }
                }

            } catch (NumberFormatException e) {
                view.displayError("Invalid option: " + userIntput);
            }
        }
    }







    /**
     * Adds a new FAQ item to a specified section or sub-section. Prompts the admin staff member for input
     * on the topic, subtopic (if applicable), question, and answer before adding it to the FAQ.
     *
     * @param section The FAQSection to which the new FAQ item will be added.
     */
    private void addFAQItem(FAQSection section) {
        if (section.getParent() == null) {
            String topic;
            do {
                topic = view.getInput("Enter topic: ");
                if (topic.isEmpty()) {
                    view.displayWarning("Topic cannot be empty");
                }
            } while (topic.isEmpty());
            FAQSection newSection = new FAQSection(topic);
            section.addSubsection(newSection);
        }

        Boolean addSubtopic = view.getYesNoInput("Do you want to provide a new subtopic?");
        if (addSubtopic) {
            String subtopic = view.getInput("Enter subtopic: ");
            FAQSection newSection = new FAQSection(subtopic);
            section.addSubsection(newSection);
            addFAQItem(newSection);
        }

        String question;
        String answer;
        do {
            question = view.getInput("Enter question: ");
            answer = view.getInput("Enter answer: ");
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

    /**
     * Displays all pages currently in the system. Offers the option to add a new page at the end of the listing.
     */
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

    /**
     * Manages inquiries submitted by users. Lists all inquiries and allows the admin staff to select one
     * for management, which includes responding to the inquiry or redirecting it to another staff member.
     */
    public void manageInquiries() {
        Collection<String> inquiryTitles = super.getInquiryTitles(sharedContext.getInquiries());
        if (inquiryTitles.isEmpty()) {
            view.displayDivider();
            view.displayInfo("No inquiries to manage");
            selectFromMenu(null, "return to main menu");
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

    /**
     * Redirects an inquiry to another staff member by assigning the inquiry to them and sending a notification email.
     *
     * @param inquiry The Inquiry to be redirected.
     */
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
