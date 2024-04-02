package view;

import model.FAQ;
import model.FAQSection;
import model.Inquiry;
import model.PageSearchResult;

import java.util.Collection;

/**
 * View interface.
 */
/**
 * The {@code View} interface defines the contract for user interaction mechanisms
 * in a console-based application. It handles inputs from the user and displays various
 * types of outputs including information messages, warnings, errors, and structured
 * data like FAQs and search results.
 */
public interface View {

    /**
     * Prompts the user for input and returns the entered string.
     *
     * @param prompt The message displayed to the user as a prompt for input.
     * @return The user's input as a {@code String}.
     */
    String getInput(String prompt);

    /**
     * Asks a yes/no question to the user and returns the answer as a boolean.
     *
     * @param question The yes/no question to be asked.
     * @return {@code true} if the user answers yes, {@code false} otherwise.
     */
    boolean getYesNoInput(String question);

    /**
     * Displays an informational message to the user.
     *
     * @param information The information message to display.
     */
    void displayInfo(String information);

    /**
     * Displays a success message to the user.
     *
     * @param message The success message to display.
     */
    void displaySuccess(String message);

    /**
     * Displays a warning message to the user.
     *
     * @param warning The warning message to display.
     */
    void displayWarning(String warning);

    /**
     * Displays an error message to the user.
     *
     * @param errorMessage The error message to display.
     */
    void displayError(String errorMessage);

    /**
     * Displays the stack trace and message of an exception.
     *
     * @param exception The exception whose information is to be displayed.
     */
    void displayException(Exception exception);

    /**
     * Displays a divider in the console, typically used for separating sections
     * of output for better readability.
     */
    void displayDivider();

    /**
     * Displays the details of an FAQ.
     *
     * @param faq The FAQ to display.
     * @param isPrivate Indicates whether the FAQ is private and should be displayed differently.
     */
    void displayFAQ(FAQ faq, boolean isPrivate);

    /**
     * Displays a specific section of an FAQ.
     *
     * @param faqSection The FAQ section to display.
     * @param isPrivate Indicates whether the FAQ section is private and should be displayed differently.
     */
    void displayFAQSection(FAQSection faqSection, boolean isPrivate);

    /**
     * Displays the details of an inquiry.
     *
     * @param inquiry The inquiry to display.
     */
    void displayInquiry(Inquiry inquiry);

    /**
     * Displays the results of a search operation.
     *
     * @param searchResults A collection of {@link PageSearchResult} objects representing the search results.
     */
    void displaySearchResults(Collection<PageSearchResult> searchResults);
}
