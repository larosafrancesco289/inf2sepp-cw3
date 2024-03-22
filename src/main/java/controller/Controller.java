package controller;

import external.*;
import view.*;
import model.*;

import java.util.Iterator;
import java.util.Collection;

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
        view.displayDivider();
        Iterator<T> iterator = items.iterator();
        Integer optionCounter = 0;
        boolean validInput = false;
        int userSelection;

        while (iterator.hasNext()) {
            optionCounter++;
            view.displayInfo((optionCounter) + ". " + iterator.next().toString());
        }
        if (selection != null) {
            view.displayInfo(selection);
        }

        while (!validInput) {
            String userInput = view.getInput(">Enter the number corisbonding to the option: \n");

            try {
                userSelection = Integer.parseInt(userInput);
                if (userSelection < optionCounter) {
                    validInput = true;
                    return userSelection;
                } else if (userSelection == optionCounter) {
                    validInput = true;
                    return -1;
                } else {
                    view.displayError("invalid input, try again");
                }
            } catch (NumberFormatException e) {
                view.displayError("invalid input, try again");
            }
        }
        return 0;
    }
}
