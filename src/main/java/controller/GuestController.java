package controller;

import external.AuthenticationService;
import external.EmailService;
import model.AuthenticatedUser;
import model.SharedContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.View;

/**
 * Controller class for guest users' functionalities.
 * Extends the Controller class to handle actions that can be performed by guest users, specifically logging in.
 */
public class GuestController extends Controller {
    /**
     * Constructs a GuestController with the specified shared context, view, authentication service, and email service.
     * Initializes the controller with necessary services and context for its operation, for guest users.
     *
     * @param sharedContext The shared application context for maintaining state across the system.
     * @param view          The view used for interacting with the user through the command line.
     * @param authService   The service responsible for authenticating users.
     * @param emailService  The service for handling email operations.
     */
    public GuestController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    /**
     * Facilitates the login process for a guest user.
     * Prompts the user for their username and password, and attempts to authenticate them using the authentication service.
     * Upon successful authentication, the user's details (email and role) are retrieved, and the current user in the shared context is updated.
     * Throws an IllegalArgumentException if the email or role is not provided or unsupported.
     *
     * @throws IllegalArgumentException If the user's email is not provided or the user's role is unsupported.
     */
    public void login() {
        view.displayInfo("\033[H\033[2J");
        String username = view.getInput("Enter your username: ");
        String password = view.getInput("Enter your password: ");
        view.displayInfo("\033[H\033[2J");//clear the screen for security

        // assert username != null : "Username should not be null";
        // assert password != null : "Password should not be null";

        String response = authService.login(username, password);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            view.displayException(e);
            return;
        }

        // assert jsonObject != null : "JSON object should not be null";

        if (jsonObject.get("error") != null) {
            view.displayError((String) jsonObject.get("error"));
            return;
        }

        // Extract the email and role
        String email = (String) jsonObject.get("email");
        String role = (String) jsonObject.get("role");

        // Check email and role
        if (email == null) {
            throw new IllegalArgumentException("User email cannot be null");
        }

        if (role == null || (!role.equals("AdminStaff") && !role.equals("TeachingStaff") && !role.equals("Student"))) {
            throw new IllegalArgumentException("Unsupported user role");
        }

        AuthenticatedUser currentUser = null;
        try {
            currentUser = new AuthenticatedUser(email, role);
        } catch (IllegalArgumentException e) {
            view.displayException(e);
            return;
        }

        // assert currentUser != null : "currentUser should not be null after creation";

        sharedContext.setCurrentUser(currentUser);
        view.displaySuccess("Login successful");
    }
}
