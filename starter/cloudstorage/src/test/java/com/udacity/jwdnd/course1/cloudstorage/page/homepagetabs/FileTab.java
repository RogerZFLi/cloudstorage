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

        File folder = new File(System.getProperty("user.dir")+"\\filesForTestingUploading");
        if(!folder.exists())
            folder.mkdir();
        File file = new File(folder.getAbsolutePath() + "\\test.txt");
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
        File folder = new File(System.getProperty("user.dir")+"\\filesForTestingUploading");
        if(!folder.exists())
            folder.mkdir();
        for (int i = 1; i <= numberOfFileToUpload; i++) {
            File file = new File(folder.getAbsolutePath() + "\\test" + i + ".txt");
            file.createNewFile();
            file.setWritable(true);
            FileWriter writer = new FileWriter(file);
            writer.write("package com.udacity.jwdnd.l4.final_review;\n" +
                    "\n" +
                    "import com.udacity.jwdnd.l4.final_review.model.Message;\n" +
                    "import com.udacity.jwdnd.l4.final_review.page.ChatPage;\n" +
                    "import com.udacity.jwdnd.l4.final_review.page.LoginPage;\n" +
                    "import com.udacity.jwdnd.l4.final_review.page.SignupPage;\n" +
                    "import io.github.bonigarcia.wdm.WebDriverManager;\n" +
                    "import org.junit.jupiter.api.AfterAll;\n" +
                    "import org.junit.jupiter.api.BeforeAll;\n" +
                    "import org.junit.jupiter.api.BeforeEach;\n" +
                    "import org.junit.jupiter.api.Test;\n" +
                    "import org.openqa.selenium.WebDriver;\n" +
                    "import org.openqa.selenium.chrome.ChromeDriver;\n" +
                    "import org.springframework.boot.test.context.SpringBootTest;\n" +
                    "import org.springframework.boot.web.server.LocalServerPort;\n" +
                    "\n" +
                    "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                    "\n" +
                    "@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)\n" +
                    "class FinalReviewApplicationTests {\n" +
                    "\n" +
                    "\t@LocalServerPort\n" +
                    "\tprivate int port;\n" +
                    "\n" +
                    "\tpublic static WebDriver driver;\n" +
                    "\tpublic String baseURL;\n" +
                    "\n" +
                    "\t@BeforeAll\n" +
                    "\tpublic static void beforeAll() {\n" +
                    "\t\tWebDriverManager.chromedriver().setup();\n" +
                    "\t\tdriver = new ChromeDriver();\n" +
                    "\t\t\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@AfterAll\n" +
                    "\tpublic static void afterAll() {\n" +
                    "\t\tdriver.quit();\n" +
                    "\t\tdriver = null;\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@BeforeEach\n" +
                    "\tpublic void beforeEach() {\n" +
                    "\t\tbaseURL = \"http://localhost:\" + port;\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@Test\n" +
                    "\tpublic void testSignupLoginAndFirstMessage() {\n" +
                    "\t\tString username = \"user1\";\n" +
                    "\t\tString password = \"user1\";\n" +
                    "\t\tString firstName = \"User1\";\n" +
                    "\t\tString lastName = \"User1\";\n" +
                    "\t\tString firstMessage = \"Hello, this is my first message\";\n" +
                    "\n" +
                    "\n" +
                    "\t\tdriver.get(baseURL + \"/signup\");\n" +
                    "\t\tSignupPage signupPage = new SignupPage(driver);\n" +
                    "\n" +
                    "\t\tsignupPage.signup(username,password,firstName,lastName);\n" +
                    "\t\tdriver.get(baseURL + \"/login\");\n" +
                    "\t\tLoginPage loginPage = new LoginPage(driver);\n" +
                    "\t\tloginPage.login(username,password);\n" +
                    "\n" +
                    "\t\tdriver.get(baseURL + \"/chat\");\n" +
                    "\t\tChatPage chatPage = new ChatPage(driver);\n" +
                    "\n" +
                    "\t\tchatPage.sendMessageText(firstMessage);\n" +
                    "\n" +
                    "\t\tMessage sentMessage = chatPage.getFirstMessageText();\n" +
                    "\t\tassertEquals(username, sentMessage.getUsername());\n" +
                    "\t\tassertEquals(firstMessage, sentMessage.getMessageText());\n" +
                    "\n" +
                    "\n" +
                    "\t}\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "}\n");
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

    public void deleteFile(int numberOfFileToDelete) throws InterruptedException {
        WebElement linkDelete;
        for (int i=1; i<=5; i++) {
            linkDelete = driver.findElement(By.id("home-file-delete-link" + i));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkDelete);
            //this "wait" triggers an timeout exception
//            wait.until(ExpectedConditions.elementToBeClickable(linkSuccessHere));
//
//            Assertions.assertEquals("Result", driver.getTitle());
//            Assertions.assertNotNull(driver.findElement(By.id("success-div")));
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkSuccessHere);
            wait.until(ExpectedConditions.elementToBeClickable(buttonUploadFile));
        }
    }

    public String getFieldFileName() {
        return fieldFileName.getAttribute("innerHTML");
    }
}
