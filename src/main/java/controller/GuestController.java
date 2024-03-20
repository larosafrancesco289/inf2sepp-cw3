package controller;

import external.*;
import model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.*;
public class GuestController extends Controller{
    public GuestController(SharedContext sharedContext, View view, AuthenticationService authService, EmailService emailService) {
        super(sharedContext, view, authService, emailService);
    }

    public void login() throws ParseException{
        String username = view.getInput("Enter your username: ");
        String password = view.getInput("Enter your password: ");
        String response = authService.login(username, password);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);

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

        if (role == null || role != "AdminStaff" && role != "TeachingStaff" && role != "Student") {
            throw new IllegalArgumentException("Unsupported user role");
        }

        AuthenticatedUser currentUser = null;
        try {
            currentUser = new AuthenticatedUser(email, role);
        } catch (IllegalArgumentException e) {
            view.displayException(e);
            return;
        }

        sharedContext.setCurrentUser(currentUser);
        view.displaySuccess("Login successful");
    }
}
