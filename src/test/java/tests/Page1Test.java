package tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import pages.Page1;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Page1 (Terms and Conditions page)
 * Tests the functionality of checkbox and link enabling/disabling
 *
 * @author Riderzzz-code
 * @version 1.1
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(1)
public class Page1Test {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;
    private Page1 page1;

    private static final String BASE_URL = "src/main/webapp";

    @BeforeAll
    static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50)
        );
    }

    @BeforeEach
    void setup() {
        context = browser.newContext();
        page = context.newPage();
        page1 = new Page1(page);
    }

    @AfterEach
    void teardown() {
        context.close();
    }

    @AfterAll
    static void teardownClass() {
        browser.close();
        playwright.close();
    }

    /**
     * Scenario: Navigate to Terms and Conditions page
     *   Given I am on the application
     *   When I navigate to page 1
     *   Then I should see the terms and conditions page
     *   And the page title should be "Nutzungsbedingungen"
     */
    @Test
    @Order(1)
    @DisplayName("Test 1: Navigate to page 1 and verify page load")
    void testNavigateToPage1() {
        // Given & When
        page1.nav(BASE_URL);

        // Then
        assertTrue(page1.isOnPage(), "Should be on page 1 (Terms and Conditions)");
        assertEquals("Nutzungsbedingungen", page1.getPageTitle(),
                "Page title should be 'Nutzungsbedingungen'");
    }

    /**
     * Scenario: Initial state of page elements
     *   Given I am on the terms and conditions page
     *   When the page loads
     *   Then the checkbox should be unchecked
     *   And the next link should be disabled
     */
    @Test
    @Order(2)
    @DisplayName("Test 2: Verify initial state - checkbox unchecked and link disabled")
    void testInitialState() {
        // Given
        page1.nav(BASE_URL);

        // When - page loads

        // Then
        assertFalse(page1.isTermsAccepted(), "Checkbox should be unchecked initially");
        assertFalse(page1.isNextLinkEnabled(), "Next link should be disabled initially");
        assertTrue(page1.isCheckboxVisible(), "Checkbox should be visible");
        assertTrue(page1.isNextLinkVisible(), "Next link should be visible");
    }

    /**
     * Scenario: Accept terms and conditions
     *   Given I am on the terms and conditions page
     *   When I check the terms acceptance checkbox
     *   Then the checkbox should be checked
     *   And the next link should be enabled
     */
    @Test
    @Order(3)
    @DisplayName("Test 3: Accept terms - checkbox checked and link enabled")
    void testAcceptTerms() {
        // Given
        page1.nav(BASE_URL);

        // When
        page1.acceptTerms();

        // Then
        assertTrue(page1.isTermsAccepted(), "Checkbox should be checked after accepting");
        assertTrue(page1.isNextLinkEnabled(), "Next link should be enabled after accepting terms");
    }

    /**
     * Scenario: Decline terms after accepting
     *   Given I am on the terms and conditions page
     *   And I have checked the terms checkbox
     *   When I uncheck the terms checkbox
     *   Then the checkbox should be unchecked
     *   And the next link should be disabled again
     */
    @Test
    @Order(4)
    @DisplayName("Test 4: Decline terms after accepting - link disabled again")
    void testDeclineTermsAfterAccepting() {
        // Given
        page1.nav(BASE_URL);
        page1.acceptTerms();
        assertTrue(page1.isNextLinkEnabled(), "Link should be enabled initially");

        // When
        page1.declineTerms();

        // Then
        assertFalse(page1.isTermsAccepted(), "Checkbox should be unchecked");
        assertFalse(page1.isNextLinkEnabled(), "Next link should be disabled after declining");
    }

    /**
     * Scenario: Toggle checkbox multiple times
     *   Given I am on the terms and conditions page
     *   When I toggle the checkbox multiple times
     *   Then the link state should change accordingly
     */
    @Test
    @Order(5)
    @DisplayName("Test 5: Toggle checkbox multiple times - link state changes")
    void testToggleCheckboxMultipleTimes() {
        // Given
        page1.nav(BASE_URL);

        // When & Then - First toggle (check)
        page1.acceptTerms();
        assertTrue(page1.isTermsAccepted(), "Checkbox should be checked");
        assertTrue(page1.isNextLinkEnabled(), "Link should be enabled");

        // When & Then - Second toggle (uncheck)
        page1.declineTerms();
        assertFalse(page1.isTermsAccepted(), "Checkbox should be unchecked");
        assertFalse(page1.isNextLinkEnabled(), "Link should be disabled");

        // When & Then - Third toggle (check again)
        page1.acceptTerms();
        assertTrue(page1.isTermsAccepted(), "Checkbox should be checked again");
        assertTrue(page1.isNextLinkEnabled(), "Link should be enabled again");
    }

    /**
     * Scenario: Verify label text content
     *   Given I am on the terms and conditions page
     *   When I read the checkbox label
     *   Then it should contain the expected text
     */
    @Test
    @Order(6)
    @DisplayName("Test 6: Verify label text for terms checkbox")
    void testTermsLabelText() {
        // Given
        page1.nav(BASE_URL);

        // When
        String labelText = page1.getTermsLabelText();

        // Then
        assertNotNull(labelText, "Label text should not be null");
        assertTrue(labelText.contains("Nutzungsbedingungen"),
                "Label should contain 'Nutzungsbedingungen'");
        assertTrue(labelText.contains("akzeptiere"),
                "Label should contain 'akzeptiere'");
    }

    /**
     * Scenario: Navigate to page 2 after accepting terms
     *   Given I am on the terms and conditions page
     *   And I have accepted the terms
     *   When I click on the next link
     *   Then I should be navigated to page 2
     */
    @Test
    @Order(7)
    @DisplayName("Test 7: Navigate to page 2 after accepting terms")
    void testNavigateToPage2AfterAccepting() {
        // Given
        page1.nav(BASE_URL);
        page1.acceptTerms();
        assertTrue(page1.isNextLinkEnabled(), "Link should be enabled");

        // When
        page1.clickNextLink();

        // Wait for navigation
        page.waitForLoadState();

        // Then
        String currentUrl = page1.getCurrentUrl();
        assertTrue(currentUrl.contains("page2.html"),
                "Should navigate to page2.html");
        assertEquals("Länderauswahl Formular", page.title(),
                "Should be on the form page");
    }

    /**
     * Scenario: Attempt navigation without accepting terms
     *   Given I am on the terms and conditions page
     *   And I have not accepted the terms
     *   When I verify the link state
     *   Then the link should be disabled
     */
    @Test
    @Order(8)
    @DisplayName("Test 8: Link is disabled without accepting terms")
    void testLinkDisabledWithoutAccepting() {
        // Given
        page1.nav(BASE_URL);
        assertFalse(page1.isTermsAccepted(), "Terms should not be accepted");

        // Then
        assertFalse(page1.isNextLinkEnabled(),
                "Link should be disabled");
        assertTrue(page1.isOnPage(),
                "Should be on page 1");
    }

    /**
     * Scenario: Verify all page elements are present
     *   Given I am on the terms and conditions page
     *   When the page loads
     *   Then all required elements should be present
     */
    @Test
    @Order(9)
    @DisplayName("Test 9: Verify all page elements are present")
    void testAllElementsPresent() {
        // Given & When
        page1.nav(BASE_URL);

        // Then
        assertTrue(page1.isOnPage(), "Should be on page 1");
        assertTrue(page1.isCheckboxVisible(), "Checkbox should be visible");
        assertTrue(page1.isNextLinkVisible(), "Next link should be visible");
        assertNotNull(page1.getPageTitle(), "Page title should be present");
        assertNotNull(page1.getTermsLabelText(), "Label text should be present");
    }

    /**
     * Scenario: Page reload maintains initial state
     *   Given I am on the terms and conditions page
     *   And I have accepted the terms
     *   When I reload the page
     *   Then the checkbox should be unchecked
     *   And the link should be disabled
     */
    @Test
    @Order(10)
    @DisplayName("Test 10: Page reload resets checkbox state")
    void testPageReloadResetsState() {
        // Given
        page1.nav(BASE_URL);
        page1.acceptTerms();
        assertTrue(page1.isTermsAccepted(), "Terms should be accepted");

        // When
        page.reload();

        // Then
        assertFalse(page1.isTermsAccepted(),
                "Checkbox should be unchecked after reload");
        assertFalse(page1.isNextLinkEnabled(),
                "Link should be disabled after reload");
    }
}