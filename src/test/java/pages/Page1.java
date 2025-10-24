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
    private final String linkPage2 = "#link";
    private final String result = "#result";

    public Page1(Page page) {
        this.page = page;
    }

    public void nav(String baseUrl) {
        String fileUrl = "file://" + Paths.get(baseUrl, "page1.html").toAbsolutePath();
        this.page.navigate(fileUrl);

    }

    public void fillAcronym(String acronym) {
        this.page.fill("#acronym", acronym);
    }

    public void selectLand(String land) {
        this.page.selectOption("#land", land);
    }

    public void checkYes() {
        this.page.check("#yes");
    }

    public void checkNo() {
        this.page.check("#no");
    }

    public void checkAccept() {
        this.page.check("#accept");
    }

    public void clickSubmit() {
        this.page.click("#submit");
    }

    public void clickLinkPage2() {
        this.page.click("#link");
    }

    public String getResult() {
        return this.page.textContent("#result");
    }

    public boolean isOnPage() {
        return this.page.title().equals("Page 1");
    }
}
