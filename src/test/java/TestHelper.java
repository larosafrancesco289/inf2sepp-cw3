import controller.AdminStaffController;
import controller.AuthenticatedUserController;
import controller.GuestController;
import controller.InquirerController;
import external.AuthenticationService;
import external.EmailService;
import external.MockAuthenticationService;
import external.MockEmailService;
import model.SharedContext;
import org.json.simple.parser.ParseException;
import view.TextUserInterface;
import view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Helper class for setting up the testing environment.
 */
public class TestHelper {
    // Redirect System.in and System.out to enable automated input and output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final AuthenticationService authService;
    private final EmailService emailService;
    private View view;
    private final SharedContext sharedContext;
    private final GuestController guestController;
    private final AuthenticatedUserController authenticatedUserController;
    private final AdminStaffController adminStaffController;
    private final InquirerController inquirerController;

    /**
     * Constructor for the TestHelper class.
     */
    public TestHelper() {
        try {
            authService = new MockAuthenticationService();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        emailService = new MockEmailService();
        view = new TextUserInterface();
        sharedContext = new SharedContext();

        // Set up all controllers with the shared context, view, and external services
        guestController = new GuestController(sharedContext, view, authService, emailService);
        authenticatedUserController = new AuthenticatedUserController(sharedContext, view, authService, emailService);
        adminStaffController = new AdminStaffController(sharedContext, view, authService, emailService);
        inquirerController = new InquirerController(sharedContext, view, authService, emailService);
    }

    /**
     * Mocks user input and output to facilitate testing of console-based interactions.
     *
     * @param data The mocked input data simulating user input.
     */
    public void mockInputOutput(String data) {
        // Mock standard input (System.in) with provided data.
        ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
        System.setIn(input);

        // Capture standard output (System.out) to allow assertions on console output.
        System.setOut(new PrintStream(outContent));

        // Update the view to use the mocked output stream
        view = new TextUserInterface();

        // Update the view for all controllers
        guestController.setView(view);
        authenticatedUserController.setView(view);
        adminStaffController.setView(view);
        inquirerController.setView(view);
    }

    /**
     * Resets System.in and System.out to their original states and clears the output stream.
     */
    public void cleanUpEnvironment() {
        // Reset System.in and System.out to their original state
        System.setIn(System.in);
        System.setOut(System.out);

        outContent.reset();
    }

    public void setUpLoggedInStudent() {
        String username = "Barbie";
        String password = "I like pink muffs and I cannot lie";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();
    }

    public void setUpLoggedInAdminStaff() {
        String username = "JackTheRipper";
        String password = "catch me if u can";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();
    }

    public void setUpPage() {
        // Log in as an admin staff member
        setUpLoggedInAdminStaff();
        // Add a new page
        String title = "Article";
        URL dataPath = getClass().getResource("/examplePage1.txt");
        String content = dataPath.getPath();
        String isPrivate = "no";
        mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n");
        adminStaffController.addPage();

        // Log out
        authenticatedUserController.logout();
    }

    public ByteArrayOutputStream getOutContent() {
        return outContent;
    }

    public SharedContext getSharedContext() {
        return sharedContext;
    }

    public GuestController getGuestController() {
        return guestController;
    }

    public AuthenticatedUserController getAuthenticatedUserController() {
        return authenticatedUserController;
    }

    public AdminStaffController getAdminStaffController() {
        return adminStaffController;
    }

    public InquirerController getInquirerController() {
        return inquirerController;
    }
}
