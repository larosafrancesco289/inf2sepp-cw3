import external.MockAuthenticationService;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the MockAuthenticationService class.
 * This class demonstrates how to use the MockAuthenticationService to test different login scenarios.
 */
public class TestMockAuthenticationService {

    private MockAuthenticationService authService;

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes the authService with a new instance of MockAuthenticationService.
     *
     * @throws URISyntaxException if a string could not be parsed as a URI reference.
     * @throws IOException if an I/O error occurs.
     * @throws ParseException if the JSON data cannot be parsed.
     */
    @BeforeEach
    public void setUp() throws URISyntaxException, IOException, ParseException {
        authService = new MockAuthenticationService();
    }

    /**
     * Converts a JSON string to a Map.
     *
     * @param jsonString the JSON string to convert.
     * @return A map representing the JSON object.
     */
    private Map<String, String> jsonStringToMap(String jsonString) {
        jsonString = jsonString.replaceAll("[{}]", ""); // Remove {}
        String[] keyValuePairs = jsonString.split(","); // Split by comma
        Map<String, String> map = new HashMap<>();
        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            map.put(entry[0].trim().replaceAll("^\"|\"$", ""), // Remove ""
                    entry[1].trim().replaceAll("^\"|\"$", "")); // Remove ""
        }
        return map;
    }

    /**
     * Tests the successful login scenario.
     * Verifies that the correct user data is returned when valid credentials are used.
     */
    @Test
    public void testValidLogin() {
        String expected = "{\"password\":\"catch me if u can\",\"role\":\"AdminStaff\",\"email\":\"jack.tr@hindenburg.ac.uk\",\"username\":\"JackTheRipper\"}";
        String result = authService.login("JackTheRipper", "catch me if u can");

        Map<String, String> expectedMap = jsonStringToMap(expected);
        Map<String, String> resultMap = jsonStringToMap(result);

        assertEquals(expectedMap, resultMap, "Valid login should return user data.");
    }

    /**
     * Tests the login scenario with an invalid password.
     * Verifies that an error message is returned when an incorrect password is used.
     */
    @Test
    public void testInvalidPassword() {
        String expected = "{\"error\":\"Wrong username or password\"}";
        String result = authService.login("JackTheRipper", "wrong password");
        assertEquals(expected, result, "Invalid password should return error message.");
    }

    /**
     * Tests the login scenario with an invalid username.
     * Verifies that an error message is returned when a non-existent username is used.
     */
    @Test
    public void testInvalidUsername() {
        String expected = "{\"error\":\"Wrong username or password\"}";
        String result = authService.login("nonexistent@hindenburg.ac.uk", "somepassword");
        assertEquals(expected, result, "Nonexistent user should return error message.");
    }

}