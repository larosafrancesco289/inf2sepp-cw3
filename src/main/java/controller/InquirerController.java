package controller;

import external.*;
import model.*;
import view.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InquirerController extends Controller{
    public InquirerController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void consultFAQ() {
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
            }
            else {
                view.displayFAQSection(currentSection, currentUser instanceof Guest);
                parent = currentSection.getParent();

                if (parent == null) {
                    view.displayInfo("[-1] to return to the main menu");
                }
                else {
                    topic = parent.getTopic();
                    view.displayInfo("[-1] to return to " + topic);
                }

                if (currentUser instanceof Guest) {
                    view.displayInfo("[-2] to request updates on this topic");
                    view.displayInfo("[-3] to stop receiving updates on this topic");
                }
                else {
                    topic = currentSection.getTopic();
                    subscribers = sharedContext.usersSubscribedToFAQTopic(topic);

                    // if current user is in the subscribers list
                    if (subscribers.contains(userEmail)) {
                        view.displayInfo("[-2] to stop receiving updates on this topic");
                    }
                    else {
                        view.displayInfo("[-2] to request updates on this topic");
                    }
                }
            }

            try {
                optionNo = Integer.parseInt(view.getInput("Please choose an option: "));
                topic = currentSection.getTopic();

                if (currentSection != null && currentUser instanceof Guest && optionNo == -2) {
                    requestFAQUpdates(null, topic);
                } else if (currentSection != null && currentUser instanceof Guest && optionNo == -3) {
                    stopFAQUpdates(null, topic);
                } else if (currentSection != null && optionNo == -2) {
                    subscribers = sharedContext.usersSubscribedToFAQTopic(topic);
                    if (subscribers.contains(userEmail)) {
                        stopFAQUpdates(userEmail, topic);
                    } else {
                        requestFAQUpdates(userEmail, topic);
                    }
                } else if (currentSection != null && optionNo == -1) {
                    parent = currentSection.getParent();
                    currentSection = parent;
                } else if (optionNo == -1) {
                    if (currentSection == null) {
                        faq = sharedContext.getFaq();
                        sections = faq.getSections();
                    }
                    else {
                        sections = parent.getSubsections();
                    }

                    // if optionNo out of section bounds
                    if (optionNo > sections.size()) {
                        view.displayError("Invalid option: " + optionNo);
                    }
                    else {
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
        } catch (Exception exception){
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

    public void contactStaff() {
    }

    private void requestFAQUpdates(String userEmail, String topic) {
        if (userEmail == null) {
            userEmail = view.getInput("Please enter your email address");
        }
        Boolean success = sharedContext.registerForFAQUpdates(userEmail, topic);
        if (success) {
            view.displaySuccess("Successfully registered " + userEmail + " for updates on " + topic + "");
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
            view.displaySuccess("Successfully unregistered " + userEmail + " for updates on " + topic + "");
        } else {
            view.displayFailure("Failed to unregister " + userEmail + " for updates on " + topic + ". Perhaps this email was already registered?");
        }
    }
}
