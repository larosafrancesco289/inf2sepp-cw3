package controller;

import external.AuthenticationService;
import external.EmailService;
import model.*;
import view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

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
        FAQ faq = sharedContext.getFaq();
        List<FAQSection> sections;
        FAQSection currentSection = null, parent = null;
        User currentUser = sharedContext.getCurrentUser();
        Collection<String> subscribers;
        String topic, newSectionName = null;
        int optionNo = 0;

        while (!(currentSection == null && optionNo == -1)) {
            if (currentSection == null) {
                sections = faq.getSections();
                view.displayFAQ(faq, currentUser instanceof Guest);
                view.displayDivider();
                view.displayInfo("[-1] to return to the main menu");
            } else {
                sections = currentSection.getSubsections();
                view.displayFAQSection(currentSection, currentUser instanceof Guest);
                parent = currentSection.getParent();

                if (parent == null) {
                    view.displayInfo("[-1] to return to the main FAQ menu");
                } else {
                    topic = parent.getTopic();
                    view.displayInfo("[-1] to return to " + topic);
                }
            }
            view.displayInfo("[-2] to add a new Q&A");
            view.displayDivider();
            String userInput = view.getInput("Please choose an option: ");
            view.displayInfo("\033[H\033[2J");



            try {
                optionNo = Integer.parseInt(userInput);

                if (optionNo < 0) {
                    if (currentSection == null) {
                        if (optionNo < -2) {
                            throw new NumberFormatException(); 
                        }
                    } else {
                        if (optionNo == -1) {
                            parent = currentSection.getParent();
                            currentSection = parent;
                            optionNo = 0;
                            break;
                        }
                    }
                    
                    if (optionNo == -2) {
                        addFAQItem(currentSection);
                    }

                } else {

                    if (optionNo > sections.size() || (optionNo == 0)) {
                        throw new NumberFormatException();
                    } else {
                        currentSection = sections.get(optionNo - 1);
                    }
                }
            } catch (NumberFormatException e) {
                view.displayDivider();
                view.displayError("Invalid option: " + userInput);
                view.displayDivider();
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
        FAQ faq = sharedContext.getFaq();
        String question;
        String answer;
        Boolean addOp = true;

        if (section != null) {
            addOp = view.getYesNoInput(
                    "Do you want to add a subtopic for this Q&A under the current topic" + section.getTopic());
            view.displayInfo("\033[H\033[2J");
        }

        if (addOp) {
            String new_section_topic;
            do {
                new_section_topic = view.getInput("Enter name of the new topic: ");
                if (new_section_topic.isEmpty()) {
                    view.displayInfo("\033[H\033[2J");
                    view.displayWarning("Topic cannot be empty");
                }
            } while (new_section_topic.isEmpty());
            Boolean goodToAdd = true;

            FAQSection newSection = new FAQSection(new_section_topic);
            newSection.setParent(section);
            if (section == null) {
                for (FAQSection existingSection : faq.getSections()) {
                    if (existingSection.getTopic().equals(new_section_topic)) {
                    goodToAdd = false;
                    section = existingSection;
                    break;
                    }
                }
                if (goodToAdd) {
                    faq.addSectionItems(newSection);
                }
            } else {
                for (FAQSection existingSection : section.getSubsections()) {
                    if (existingSection.getTopic().equals(new_section_topic)) {
                        goodToAdd = false;
                        section = existingSection;
                        break;
                    }
                }
                if (goodToAdd) {
                    section.addSubsection(newSection);
                }
            }

            if (goodToAdd) {
                section = newSection;
                view.displayInfo("\033[H\033[2J");
                view.displaySuccess(" New section " + new_section_topic + " added!");
                view.displayDivider();
            } else {
                view.displayInfo("\033[H\033[2J");
                view.displayWarning("Fail to add new subtopic, topic already exists!");
                view.displayWarning("Adding Q&A to the exist subtopic!");
            }
        }

        do {
            question = view.getInput("Enter question: ");
            answer = view.getInput("Enter answer: ");
            view.displayInfo("\033[H\033[2J");
            
            if (answer.isEmpty()) {
                view.displayWarning("Answer cannot be empty");
            } else if (question.isEmpty()) {
                view.displayWarning("Question cannot be empty");
            }
            
        } while (question.isEmpty() || answer.isEmpty());

        
        section.addItem(question, answer);
        String emailBody = String.format("The list of Q&A pair for subtopic: %s\n", section.getTopic());
        String senderEmail = sharedContext.getCurrentUser().getEmail();
        int subscriberCounter = 0;

        for (FAQItem item : section.getItems()){
            emailBody += String.format("Q: %s\n", item.getQuestion());
            emailBody += String.format("A: %s\n\n", item.getAnswer());
        }

        emailService.sendEmail(senderEmail, SharedContext.ADMIN_STAFF_EMAIL, "New Q&A added to subtopic " + section.getTopic(), emailBody);
        view.displayDivider();
        for (String email : sharedContext.usersSubscribedToFAQTopic(section.getTopic())) {
            emailService.sendEmail(SharedContext.ADMIN_STAFF_EMAIL, email, "New Q&A added to subtopic " + section.getTopic(), emailBody);
            view.displayDivider();
            subscriberCounter++;
        }
        view.displayDivider();
        view.displaySuccess("Added FAQ question: " + question);
        view.displaySuccess("Emails sent to admins and " + subscriberCounter + " subscriber(s)");
        view.displayDivider();
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
        int optionNo = 0;

        do {
            ArrayList<String> inquiryTitles = super.getInquiryTitles(sharedContext.getInquiries());
            if (inquiryTitles.isEmpty()) {
            view.displayDivider();
            view.displayInfo("No inquiries to manage");
            selectFromMenu(null, "return to main menu");
            return;
        }
        view.displayInfo("Inquiries to manage:");
        optionNo = selectFromMenu(inquiryTitles, "return to main menu");
        view.displayDivider();

        if (optionNo != -1) {
            Inquiry inquiry = sharedContext.getInquiries().get(optionNo - 1);

            // print the content of the inquiry
            view.displayInquiry(inquiry);
            // need to add alternative scenarios here
            Boolean respond = view.getYesNoInput("Do you want to respond to this inquiry?");
            view.displayInfo("\033[H\033[2J");
            if (respond) {
                respondToInquiry(inquiry);
            } else {
                Boolean redirect = view.getYesNoInput("Do you want to redirect this inquiry?");
                view.displayInfo("\033[H\033[2J");
                if (redirect) {
                    redirectInquiry(inquiry);
                }
            }
        }
    } while (optionNo != -1);
    }

    /**
     * Redirects an inquiry to another staff member by assigning the inquiry to them and sending a notification email.
     *
     * @param inquiry The Inquiry to be redirected.
     */
    private void redirectInquiry(Inquiry inquiry) {
        String teachingStaffEmail;

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        boolean valid = false;

        teachingStaffEmail = view.getInput("Enter the email of the staff member to whom this inquiry should be redirected: ");

        while (!valid) {

            if (Pattern.compile(emailRegex).matcher(teachingStaffEmail).matches()) {
                valid = true;
            } else {
                teachingStaffEmail = view.getInput("Invalid email provided, please enter again using the format, email@domain: ");
            }

        }

        inquiry.setAssignedTo(teachingStaffEmail);
        String emailSubject = String.format("New inquiry from \"%s\" has been redirected to you", inquiry.getInquirerEmail());
        String emailBody = String.format("Inquiry \"%s\" was redirected to you\nPlease log in to the Self Service Portal to review the inquiry.",inquiry.getInquirerEmail());

        emailService.sendEmail(SharedContext.ADMIN_STAFF_EMAIL, teachingStaffEmail, emailSubject, emailBody);
        view.displayDivider();
        view.displaySuccess("Inquiry assigned to " + teachingStaffEmail);
    }
}
