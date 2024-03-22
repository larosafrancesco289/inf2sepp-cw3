package controller;

import external.*;
import model.*;
import view.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class InquirerController extends Controller {
    public InquirerController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void consultFAQ() {
        FAQ faq;
        List<FAQSection> sections;
        FAQSection currentSection = null, parent = null;
        User currentUser = sharedContext.getCurrentUser();
        Collection<String> subscribers;
        String topic, userEmail = null;
        int optionNo = 0;

        if (currentUser instanceof AuthenticatedUser) {
            userEmail = ((AuthenticatedUser) currentUser).getEmail();
        }

        
        while (!(currentSection == null && optionNo == -1)) {
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

                if (currentUser instanceof Guest) {
                    view.displayInfo("[-2] to request updates on this topic");
                    view.displayInfo("[-3] to stop receiving updates on this topic");
                } else {
                    topic = currentSection.getTopic();
                    subscribers = sharedContext.usersSubscribedToFAQTopic(topic);

                    // if current user is in the subscribers list
                    if (subscribers.contains(userEmail)) {
                        view.displayInfo("[-2] to stop receiving updates on this topic");
                    } else {
                        view.displayInfo("[-2] to request updates on this topic");
                    }
                }
            }

            try {
                optionNo = Integer.parseInt(view.getInput("Please choose an option: "));
                topic = currentSection.getTopic();

                // Handeling the negative options
                if (currentSection != null) {
                    switch (optionNo) {
                        case -1:
                            parent = currentSection.getParent();
                            currentSection = parent;
                            break;   
                        
                        case -2: 
                            if (currentUser instanceof Guest) {
                                requestFAQUpdates(null, topic);
                                break;
                            }
                            subscribers = sharedContext.usersSubscribedToFAQTopic(topic);
                            if (subscribers.contains(userEmail)) {
                                 stopFAQUpdates(userEmail, topic);
                                }
                            break;
                            
                        case -3: 
                            if (currentUser instanceof Guest) {
                                requestFAQUpdates(null, topic);
                            }
                            break;
                        
                    }
                } 
                //update the view
                if (optionNo != -1){
                    if (currentSection == null) {
                        faq = sharedContext.getFaq();
                        sections = faq.getSections();
                    } else {
                        sections = currentSection.getSubsections();
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

    public void searchPages() {
        String searchQuery = view.getInput("Enter your search query: ");
        HashMap<String, Page> availablePages = sharedContext.getPages();

        // If current user is a guest
        if (sharedContext.getCurrentUser() instanceof Guest) {
            for (Page page : availablePages.values()) {
                Boolean isPrivate = page.isPrivate();
                if (isPrivate) {
                    availablePages.remove(page.getTitle());
                }
            }
        }

        PageSearch search;
        try {
            search = new PageSearch(availablePages);
            // Should be a IOException NEED TO ASK
        } catch (Exception exception) {
            view.displayException(exception);
            return;
        }
        Collection<PageSearchResult> results = search.search(searchQuery);

        if (results.size() > 4) {
            // take the first 4 results
            results = ((List<PageSearchResult>) results).subList(0, 4);
        }
        view.displaySearchResults(results);
    }

    /**
     * Method adds and Inquiry object to inquiryList within sharedContext
     * User inputs inquiry subject/content
     * Gets user email from object if logged in, otherwise asks for input
     * Notifies admin staff of inquiry via email system
     * **/
    public void contactStaff() {

        User currentUser = sharedContext.getCurrentUser();
        Inquiry userInquiry;
        String userEmail;
        String inquirySubject;
        String inquiryContent;

        /*
        * 1/2 - get details
        * - if guest input email, otherwise get from user
        * - get subject/content from user input
        * */

        if (currentUser instanceof AuthenticatedUser){
            userEmail = ((AuthenticatedUser) currentUser).getEmail();
        } else {
            userEmail = view.getInput("Please enter your email address:");

            // validate input email address
            String emailRegex = "^(.+)@(\\S+) $";
            boolean valid = false;

            while (!valid){

                if (Pattern.compile(emailRegex).matcher(userEmail).matches()){
                    valid = true;
                } else {
                    userEmail = view.getInput("Invalid email provided, please enter again using the format, email@domain:");
                }

            }
        }

        inquirySubject = view.getInput("Please enter the subject of your inquiry:");
        inquiryContent = view.getInput("Please enter your message to pass to staff members:");


        // 3 - email all admin staff

        emailService.sendEmail(userEmail, "adminStaff", inquirySubject, inquiryContent);

        // 4 - Add inquiry to list in SharedContext

        userInquiry = new Inquiry(userEmail, inquirySubject, inquiryContent);

        List<Inquiry> currInquiryList = sharedContext.getInquiries();

        currInquiryList.add(userInquiry);
        sharedContext.setInquiries(currInquiryList);

        // 5 - Confirm inquiry sent

        view.displayInfo("Inquiry sent.");

    }

    private void requestFAQUpdates(String userEmail, String topic) {
        if (userEmail == null) {
            userEmail = view.getInput("Please enter your email address");
        }
        Boolean success = sharedContext.registerForFAQUpdates(userEmail, topic);
        if (success) {
            view.displaySuccess("Successfully registered " + userEmail + " for updates on " + topic);
        } else {
            view.displayFailure("Failed to register " + userEmail + " for updates on " + topic + ". Perhaps this email was already registered?");
        }
    }

    private void stopFAQUpdates(String userEmail, String topic) {
        if (userEmail == null) {
            userEmail = view.getInput("Please enter your email address");
        }
        Boolean success = sharedContext.unregisterForFAQUpdates(userEmail, topic);
        if (success) {
            view.displaySuccess("Successfully unregistered " + userEmail + " for updates on " + topic);
        } else {
            view.displayFailure("Failed to unregister " + userEmail + " for updates on " + topic + ". Perhaps this email was already registered?");
        }
    }
}
