package controller;

import external.AuthenticationService;
import external.EmailService;
import model.SharedContext;
import view.View;

import java.util.Collection;
import java.util.Iterator;

public abstract class Controller {
    public SharedContext sharedContext;
    public View view;
    public AuthenticationService authService;
    public EmailService emailService;

    protected Controller(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        this.sharedContext = sharedContext;
        this.view = view;
        this.authService = authService;
        this.emailService = emailService;
    }

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
                optionCounter++;
                view.displayInfo("[-1] " + selection);
            }

            view.displayDivider();
            String userInput = view.getInput("Please choose an option \n");

            try {
                optionNo = Integer.parseInt(userInput);
                if (selection != null && optionNo == -1) {
                    return -1;
                }
                if (optionNo <= optionCounter && optionNo > 0) {
                    return optionNo;
                }
                view.displayInfo("\033[H\033[2J");
                view.displayDivider();
                view.displayError("invalid option: " + userInput);
            } catch (NumberFormatException e) {
                view.displayDivider();
                view.displayInfo("\033[H\033[2J");
                view.displayError("invalid option: " + userInput);
            }
        }
    }

    public SharedContext getSharedContext() {
        return sharedContext;
    }

    public void setSharedContext(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public AuthenticationService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
