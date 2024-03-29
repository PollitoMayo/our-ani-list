package cl.exdev.ouranilist.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

import cl.exdev.ouranilist.R;
import cl.exdev.ouranilist.adapters.AnimesAdapter;
import cl.exdev.ouranilist.models.Anime;
import cl.exdev.ouranilist.models.KitsuResponse;
import cl.exdev.ouranilist.models.User;
import cl.exdev.ouranilist.services.KitsuService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private User user;

    private ListView lvAnimes;
    private Retrofit _retrofit;
    private SearchView search;
    private int limit = 10;
    private int offset = 0;
    private String filter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        user = (User) getIntent().getExtras().getSerializable("user");

        _retrofit = new Retrofit.Builder().baseUrl("https://kitsu.io/api/edge/").addConverterFactory(GsonConverterFactory.create())
                .build();
        offset = 0;
        filter = "";

        lvAnimes = (ListView) findViewById(R.id.animes_list);
        search = (SearchView) findViewById(R.id.search);

        this.onSearchListener();
        this.getAnimes(this, filter, offset);
    }

    private void getAnimes(Context context, String filter, int offset){
        KitsuService service = _retrofit.create(KitsuService.class);
        String sort = filter.compareTo("") == 0 ? "popularityRank,ratingRank" : null;
        service.searchAnimeList(filter.compareTo("") == 0 ? null : filter, limit, offset, sort).enqueue(new Callback<KitsuResponse>() {
            @Override
            public void onResponse(Call<KitsuResponse> call, Response<KitsuResponse> response) {
                KitsuResponse kitsuResponse = response.body();
                ArrayList<Anime> animes = kitsuResponse.getdata();
                ArrayAdapter<Anime> adapter = new AnimesAdapter(context, animes);
                lvAnimes.setAdapter(adapter);

                lvAnimes.setOnItemClickListener((parent, view, i, id) -> {
                    Anime selectedAnime = animes.get(i);
                    Intent intent = new Intent(context, AnimeActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("anime", selectedAnime);
                    startActivity(intent);
                });
            }

            @Override
            public void onFailure(Call<KitsuResponse> call, Throwable t) {
                Log.e("RESPONSE:", "Fallo", t);
                Toast.makeText(getApplicationContext(), "No se pudo cargar la información", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void previousItems(View view) {
        if (offset != 0) {
            int newOffset = offset - limit;
            offset = newOffset;
            this.getAnimes(this, filter, newOffset);
        }
    }

    public void nextItems(View view) {
        int newOffset = offset + limit;
        offset = newOffset;
        this.getAnimes(this, filter, newOffset);
    }

    private void onSearchListener() {
        search.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filter = query;
        int newOffset = 0;
        offset = newOffset;
        this.getAnimes(this, query, newOffset);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.compareTo("") == 0) {
            int newOffset = 0;
            offset = newOffset;
            this.getAnimes(this, "", newOffset);
        }
        return false;
    }
}
