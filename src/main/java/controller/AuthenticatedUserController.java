package controller;

import external.*;
import model.*;
import view.*;

public class AuthenticatedUserController extends Controller {
    public AuthenticatedUserController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void logout() {
        sharedContext.setCurrentUser(new Guest());
        view.displaySuccess("You have been logged out");
    }
}
