//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
    @DisplayName("Test du formulaire - Soumission avec nom")
    void testSoumissionFormulaire() {
        this.indexPage.naviguer("src/main/webapp");
        Assertions.assertTrue(this.indexPage.estSurPage(), "Devrait être sur la page index");
        this.indexPage.remplirAcronym("FR");
        this.indexPage.cliquerEnvoyer();
        String resultat = this.indexPage.obtenirResultat();
        Assertions.assertEquals("Form successfully validated!", resultat, "Le résultat devrait afficher la validation du formulaire");
    }

    @Test
    @DisplayName("Test de la liste déroulante - Sélection pays")
    void testSelectionPays() {
        this.indexPage.naviguer("src/main/webapp");
        this.indexPage.selectionnerLand("FR");
        String valeur = this.page.evaluate("document.getElementById('land').value").toString();
        Assertions.assertEquals("FR", valeur, "Le pays FR devrait être sélectionné");
    }

    @Test
    @DisplayName("Test des boutons radio - Sélection Oui")
    void testBoutonRadio() {
        this.indexPage.naviguer("src/main/webapp");
        this.indexPage.cocherYes();
        Assertions.assertTrue(this.page.isChecked("#yes"), "Le bouton Yes devrait être coché");
        Assertions.assertFalse(this.page.isChecked("#no"), "Le bouton No ne devrait pas être coché");
    }

    @Test
    @DisplayName("Test de la case à cocher - Acceptation")
    void testCaseACocher() {
        this.indexPage.naviguer("src/main/webapp");
        this.indexPage.cocherAccept();
        Assertions.assertTrue(this.page.isChecked("#accept"), "La case accept devrait être cochée");
    }

    @Test
    @DisplayName("Test de navigation - Aller à page 2")
    void testNavigationVersPage2() {
        this.indexPage.naviguer("src/main/webapp");
        this.indexPage.cliquerLienPage2();
        Assertions.assertTrue(this.page2.estSurPage(), "Devrait être sur la page 2");
        Assertions.assertEquals("Page 2", this.page2.obtenirTitre(), "Le titre devrait être Page 2");
    }

    @Test
    @DisplayName("Test de navigation - Retour à l'index")
    void testNavigationRetourIndex() {
        this.page2.naviguer("src/main/webapp");
        Assertions.assertTrue(this.page2.estSurPage(), "Devrait être sur la page 2");
        this.page2.cliquerRetour();
        Assertions.assertTrue(this.indexPage.estSurPage(), "Devrait être de retour sur la page index");
    }

    @Test
    @DisplayName("Test complet du formulaire - Tous les champs")
    void testFormulaireComplet() {
        this.indexPage.naviguer("src/main/webapp");
        this.indexPage.remplirAcronym("BE");
        this.indexPage.selectionnerLand("BE");
        this.indexPage.cocherYes();
        this.indexPage.cocherAccept();
        this.indexPage.cliquerEnvoyer();
        Assertions.assertEquals("Form successfully validated!", this.indexPage.obtenirResultat());
        Assertions.assertEquals("BE", this.page.evaluate("document.getElementById('land').value").toString());
        Assertions.assertTrue(this.page.isChecked("#yes"));
        Assertions.assertTrue(this.page.isChecked("#accept"));
    }
}
