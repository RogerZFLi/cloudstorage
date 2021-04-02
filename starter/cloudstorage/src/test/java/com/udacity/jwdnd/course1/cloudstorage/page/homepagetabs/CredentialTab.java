package com.udacity.jwdnd.course1.cloudstorage.page.homepagetabs;

import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialTab {

    @FindBy(id = "nav-credentials-tab")
    private WebElement linkNavCredential;
    @FindBy(id = "nav-credentials")
    private WebElement navCredential;
    @FindBy(id = "add-credential-button")
    private WebElement buttonAddCredential;
    @FindBy(xpath = "//*[@id='edit-credential-button']")
    private WebElement buttonEditCredential;
    @FindBy(xpath = "//*[@id='delete-credential-link']")
    private WebElement linkDeleteCredential;
    @FindBy(name = "edit-credential-button")
    private List<WebElement> buttonsEditCredential;
    @FindBy(name = "delete-credential-link")
    private List<WebElement> linksDeleteCredential;

    @FindBy(id = "credential-url1")
    private WebElement fieldCredentialUrl;
    @FindBy(id= "credential-username1")
    private WebElement fieldCredentialUsername;
    @FindBy(id= "credential-password1")
    private WebElement fieldCredentialPassword;
    @FindBy(name = "credential-url")
    private List<WebElement> fieldsCredentialUrl;
    @FindBy(name = "credential-username")
    private List<WebElement> fieldsCredentialUsername;
    @FindBy(name = "credential-password")
    private List<WebElement> fieldsCredentialPassword;

    @FindBy(id = "credentialModal")
    private WebElement modalCredential;

    @FindBy(id = "credential-modal-close-button")
    private WebElement buttonCloseCredentialModal;

    @FindBy(id = "credential-id")
    private WebElement inputCredentialId;
    @FindBy(id = "credential-url")
    private WebElement inputCredentialUrl;
    @FindBy(id = "credential-username")
    private WebElement inputCredentialUsername;
    @FindBy(id = "modal-credential-password")
    private WebElement inputCredentialPassword;

    @FindBy(id = "credential-modal-confirm-button")
    private WebElement buttonSaveChanges;
    @FindBy(id = "credential-modal-cancel-button")
    private WebElement buttonCancel;

    @FindBy(id = "error-here")
    private WebElement linkErrorHere;
    @FindBy(id = "success-here")
    private WebElement linkSuccessHere;

    public static final String TEST_ADD_URL = "http://test.add-example.com";
    public static final String TEST_ADD_USERNAME = "testaddusername";
    public static final String TEST_ADD_PASSWORD = "testaddpassword";
    public static final String TEST_EDIT_URL = "http://test.edit-example.com";
    public static final String TEST_EDIT_USERNAME = "testeditusername";
    public static final String TEST_EDIT_PASSWORD = "testeditpassword";;

    private final WebDriver driver;
    private WebDriverWait wait;

    public CredentialTab(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver, 100);
    }

    public void clickCredentialTab(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  linkNavCredential);
        wait.until(ExpectedConditions.elementToBeClickable(buttonAddCredential));
    }
    public void closeCredentialModal(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  buttonCloseCredentialModal);
        wait.until(ExpectedConditions.elementToBeClickable(buttonAddCredential));
    }
    public void addCredentialButCancel(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  buttonAddCredential);
        wait.until(ExpectedConditions.visibilityOf(modalCredential));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_ADD_URL + "';", inputCredentialUrl);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_ADD_USERNAME + "';", inputCredentialUsername);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_ADD_PASSWORD + "';", inputCredentialPassword);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  buttonCancel);
        wait.until(ExpectedConditions.elementToBeClickable(buttonAddCredential));
    }

    public void addCredentialAndSave(){
        for(int i=1; i<=5; i++) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonAddCredential);
            wait.until(ExpectedConditions.visibilityOf(modalCredential));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_ADD_URL + i + "';", inputCredentialUrl);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_ADD_USERNAME + i + "';", inputCredentialUsername);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_ADD_PASSWORD + "';", inputCredentialPassword);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonSaveChanges);
            wait.until(ExpectedConditions.elementToBeClickable(linkSuccessHere));
            Assertions.assertEquals("Result", driver.getTitle());
            Assertions.assertNotNull(driver.findElement(By.id("success-div")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkSuccessHere);
            wait.until(ExpectedConditions.elementToBeClickable(buttonAddCredential));
        }
    }

    public void editCredentialAndSave() {
        for(int i=1; i<=5; i++){
            buttonEditCredential = driver.findElement(By.id("edit-credential-button" + i));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonEditCredential);
            wait.until(ExpectedConditions.visibilityOf(modalCredential));
            WebElement passwordInput = driver.findElement(By.id("modal-credential-password"));
            Assertions.assertEquals(TEST_ADD_PASSWORD, passwordInput.getAttribute("value"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_EDIT_URL+ i + "';", inputCredentialUrl);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_EDIT_USERNAME + i + "';", inputCredentialUsername);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_EDIT_PASSWORD + i + "';", inputCredentialPassword);
            passwordInput = driver.findElement(By.id("modal-credential-password"));
            String editedPassword = passwordInput.getAttribute("value");
            Assertions.assertEquals(TEST_EDIT_PASSWORD+i, editedPassword);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonSaveChanges);
            wait.until(ExpectedConditions.elementToBeClickable(linkSuccessHere));
            Assertions.assertEquals("Result", driver.getTitle());
            Assertions.assertNotNull(driver.findElement(By.id("success-div")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkSuccessHere);
            wait.until(ExpectedConditions.elementToBeClickable(buttonAddCredential));
            EncryptionService encryptionService = new EncryptionService();
            WebElement keyField = driver.findElement(By.id("credential-key" + i));
            String key = keyField.getAttribute("value");
            WebElement passwordField = driver.findElement(By.id("credential-password" + i));
            String actualEncryptedPassword = passwordField.getText();
            String expectedEncryptedPassword = encryptionService.encryptValue(editedPassword, key);
            Assertions.assertEquals(expectedEncryptedPassword, actualEncryptedPassword);
        }
    }

    public void deleteCredential() {
        for(int i=1; i<=5; i++){
            linkDeleteCredential = driver.findElement(By.id("delete-credential-link" + i));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkDeleteCredential);
            wait.until(ExpectedConditions.elementToBeClickable(linkSuccessHere));
            Assertions.assertEquals("Result", driver.getTitle());
            Assertions.assertNotNull(driver.findElement(By.id("success-div")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkSuccessHere);
            wait.until(ExpectedConditions.elementToBeClickable(buttonAddCredential));
        }
    }

    public String getFieldCredentialUrl() {
        return fieldCredentialUrl.getAttribute("innerHTML");
    }

    public String getFieldCredentialUsername() {
        return fieldCredentialUsername.getAttribute("innerHTML");
    }

    public String getFieldCredentialPassword() {
        return fieldCredentialPassword.getAttribute("innerHTML");
    }

    public List<WebElement> getFieldsCredentialUrl() {
        return fieldsCredentialUrl;
    }
}
