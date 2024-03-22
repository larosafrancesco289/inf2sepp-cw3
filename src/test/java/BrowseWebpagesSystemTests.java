import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import external.MockEmailService;

import static org.junit.jupiter.api.Assertions.*;

import external.MockAuthenticationService;
import external.MockEmailService;
import org.junit.jupiter.api.Test;

import model.*;
import view.*;
import controller.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

class BrowseWebpagesSystemTests {
    private User user;
    private View view;
    private SharedContext sharedContext;
    private MockAuthenticationService mockAuthenticationService;
    private MockEmailService mockEmailService;
    private InquirerController inquirerController;

    // Set up the test environment
    @BeforeEach
    void setUp() throws URISyntaxException, IOException, ParseException {
        view = new TextUserInterface();
        sharedContext = new SharedContext();
        mockAuthenticationService = new MockAuthenticationService();
        mockEmailService = new MockEmailService();
        inquirerController = new InquirerController(sharedContext, view, mockAuthenticationService, mockEmailService);

        Page page1 = new Page("Page 1", "This is the content for page 1", false);
        Page page2 = new Page("Page 2", "This is the content for page 2", false);
        Page page3 = new Page("Page 3", "This is the content for page 3", false);
        Page page4 = new Page("Page 4", "This is the content for page 4", false);
        Page page5 = new Page("Page 5", "This is the content for page 5", false);
        Page unique1 = new Page("Unique 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", false);
        Page unique2 = new Page("Unique 2", "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", false);
        for (Page page : List.of(page1, page2, page3, page4, page5, unique1, unique2)) {
            sharedContext.addPage(page);
        }
    }

    @Test
    void testGuestMainScenario() {
        sharedContext.setCurrentUser(new Guest());
        inquirerController.searchPages();
        assertEquals(5, sharedContext.getPages().size(), "All pages should be available to the guest");
    }
}
