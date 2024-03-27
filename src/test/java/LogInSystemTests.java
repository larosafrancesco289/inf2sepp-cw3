// JUnit

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import controller.*;
import view.*;
import external.*;
/**
 * Tests for the Login use case.
 */
class LogInSystemTests {
    private AuthenticationService authService;
    private EmailService emailService;
    private View view;
    private SharedContext sharedContext;
    private GuestController guestController;
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
    }

    /**
     * Set up before each test: Initializes mocked services and shared context.
     * This method is called before the execution of each test method.
     */
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
     * Test case: Successful login as a student.
     * This test verifies that a student can successfully log in and that the appropriate
     * success message is displayed.
     */
    @Test
    void testSuccessfulLoginStudent() {
        String username = "Barbie";
        String password = "I like pink muffs and I cannot lie";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();

        // Assert that the login was successful and the correct role is set.
        assertTrue(outContent.toString().contains("Login successful"));
        assertEquals("Student", sharedContext.getCurrentUser().getRole());
    }

    /**
     * Test case: Successful login as an admin.
     * This test verifies that an admin can successfully log in and that the appropriate
     * success message is displayed.
     */
    @Test
    void testSuccessfulLoginAdminStaff() {
        String username = "JackTheRipper";
        String password = "catch me if u can";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();

        assertTrue(outContent.toString().contains("Login successful"));
        assertEquals("AdminStaff", sharedContext.getCurrentUser().getRole());
    }

    /**
     * Test case: Successful login as a teaching staff.
     * This test verifies that a teaching staff member can successfully log in and that the appropriate
     * success message is displayed.
     */
    @Test
    void testSuccessfulLoginTeachingStaff() {
        String username = "JSON Derulo";
        String password = "Desrouleaux";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();

        assertTrue(outContent.toString().contains("Login successful"));
        assertEquals("TeachingStaff", sharedContext.getCurrentUser().getRole());
    }

    /**
     * Test case: Display of error message upon login failure.
     * This test verifies that an appropriate error message is displayed when login fails.
     */
    @Test
    void testWrongUsernameOrPassword() {
        String username = "Barbie";
        String password = "";
        mockInputOutput(username + "\n" + password + "\n");
        guestController.login();

        assertTrue(outContent.toString().contains("Wrong username or password"));
    }
}
