import controller.AdminStaffController;
import controller.GuestController;
import external.AuthenticationService;
import external.EmailService;
import external.MockAuthenticationService;
import external.MockEmailService;
import model.SharedContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.TextUserInterface;
import view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddWebpageSystemTests {

    private AuthenticationService authService;
    private EmailService emailService;
    private View view;
    private SharedContext sharedContext;
    private GuestController guestController;

    private AdminStaffController adminStaffController;
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
        adminStaffController = new AdminStaffController(sharedContext, view, authService, emailService);
    }

    /**
     * Set up before each test: Initializes mocked services and shared context.
     * This method is called before the execution of each test method.
     */
    @BeforeEach
    void setUp() {
        try {
            authService = new MockAuthenticationService();
        } catch (Exception e) {
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

    @Test
    void testAddNewPage() {
        // Log in as an admin staff member
        String username = "JackTheRipper";
        String password = "catch me if u can";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();

        // Add a new page
        String title = "Article";
        URL dataPath = getClass().getResource("/examplePage1.txt");
        String content = dataPath.getPath();
        String isPrivate = "no";
        mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n");
        adminStaffController.addPage();

        // Verify that the page was added
        assert (sharedContext.getPages().containsKey(title));
        assertTrue(outContent.toString().contains("Added page " + title));
    }

    @Test
    void testAddExistingPageOverwrite() {
        // Log in as an admin staff member
        String username = "JackTheRipper";
        String password = "catch me if u can";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();

        // Add a new page
        String title = "Article";
        URL dataPath = getClass().getResource("/examplePage1.txt");
        String content = dataPath.getPath();
        String isPrivate = "no";
        mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n");
        adminStaffController.addPage();

        // Add another page with the same title, overwriting the existing page
        content = getClass().getResource("/examplePage2.txt").getPath();
        mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n" + "yes");
        adminStaffController.addPage();

        // Check if sharedContext contains the new page by the content
        assertTrue(sharedContext.getPages().containsKey(title));
    }

    @Test
    void testAddExistingPageNoOverwrite() {
        // Log in as an admin staff member
        String username = "JackTheRipper";
        String password = "catch me if u can";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();

        // Add a new page
        String title = "Article";
        URL dataPath = getClass().getResource("/examplePage1.txt");
        String content = dataPath.getPath();
        String isPrivate = "no";
        mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n");
        adminStaffController.addPage();

        // Add the same page again, not overwriting the existing page
        mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n" + "no");
        adminStaffController.addPage();

        // Confirm that the page was not overwritten
        assertTrue(outContent.toString().contains("Cancelled adding new page"));
    }

    // TODO: Don't know how to test email service failing
    @Test
    void emailServiceFail() {
        //testing email service failing

    }

}
