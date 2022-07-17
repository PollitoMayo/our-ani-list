package cl.exdev.ouranilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import cl.exdev.ouranilist.models.Anime;
import cl.exdev.ouranilist.models.KitsuResponse;
import cl.exdev.ouranilist.models.User;
import cl.exdev.ouranilist.services.KitsuService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cl.exdev.ouranilist.adapters.AnimesAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView lvAnimes;
    private Retrofit _retrofit;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _retrofit = new Retrofit.Builder().baseUrl("https://kitsu.io/api/edge/").addConverterFactory(GsonConverterFactory.create())
                .build();

        user = (User) getIntent().getExtras().getSerializable("user");
        setTitle("La lista de " + user.getName());

        lvAnimes = (ListView) findViewById(R.id.animes_list);

        this.getAnimes(this);
    }

    private void getAnimes(Context context){
        KitsuService service = _retrofit.create(KitsuService.class);
        service.getAnimeList().enqueue(new Callback<KitsuResponse>() {
            @Override
            public void onResponse(Call<KitsuResponse> call, Response<KitsuResponse> response) {
                KitsuResponse kitsuResponse = response.body();
                ArrayList<Anime> animes = kitsuResponse.getdata();
                ArrayAdapter<Anime> adapter = new AnimesAdapter(context, animes);
                lvAnimes.setAdapter(adapter);

                lvAnimes.setOnItemClickListener((parent, view, i, id) -> {
                    String animeName = animes.get(i).getAttributes().getCanonicalTitle();
                    Toast.makeText(MainActivity.this, animeName, Toast.LENGTH_LONG).show();
                });
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
            Toast.makeText(this, "Cerrando sesión de " + user.getName(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}