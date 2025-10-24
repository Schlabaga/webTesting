package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.Page1;
import pages.Page2;

public class WebTest {
    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;
    private Page1 indexPage;
    private Page2 page2;
    private static final String BASE_URL = "src/main/webapp";

    public WebTest() {
    }

    @BeforeAll
    static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch((new BrowserType.LaunchOptions()).setHeadless(false));
    }

    @BeforeEach
    void setup() {
        this.context = browser.newContext();
        this.page = this.context.newPage();
        this.indexPage = new Page1(this.page);
        this.page2 = new Page2(this.page);
    }

    @AfterEach
    void teardown() {
        this.context.close();
    }

    @AfterAll
    static void teardownClass() {
        browser.close();
        playwright.close();
    }

    @Test
    @DisplayName("Form Test - Submitting with name")
    void testFormSubmit() {
        this.indexPage.nav("src/main/webapp");
        Assertions.assertTrue(this.indexPage.isOnPage(), "Should be on the index page");
        this.indexPage.fillAcronym("FR");
        this.indexPage.clickSubmit();
        String resultat = this.indexPage.getResult();
        Assertions.assertEquals("Form successfully validated!", resultat, "Le result should indicate successful form validation");
    }

    @Test
    @DisplayName("Droplist test - land selection")
    void testLandSelection() {
        this.indexPage.nav("src/main/webapp");
        this.indexPage.selectLand("FR");
        String valeur = this.page.evaluate("document.getElementById('land').value").toString();
        Assertions.assertEquals("FR", valeur, "The selected land should be FR");
    }

    @Test
    @DisplayName("Test radio buttons - Yes selection")
    void testRadioButton() {
        this.indexPage.nav("src/main/webapp");
        this.indexPage.checkYes();
        Assertions.assertTrue(this.page.isChecked("#yes"), "The Yes button should be checked");
        Assertions.assertFalse(this.page.isChecked("#no"), "The No button should not be checked");
    }

    @Test
    @DisplayName("Checkbox test - Acceptation")
    void testCheckbox() {
        this.indexPage.nav("src/main/webapp");
        this.indexPage.checkAccept();
        Assertions.assertTrue(this.page.isChecked("#accept"), "The Accept checkbox should be checked");
    }

    @Test
    @DisplayName("Navigation test - Go to Page 2")
    void testNavigationToPage2() {
        this.indexPage.nav("src/main/webapp");
        this.indexPage.clickLinkPage2();
        Assertions.assertTrue(this.page2.isOnPage(), "Should be on Page 2");
        Assertions.assertEquals("Page 2", this.page2.getTitle(), "The title should be on Page 2");
    }

    @Test
    @DisplayName("Navigation test - get back to index page")
    void testNavigationBackToIndex() {
        this.page2.nav("src/main/webapp");
        Assertions.assertTrue(this.page2.isOnPage(), "Should be on Page 2");
        this.page2.clickBack();
        Assertions.assertTrue(this.indexPage.isOnPage(), "Should be back on the index page");
    }

    @Test
    @DisplayName("Total form test - All the fields filled")
    void testFullForm() {
        this.indexPage.nav("src/main/webapp");
        this.indexPage.fillAcronym("BE");
        this.indexPage.selectLand("BE");
        this.indexPage.checkYes();
        this.indexPage.checkAccept();
        this.indexPage.clickSubmit();
        Assertions.assertEquals("Form successfully validated!", this.indexPage.getResult());
        Assertions.assertEquals("BE", this.page.evaluate("document.getElementById('land').value").toString());
        Assertions.assertTrue(this.page.isChecked("#yes"));
        Assertions.assertTrue(this.page.isChecked("#accept"));
    }
}
