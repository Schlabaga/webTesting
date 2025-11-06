package tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import pages.Page2;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Page2 (Country Selection Form)
 * Tests the form validation, input fields, and navigation
 *
 * @author Schlabaga + Riderzzz-code
 * @version 2.1
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(2)
public class Page2Test {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;
    private Page2 page2;

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
        page2 = new Page2(page);
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
     * Scenario: Navigate to the form page
     *   Given I am on the application
     *   When I navigate to page 2
     *   Then I should see the country selection form
     *   And the page title should be "Länderauswahl Formular"
     */
    @Test
    @Order(1)
    @DisplayName("Test 1: Navigate to page 2 and verify page load")
    void testNavigateToPage2() {
        // Given & When
        page2.nav(BASE_URL);

        // Then
        assertTrue(page2.isOnPage(), "Should be on page 2 (Country Selection Form)");
        assertEquals("Länderauswahl Formular", page2.getPageTitle(),
                "Page title should be 'Länderauswahl Formular'");
    }

    /**
     * Scenario: Fill acronym input field
     *   Given I am on the form page
     *   When I enter "FR" in the acronym field
     *   Then the field should contain "FR"
     */
    @Test
    @Order(2)
    @DisplayName("Test 2: Fill acronym input field")
    void testFillAcronymField() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.fillAcronym("FR");

