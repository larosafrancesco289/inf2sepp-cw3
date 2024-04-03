import controller.MenuController;
import external.MockAuthenticationService;
import external.MockEmailService;
import model.SharedContext;
import org.json.simple.parser.ParseException;
import view.TextUserInterface;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The main class of the application.
 */
public class Main {
    /**
     * The main method of the application.
     *
     * @param args The command line arguments
     * @throws IOException        if an I/O error occurs
     * @throws ParseException     if a parse error occurs
     * @throws URISyntaxException if a URI syntax error occurs
     */
    public static void main(String[] args) throws IOException, ParseException, URISyntaxException {
        // Initialize the application
        MockAuthenticationService authService = new MockAuthenticationService();
        MockEmailService emailService = new MockEmailService();
        SharedContext sharedContext = new SharedContext();
        TextUserInterface textUserInterface = new TextUserInterface();
        MenuController menuController = new MenuController(sharedContext, textUserInterface, authService, emailService);
        while (true) {
            // Start the menu
            menuController.mainMenu();
            // String username = textUserInterface.getInput("Enter your username: ");
            // String password = textUserInterface.getInput("Enter your password: ");
            // try {
            //     String token = authService.login(username, password);
            //     menuController.mainMenu();
            // } catch (Exception e) {
            //     textUserInterface.displayError(e.getMessage());
            // }
        }
    }
}
