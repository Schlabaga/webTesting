package pages;

import com.microsoft.playwright.Page;
import java.nio.file.Paths;


/**
 * Page Object pour la page page1.html
 * Represents elements and actions available for the first page
 *
 * @author Riderzzz-code + Schlabaga
 * @version 2.1
 */
public class Page1 {

    private final Page page;

    // Locators for page elements
    private final String termsCheckbox = "#acceptTerms";
    private final String termsLabel = "label[for='acceptTerms']";
    private final String nextLink = "#nextLink";
    private final String pageTitle = "h1";

    /**
     * Constructor
     * @param page Instance of Playwright Page
     */
    public Page1(Page page) {
        this.page = page;
    }

    /**
     * Navigate to page1.html
     * @param baseUrl Base URL of the application
     */
    public void nav(String baseUrl) {
        String fileUrl = "file://" + Paths.get(baseUrl, "page1.html").toAbsolutePath();
        this.page.navigate(fileUrl);
    }

    /**
     * Check the terms and conditions checkbox
     */
    public void acceptTerms() {
        this.page.check(termsCheckbox);
        page.waitForTimeout(300); // Wait for javaScript to update the link
    }

    /**
     * Uncheck the terms and conditions checkbox
     */
    public void declineTerms() {
        this.page.uncheck(termsCheckbox);
        page.waitForTimeout(300); // Wait for JavaScript to update the link
    }

    /**
     * Check if the terms checkbox is checked
     * @return true if checked, false otherwise
     */
    public boolean isTermsAccepted() {
        return this.page.isChecked(termsCheckbox);
    }

    /**
     * Click the Next link to navigate to page2.html
     */
    public void clickNextLink() {
        this.page.click(nextLink);
    }

    /**
     * Check if the next link is enabled
     * @return true if enabled, false otherwise
     */
    public boolean isNextLinkEnabled() {
        String classValue = page.evaluate("document.querySelector('#nextLink').className").toString();
        return classValue.contains("enabled");
    }

    /**
     * Get page title text
     * @return Title text from h1 element
     */
    public String getPageTitle() {
        return this.page.textContent(pageTitle).trim();
    }

    /**
     * Get the label text for terms checkbox
     * @return Label text
     */
    public String getTermsLabelText() {
        return page.textContent(termsLabel).trim();
    }

    /**
     * Verify if we are on page 1
     * @return true if on page 1, false otherwise
     */
    public boolean isOnPage() {
        return page.title().equals("Nutzungsbedingungen");
    }

    /**
     * Get the current page URL
     * @return Current URL
     */
    public String getCurrentUrl() {
        return page.url();
    }

    /**
     * Check if checkbox element is visible
     * @return true if visible, false otherwise
     */
    public boolean isCheckboxVisible() {
        return page.isVisible(termsCheckbox);
    }

    /**
     * Check if next link element is visible
     * @return true if visible, false otherwise
     */
    public boolean isNextLinkVisible() {
        return page.isVisible(nextLink);
    }
}
