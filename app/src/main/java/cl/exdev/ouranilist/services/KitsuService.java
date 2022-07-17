package cl.exdev.ouranilist.services;

import cl.exdev.ouranilist.models.KitsuResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface KitsuService {
    @GET("anime")
    public Call<KitsuResponse> getAnimeList();
}
