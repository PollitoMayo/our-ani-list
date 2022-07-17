package cl.exdev.ouranilist.models;

import java.util.ArrayList;

public class KitsuResponse {
    private ArrayList<Anime> data;

    public void setData(ArrayList<Anime> value) {
        this.data = value;
    }

    public ArrayList<Anime> getdata() {
        return data;
    }
}
