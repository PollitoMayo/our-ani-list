package cl.exdev.ouranilist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cl.exdev.ouranilist.R;
import cl.exdev.ouranilist.database.AdminSQLiteOpenHelper;
import cl.exdev.ouranilist.models.Anime;
import cl.exdev.ouranilist.models.User;

public class AnimeActivity extends AppCompatActivity {
    private User user;
    private Anime anime;

    private boolean isLiked;

    private ImageView ivPoster;
    private ImageView ivCover;
    private TextView tvTitle;
    private TextView tvSubtitle;
    private TextView tvSynopsis;
    private MenuItem miLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        user = (User) getIntent().getExtras().getSerializable("user");
        anime = (Anime) getIntent().getExtras().getSerializable("anime");
        isLiked = anime.getIsLiked() != null ? anime.getIsLiked() : false;

        setTitle(anime.getAttributes().getCanonicalTitle());


        ivPoster = findViewById(R.id.anime_details_poster);
        ivCover = findViewById(R.id.anime_details_cover);
        tvTitle = findViewById(R.id.anime_details_title);
        tvSubtitle = findViewById(R.id.anime_details_subtitle);
        tvSynopsis = findViewById(R.id.anime_details_synopsis);

        Glide.with(this)
                .load(anime.getAttributes().getPosterImage().getLarge())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPoster);

        Glide.with(this)
                .load(anime.getAttributes().getCoverImage().getLarge())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCover);

        tvTitle.setText(anime.getAttributes().getCanonicalTitle());
        tvSubtitle.setText("" + anime.getAttributes().getStartYear());
        tvSynopsis.setText(anime.getAttributes().getSynopsis());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.like, menu);

        miLike = menu.getItem(0);
        Boolean status = getCurrentStatus(user.getUsername(), anime.getId());
        isLiked = status != null ? status : false;
        setIcon(miLike);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.like) {
            onLikeItemPressed(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void onLikeItemPressed(MenuItem item) {
        isLiked = !isLiked;
        setIcon(item);

        Boolean currentStatus = getCurrentStatus(user.getUsername(), anime.getId());
        if (currentStatus == null) {
            saveFavorite(user.getUsername(), anime.getId(), isLiked);
        } else {
            isLiked = !currentStatus;
            updateFavorite(user.getUsername(), anime.getId(), isLiked);
        }

        setIcon(item);
        Toast.makeText(this, isLiked ? "Guardado en favoritos" : "Quitado de favoritos", Toast.LENGTH_LONG).show();
    }

    private void setIcon(MenuItem item) {
        item.setIcon(isLiked ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24);
    }

    private boolean updateFavorite(String username, String animeId, boolean value) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues newFavorite = new ContentValues();
        newFavorite.put("username", username);
        newFavorite.put("animeId", animeId);
        newFavorite.put("liked", value);

        db.update("favorites", newFavorite, "username = '" + username + "' AND animeId = '" + animeId + "'", null);
        return value;
    }

    private boolean saveFavorite(String username, String animeId, boolean value) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues newFavorite = new ContentValues();
        newFavorite.put("username", username);
        newFavorite.put("animeId", animeId);
        newFavorite.put("liked", value);

        db.insert("favorites", null, newFavorite);
        return value;
    }

    private Boolean getCurrentStatus(String username, String animeId) {
        Boolean status = null;

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT liked FROM favorites WHERE username = '" + username + "' AND animeId = '" + animeId + "'", null);

        boolean existFirstElement = cursor.moveToFirst();

        if (existFirstElement) {
            status = cursor.getInt(0) == 1;
            cursor.close();
        }

        return status;
    }
}