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

    @Override
    public String toString() {
        return "Yemek{" +
                "adi='" + adi + '\'' +
                ", fiyat=" + fiyat +
                ", kalori=" + kalori +
                '}';
    }
}
