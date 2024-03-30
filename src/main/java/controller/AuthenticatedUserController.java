package controller;

import external.*;
import model.*;
import view.*;

public class AuthenticatedUserController extends Controller {
    public AuthenticatedUserController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    /**
     * Logs authenticated user out. Sets current user to user of type guest
     */
    public void logout() {
        // assert sharedContext.getCurrentUser() instanceof AuthenticatedUser : "The current user is authenticated";
        sharedContext.setCurrentUser(new Guest());
        view.displaySuccess("You have been logged out");
    }
}
