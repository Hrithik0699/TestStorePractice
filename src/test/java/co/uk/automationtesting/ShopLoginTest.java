package co.uk.automationtesting;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.ExtentManager;
import base.Hooks;
import pageObjects.HomePage;
import pageObjects.ShopHomePage;
import pageObjects.ShopSignInPage;
import pageObjects.ShopSignInPage;
import pageObjects.ShopYourAccount;

@Listeners(resources.Listeners.class)

public class ShopLoginTest extends Hooks {

    public ShopLoginTest() throws IOException {
        super();
        // TODO Auto-generated constructor stub
    }

    @Test
    public void checkLoginTest() throws IOException, InterruptedException {

        ExtentManager.log("Starting ShopLoginTest...");

        // creating an object of the automationtesting.co.uk webpage
//        HomePage home = new HomePage();
//        home.getTestStoreLink().click();

        // creating an object of the test store homepage
        ShopHomePage shopHome = new ShopHomePage();
        shopHome.getSignInButton().click();

        FileInputStream workbookLocation = new FileInputStream(System.getProperty("user.dir") +
                "\\src\\main\\java\\resources\\credentials.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(workbookLocation);
        XSSFSheet sheet = workbook.getSheetAt(0);

        /****************************************************************************
         * Excel Spreadsheet Layout Reminder (teaching purposes only)
         *
         * |Row=0 -->| Email Address (Cell 0) Password (Cell 1) *
         * --------------------------------------------------------------------
         * |Row=1 -->| test@test.com (Cell 0) test123 (Cell 1)
         * |Row=2 -->| john.smith@test.com (Cell 0) test123 (Cell 1)
         * |Row=3 -->| lucy.jones@test.com (Cell 0) catlover1 (Cell 1)
         * |Row=4 -->| martin.brian@test.com (Cell 0) ilovepasta5 (Cell 1)
         ****************************************************************************/

        Row row1 = sheet.getRow(1);
        Cell cellR1C0 = row1.getCell(0);
        Cell cellR1C1 = row1.getCell(1);

        String emailRow1 = cellR1C0.toString();
        String passwordRow1 = cellR1C1.toString();

        System.out.println(emailRow1);
        System.out.println(passwordRow1);

        ShopSignInPage shop = new ShopSignInPage();
        shop.getEmailField().sendKeys(emailRow1);
        shop.getPasswordField().sendKeys(passwordRow1);

        Thread.sleep(3000);

        shop.getSubmitButton().click();

        ShopYourAccount yourAccount = new ShopYourAccount();

        try {
            yourAccount.getSignOut().click();
            ExtentManager.pass("user HAS signed in");
        } catch (Exception e) {
            ExtentManager.fail("user could NOT sign in");
            Assert.fail();
        }


        Row row2 = sheet.getRow(2);
        Cell cellR2C0 = row2.getCell(0);
        Cell cellR2C1 = row2.getCell(1);

        String emailRow2 = cellR2C0.toString();
        String passwordRow2 = cellR2C1.toString();

        shop.getEmailField().sendKeys(emailRow2);
        shop.getPasswordField().sendKeys(passwordRow2);
        Thread.sleep(3000);
        shop.getSubmitButton().click();

        try {
            yourAccount.getSignOut().click();
            ExtentManager.pass("user HAS signed in");
        } catch (Exception e) {
            ExtentManager.fail("user could NOT sign in");
            Assert.fail();
        }

    }

}
