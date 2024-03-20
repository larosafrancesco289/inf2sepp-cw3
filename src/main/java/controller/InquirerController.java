package controller;

import external.*;
import model.*;
import view.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InquirerController extends Controller{
    public InquirerController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void consultFAQ() {
    }

    public void searchPages() {
        String searchQuery = view.getInput("Enter your search query: ");
        HashMap<String, Page> availablePages = sharedContext.getPages();

        // If current user is a guest
        if (sharedContext.getCurrentUser() instanceof Guest) {
            for (Page page : availablePages.values()) {
                Boolean isPrivate = page.isPrivate();
                if (isPrivate) {
                    availablePages.remove(page);
                }
            }
        }

        try {
            PageSearch search = new PageSearch(availablePages);
            Collection<PageSearchResult> results = search.search(searchQuery);

            if (results.size() > 4) {
                // take the first 4 results
                results = ((List<PageSearchResult>) results).subList(0, 4);
            }
            view.displaySearchResults(results);

        } catch (Exception exception) {
            view.displayException(exception);
        }
    }

    public void contactStaff() {
    }

    private void requestFAQUpdates(String topic, String email) {
    }

    private void stopFAQUpdates(String topic, String email) {
    }
}
