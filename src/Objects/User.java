package Objects;
public class User  {
	private String ad;
	private String soyad;
	private String sifre;
	private String ePosta;
	private String bolum;
	public User(String ad,String soyad,String ePosta,String sifre, String bolum) {
		this.ad = ad;
        this.soyad = soyad;
        this.sifre = sifre;
        this.ePosta = ePosta;
        this.bolum = bolum;
	}
	public User() {}

	public String getSoyad() {
		
		return soyad;
	}
	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}
	public String getAd() {
	
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	public String getSifre() {
		return sifre;
	}
	public void setSifre(String sifre) {
		this.sifre = sifre;
	}
	public String getePosta() {
		return ePosta;
	}
	public void setePosta(String ePosta) {
		this.ePosta = ePosta;
	}
	public String getBolum() {
		return bolum;
	}
	public void setBolum(String bolum) {
		this.bolum = bolum;
	}

}
