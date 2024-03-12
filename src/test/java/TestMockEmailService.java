import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import external.MockEmailService;
import static org.junit.jupiter.api.Assertions.*;

class TestMockEmailService {

    private MockEmailService emailService;

    @BeforeEach
    void setUp() {
        emailService = new MockEmailService();
    }

    @Test
    void testSendEmail_Success() {
        int status = emailService.sendEmail("sender@example.com", "recipient@example.com", "Subject", "Content");
        assertEquals(emailService.STATUS_SUCCESS, status, "Email sending should be successful");
    }

    @Test
    void testSendEmail_InvalidSenderEmail() {
        int status = emailService.sendEmail("invalid_sender", "recipient@example.com", "Subject", "Content");
        assertEquals(emailService.STATUS_INVALID_SENDER_EMAIL, status, "Should return status for invalid sender email");
    }

    @Test
    void testSendEmail_InvalidRecipientEmail() {
        int status = emailService.sendEmail("sender@example.com", "invalid_recipient", "Subject", "Content");
        assertEquals(emailService.STATUS_INVALID_RECIPIENT_EMAIL, status, "Should return status for invalid recipient email");
    }

    @Test
    void testSendEmail_NullSender() {
        int status = emailService.sendEmail(null, "recipient@example.com", "Subject", "Content");
        assertEquals(emailService.STATUS_INVALID_SENDER_EMAIL, status, "Null sender should be considered invalid");
    }

    @Test
    void testSendEmail_NullRecipient() {
        int status = emailService.sendEmail("sender@example.com", null, "Subject", "Content");
        assertEquals(emailService.STATUS_INVALID_RECIPIENT_EMAIL, status, "Null recipient should be considered invalid");
    }

}