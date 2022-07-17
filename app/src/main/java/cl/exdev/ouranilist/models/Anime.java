package cl.exdev.ouranilist.models;
import java.io.Serializable;

public class Anime implements Serializable {
    private AnimeAttributes attributes;

    public void setAttributes(AnimeAttributes value) {
        this.attributes = value;
    }

    public AnimeAttributes getAttributes() {
        return this.attributes;
    }
}
