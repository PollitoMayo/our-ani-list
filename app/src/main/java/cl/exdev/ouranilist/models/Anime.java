package cl.exdev.ouranilist.models;
import java.io.Serializable;

public class Anime implements Serializable {
    private String id;
    private AnimeAttributes attributes;
    private Boolean isLiked;

    public void setAttributes(AnimeAttributes value) {
        this.attributes = value;
    }

    public AnimeAttributes getAttributes() {
        return this.attributes;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean liked) {
        isLiked = liked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
