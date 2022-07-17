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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import cl.exdev.ouranilist.adapters.AnimesAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView lvAnimes;

    private String elements[] = {"SPY×FAMILY", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa"};

    private Retrofit _retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _retrofit = new Retrofit.Builder().baseUrl("https://kitsu.io/api/edge/").addConverterFactory(GsonConverterFactory.create())
                .build();
        
        setTitle("La lista de Pancito");

        lvAnimes = (ListView) findViewById(R.id.animes_list);

        ArrayAdapter<String> adapter = new AnimesAdapter(this, elements);
        lvAnimes.setAdapter(adapter);

        lvAnimes.setOnItemClickListener((parent, view, i, id) -> {
            String animeName = elements[i];
            Toast.makeText(MainActivity.this, animeName, Toast.LENGTH_LONG).show();
        });
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}