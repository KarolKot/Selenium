package ogloszenia;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;

public class UstawieniaSelenium {
	public static final String GECKO = "C:/Tools/geckodriver.exe";
	
	public static FirefoxDriver getDriver() {
		System.setProperty("webdriver.gecko.driver", GECKO);
		FirefoxDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return driver;
	}
}
