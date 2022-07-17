package cl.exdev.ouranilist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import cl.exdev.ouranilist.models.Anime;
import cl.exdev.ouranilist.models.AnimeAttributes;
import cl.exdev.ouranilist.models.KitsuResponse;
import cl.exdev.ouranilist.services.KitsuService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit _retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _retrofit = new Retrofit.Builder().baseUrl("https://kitsu.io/api/edge/").addConverterFactory(GsonConverterFactory.create())
                .build();
        this.getAnimes();
    }

    private void getAnimes(){
        KitsuService service = _retrofit.create(KitsuService.class);
        service.getAnimeList().enqueue(new Callback<KitsuResponse>() {
            @Override
            public void onResponse(Call<KitsuResponse> call, Response<KitsuResponse> response) {
                KitsuResponse kitsuResponse = response.body();
                ArrayList<Anime> animes = kitsuResponse.getdata();
                for (int i = 0; i < animes.size(); i++) {
                    Anime anime = animes.get(i);
                    AnimeAttributes animeAttributes = anime.getAttributes();
                    Log.e("RESPONSE:", animeAttributes.getCanonicalTitle());
                }
            }

            @Override
            public void onFailure(Call<KitsuResponse> call, Throwable t) {
                Log.e("RESPONSE:", "Fallo", t);
                Toast.makeText(getApplicationContext(), "No se pudo cargar la información", Toast.LENGTH_LONG).show();
            }
        });
    }
}