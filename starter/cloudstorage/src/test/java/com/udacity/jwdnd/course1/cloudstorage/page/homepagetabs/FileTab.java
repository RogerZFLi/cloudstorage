package com.udacity.jwdnd.course1.cloudstorage.page.homepagetabs;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileTab {


    @FindBy( id = "fileUpload")//css = "[type='file']")
    private WebElement fileUpload;

    @FindBy(id = "upload-file-button")
    private WebElement buttonUploadFile;

    @FindBy(name = "home-file-view-link")
    private List<WebElement> linksFileView;

    @FindBy(name = "home-file-delete-link")
    private List<WebElement> linksFileDelete;
    @FindBy(xpath = "//*[@id='home-file-name']")
    private WebElement fieldFileName;

    @FindBy(id = "error-here")
    private WebElement linkErrorHere;

    @FindBy(id = "success-here")
    private  WebElement linkSuccessHere;

    private final WebDriver driver;
    WebDriverWait wait;

    public FileTab(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 100);
    }

    public int uploadEmptyFileFail(int numberOfFileToUpload) throws IOException {

        final File folder = new File(System.getProperty("user.dir")+"\\filesForTestingUploading");
        if(!folder.exists()) {
            folder.mkdir();
        }
        final File file = new File(folder.getAbsolutePath() + "\\test.txt");
        file.createNewFile();
        String filePath = file.getAbsolutePath();
        fileUpload.sendKeys(filePath);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonUploadFile);
        wait.until(ExpectedConditions.elementToBeClickable(linkErrorHere));
        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertNotNull(driver.findElement(By.id("error-div")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkErrorHere);
        //**************************************************
        //Following block of code is referenced from Raymond B at Udacity Mentor forum:
        // https://knowledge.udacity.com/questions/420694

        // List the files on that folder
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        Optional<File> result =
                Arrays.stream(listOfFiles)
                        .filter(f -> f.getName().equalsIgnoreCase("test.txt"))
                        .findFirst();
        Assertions.assertTrue(result.isPresent());
        File f = result.get();
        f.deleteOnExit();
        //*************************************************
        return linksFileView.size();
    }

    public int uploadFile(int numberOfFileToUpload) throws IOException {
        final File folder = new File(System.getProperty("user.dir")+"\\filesForTestingUploading");
        if(!folder.exists())
            folder.mkdir();
        for (int i = 1; i <= numberOfFileToUpload; i++) {
            final File file = new File(folder.getAbsolutePath() + "\\test" + i + ".txt");
            file.createNewFile();
            file.setWritable(true);
            FileWriter writer = new FileWriter(file);
            writer.write(new StringBuilder().append("package com.udacity.jwdnd.l4.final_review;\n").append("\n").append("import com.udacity.jwdnd.l4.final_review.model.Message;\n").append("import com.udacity.jwdnd.l4.final_review.page.ChatPage;\n").append("import com.udacity.jwdnd.l4.final_review.page.LoginPage;\n").append("import com.udacity.jwdnd.l4.final_review.page.SignupPage;\n").append("import io.github.bonigarcia.wdm.WebDriverManager;\n").append("import org.junit.jupiter.api.AfterAll;\n").append("import org.junit.jupiter.api.BeforeAll;\n").append("import org.junit.jupiter.api.BeforeEach;\n").append("import org.junit.jupiter.api.Test;\n").append("import org.openqa.selenium.WebDriver;\n").append("import org.openqa.selenium.chrome.ChromeDriver;\n").append("import org.springframework.boot.test.context.SpringBootTest;\n").append("import org.springframework.boot.web.server.LocalServerPort;\n").append("\n").append("import static org.junit.jupiter.api.Assertions.assertEquals;\n").append("\n").append("@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)\n").append("class FinalReviewApplicationTests {\n").append("\n").append("\t@LocalServerPort\n").append("\tprivate int port;\n").append("\n").append("\tpublic static WebDriver driver;\n").append("\tpublic String baseURL;\n").append("\n").append("\t@BeforeAll\n").append("\tpublic static void beforeAll() {\n").append("\t\tWebDriverManager.chromedriver().setup();\n").append("\t\tdriver = new ChromeDriver();\n").append("\t\t\n").append("\t}\n").append("\n").append("\t@AfterAll\n").append("\tpublic static void afterAll() {\n").append("\t\tdriver.quit();\n").append("\t\tdriver = null;\n").append("\t}\n").append("\n").append("\t@BeforeEach\n").append("\tpublic void beforeEach() {\n").append("\t\tbaseURL = \"http://localhost:\" + port;\n").append("\t}\n").append("\n").append("\t@Test\n").append("\tpublic void testSignupLoginAndFirstMessage() {\n").append("\t\tString username = \"user1\";\n").append("\t\tString password = \"user1\";\n").append("\t\tString firstName = \"User1\";\n").append("\t\tString lastName = \"User1\";\n").append("\t\tString firstMessage = \"Hello, this is my first message\";\n").append("\n").append("\n").append("\t\tdriver.get(baseURL + \"/signup\");\n").append("\t\tSignupPage signupPage = new SignupPage(driver);\n").append("\n").append("\t\tsignupPage.signup(username,password,firstName,lastName);\n").append("\t\tdriver.get(baseURL + \"/login\");\n").append("\t\tLoginPage loginPage = new LoginPage(driver);\n").append("\t\tloginPage.login(username,password);\n").append("\n").append("\t\tdriver.get(baseURL + \"/chat\");\n").append("\t\tChatPage chatPage = new ChatPage(driver);\n").append("\n").append("\t\tchatPage.sendMessageText(firstMessage);\n").append("\n").append("\t\tMessage sentMessage = chatPage.getFirstMessageText();\n").append("\t\tassertEquals(username, sentMessage.getUsername());\n").append("\t\tassertEquals(firstMessage, sentMessage.getMessageText());\n").append("\n").append("\n").append("\t}\n").append("\n").append("\n").append("\n").append("\n").append("}\n").toString());
            writer.close();
            String filePath = file.getAbsolutePath();
            fileUpload = driver.findElement(By.id("fileUpload"));
            fileUpload.sendKeys(filePath);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonUploadFile);
            wait.until(ExpectedConditions.elementToBeClickable(linkSuccessHere));
            Assertions.assertEquals("Result", driver.getTitle());
            Assertions.assertNotNull(driver.findElement(By.id("success-div")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkSuccessHere);
            wait.until(ExpectedConditions.elementToBeClickable(buttonUploadFile));

            File[] listOfFiles = folder.listFiles();
            int finalI = i;
            assert listOfFiles != null;
            Optional<File> result =
                    Arrays.stream(listOfFiles)
                            .filter(f -> f.getName().equalsIgnoreCase("test" + finalI + ".txt"))
                            .findFirst();
            Assertions.assertTrue(result.isPresent());
            File f = result.get();
            f.deleteOnExit();
        }

        return linksFileView.size();
    }

    public void viewAndDownloadFile() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linksFileView.get(0));
        wait.until(ExpectedConditions.elementToBeClickable(buttonUploadFile));
    }

    public void deleteFile(int numberOfFileToDelete) {
        WebElement linkDelete;
        for (int i=1; i<=5; i++) {
            linkDelete = driver.findElement(By.id("home-file-delete-link" + i));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkDelete);
            //this "wait" triggers an timeout exception
            wait.until(ExpectedConditions.elementToBeClickable(linkSuccessHere));

            Assertions.assertEquals("Result", driver.getTitle());
            Assertions.assertNotNull(driver.findElement(By.id("success-div")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkSuccessHere);
            wait.until(ExpectedConditions.elementToBeClickable(buttonUploadFile));
        }
    }

    public String getFieldFileName() {
        return fieldFileName.getAttribute("innerHTML");
    }
}
