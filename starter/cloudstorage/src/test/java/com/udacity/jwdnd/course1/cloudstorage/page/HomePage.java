package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    //This button belongs to the home page, out of any tabs
    @FindBy(id = "home-logout-button")
    private WebElement buttonLogout;
    private final WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);

    }

    public void logout() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonLogout);
    }
}
