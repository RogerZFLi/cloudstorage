package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.page.homepagetabs.CredentialTab;
import com.udacity.jwdnd.course1.cloudstorage.page.homepagetabs.FileTab;
import com.udacity.jwdnd.course1.cloudstorage.page.homepagetabs.NoteTab;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static final String USERNAME = "testuser";
	private static final String PASSWORD = "testpassword";
	private static final String WRONG_USERNAME = "testwronguser";
	private static final String WRONG_PASSWORD = "testwrongpassword";
	private static WebDriver driver;
	private String baseURL;
	private WebDriverWait wait;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		//********************************************
		//From forum
		
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("download.prompt_for_download", false);
		String downloadDirectory = null;

		downloadDirectory = System.getProperty("user.dir");
		prefs.put("download.default_directory", downloadDirectory);
		options.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(options);
		//********************************************
		wait = new WebDriverWait(driver, 30,200);
		baseURL = "http://127.0.0.1:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	private boolean isLoggedIn(){
		driver.get(baseURL + "/home");
		return ("Home".equals(driver.getTitle()));
	}


	private void ensureLoginBeforeRunning(){
		if(!isLoggedIn()){
			driver.get(baseURL + "/signup");
			new SignupPage(driver).signup(USERNAME, PASSWORD);
			driver.get(baseURL + "/login");
			new LoginPage(driver).login(USERNAME,PASSWORD);
		}
	}

	@Test
	@Order(1)
	public void testLoginLinkToSignup_ignoreIfIsLoggedIn(){
		Assumptions.assumeFalse(isLoggedIn());
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.linkToSignup();
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testSignupBackToLogin_ignoreIfIsLoggedIn() {
		Assumptions.assumeFalse(isLoggedIn());
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.linkBackToLogin();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void testGoToHomePageWithoutLogin(){
		driver.get(baseURL + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	public void testSignupSuccess_ignoreIfIsLoggedIn(){
		Assumptions.assumeFalse(isLoggedIn());
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(USERNAME,PASSWORD);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-submit-button")));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(5)
	public void testSignupDuplicateUserFail_ignoreIfIsLoggedIn(){
		Assumptions.assumeFalse(isLoggedIn());
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(USERNAME,PASSWORD);
		wait.until(w->w.findElement(By.id("inputUsername")));
		Assertions.assertNotNull(driver.findElement(By.id("error")));
	}

	@Test
	@Order(6)
	public void testLoginFail_ignoreIfIsLoggedIn(){
		Assumptions.assumeFalse(isLoggedIn());
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(WRONG_USERNAME, WRONG_PASSWORD);
		Assertions.assertNotNull(driver.findElement(By.id("error")));
	}

	@Test
	@Order(7)
	public void testLoginSuccess_ignoreIfIsLoggedIn(){
		Assumptions.assumeFalse(isLoggedIn());
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);
		Assertions.assertEquals("Home", driver.getTitle());

	}

	@Test
	@Order(8)
	public void testLogout(){
		ensureLoginBeforeRunning();
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());
		homePage.logout();
		driver.get(baseURL + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}



	@Test
	@Order(9)
	public void testUploadViewDeleteFile() throws InterruptedException, IOException {
		ensureLoginBeforeRunning();
		driver.get(baseURL + "/home");
		FileTab fileTab = new FileTab(driver);
		
		int numberOfFile = fileTab.uploadEmptyFileFail(1);
		Assertions.assertNotEquals(numberOfFile, 1);
		Assertions.assertEquals(0, numberOfFile);

		numberOfFile = fileTab.uploadFile(5);
		Assertions.assertEquals(5, numberOfFile);
		fileTab.viewAndDownloadFile();
		//**************************************************
		//Following block of code is referenced from Raymond B at Udacity Mentor forum:
		// https://knowledge.udacity.com/questions/420694
		File folder = new File(System.getProperty("user.dir"));
		// List the files on that folder
		File[] listOfFiles = folder.listFiles();
		Optional<File> result =
				Arrays.stream(listOfFiles)
						.filter(file -> file.getName().equalsIgnoreCase("test1.txt"))
						.findFirst();
		Assertions.assertTrue(result.isPresent());
		File file = result.get();
		file.deleteOnExit();
		//*************************************************

//		wait.until(ExpectedConditions.elementToBeClickable(By.id("uploadFile")));

		fileTab.deleteFile(5);
		Assertions.assertThrows(NoSuchElementException.class, ()->fileTab.getFieldFileName());

	}

	@Test
	@Order(10)
	public void testAddEditDeleteNote(){
		ensureLoginBeforeRunning();
		driver.get(baseURL + "/home");
		NoteTab noteTab = new NoteTab(driver);
		noteTab.clickNoteTab();
		noteTab.closeNoteModal();
		noteTab.addNoteButCancel();
		noteTab.addNoteAndSave();
		Assertions.assertEquals(5, noteTab.getFieldsNoteTitle().size());
		noteTab.editNoteAndSave();
		Assertions.assertEquals(NoteTab.TEST_EDIT_NOTE_TITLE + 1, noteTab.getFieldNoteTitle());
		noteTab.deleteNote();
		Assertions.assertThrows(NoSuchElementException.class, ()->noteTab.getFieldNoteTitle());
	}

	@Test
	@Order(11)
	public void testAddEditDeleteCredential(){
		ensureLoginBeforeRunning();
		driver.get(baseURL + "/home");
		CredentialTab credentialTab = new CredentialTab(driver);
		credentialTab.clickCredentialTab();
		credentialTab.closeCredentialModal();
		credentialTab.addCredentialButCancel();
		credentialTab.addCredentialAndSave();
		Assertions.assertEquals(5, credentialTab.getFieldsCredentialUrl().size());
		credentialTab.editCredentialAndSave();
		Assertions.assertEquals(CredentialTab.TEST_EDIT_URL + 1, credentialTab.getFieldCredentialUrl());
		credentialTab.deleteCredential();
		Assertions.assertThrows(NoSuchElementException.class, ()->credentialTab.getFieldCredentialUrl());
	}

}
