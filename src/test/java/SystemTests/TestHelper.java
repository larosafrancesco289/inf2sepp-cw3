package SystemTests;

import controller.*;
import external.AuthenticationService;
import external.EmailService;
import external.MockAuthenticationService;
import external.MockEmailService;
import model.Inquiry;
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
import java.util.List;

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
    private final TeachingStaffController teachingStaffController;

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
        teachingStaffController = new TeachingStaffController(sharedContext, view, authService, emailService);
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
        teachingStaffController.setView(view);
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

    /**
     * Sets up the testing environment by logging in as a student.
     */
    public void setUpLoggedInStudent() {
        String username = "Barbie";
        String password = "I like pink muffs and I cannot lie";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();
        outContent.reset();
    }

    /**
     * Sets up the testing environment by logging in as an admin staff member.
     */
    public void setUpLoggedInAdminStaff() {
        String username = "JackTheRipper";
        String password = "catch me if u can";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();
        outContent.reset();
    }

    /**
     * Sets up the testing environment by logging in as a teaching staff member.
     */
    public void setUpLoggedInTeachingStaff() {
        String username = "JSON Derulo";
        String password = "Desrouleaux";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();
        outContent.reset();
    }

    /**
     * Sets up the testing environment by logging out if a user is currently logged in.
     */
    public void setUpGuest() {
        if (sharedContext.getCurrentUser() != null) {
            authenticatedUserController.logout();
        }
    }

    /**
     * Sets up the testing environment by adding two public pages and one private page.
     */
    public void setUpPages() {
        // Log in as an admin staff member
        setUpLoggedInAdminStaff();

        // Add a new page
        String title = "Article";
        URL dataPath = getClass().getResource("/examplePage1.txt");
        String content = dataPath.getPath();
        String isPrivate = "no";
        mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n");
        adminStaffController.addPage();

        // Add another new page
        title = "Webpage";
        dataPath = getClass().getResource("/examplePage2.txt");
        content = dataPath.getPath();
        isPrivate = "no";
        mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n");
        adminStaffController.addPage();

        // Add a private page
        title = "Blog";
        dataPath = getClass().getResource("/examplePage3.txt");
        content = dataPath.getPath();
        isPrivate = "yes";
        mockInputOutput(title + "\n" + content + "\n" + isPrivate + "\n");
        adminStaffController.addPage();

        // Log out
        authenticatedUserController.logout();
        outContent.reset();
    }

    /**
     * Sets up an Inquiry object with a title, content and sender
     *
     * @param redirect if true, assign inquiry to staff member
     */
    public void setupMockInquiry(boolean redirect) {

        String title = "TestInquiry";
        String content = "This is a test";
        String sender = "test@test.com";
        String redirectEmail = "json.d@hindenburg.ac.uk";

        Inquiry testInquiry = new Inquiry(sender, title, content);

        if (redirect) {
            testInquiry.setAssignedTo(redirectEmail);
        }

        List<Inquiry> allInquiries = sharedContext.getInquiries();
        allInquiries.add(testInquiry);
        sharedContext.setInquiries(allInquiries);

    }

    /**
     * Retrieves the current ByteArrayOutputStream instance.
     * This stream is typically used to capture output data, such as logging or debugging information.
     *
     * @return The current instance of ByteArrayOutputStream.
     */
    public ByteArrayOutputStream getOutContent() {
        return outContent;
    }

    /**
     * Gets the shared context of the application.
     *
     * @return The shared context instance of the application.
     */
    public SharedContext getSharedContext() {
        return sharedContext;
    }

    /**
     * Retrieves the guest controller.
     *
     * @return The guest controller instance.
     */
    public GuestController getGuestController() {
        return guestController;
    }

    /**
     * Retrieves the authenticated user controller.
     *
     * @return The authenticated user controller instance.
     */
    public AuthenticatedUserController getAuthenticatedUserController() {
        return authenticatedUserController;
    }

    /**
     * Retrieves the admin staff controller.
     *
     * @return The admin staff controller instance.
     */
    public AdminStaffController getAdminStaffController() {
        return adminStaffController;
    }

    /**
     * Retrieves the teaching staff controller.
     *
     * @return The teaching staff controller instance.
     */
    public TeachingStaffController getTeachingStaffController() {
        return teachingStaffController;
    }

    /**
     * Retrieves the inquirer controller.
     *
     * @return The inquirer controller instance.
     */
    public InquirerController getInquirerController() {
        return inquirerController;
    }
}