package test.java.Testcases;
    

import java.net.URLEncoder;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.google.gson.JsonObject;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestScenario1 {

	String status="";
	Page page;
	Browser browser;
	Playwright playwright;

	@Parameters({"Browser","Platform","BrowserVersion"})
	@Test
	public void TestCase1(String Browser,String Platform,String BrowserVersion)
	{   try (Playwright playwright = Playwright.create()) {
        JsonObject capabilities = new JsonObject();
        JsonObject ltOptions = new JsonObject();

        String user = "surbhi_jain1";
        String accessKey = "iB3K2vQQsQiimn1wKPOVNrHW14xD5p0WSzPXfRGwGvqedGxdvg";

        capabilities.addProperty("browsername", Browser); // Browsers allowed: `Chrome`, `MicrosoftEdge`, `pw-chromium`, `pw-firefox` and `pw-webkit`
        capabilities.addProperty("browserVersion",BrowserVersion);
        ltOptions.addProperty("platform", Platform);
        ltOptions.addProperty("name", "Playwright101Test");
        ltOptions.addProperty("build", "Playwright101Java Build");
        ltOptions.addProperty("user", user);
        ltOptions.addProperty("accessKey", accessKey);
        capabilities.add("LT:Options", ltOptions);

        BrowserType chromium = playwright.chromium();
        String caps = URLEncoder.encode(capabilities.toString(), "utf-8");
        String cdpUrl = "wss://cdp.lambdatest.com/playwright?capabilities=" + caps;
        Browser browser = chromium.connect(cdpUrl);
        Page page = browser.newPage();
        page.setDefaultNavigationTimeout(180000);
        try {
            page.navigate("https://www.lambdatest.com/selenium-playground");
            Thread.sleep(2000);
            Locator locator = page.locator("//a[text()='Simple Form Demo']");
            locator.click();
            Thread.sleep(2000);
            String actualCurrentUrl=page.url();
            Assert.assertTrue(actualCurrentUrl.contains("simple-form-demo"),"Url not matched");
            String expectedMessage="Welcome to LambdaTest";
            Locator messageInputBox=page.locator("input[id='user-message']"); 
            messageInputBox.fill(expectedMessage);
            Thread.sleep(2000);
           locator=page.locator("button[id='showInput']");
           locator.click();
           Thread.sleep(2000);
           locator=page.locator("//div[@id='user-message']//p[@id='message']");
           String actualMessage=locator.innerText();
           Assert.assertEquals(actualMessage, expectedMessage,"Message is not Correct");   
                // Use the following code to mark the test status.
                setTestStatus("passed", "Title matched", page);   

        } catch (Exception err) {
            setTestStatus("failed", err.getMessage(), page);
            err.printStackTrace();
        }
        browser.close();
        page.close();
		playwright.close();
    } catch (Exception err) {
        err.printStackTrace();
    }
		
	}
	
	 public static void setTestStatus(String status, String remark, Page page) {
	        Object result;
	        result = page.evaluate("_ => {}", "lambdatest_action: { \"action\": \"setTestStatus\", \"arguments\": { \"status\": \"" + status + "\", \"remark\": \"" + remark + "\"}}");
	    }
}

