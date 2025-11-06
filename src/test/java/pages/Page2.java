package pages;

import com.microsoft.playwright.Page;
import java.nio.file.Paths;

/**
 * Page Object pour la page page2.html
 * Represents elements and actions available on the form page
 *
 * @author Schlabaga + Riderzzz-code
 * @version 2.0
 */
public class Page2 {

    private final Page page;

    // Locators for forms elements
    private final String acronymInput = "#acronym";
    private final String countrySelect = "#country";
    private final String euYesRadio = "#euYes";
    private final String euNoRadio = "#euNo";
    private final String dataConsentCheckbox = "#dataConsent";
    private final String submitButton = "#submitButton";
    private final String backLink = "#backLink";
    private final String resultMessage = "#resultMessage";
    private final String pageTitle = "h1";

    /**
     * Constructor
     * @param page Instance de Playwright Page
     */
    public Page2(Page page) {
        this.page = page;
    }

    /**
     * Navigate to page2.html
     * @param baseUrl Base URL of the web application
     */
    public void nav(String baseUrl) {
        String fileUrl = "file://" + Paths.get(baseUrl, "page2.html").toAbsolutePath();
        this.page.navigate(fileUrl);

    }

    /**
     * Fill the acronym input field
     * @param acronym Country acronym (e.g., "FR", "BE")
     */
    public void fillAcronym(String acronym) {
        page.fill(acronymInput, acronym);
    }

    /**
     * Select a country from the dropdown
     * @param countryCode Country code to select (e.g., "FR", "BE")
     */
    public void selectCountry(String countryCode) {
        page.selectOption(countrySelect, countryCode);
    }

    /**
     * Select "Yes" for EU residence radio button
     */
    public void selectEuYes() {
        page.check(euYesRadio);
    }

    /**
     * Select "No" for EU residence radio button
     */
    public void selectEuNo() {
        page.check(euNoRadio);
    }

    /**
     * Check the data consent checkbox
     */
    public void checkDataConsent() {
        page.check(dataConsentCheckbox);
    }

    /**
     * Uncheck the data consent checkbox
     */
    public void uncheckDataConsent() {
        page.uncheck(dataConsentCheckbox);
    }

    /**
     * Click the submit button
     */
    public void clickSubmit() {
        page.click(submitButton);
    }

    /**
     * Click the back link to return to page 1
     */
    public void clickBackLink() {
        page.click(backLink);
    }

    /**
     * Get the result message text
     * @return Result message text
     */
    public String getResultMessage() {
        return page.textContent(resultMessage).trim();
    }

    /**
     * Check if result message is displayed
     * @return true if visible, false otherwise
     */
    public boolean isResultMessageVisible() {
        return page.isVisible(resultMessage);
    }

    /**
     * Check if result message indicates success
     * @return true if success message, false otherwise
     */
    public boolean isResultSuccess() {
        String classAttribute = page.getAttribute(resultMessage, "class");
        return classAttribute != null && classAttribute.contains("success");
    }

    /**
     * Check if result message indicates error
     * @return true if error message, false otherwise
     */
    public boolean isResultError() {
        String classAttribute = page.getAttribute(resultMessage, "class");
        return classAttribute != null && classAttribute.contains("error");
    }

    /**
     * Get the value of the acronym input field
     * @return Current value in acronym field
     */
    public String getAcronymValue() {
        return page.inputValue(acronymInput);
    }

    /**
     * Get the selected country value
     * @return Selected country code
     */
    public String getSelectedCountry() {
        return page.inputValue(countrySelect);
    }

    /**
     * Check if EU Yes radio button is checked
     * @return true if checked, false otherwise
     */
    public boolean isEuYesChecked() {
        return page.isChecked(euYesRadio);
    }

    /**
     * Check if EU No radio button is checked
     * @return true if checked, false otherwise
     */
    public boolean isEuNoChecked() {
        return page.isChecked(euNoRadio);
    }

    /**
     * Check if data consent checkbox is checked
     * @return true if checked, false otherwise
     */
    public boolean isDataConsentChecked() {
        return page.isChecked(dataConsentCheckbox);
    }

    /**
     * Get the page title text
     * @return Title text from h1 element
     */
    public String getPageTitle() {
        return page.textContent(pageTitle).trim();
    }

    /**
     * Verify if we are on page 2
     * @return true if on page 2, false otherwise
     */
    public boolean isOnPage() {
        return page.title().equals("Länderauswahl Formular");
    }

    /**
     * Get the background color of the body (for validation feedback)
     * @return Background color as RGB string
     */
    public String getBodyBackgroundColor() {
        return (String) page.evaluate("window.getComputedStyle(document.body).backgroundColor");
    }

    /**
     * Fill the complete form with all required fields
     * @param acronym Country acronym
     * @param country Country code
     * @param euResident true for Yes, false for No
     * @param consent true to check consent, false otherwise
     */
    public void fillCompleteForm(String acronym, String country, boolean euResident, boolean consent) {
        fillAcronym(acronym);
        selectCountry(country);

        if (euResident) {
            selectEuYes();
        } else {
            selectEuNo();
        }

        if (consent) {
            checkDataConsent();
        }
    }
}