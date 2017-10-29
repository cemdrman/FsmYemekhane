package cemdirman.com.yemekhane.model;

import java.util.ArrayList;

/**
 * Created by cem on 10/29/17.
 */

public class Menu {

    private static String tarih;
    private static ArrayList<Yemek> menu = new ArrayList<>();

    private Menu(){

    }

    public static void addYemek(Yemek yemek){
        menu.add(yemek);
    }

    public static ArrayList<Yemek> getMenu(){
        return menu;
    }

    public static String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "tarih='" + tarih + '\'' +
                ", menu=" + menu +
                '}';
    }
}
