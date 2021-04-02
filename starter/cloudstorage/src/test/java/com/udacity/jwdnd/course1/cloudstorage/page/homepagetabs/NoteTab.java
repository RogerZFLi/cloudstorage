package com.udacity.jwdnd.course1.cloudstorage.page.homepagetabs;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NoteTab {

    @FindBy(id = "nav-notes-tab")
    private WebElement linkNavNotes;
    @FindBy(id = "nav-notes")
    private WebElement navNotes;
    @FindBy(id = "add-note-button")
    private WebElement buttonAddNote;
    @FindBy(xpath = "//*[@id='edit-note-button']")
    private WebElement buttonEditNote;
    @FindBy(xpath = "//*[@id='delete-note-link']")
    private WebElement linkDeleteNote;
    @FindBy(name = "edit-note-button")
    private List<WebElement> buttonsEditNote;
    @FindBy(name = "delete-note-link")
    private List<WebElement> linksDeleteNote;

    @FindBy(id = "note-title1")
    private WebElement fieldNoteTitle;
    @FindBy(xpath = "//*[@id='note-description']")
    private WebElement fieldNoteDescription;
    @FindBy(name = "note-title")
    private List<WebElement> fieldsNoteTitle;
    @FindBy(name = "note-description")
    private List<WebElement> fieldsNoteDescription;

    @FindBy(id = "noteModal")
    private WebElement modalNote;

    @FindBy(id = "note-modal-close-button")
    private WebElement buttonCloseNoteModal;

    @FindBy(id = "note-id")
    private WebElement inputNoteId;
    @FindBy(id = "note-title")
    private WebElement inputNoteTitle;
    @FindBy(id = "note-description")
    private WebElement txtNoteDescription;

    @FindBy(id = "note-modal-confirm-button")
    private WebElement buttonSaveChanges;
    @FindBy(id = "note-modal-cancel-button")
    private WebElement buttonCancel;

    @FindBy(id = "error-here")
    private WebElement linkErrorHere;
    @FindBy(id = "success-here")
    private WebElement linkSuccessHere;

    public static final String TEST_ADD_NOTE_TITLE = "Test Add Note";
    public static final String TEST_ADD_NOTE_DESCRIPTION = "Test Add Description";
    public static final String TEST_EDIT_NOTE_TITLE = "Test EDIT Note";
    public static final String TEST_EDIT_NOTE_DESCRIPTION = "Test EDIT Description";

    private final WebDriver driver;
    private WebDriverWait wait;

    public NoteTab(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver, 100);
    }

    public void clickNoteTab(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  linkNavNotes);
        wait.until(ExpectedConditions.elementToBeClickable(buttonAddNote));
    }
    public void closeNoteModal(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  buttonCloseNoteModal);
        wait.until(ExpectedConditions.elementToBeClickable(buttonAddNote));
    }
    public void addNoteButCancel(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  buttonAddNote);
        wait.until(ExpectedConditions.visibilityOf(modalNote));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_ADD_NOTE_TITLE + "';", inputNoteTitle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_ADD_NOTE_DESCRIPTION + "';", txtNoteDescription);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",  buttonCancel);
        wait.until(ExpectedConditions.elementToBeClickable(buttonAddNote));
    }

    public void addNoteAndSave(){
        for(int i=1; i<=5; i++) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonAddNote);
            wait.until(ExpectedConditions.visibilityOf(modalNote));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_ADD_NOTE_TITLE + i + "';", inputNoteTitle);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_ADD_NOTE_DESCRIPTION + i + "';", txtNoteDescription);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonSaveChanges);
            wait.until(ExpectedConditions.elementToBeClickable(linkSuccessHere));
            Assertions.assertEquals("Result", driver.getTitle());
            Assertions.assertNotNull(driver.findElement(By.id("success-div")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkSuccessHere);
            wait.until(ExpectedConditions.elementToBeClickable(buttonAddNote));
        }
    }

    public void editNoteAndSave() {
        for(int i=1; i<=5; i++){
            buttonEditNote = driver.findElement(By.id("edit-note-button" + i));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonEditNote);
            wait.until(ExpectedConditions.visibilityOf(modalNote));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_EDIT_NOTE_TITLE + i + "';", inputNoteTitle);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + TEST_EDIT_NOTE_DESCRIPTION + i + "';", txtNoteDescription);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonSaveChanges);
            wait.until(ExpectedConditions.elementToBeClickable(linkSuccessHere));
            Assertions.assertEquals("Result", driver.getTitle());
            Assertions.assertNotNull(driver.findElement(By.id("success-div")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkSuccessHere);
            wait.until(ExpectedConditions.elementToBeClickable(buttonAddNote));
        }
    }

    public void deleteNote() {
        for(int i=1; i<=5; i++){
            linkDeleteNote = driver.findElement(By.id("delete-note-link" + i));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkDeleteNote);
            wait.until(ExpectedConditions.elementToBeClickable(linkSuccessHere));
            Assertions.assertEquals("Result", driver.getTitle());
            Assertions.assertNotNull(driver.findElement(By.id("success-div")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkSuccessHere);
            wait.until(ExpectedConditions.elementToBeClickable(buttonAddNote));
        }
    }

    public String getFieldNoteTitle() {
        return fieldNoteTitle.getAttribute("innerHTML");
    }

    public String getFieldNoteDescription() {
        return fieldNoteDescription.getAttribute("innerHTML");
    }

    public List<WebElement> getFieldsNoteTitle() {
        return fieldsNoteTitle;
    }

    public List<WebElement> getFieldsNoteDescription() {
        return fieldsNoteDescription;
    }
}
