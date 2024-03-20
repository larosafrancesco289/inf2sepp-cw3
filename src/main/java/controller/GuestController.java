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

    public void login(){
        String username = view.getInput("Enter your username: ");
        String password = view.getInput("Enter your password: ");
        String response = authService.login(username, password);

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response);

            // Extract the role value
            String role = (String) jsonObject.get("role");

            AuthenticatedUser currentUser = new AuthenticatedUser(username, role);
            sharedContext.setCurrentUser(currentUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
