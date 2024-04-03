package view;

import model.*;

import java.util.Collection;
import java.util.Scanner;
import java.util.List;

/**
 * A console-based implementation of the {@link View} interface to interact with users via text.
 * This class uses standard input and output to display messages and gather user input.
 */
public class TextUserInterface implements View {
    private final Scanner scanner; // Scanner to read user input

    /**
     * Constructs a new TextUserInterface, initializing the scanner to read from standard input.
     */
    public TextUserInterface() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts the user for input and returns the user's response as a String.
     *
     * @param prompt the message to display to the user before waiting for input.
     * @return the user's input as a String.
     */
    @Override
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Asks the user a yes/no question until a valid response is given.
     *
     * @param question the yes/no question to ask the user.
     * @return true if the user answers "yes", false if the user answers "no".
     */
    @Override
    public boolean getYesNoInput(String question) {
        String input;
        do {
            System.out.print(question + " (yes/no): ");
            input = scanner.nextLine().trim().toLowerCase();
        } while (!input.equals("yes") && !input.equals("no"));
        return input.equals("yes");
    }

    /**
     * Displays informational message to the user.
     *
     * @param information the information to display.
     */
    @Override
    public void displayInfo(String information) {
        System.out.println(information);
    }

    /**
     * Displays a success message to the user.
     *
     * @param message the success message to display.
     */
    @Override
    public void displaySuccess(String message) {
        System.out.println("\u001B[32m"+message+" \u001B[37m");
    }

    /**
     * Displays a warning message to the user.
     *
     * @param warning the warning message to display.
     */
    @Override
    public void displayWarning(String warning) {
        System.out.println("Warning: " + warning);
    }

    /**
     * Displays an error message to the user with red text color.
     *
     * @param errorMessage the error message to display.
     */
    @Override
    public void displayError(String errorMessage) {
        System.out.println("\u001B[31m Error: " + errorMessage + "\u001B[37m");
    }

    /**
     * Displays an exception's message to the user.
     *
     * @param exception the exception whose message is to be displayed.
     */
    @Override
    public void displayException(Exception exception) {
        System.out.println("Exception: " + exception.getMessage());
    }

    /**
     * Displays a divider line to the user, useful for separating sections of output.
     */
    @Override
    public void displayDivider() {
        System.out.println("--------------------------------------------------");
    }

    /**
     * Displays the top-level sections of a FAQ.
     *
     * @param faq       the FAQ to display.
     * @param isPrivate whether or not the FAQ is private.
     */
    @Override
    public void displayFAQ(FAQ faq, boolean isPrivate) {
        // Note that the requirement for private FAQs is not present in this implementation.
        System.out.println("FAQ Sections:");
        int i = 0;
        for (FAQSection section : faq.getSections()) {
            System.out.println(String.format("[ %d ] ", ++i) + section.getTopic());
        }
    }

    /**
     * Displays a specific section of a FAQ, including subtopics and items.
     *
     * @param faqSection the section of the FAQ to display.
     * @param isPrivate  whether or not the section is private.
     */
    @Override
    public void displayFAQSection(FAQSection faqSection, boolean isPrivate) {
        List<FAQItem> qaPairs = faqSection.getItems();
        List<FAQSection> subSections = faqSection.getSubsections();
        // Note that the requirement for private FAQs is not present in this implementation.
        System.out.printf("Topic: %s \n", faqSection.getTopic());
        displayDivider();
        if (subSections.size() != 0) {
            System.out.println("Subtopics:");
            int i = 0;
            for (FAQSection subsection : subSections) {
                System.out.println(String.format("[ %d ] ", ++i) + subsection.getTopic());
            }
        }
        if (qaPairs.size() != 0) {
            displayDivider();
            System.out.println("Questions:");
            for (FAQItem item : qaPairs) {
                System.out.printf("Q: %s\n", item.getQuestion());
                System.out.printf("A: %s\n\n", item.getAnswer());
            }
        }
        if (subSections.size() + qaPairs.size() == 0) {
            displayInfo("Current topic is empty");
        }
        displayDivider();
    }

    /**
     * Displays an inquiry to the user, including the subject, sender, and content.
     *
     * @param inquiry the inquiry to display.
     */
    @Override
    public void displayInquiry(Inquiry inquiry) {
        System.out.printf("Subject: %s\n", inquiry.getSubject());
        System.out.printf("Sender: %s\n", inquiry.getInquirerEmail());
        System.out.printf("Content: %s\n", inquiry.getContent());
    }

    /**
     * Displays the results of a search operation, showing each result's title and content.
     *
     * @param searchResults the collection of search results to display.
     */
    @Override
    public void displaySearchResults(Collection<PageSearchResult> searchResults) {
        System.out.println("Search Results:");
        for (PageSearchResult result : searchResults) {
            System.out.println(result.getFormattedContent());
        }
    }
}