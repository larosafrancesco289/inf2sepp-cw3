import external.AuthenticationService;
import external.EmailService;
import external.MockAuthenticationService;
import external.MockEmailService;
import model.*;
import controller.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.TextUserInterface;
import view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests for the Logout use case.
 */
class LogOutSystemTests {
    private AuthenticationService authService;
    private EmailService emailService;
    private View view;
    private SharedContext sharedContext;
    private GuestController guestController;
    private AuthenticatedUserController authenticatedUserController;
    // Redirect System.in and System.out to enable automated input and output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /**
     * Mocks user input and output to facilitate testing of console-based interactions.
     * This method prepares the testing environment by mocking standard input (System.in)
     * and capturing standard output (System.out) for assertions.
     *
     * @param data The mocked input data simulating user input.
     */
    void mockInputOutput(String data) {
        // Mock standard input (System.in) with provided data.
        ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
        System.setIn(input);

        // Capture standard output (System.out) to allow assertions on console output.
        System.setOut(new PrintStream(outContent));

        // Reinitialize the view and guestController to use the mocked System.in and captured System.out.
        view = new TextUserInterface();
        guestController = new GuestController(sharedContext, view, authService, emailService);
        authenticatedUserController = new AuthenticatedUserController(sharedContext, view, authService, emailService);
    }

    @BeforeEach
    void setUp() {
        try {
            authService = new MockAuthenticationService();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        emailService = new MockEmailService();
        sharedContext = new SharedContext();
    }

    /**
     * Clean up after each test: Resets System.in and System.out to their original states.
     * This method is called after the execution of each test method.
     */
    @AfterEach
    void cleanInputOutput() {
        // Reset System.in to its original state
        System.setIn(System.in);
        System.setOut(System.out);

        outContent.reset();
    }
    /**
     * Test case: Successful logout.
     * This test verifies that an authenticated user can successfully log out and that the appropriate
     * success message is displayed.
     */
    @Test
    void testLoggedOut() {
        // Log in first
        String username = "Barbie";
        String password = "I like pink muffs and I cannot lie";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();

        // Log out
        authenticatedUserController.logout();
        assert(sharedContext.getCurrentUser() instanceof Guest);
        assert(outContent.toString().contains("You have been logged out"));
    }

}
