package ogloszenia;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Pobieracz {
	private static final int CZAS_MINIMALNY = 2;
	private static final int CZAS_MAKSYMALNY = 10;
	
	private static final String ADRES = "http://moto.gratka.pl/";

	private static final Pattern regexCeny = Pattern.compile("(\\d+\\s*)+");  //      (\d+\s*)+
	private static final Pattern regexPrzebiegu = Pattern.compile("(\\d+\\s*)+");
	private static final Pattern regexRocznika = Pattern.compile("(\\d+)r\\.");

	private FirefoxDriver driver;
	private List<Ogloszenie> ogloszenia = new LinkedList<>();
	
	public Pobieracz() {
		driver = UstawieniaSelenium.getDriver();
	}

	public void close() {
		if(driver != null) {
			driver.quit();
		}
	}
	
	public List<Ogloszenie> getOgloszenia() {
		return Collections.unmodifiableList(ogloszenia);
	}

	public void wyszukaj(String fraza) {
		driver.get(ADRES);
		wypelnijFormularz(fraza);
	    do {
			try {
				Thread.sleep(CZAS_MINIMALNY * 1000);
			} catch (InterruptedException e) {
			}
			wczytajOgloszeniaZJednejStrony();
	    } while(przejdzNaNastepnaStrone());
	}
	
	private void wypelnijFormularz(String fraza) {
		System.out.println("Wszedłem na stronę " + driver.getTitle());
		// Szukam elementu formularza
		WebElement elementFormularza = driver.findElement(By.id("element-87"));
		elementFormularza.click();
		elementFormularza.clear();
		elementFormularza.sendKeys(fraza);
		// Klikam Szukaj
		driver.findElement(By.cssSelector("button.btn.btnSearch")).click();
	}

	private void wczytajOgloszeniaZJednejStrony() {
	    List<WebElement> oferty = driver.findElements(By.className("opisOferty"));
	    System.out.println("Znalazłem " + oferty.size() + " ofert");
	    
	    for(WebElement oferta : oferty) {
	    	try {

				WebElement h3 = oferta.findElement(By.tagName("h3"));
				String tytul = h3.getText().trim();
				String url = h3.findElement(By.tagName("a")).getAttribute("href");
				
				
				WebElement elementCena = oferta.findElement(By.xpath("div/strong"));

		    	int cena = 0;
		    	Matcher matcherCeny = regexCeny.matcher(elementCena.getText());
		    	if(matcherCeny.find()) {
		    		cena = Integer.parseInt(matcherCeny.group().replace(" ", ""));	    	
		    	}
		    	
		    	int przebieg = 0;
// Ewentualnie odkomentować, ale wolniej działa...
//		    	WebElement elementPrzebieg = oferta.findElement(By.xpath(".//li[@title='Przebieg']"));
//		    	Matcher matcherPrzbiegu = regexPrzebiegu.matcher(elementPrzebieg.getText());
//		    	if(matcherPrzbiegu.find()) {
//		    		przebieg = Integer.parseInt(matcherPrzbiegu.group().replace(" ", ""));
//		    	}

		    	WebElement elementRocznik = oferta.findElement(By.xpath(".//li[@title='Rok produkcji']"));
		    	int rocznik = 0;
		    	Matcher matcherRocznika = regexRocznika.matcher(elementRocznik.getText());
		    	if(matcherRocznika.find()) {
		    		rocznik = Integer.parseInt(matcherRocznika.group(1));
		    	}

		    	WebElement elementSilnik = oferta.findElement(By.xpath(".//li[@title='Pojemność silnika']"));
		    	String silnik = elementSilnik.getText().trim();

		    	
		    	Ogloszenie ogloszenie = new Ogloszenie(tytul, cena, rocznik, przebieg, silnik, url);
		    	ogloszenia.add(ogloszenie);
	    	} catch(Exception e) {
	    		System.err.println("WARNING: Dla jednego ogłoszenia był wyjątek " + e.getClass().getSimpleName() + " " + e.getMessage());
	    		//e.printStackTrace();
	    	}
	    }
	}

	private boolean przejdzNaNastepnaStrone() {
    	try {
    		WebElement elementNastepny = driver.findElement(By.cssSelector(".stronicowanie a.nastepny"));
    		System.out.println("Następna strona...");
	    	elementNastepny.click();
	    	return true;
    	} catch (Exception e) {
    		System.err.println("WARNING: Przy przejdzNaNastepnaStrone był wyjątek " + e.getClass().getSimpleName() + " " + e.getMessage());
    		//e.printStackTrace();
    		return false;
    	}
	}
}
