package ogloszenia;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WypiszNaKonsole1 {

	public static void main(String[] args) {
		String czegoSzukamy = "Polonez";
		
		FirefoxDriver driver = null;
		try {
			System.out.println("Otwieram drivera...");
			driver = UstawieniaSelenium.getDriver();
			
			System.out.println("Wchodzę na stronę...");
			driver.get("http://moto.gratka.pl");
			
			System.out.println("Tytuł: " + driver.getTitle());
			System.out.println("Wpisuję i klikam...");
			
			WebElement poleFormularza = driver.findElement(By.id("element-87"));
			poleFormularza.click();
			poleFormularza.clear();
			poleFormularza.sendKeys(czegoSzukamy);
			
			WebElement guzik = driver.findElement(By.cssSelector("button.btn.btnSearch"));
			guzik.click();
			
			System.out.println("Po kliknięciu: " + driver.getCurrentUrl());
			
			
			List<WebElement> elementy = driver.findElements(By.className("opisOferty"));
			for(WebElement oferta : elementy) {
				WebElement elementH3 = oferta.findElement(By.tagName("h3"));
				String tytul = elementH3.getText();
				System.out.println("* " + tytul);
				
				WebElement elementCena = oferta.findElement(By.xpath("div/strong"));
				System.out.println("    " + elementCena.getText());
				
//				WebElement elementRocznik = oferta.findElement(By.xpath(".//li[3]"));
//				System.out.println("    " + elementRocznik.getText());
				
				List<WebElement> li = oferta.findElements(By.xpath(".//li"));
				for (WebElement element : li) {
					System.out.println("    " + element.getText());
				}
			}
			
		} finally {
			if(driver != null) {
				driver.quit();
			}
		}
	}

}
