import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import external.MockAuthenticationService;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class TestMockAuthenticationService {

    private MockAuthenticationService authService;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException, ParseException {
        authService = new MockAuthenticationService();
    }

    private Map<String, String> jsonStringToMap(String jsonString) {
        jsonString = jsonString.replaceAll("[{}]", ""); // Remove {}
        String[] keyValuePairs = jsonString.split(",");
        Map<String, String> map = new HashMap<>();
        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            map.put(entry[0].trim().replaceAll("^\"|\"$", ""), // Remove ""
                    entry[1].trim().replaceAll("^\"|\"$", "")); // Remove ""
        }
        return map;
    }
    @Test
    public void testValidLogin() {
        String expected = "{\"password\":\"catch me if u can\",\"role\":\"AdminStaff\",\"email\":\"jack.tr@hindenburg.ac.uk\",\"username\":\"JackTheRipper\"}";
        String result = authService.login("JackTheRipper", "catch me if u can");

        Map<String, String> expectedMap = jsonStringToMap(expected);
        Map<String, String> resultMap = jsonStringToMap(result);

        assertEquals(expectedMap, resultMap, "Valid login should return user data.");
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