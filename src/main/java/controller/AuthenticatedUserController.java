package controller;

import external.AuthenticationService;
import external.EmailService;
import model.Guest;
import model.SharedContext;
import view.View;

/**
 * Controller class for authenticated user functionalities.
 * This class extends the Controller class and includes methods specific to the actions an authenticated user can perform,
 * such as logging out.
 */
public class AuthenticatedUserController extends Controller {
    /**
     * Constructs an AuthenticatedUserController with the specified shared context, view, authentication service, and email service.
     * This constructor initializes the controller with necessary dependencies for its operation.
     *
     * @param sharedContext The shared application context.
     * @param view          The view used for input and output operations.
     * @param authService   The authentication service for user authentication.
     * @param emailService  The email service for sending notifications.
     */
    public AuthenticatedUserController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    /**
     * Logs the authenticated user out of the system. Sets the current user in the shared context to a new Guest instance,
     * effectively removing any authenticated user's session. It also informs the user of the successful logout.
     */
    public void logout() {
        // assert sharedContext.getCurrentUser() instanceof AuthenticatedUser : "The current user is authenticated";
        sharedContext.setCurrentUser(new Guest());
        view.displaySuccess("You have been logged out");
    }
}
