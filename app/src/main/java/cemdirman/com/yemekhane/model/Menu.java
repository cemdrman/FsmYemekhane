package cemdirman.com.yemekhane.model;

import java.util.ArrayList;

/**
 * Created by cem on 10/29/17.
 */

public class Menu {

    private String tarih;
    private ArrayList<Yemek> menu = new ArrayList<>();

    private Menu(){
        
    }

    public void addYemek(Yemek yemek){
        menu.add(yemek);
    }

    public ArrayList<Yemek> getMenu(){
        return menu;
    }

    public String getTarih() {
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
