import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

/**
 * Tests the browse webpages (admin) use case.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is used to ensure that the setUp method is run only once
class BrowseWebpagesSystemTests {
    private final TestHelper testHelper = new TestHelper(); // TestHelper class is used to set up the testing environment

    /**
     * Sets up the testing environment before each test by logging in as an admin staff member.
     */
    @BeforeAll
    void setUp() {
        testHelper.setUpPages();
    }

    /**
     * Cleans up the testing environment after each test.
     */
    @AfterEach
    void cleanUp() {
        testHelper.cleanUpEnvironment();
    }
}
