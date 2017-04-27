package ogloszenia;

public class Ogloszenie {
	// Klasa immutable - oznaczamy pola jako final
	private final String tytul;
	private final int cena;
	private final int rocznik;
	private final int przebieg;
	private final String silnik;
	private final String url;
	
	public Ogloszenie(String tytul, int cena, int rocznik, int przebieg, String silnik, String url) {
		this.tytul = tytul;
		this.cena = cena;
		this.rocznik = rocznik;
		this.przebieg = przebieg;
		this.silnik = silnik;
		this.url = url;
	}

	public String getTytul() {
		return tytul;
	}

	public int getCena() {
		return cena;
	}

	public int getRocznik() {
		return rocznik;
	}

	public int getPrzebieg() {
		return przebieg;
	}

	public String getSilnik() {
		return silnik;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "Ogloszenie [tytul=" + tytul + ", cena=" + cena + ", rocznik=" + rocznik + ", przebieg=" + przebieg
				+ ", silnik=" + silnik + ", url=" + url + "]";
	}
}
