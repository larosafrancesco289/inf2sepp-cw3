package view;

import model.FAQ;
import model.FAQSection;
import model.Inquiry;
import model.PageSearchResult;

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

    void displayFAQ(FAQ faq, boolean isPrivate);

    void displayFAQSection(FAQSection faqSection, boolean isPrivate);

    void displayInquiry(Inquiry inquiry);

    void displaySearchResults(Collection<PageSearchResult> searchResults);

}
