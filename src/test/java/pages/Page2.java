package pages;

import com.microsoft.playwright.Page;

import java.nio.file.Paths;

/**
 * Page Object pour la page page2.html
 * Représente les éléments et actions de la deuxième page
 */
public class Page2 {

    private final Page page;

    // Locators
    private final String returnLink = "#return";
    private final String title = "h1";

    /**
     * Constructeur
     * @param page Instance de Playwright Page
     */
    public Page2(Page page) {
        this.page = page;
    }

    /**
     * Naviguer vers la page 2
     * @param baseUrl URL de base de l'application
     */
    public void nav(String baseUrl) {
        String fileUrl = "file://" + Paths.get(baseUrl, "page2.html").toAbsolutePath();
        this.page.navigate(fileUrl);

    }

    /**
     * Cliquer sur le lien Retour
     */
    public void clickBack() {
        page.click(returnLink);
    }

    /**
     * Obtenir le titre de la page
     * @return Texte du titre H1
     */
    public String getTitle() {
        return page.textContent(title).trim();
    }

    /**
     * Vérifier si on est sur la page 2
     * @return true si sur la page 2
     */
    public boolean isOnPage() {
        return page.title().contains("Page 2");
    }
}