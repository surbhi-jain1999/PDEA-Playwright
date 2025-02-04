package test.java.Testcases;
    

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
import com.microsoft.playwright.options.WaitForSelectorState;

public class TestScenario3 {

	String status="";
	Page page;
	Browser browser;
	Playwright playwright;

	@Parameters({"Browser","Platform","BrowserVersion"})
	@Test
	public void TestCase3(String Browser,String Platform,String BrowserVersion)
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
            Locator locator = page.locator("//a[text()='Input Form Submit']");
            locator.click();
            Thread.sleep(3000);
            
            Locator locator2=page.locator("//div//h2[text()='Input form validations']");	
			locator2.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(240000));
			Locator submitBtn=page.locator("//button[text()='Submit']");
			submitBtn.click();
			Thread.sleep(2000);
			Locator validationMessage = page.locator("input:has-text('Please fill out this field.')");	

		      if (validationMessage.isVisible()) {
		        System.out.println("Validation message is displayed: 'Please fill out this field'");
		      } else {
		        System.out.println("Validation message not displayed.");
		      }
            
		      page.locator("//input[@placeholder='Name']").fill("TestUser");
		      page.locator("//input[@placeholder='Email']").fill("testuser@example.com");
		      Thread.sleep(1000);
		      page.locator("//input[@placeholder='Password']").fill("TestUser@123");
		      page.locator("//input[@placeholder='Company']").fill("Persistent");
		      Thread.sleep(1000);
		      page.locator("//input[@placeholder='Website']").fill("www.testweb.com");
		      page.locator("//input[@placeholder='City']").fill("Hyderabad");
		      page.locator("//select[@name='country']").selectOption("US");
		      Thread.sleep(1000);
		      page.locator("//input[@placeholder='Address 1']").fill("BhagyaNagar Colony");
		      page.locator("//input[@placeholder='Address 2']").fill("KukutPali");
		      Thread.sleep(1000);
		      page.locator("//input[@placeholder='State']").fill("Telanagana");
		      page.locator("//input[@placeholder='Zip code']").fill("500072");

		      page.locator("//button[text()='Submit']").click();
		      Thread.sleep(3000);

		      assertThat(page.locator("//p[@class='success-msg hidden']"))
		          .containsText("Thanks for contacting us, we will get back to you shortly.");
		      Thread.sleep(2000);
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

