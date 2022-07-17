package cl.exdev.ouranilist.services;

import cl.exdev.ouranilist.models.KitsuResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KitsuService {
    @GET("anime")
    public Call<KitsuResponse> getAnimeList(@Query("filter[text]") String filter, @Query("page[limit]") int limit, @Query("page[offset]") int offset);
}
