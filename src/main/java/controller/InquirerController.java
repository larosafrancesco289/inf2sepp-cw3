package controller;

import external.AuthenticationService;
import external.EmailService;
import model.*;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import view.View;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Controller class responsible for handling actions performed by inquirers (users and guests),
 * such as consulting FAQs, searching pages, and contacting staff.
 */
public class InquirerController extends Controller {
    /**
     * Constructs an InquirerController with specified shared context, view, authentication service, and email service.
     *
     * @param sharedContext The shared context across the application.
     * @param view          The view for user interaction.
     * @param authService   The service for authentication.
     * @param emailService  The service for email operations.
     */
    public InquirerController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    /**
     * Allows the user to consult the Frequently Asked Questions (FAQ) section.
     * Users can navigate through FAQ sections and request updates on specific topics.
     */
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
                view.displayDivider();
                view.displayInfo("[-1] to return to the main menu");
            } else {
                view.displayFAQSection(currentSection, currentUser instanceof Guest);
                parent = currentSection.getParent();
                view.displayDivider();
                if (parent == null) {
                    view.displayInfo("[-1] to return to the main FAQ menu");
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
            view.displayDivider();
            String userInput = view.getInput("Please choose an option: ");
            view.displayInfo("\033[H\033[2J");
            try {
                optionNo = Integer.parseInt(userInput);
                //topic = currentSection.getTopic();

                // Handeling the negative options

                if (currentSection == null) {
                    if (optionNo < -1) {
                        throw new NumberFormatException(); //Not in the sequence diagram but required
                    }
                } else {
                    if (optionNo == 0) {
                        throw new NumberFormatException();
                    }
                    switch (optionNo) {
                        case -1:
                            parent = currentSection.getParent();
                            currentSection = parent;
                            optionNo = 0;
                            break;
                        case -2:
                            topic = currentSection.getTopic();
                            if (currentUser instanceof Guest) {
                                requestFAQUpdates(null, topic);
                                break;
                            }
                            subscribers = sharedContext.usersSubscribedToFAQTopic(topic);
                            if (subscribers.contains(userEmail)) {
                                stopFAQUpdates(userEmail, topic);
                            } else {
                                requestFAQUpdates(userEmail, topic);
                            }
                            break;

                        case -3:
                            topic = currentSection.getTopic();
                            if (currentUser instanceof Guest) {
                                stopFAQUpdates(null, topic);
                            } else {
                                throw new NumberFormatException(); //Not in the sequence diagram but required
                            }
                            break;

                    }
                }
                //update the view
                if (optionNo != -1 || optionNo == 0) {
                    if (currentSection == null) {
                        faq = sharedContext.getFaq();
                        sections = faq.getSections();
                    } else {
                        sections = currentSection.getSubsections();
                    }
                    // if optionNo out of section bounds
                    if ((optionNo > sections.size())) {
                        view.displayError("Invalid option: " + userInput);
                        view.displayDivider();
                    } else if (optionNo > 0) {
                        currentSection = sections.get(optionNo - 1);
                    }
                }

            } catch (NumberFormatException e) {
                view.displayError("Invalid option: " + userInput);
                view.displayDivider();
            }
        }
    }

    /**
     * Provides a search functionality for the user to search through available pages.
     * If the user is a guest, private pages are excluded from the search results.
     */
    public void searchPages() {
        String searchQuery = view.getInput("Enter your search query: ");
        // assert searchQuery != null : "Search query cannot be null";

        // If search query is empty, prompt user to enter a valid search query
        while (searchQuery.isEmpty()) {
            view.displayError("Search query cannot be empty.");
            searchQuery = view.getInput("Please enter a valid search query: ");
        }

        // If search query is incorrect, prompt user to enter a valid search query
        StandardQueryParser queryParser = new StandardQueryParser();
        while (true) {
            try {
                queryParser.parse(searchQuery, "content");
                break;
            } catch (Exception exception) {
                view.displayError("Invalid search query. Please try again.");
                searchQuery = view.getInput("Please enter a valid search query: ");
            }
        }

        HashMap<String, Page> availablePages = sharedContext.getPages();

        // If current user is a guest remove private pages from search results
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
        } catch (IOException exception) {
            // This will also catch other query format exceptions
            view.displayException(exception);
            return;
        }

        Collection<PageSearchResult> results = null;
        try {
            results = search.search(searchQuery);
        } catch (Exception exception) {
            view.displayException(exception);
        }

        // if no results found
        if (results == null || results.isEmpty()) {
            view.displayInfo("\033[H\033[2J");
            view.displayError("No results found for query: " + searchQuery);
            return;
        }

        // Limit the number of results to 4
        if (results.size() > 4) {
            results = ((List<PageSearchResult>) results).subList(0, 4);
        }

        view.displaySearchResults(results);
    }

    /**
     * Allows users to contact staff by sending an inquiry.
     * Users provide an email (if not authenticated), a subject, and content for their inquiry.
     * The inquiry is then sent to all admin staff via email and added to the shared context's inquiry list.
     */
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

        if (currentUser instanceof AuthenticatedUser) {
            userEmail = ((AuthenticatedUser) currentUser).getEmail();
        } else {
            userEmail = view.getInput("Please enter your email address: ");

            // validate input email address
            String emailRegex = "(.*)@(.*)";
            boolean valid = false;

            while (!valid) {

                if (Pattern.compile(emailRegex).matcher(userEmail).matches()) {
                    valid = true;
                } else {
                    userEmail = view
                            .getInput("Invalid email provided, please enter again using the format, email@domain: ");
                }

            }
            view.displayInfo("\033[H\033[2J");
        }

        inquirySubject = view.getInput("Please enter the subject of your inquiry: ");
        inquiryContent = view.getInput("Please enter your message to pass to staff members: ");


        // 3 - email all admin staff

        emailService.sendEmail(userEmail, "adminStaff", inquirySubject, inquiryContent);

        // 4 - Add inquiry to list in SharedContext

        userInquiry = new Inquiry(userEmail, inquirySubject, inquiryContent);

        List<Inquiry> currInquiryList = sharedContext.getInquiries();

        currInquiryList.add(userInquiry);
        sharedContext.setInquiries(currInquiryList);

        // 5 - Confirm inquiry sent
        view.displayInfo("\033[H\033[2J");
        view.displaySuccess("Inquiry sent.");

    }

    /**
     * Registers a user for updates on a specific FAQ topic.
     * If the user is not authenticated, their email is requested for registration.
     *
     * @param userEmail The email of the user to register for updates.
     * @param topic     The FAQ topic the user wants updates on.
     */
    private void requestFAQUpdates(String userEmail, String topic) {
        if (userEmail == null) {
            userEmail = view.getInput("Please enter your email address: ");
            String emailRegex = "(.*)@(.*)";
            boolean valid = false;

            while (!valid) {

                if (Pattern.compile(emailRegex).matcher(userEmail).matches()) {
                    valid = true;
                } else {
                    userEmail = view.getInput("Invalid email provided, please enter again using the format, email@domain: ");
                }
                view.displayInfo("\033[H\033[2J");
            }
        }
        Boolean success = sharedContext.registerForFAQUpdates(userEmail, topic);
        if (success) {
            view.displaySuccess("Successfully registered " + userEmail + " for updates on " + topic);
            view.displayDivider();
        } else {
            view.displayError("Failed to register " + userEmail + " for updates on " + topic + ". Perhaps this email was already registered?");
            view.displayDivider();
        }
    }

    /**
     * Unregisters a user from updates on a specific FAQ topic.
     * If the user is not authenticated, their email is requested.
     *
     * @param userEmail The email of the user to unregister from updates.
     * @param topic     The FAQ topic the user wants to stop receiving updates on.
     */
    private void stopFAQUpdates(String userEmail, String topic) {
        if (userEmail == null) {
            userEmail = view.getInput("Please enter your email address: ");
            String emailRegex = "(.*)@(.*)";
            boolean valid = false;

            while (!valid) {

                if (Pattern.compile(emailRegex).matcher(userEmail).matches()) {
                    valid = true;
                } else {
                    userEmail = view
                            .getInput("Invalid email provided, please enter again using the format, email@domain: ");
                }

            }
            view.displayInfo("\033[H\033[2J");
        }
        Boolean success = sharedContext.unregisterForFAQUpdates(userEmail, topic);
        if (success) {
            view.displaySuccess("Successfully unregistered " + userEmail + " for updates on " + topic);
            view.displayDivider();
        } else {
            view.displayError("Failed to unregister " + userEmail + " for updates on " + topic + ". Perhaps this email was already registered?");
            view.displayDivider();
        }
    }
}
