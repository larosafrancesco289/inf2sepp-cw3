package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.View;

import java.util.Collection;
import java.util.Iterator;

/**
 * Abstract base class for controllers within the application.
 * Provides common functionality and fields needed by all controllers, including shared context,
 * view handling, authentication services, and email services.
 */
public abstract class Controller {
    public SharedContext sharedContext;
    public View view;
    public AuthenticationService authService;
    public EmailService emailService;

    /**
     * Constructs a Controller with the specified shared context, view, authentication service, and email service.
     *
     * @param sharedContext The shared context for application-wide state management.
     * @param view          The interface for user interaction.
     * @param authService   The service for authenticating users.
     * @param emailService  The service for sending emails.
     */
    protected Controller(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        this.sharedContext = sharedContext;
        this.view = view;
        this.authService = authService;
        this.emailService = emailService;
    }

    /**
     * Presents a menu to the user based on a collection of items and allows the user to select an option.
     * Optionally includes a selection for returning or performing an alternate action.
     *
     * @param items     The collection of items to display in the menu.
     * @param selection An optional selection string for performing an alternate action (e.g., "return to main menu").
     * @return The user-selected option number, or -1 if the alternate selection is chosen.
     */
    protected <T> int selectFromMenu(Collection<T> items, String selection) {
        int optionNo;

        while (true) {
            view.displayDivider();
            Integer optionCounter = 0;

            if (items != null) {
                Iterator<T> iterator = items.iterator();
                while (iterator.hasNext()) {
                    optionCounter++;
                    view.displayInfo("[" + (optionCounter) + "] " + iterator.next().toString());
                }
            }

            if (selection != null) {
                view.displayInfo("[-1] " + selection);
            }

            view.displayDivider();
            String userInput = view.getInput("Please choose an option \n");
            view.displayInfo("\033[H\033[2J");
            try {
                optionNo = Integer.parseInt(userInput);
                if (selection != null && optionNo == -1) {
                    return -1;
                }
                if (optionNo <= optionCounter && optionNo > 0) {
                    return optionNo;
                }
                view.displayDivider();
                view.displayError("invalid option: " + userInput);
            } catch (NumberFormatException e) {
                view.displayDivider();
                view.displayError("invalid option: " + userInput);
            }
        }
    }

    /**
     * Gets the shared context.
     *
     * @return The shared context.
     */
    public SharedContext getSharedContext() {
        return sharedContext;
    }

    /**
     * Sets the shared context.
     *
     * @param sharedContext The shared context to set.
     */
    public void setSharedContext(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    /**
     * Gets the view.
     *
     * @return The view.
     */
    public View getView() {
        return view;
    }

    /**
     * Sets the view.
     *
     * @param view The view to set.
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Gets the authentication service.
     *
     * @return The authentication service.
     */
    public AuthenticationService getAuthService() {
        return authService;
    }

    /**
     * Sets the authentication service.
     *
     * @param authService The authentication service to set.
     */
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    /**
     * Gets the email service.
     *
     * @return The email service.
     */
    public EmailService getEmailService() {
        return emailService;
    }

    /**
     * Sets the email service.
     *
     * @param emailService The email service to set.
     */
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
