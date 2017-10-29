package cemdirman.com.yemekhane.model;

/**
 * Created by cem on 10/29/17.
 */

public class Yemek {

    private String adi;
    private double fiyat;
    private int kalori;

    public Yemek(String adi, double fiyat, int kalori) {
        this.adi = adi;
        this.fiyat = fiyat;
        this.kalori = kalori;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }

    public int getKalori() {
        return kalori;
    }

    public void setKalori(int kalori) {
        this.kalori = kalori;
    }

    @Override
    public String toString() {
        return "Yemek{" +
                "adi='" + adi + '\'' +
                ", fiyat=" + fiyat +
                ", kalori=" + kalori +
                '}';
    }


}
