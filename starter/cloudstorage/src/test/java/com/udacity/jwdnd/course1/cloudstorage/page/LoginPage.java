package com.udacity.jwdnd.course1.cloudstorage.page;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "error")
    private WebElement divError;
    @FindBy(id = "logout-msg")
    private WebElement divLogout;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;
    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "login-submit-button")
    private WebElement buttonLogin;
    @FindBy(id = "login-signup-link")
    private WebElement linkSignup;

    private final WebDriver driver;
//    private static final String USERNAME = "testuser";
//    private static final String PASSWORD = "testpassword";
//    private static final String WRONG_USERNAME = "testwronguser";
//    private static final String WRONG_PASSWORD = "testwrongpassword";

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String usernameInput, String passwordInput){
//        inputUsername.sendKeys(usernameInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + usernameInput + "';", inputUsername);
//        inputPassword.sendKeys(passwordInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + passwordInput + "';", inputPassword);
//        buttonLogin.click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  buttonLogin);
    }

    public void linkToSignup(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  linkSignup);
    }

    public WebElement getDivError() {
        return divError;
    }


}

