// JUnit

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import model.*;
import controller.*;
import view.*;
import external.*;

// class imports

//instantiate main?? not really sure how to test this
class LogInSystemTests {

    private AuthenticationService authService;
    private EmailService emailService;
    private View view;
    private SharedContext sharedContext;
    private GuestController guestController;

    void mockInput(String data) {
        ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
        System.setIn(input);
    }

    @BeforeEach
    void setUp() throws Exception{
        // Mock or instantiate your services here
        // For simplicity, let's assume these are instantiated
        authService = new MockAuthenticationService(); // Assuming you have a mock implementation
        emailService = new MockEmailService(); // Assuming you have a mock implementation
        view = new TextUserInterface(); // Assuming this is your actual implementation
        sharedContext = new SharedContext();

        guestController = new GuestController(sharedContext, view, authService, emailService);
    }

    @Test
    void testSuccessfulLoginStudent() {
        String username = "Barbie";
        String password = "I like pink muffs and I cannot lie";
        mockInput(username + "\n" + password + "\n");

        guestController.login();

        assertEquals("Student", sharedContext.getCurrentUser().getRole());
    }

    @Test
    void testSuccessfulLogin() {

    }

    @Test
    void testErrorInResponse() {
        // write test here

    }

    @Test
    void testUserEmailNull() {

        // write test here

    }

    @Test
    void testUnsupportedUserRole() {

        // write test here

    }

    @Test
    void testOtherError() {

        // write test here

    }

}