        // Then
        assertEquals("FR", page2.getAcronymValue(),
                "Acronym field should contain 'FR'");
    }

    /**
     * Scenario: Select country from dropdown
     *   Given I am on the form page
     *   When I select "Belgium (BE)" from the country dropdown
     *   Then "BE" should be selected
     */
    @Test
    @Order(3)
    @DisplayName("Test 3: Select country from dropdown")
    void testSelectCountry() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.selectCountry("BE");

        // Then
        assertEquals("BE", page2.getSelectedCountry(),
                "Selected country should be 'BE'");
    }

    /**
     * Scenario: Select EU Yes radio button
     *   Given I am on the form page
     *   When I select "Yes" for EU residence
     *   Then the "Yes" radio button should be checked
     *   And the "No" radio button should not be checked
     */
    @Test
    @Order(4)
    @DisplayName("Test 4: Select EU Yes radio button")
    void testSelectEuYesRadio() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.selectEuYes();

        // Then
        assertTrue(page2.isEuYesChecked(),
                "EU Yes radio button should be checked");
        assertFalse(page2.isEuNoChecked(),
                "EU No radio button should not be checked");
    }

    /**
     * Scenario: Select EU No radio button
     *   Given I am on the form page
     *   When I select "No" for EU residence
     *   Then the "No" radio button should be checked
     *   And the "Yes" radio button should not be checked
     */
    @Test
    @Order(5)
    @DisplayName("Test 5: Select EU No radio button")
    void testSelectEuNoRadio() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.selectEuNo();

        // Then
        assertTrue(page2.isEuNoChecked(),
                "EU No radio button should be checked");
        assertFalse(page2.isEuYesChecked(),
                "EU Yes radio button should not be checked");
    }

    /**
     * Scenario: Check data consent checkbox
     *   Given I am on the form page
     *   When I check the data consent checkbox
     *   Then the checkbox should be checked
     */
    @Test
    @Order(6)
    @DisplayName("Test 6: Check data consent checkbox")
    void testCheckDataConsent() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.checkDataConsent();

        // Then
        assertTrue(page2.isDataConsentChecked(),
                "Data consent checkbox should be checked");
    }

    /**
     * Scenario: Submit empty form shows errors
     *   Given I am on the form page
     *   When I submit the form without filling any fields
     *   Then I should see error messages
     *   And the result should indicate errors
     */
    @Test
    @Order(7)
    @DisplayName("Test 7: Submit empty form - validation errors")
    void testSubmitEmptyForm() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.clickSubmit();

        // Wait for result
        page.waitForTimeout(500);

        // Then
        assertTrue(page2.isResultMessageVisible(),
                "Result message should be visible");
        assertTrue(page2.isResultError(),
                "Result should indicate error");
        String resultText = page2.getResultMessage();
        assertTrue(resultText.contains("Fehler"),
                "Result should contain error message");
    }

    /**
     * Scenario: Submit valid form for France (EU country)
     *   Given I am on the form page
     *   When I fill all fields correctly for France
     *   And I submit the form
     *   Then I should see a success message
     */
    @Test
    @Order(8)
    @DisplayName("Test 8: Submit valid form for France - success")
    void testSubmitValidFormFrance() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.fillAcronym("FR");
        page2.selectCountry("FR");
        page2.selectEuYes();
        page2.checkDataConsent();
        page2.clickSubmit();

        // Wait for result
        page.waitForTimeout(500);

        // Then
        assertTrue(page2.isResultMessageVisible(),
                "Result message should be visible");
        assertTrue(page2.isResultSuccess(),
                "Result should indicate success");
        String resultText = page2.getResultMessage();
        assertTrue(resultText.contains("erfolgreich"),
                "Result should contain success message");
    }

    /**
     * Scenario: Submit valid form for Belgium (EU country)
     *   Given I am on the form page
     *   When I fill all fields correctly for Belgium
     *   And I submit the form
     *   Then I should see a success message
     */
    @Test
    @Order(9)
    @DisplayName("Test 9: Submit valid form for Germany - success")
    void testSubmitValidFormGermany() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.fillCompleteForm("DE", "DE", true, true);
        page2.clickSubmit();

        // Wait for result
        page.waitForTimeout(500);

        // Then
        assertTrue(page2.isResultSuccess(),
                "Result should indicate success for Germany");
        assertEquals("DE", page2.getAcronymValue(),
                "Acronym should be DE");
        assertEquals("DE", page2.getSelectedCountry(),
                "Selected country should be DE");
    }

    /**
     * Scenario: Submit valid form for USA (non-EU country)
     *   Given I am on the form page
     *   When I fill all fields correctly for USA
     *   And I submit the form
     *   Then I should see a success message
     */
    @Test
    @Order(10)
    @DisplayName("Test 10: Submit valid form for USA - success")
    void testSubmitValidFormUSA() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.fillAcronym("US");
        page2.selectCountry("US");
        page2.selectEuNo();
        page2.checkDataConsent();
        page2.clickSubmit();

        // Wait for result
        page.waitForTimeout(500);

        // Then
        assertTrue(page2.isResultSuccess(),
                "Result should indicate success for USA");
    }

    /**
     * Scenario: Acronym and country mismatch
     *   Given I am on the form page
     *   When I enter "FR" but select "BE"
     *   And I submit the form
     *   Then I should see an error about mismatch
     */
    @Test
    @Order(11)
    @DisplayName("Test 11: Acronym and country mismatch - error")
    void testAcronymCountryMismatch() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.fillAcronym("FR");
        page2.selectCountry("BE");
        page2.selectEuYes(); // Both FR and BE are in EU, so this is valid
        page2.checkDataConsent();
        page2.clickSubmit();

        // Wait for result
        page.waitForTimeout(500);

        // Then
        assertTrue(page2.isResultError(),
                "Result should indicate error");
        String resultText = page2.getResultMessage();
        assertTrue(resultText.contains("nicht") || resultText.contains("Akronym"),
                "Result should mention acronym mismatch");
    }

    /**
     * Scenario: EU residence inconsistency - Yes but non-EU country
     *   Given I am on the form page
     *   When I select USA but answer Yes to EU residence
     *   And I submit the form
     *   Then I should see an error about EU inconsistency
     */
    @Test
    @Order(12)
    @DisplayName("Test 12: EU residence inconsistency (Yes but USA) - error")
    void testEuInconsistencyYesUSA() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.fillAcronym("US");
        page2.selectCountry("US");
        page2.selectEuYes(); // Incorrect: USA is not in EU
        page2.checkDataConsent();
        page2.clickSubmit();

        // Wait for result
        page.waitForTimeout(500);

        // Then
        assertTrue(page2.isResultError(),
                "Result should indicate error");
        String resultText = page2.getResultMessage();
        assertTrue(resultText.contains("EU"),
                "Result should mention EU inconsistency");
    }

    /**
     * Scenario: EU residence inconsistency - No but EU country
     *   Given I am on the form page
     *   When I select France but answer No to EU residence
     *   And I submit the form
     *   Then I should see an error about EU inconsistency
     */
    @Test
    @Order(13)
    @DisplayName("Test 13: EU residence inconsistency (No but France) - error")
    void testEuInconsistencyNoFrance() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.fillAcronym("FR");
        page2.selectCountry("FR");
        page2.selectEuNo(); // Incorrect: France is in EU
        page2.checkDataConsent();
        page2.clickSubmit();

        // Wait for result
        page.waitForTimeout(500);

        // Then
        assertTrue(page2.isResultError(),
                "Result should indicate error");
    }

    /**
     * Scenario: Missing data consent
     *   Given I am on the form page
     *   When I fill all fields except data consent
     *   And I submit the form
     *   Then I should see an error about missing consent
     */
    @Test
    @Order(14)
    @DisplayName("Test 14: Missing data consent - error")
    void testMissingDataConsent() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.fillAcronym("DK");
        page2.selectCountry("DK");
        page2.selectEuYes();
        // Not checking data consent
        page2.clickSubmit();

        // Wait for result
        page.waitForTimeout(500);

        // Then
        assertTrue(page2.isResultError(),
                "Result should indicate error");
        String resultText = page2.getResultMessage();
        assertTrue(resultText.contains("Datenverarbeitung") || resultText.contains("zustimmen"),
                "Result should mention consent requirement");
    }

    /**
     * Scenario: Navigate back to page 1
     *   Given I am on the form page
     *   When I click the back link
     *   Then I should be navigated to page 1
     */
    @Test
    @Order(15)
    @DisplayName("Test 15: Navigate back to page 1")
    void testNavigateBackToPage1() {
        // Given
        page2.nav(BASE_URL);
        assertTrue(page2.isOnPage(), "Should be on page 2");

        // When
        page2.clickBackLink();

        // Wait for navigation
        page.waitForLoadState();

        // Then
        String currentUrl = page.url();
        assertTrue(currentUrl.contains("page1.html"),
                "Should navigate back to page1.html");
        assertEquals("Nutzungsbedingungen", page.title(),
                "Should be on the terms page");
    }

    /**
     * Scenario: Test acronym uppercase conversion
     *   Given I am on the form page
     *   When I enter "fr" in lowercase
     *   Then it should be converted to "FR" in uppercase
     */
    @Test
    @Order(16)
    @DisplayName("Test 16: Acronym converts to uppercase automatically")
    void testAcronymUppercaseConversion() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.fillAcronym("fr");

        // Wait a bit for JavaScript to process
        page.waitForTimeout(300);

        // Then
        assertEquals("FR", page2.getAcronymValue(),
                "Acronym should be converted to uppercase");
    }

    /**
     * Scenario: Complete form workflow for Denmark
     *   Given I am on the form page
     *   When I fill all fields correctly for Denmark
     *   And I submit the form
     *   Then all fields should retain their values
     *   And I should see a success message
     */
    @Test
    @Order(17)
    @DisplayName("Test 17: Complete form workflow for Denmark")
    void testCompleteFormWorkflowDenmark() {
        // Given
        page2.nav(BASE_URL);

        // When
        page2.fillCompleteForm("DK", "DK", true, true);
        page2.clickSubmit();

        // Wait for result
        page.waitForTimeout(500);

        // Then
        assertEquals("DK", page2.getAcronymValue(),
                "Acronym should be DK");
        assertEquals("DK", page2.getSelectedCountry(),
                "Country should be DK");
        assertTrue(page2.isEuYesChecked(),
                "EU Yes should be checked");
        assertTrue(page2.isDataConsentChecked(),
                "Data consent should be checked");
        assertTrue(page2.isResultSuccess(),
                "Form validation should succeed");
    }

    /**
     * Scenario: Test multiple form submissions
     *   Given I am on the form page
     *   When I submit the form multiple times with different data
     *   Then each submission should be validated independently
     */
    @Test
    @Order(18)
    @DisplayName("Test 18: Multiple form submissions")
    void testMultipleSubmissions() {
        // Given
        page2.nav(BASE_URL);

        // First submission - valid
        page2.fillCompleteForm("FR", "FR", true, true);
        page2.clickSubmit();
        page.waitForTimeout(500);
        assertTrue(page2.isResultSuccess(), "First submission should succeed");

        // Second submission - change to invalid
        page2.fillAcronym("US");
        page2.selectCountry("US");
        page2.selectEuYes(); // Should be No for US
        page2.clickSubmit();
        page.waitForTimeout(500);
        assertTrue(page2.isResultError(), "Second submission should fail");

        // Third submission - fix the error
        page2.selectEuNo();
        page2.clickSubmit();
        page.waitForTimeout(500);
        assertTrue(page2.isResultSuccess(), "Third submission should succeed");
    }
}