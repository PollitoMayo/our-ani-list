package cl.exdev.ouranilist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import cl.exdev.ouranilist.R;
import cl.exdev.ouranilist.adapters.AnimesAdapter;
import cl.exdev.ouranilist.database.AdminSQLiteOpenHelper;
import cl.exdev.ouranilist.models.Anime;
import cl.exdev.ouranilist.models.KitsuResponse;
import cl.exdev.ouranilist.models.User;
import cl.exdev.ouranilist.services.KitsuService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private User user;
    private ArrayList<String> animeIds;

    private Retrofit _retrofit;

    private FloatingActionButton fabSearch;
    private ListView lvAnimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable("user");
            setTitle("La lista de " + user.getName());
        }

        fabSearch = (FloatingActionButton) findViewById(R.id.search_button);

        fabSearch.setOnClickListener(view -> {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });

        lvAnimes = (ListView) findViewById(R.id.favorites_animes_list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        animeIds = getLikedAnimeIds(user.getUsername());
        Log.d("UWU", String.join(",", animeIds));

        _retrofit = new Retrofit.Builder().baseUrl("https://kitsu.io/api/edge/").addConverterFactory(GsonConverterFactory.create())
                .build();

        this.getAnimes(this);
    }

    private void getAnimes(Context context) {
        KitsuService service = _retrofit.create(KitsuService.class);
        service.getAnimeByIds(String.join(",", animeIds)).enqueue(new Callback<KitsuResponse>() {
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
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> getLikedAnimeIds(String username) {
        ArrayList<String> list = new ArrayList();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT animeId FROM favorites WHERE username = '" + username + "' AND liked = 1", null);

        boolean existFirstElement = cursor.moveToFirst();

        if (existFirstElement) {
            while (!cursor.isLast()) {
                Log.d("UWU", "cursor" + cursor.getString(0));
                list.add(cursor.getString(0));
                cursor.moveToNext();
            }
            Log.d("UWU", "cursor" + cursor.getString(0));
            list.add(cursor.getString(0));
            cursor.close();
        }

        return list;
    }
}