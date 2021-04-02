package com.udacity.jwdnd.course1.cloudstorage.page;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "error")
    private WebElement divError;
    @FindBy(id = "success")
    private WebElement divSuccess;

    @FindBy(id = "inputFirstName")
    private  WebElement inputFirstName;
    @FindBy(id = "inputLastName")
    private  WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;
    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "signup-submit-button")
    private WebElement buttonSignup;
    @FindBy(id = "signup-back-to-login")
    private WebElement linkBackToLogin;
    @FindBy(id = "signup-login")
    private WebElement linkLogin;

    private final WebDriver driver;

    private static final String FIRST_NAME = "User";
    private static final String LAST_NAME = "Test";

    public SignupPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void linkBackToLogin(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  linkBackToLogin);
    }

    public void linkLogin(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  linkLogin);
    }



    public void signup(String usernameInput, String passwordInput){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + FIRST_NAME + "';", inputFirstName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + LAST_NAME + "';", inputLastName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + usernameInput + "';", inputUsername);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + passwordInput + "';", inputPassword);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  buttonSignup);

    }

    public WebElement getDivError() {
        return divError;
    }

    public WebElement getDivSuccess() {
        return divSuccess;
    }
}
