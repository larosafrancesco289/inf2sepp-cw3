package view;
import model.*;
import java.util.Collection;
import java.util.Scanner;

public class TextUserInterface implements View {
    private Scanner scanner;

    public TextUserInterface() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public boolean getYesNoInput(String question) {
        String input;
        do {
            System.out.print(question + " (yes/no): ");
            input = scanner.nextLine().trim().toLowerCase();
        } while (!input.equals("yes") && !input.equals("no"));
        return input.equals("yes");
    }

    @Override
    public void displayInfo(String information) {
        System.out.println(information);
    }

    @Override
    public void displaySuccess(String message) {
        System.out.println(message);
    }

    @Override
    public void displayWarning(String warning) {
        System.out.println("Warning: " + warning);
    }

    @Override
    public void displayError(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }

    @Override
    public void displayException(Exception exception) {
        System.out.println("Exception: " + exception.getMessage());
    }

    @Override
    public void displayDivider() {
        System.out.println("--------------------------------------------------");
    }

    @Override
    public void displayFAQ(FAQ faq, boolean complete) {
        // Implementation would depend on how the FAQ class is structured
    }

    @Override
    public void displayFAQSection(FAQSection faqSection, boolean complete) {
        // Implementation would depend on how the FAQSection class is structured
    }

    @Override
    public void displayInquiry(Inquiry inquiry) {
        // Implementation would depend on how the Inquiry class is structured
    }

    @Override
    public void displaySearchResults(Collection<PageSearchResult> searchResults) {
        // Implementation would depend on how the search results are to be displayed
    }
}
