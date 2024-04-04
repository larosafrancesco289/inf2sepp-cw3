package UnitTests;

import external.MockEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class demonstrates tests the MockEmailService using JUnit.
 * It covers various scenarios including successful email sending, and handling of invalid or null email addresses for both sender and recipient.
 */
class TestMockEmailService {
    private MockEmailService emailService; // Instance of MockEmailService to be tested

    /**
     * Setup method to initialize the MockEmailService instance before each test.
     * This ensures each test starts with a fresh instance.
     */
    @BeforeEach
    void setUp() {
        emailService = new MockEmailService();
    }

    /**
     * Test to verify that the email sending functionality works as expected when valid sender and recipient emails are provided.
     * Asserts that the status returned by the sendEmail method equals STATUS_SUCCESS.
     */
    @Test
    void testSuccess() {
        int status = emailService.sendEmail("sender@example.com", "recipient@example.com", "Subject", "Content");
        assertEquals(emailService.STATUS_SUCCESS, status, "Email sending should be successful");
    }

    /**
     * Test to ensure that an invalid sender email address is correctly handled by the sendEmail method.
     * Asserts that the status returned equals STATUS_INVALID_SENDER_EMAIL.
     */
    @Test
    void testInvalidSenderEmail() {
        int status = emailService.sendEmail("invalid_sender", "recipient@example.com", "Subject", "Content");
        assertEquals(emailService.STATUS_INVALID_SENDER_EMAIL, status, "Should return status for invalid sender email");
    }

    /**
     * Test to ensure that an invalid recipient email address is correctly handled by the sendEmail method.
     * Asserts that the status returned equals STATUS_INVALID_RECIPIENT_EMAIL.
     */
    @Test
    void testInvalidRecipientEmail() {
        int status = emailService.sendEmail("sender@example.com", "invalid_recipient", "Subject", "Content");
        assertEquals(emailService.STATUS_INVALID_RECIPIENT_EMAIL, status, "Should return status for invalid recipient email");
    }

    /**
     * Test to check the behavior of the sendEmail method when the sender email is null.
     * Asserts that the status returned equals STATUS_INVALID_SENDER_EMAIL, treating null as an invalid email.
     */
    @Test
    void testNullSender() {
        int status = emailService.sendEmail(null, "recipient@example.com", "Subject", "Content");
        assertEquals(emailService.STATUS_INVALID_SENDER_EMAIL, status, "Null sender should be considered invalid");
    }

    /**
     * Test to check the behavior of the sendEmail method when the recipient email is null.
     * Asserts that the status returned equals STATUS_INVALID_RECIPIENT_EMAIL, treating null as an invalid email.
     */
    @Test
    void testNullRecipient() {
        int status = emailService.sendEmail("sender@example.com", null, "Subject", "Content");
        assertEquals(emailService.STATUS_INVALID_RECIPIENT_EMAIL, status, "Null recipient should be considered invalid");
    }
}