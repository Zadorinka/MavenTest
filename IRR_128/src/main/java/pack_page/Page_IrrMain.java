package pack_page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pack_utils.ExceptFailTest;
import pack_utils.Proper;

public class Page_IrrMain extends Page
{
	
	@FindBy(id="a_login")             
	private WebElement wLinkEnter;
	
	@FindBy(xpath="//div[@class='popupRegions']")
	private WebElement wWindowSelectOfRegion;
	
	@FindBy(xpath="//div[@class='button-style btn-a']/a")
	private WebElement wOkButtonWindowSelectOfRegion;
	
	@FindBy(xpath="//div[@id='popup-wrap']//input[@class='login']")
	private WebElement wFieldLoginForm;
	
	@FindBy(xpath="//div[@id='popup-wrap']//input[@name='password']")
	private WebElement wFieldPasswordForm;
	
	@FindBy(xpath="//div[@id='popup-wrap']//a[@class='loginFormSubmit']")
	private WebElement wButtonEnterForm;
	
	@FindBy(xpath="//a[@id='load_user_ads_counter']/span[2]")
	private WebElement wLinkPrivateOffice;
	
	//@FindBy(xpath="(//a[contains(@href, '/myadverts/')])[2]")
	@FindBy(xpath="//div[@id='block_links_lk']/ul/li/a")
	private WebElement wLinkMyAdverts;
	
	
	private String sUrl;
	
	public Page_IrrMain(WebDriver driver){super(driver);}

	public void OpenPage(String sUrl) throws ExceptFailTest
	{
		//sUrlIRR = Proper.GetProperty("urlIrr");
		driver.navigate().to(sUrl);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wLog.WriteString(1, "Открываем страницу " + sUrl);
		System.out.println("Открываем страницу " + sUrl);
	}
	
	public void OpenFormAuthorization() throws ExceptFailTest
	{
		
		CheckElementPresent(1,"//div[@class='popupRegions']");
		if(wWindowSelectOfRegion.isDisplayed())
		{
			wLog.WriteString(1, "Закрываем окно выбора регионов");
			System.out.println("Закрываем окно выбора регионов");
			CheckElementPresent(1,"//div[@class='button-style btn-a']/a");
			wOkButtonWindowSelectOfRegion.click();
		}
		CheckElementPresent(2, "a_login");
		wLog.WriteString(1,"Открываем форму авторизации");
		System.out.println("Открываем форму авторизации");
		wLinkEnter.click();
		
	}
	
	public Page_IrrPrivateOffice LoginOn(String sLogin, String sPasswordIrr) throws ExceptFailTest
	{
		Sleep(1000);
		wLog.WriteString(1, "Вводим логин");
		System.out.println("Вводим логин");
		CheckElementPresent(1, "//div[@id='popup-wrap']//input[@class='login']");
		wFieldLoginForm.sendKeys(Proper.GetProperty(sLogin));
		wLog.WriteString(1, "Вводим пароль");
		System.out.println("Вводим пароль");
		CheckElementPresent(1, "//div[@id='popup-wrap']//input[@name='password']");
		
		//wFieldPasswordForm.sendKeys(Proper.GetProperty("passwordIRR"));	
		wFieldPasswordForm.sendKeys(sPasswordIrr);
		
		CheckElementPresent(1, "//div[@id='popup-wrap']//a[@class='loginFormSubmit']");
		wButtonEnterForm.click();
		wLog.WriteString(1, "Нажимаем войти");
		System.out.println("Нажимаем войти");
		Sleep(1000);
		CheckElementPresent(1, "//a[@id='load_user_ads_counter']/span[2]");
		wLinkPrivateOffice.click();
		wLog.WriteString(1, "Переходим в личный кабинет");
		System.out.println("Переходим в личный кабинет");
		Sleep(1500);
		CheckElementPresent(1,"//div[@id='block_links_lk']/ul/li/a");	
		wLinkMyAdverts.click();
		//driver.get(Proper.GetProperty("urlIrr")+"myadverts/");
		driver.get(driver.getCurrentUrl()+"myadverts/");
		return PageFactory.initElements(driver, Page_IrrPrivateOffice.class);
	}


	
}
