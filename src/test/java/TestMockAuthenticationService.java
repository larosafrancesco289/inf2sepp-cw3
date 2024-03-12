import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import external.MockAuthenticationService;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestMockAuthenticationService {

    private MockAuthenticationService authService;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException, ParseException {
        authService = new MockAuthenticationService();
    }

    @Test
    public void testValidLogin() {
        String expected = "{\"username\":\"JackTheRipper\",\"password\":\"catch me if u can\",\"email\":\"jack.tr@hindenburg.ac.uk\",\"role\":\"AdminStaff\"}";
        String result = authService.login("jack.tr@hindenburg.ac.uk", "catch me if u can");
        assertEquals(expected, result, "Valid login should return user data.");
    }

    @Test
    public void testInvalidPassword() {
        String expected = "{\"error\":\"Wrong username or password\"}";
        String result = authService.login("jack.tr@hindenburg.ac.uk", "wrong password");
        assertEquals(expected, result, "Invalid password should return error message.");
    }

    @Test
    public void testInvalidUsername() {
        String expected = "{\"error\":\"Wrong username or password\"}";
        String result = authService.login("nonexistent@hindenburg.ac.uk", "somepassword");
        assertEquals(expected, result, "Nonexistent user should return error message.");
    }

}