package view;

import model.*;

import java.util.Collection;

public interface View {
    String getInput(String prompt);
    boolean getYesNoInput(String question);
    void displayInfo(String information);
    void displaySuccess(String message);
    void displayWarning(String warning);
    void displayError(String errorMessage);
    void displayException(Exception exception);
    void displayDivider();
    void displayFAQ(FAQ faq, boolean complete);
    void displayFAQSection(FAQSection faqSection, boolean complete);
    void displayInquiry(Inquiry inquiry);
    void displaySearchResults(Collection<PageSearchResult> searchResults);

}
