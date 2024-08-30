package Objects;

public class Stajyer extends User {
    private String nickname;
    private String telefon;
    private String tc;
    private String okul;
    private String okulBolum;
    private int sinif;

    public Stajyer(String ad, String soyad, String ePosta, String sifre, String bolum, String tc, String nickname, String okul, String okulBolum, int sinif, String telefon) {
        super(ad, soyad, ePosta, sifre, bolum);
        this.nickname = nickname;
        this.tc = tc;
        this.okul = okul;
        
        this.okulBolum = okulBolum;
        this.sinif = sinif; 
        this.telefon = telefon;
    }

    public Stajyer() {}

    // Stajyer'e özgü getter ve setter metodları
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public String getTc() { return tc; }
    public void setTc(String tc) { this.tc = tc; }
    public String getOkul() { return okul; }
    public void setOkul(String okul) { this.okul = okul; }
    public String getOkulBolum() { return okulBolum; }
    public void setOkulBolum(String okulBolum) { this.okulBolum = okulBolum; }
    public int getSinif() { return sinif; }
    public void setSinif(int i) { this.sinif = i; }
}
