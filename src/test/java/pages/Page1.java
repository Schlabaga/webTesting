//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pages;

import com.microsoft.playwright.Page;
import java.nio.file.Paths;

public class Page1 {
    private final Page page;
    private final String acronymInput = "#acronym";
    private final String landSelect = "#land";
    private final String yesRadio = "#yes";
    private final String noRadio = "#no";
    private final String acceptCheckbox = "#accept";
    private final String submitButton = "#submit";
    private final String lienPage2 = "#lien";
    private final String resultat = "#resultat";

    public Page1(Page page) {
        this.page = page;
    }

    public void naviguer(String baseUrl) {
        String fileUrl = "file://" + Paths.get(baseUrl, "page1.html").toAbsolutePath();
        this.page.navigate(fileUrl);

    }

    public void remplirAcronym(String acronym) {
        this.page.fill("#acronym", acronym);
    }

    public void selectionnerLand(String land) {
        this.page.selectOption("#land", land);
    }

    public void cocherYes() {
        this.page.check("#yes");
    }

    public void cocherNo() {
        this.page.check("#no");
    }

    public void cocherAccept() {
        this.page.check("#accept");
    }

    public void cliquerEnvoyer() {
        this.page.click("#submit");
    }

    public void cliquerLienPage2() {
        this.page.click("#lien");
    }

    public String obtenirResultat() {
        return this.page.textContent("#resultat");
    }

    public boolean estSurPage() {
        return this.page.title().equals("Page 1");
    }
}
