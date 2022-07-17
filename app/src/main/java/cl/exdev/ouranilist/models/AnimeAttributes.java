package cl.exdev.ouranilist.models;

import java.io.Serializable;

public class AnimeAttributes implements Serializable {
    private String slug;
    private String synopsis;
    private String description;
    private int coverImageTopOffset;
    private String canonicalTitle;
    private String averageRating;
    private String startDate;
    private String endDate;
    private Object nextRelease;
    private String subtype;
    private String status;
    private AnimeImageSet posterImage;
    private AnimeImageSet coverImage;
    private int episodeCount;
    private boolean nsfw;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCoverImageTopOffset() {
        return coverImageTopOffset;
    }

    public void setCoverImageTopOffset(int coverImageTopOffset) {
        this.coverImageTopOffset = coverImageTopOffset;
    }

    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public void setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Object getNextRelease() {
        return nextRelease;
    }

    public void setNextRelease(Object nextRelease) {
        this.nextRelease = nextRelease;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AnimeImageSet getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(AnimeImageSet posterImage) {
        this.posterImage = posterImage;
    }

    public AnimeImageSet getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(AnimeImageSet coverImage) {
        this.coverImage = coverImage;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }
}

