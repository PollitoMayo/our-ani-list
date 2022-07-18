package cl.exdev.ouranilist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import cl.exdev.ouranilist.models.Anime;
import cl.exdev.ouranilist.models.User;

public class AnimeActivity extends AppCompatActivity {
    private boolean isLiked = false;
    private Anime anime;

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

        anime = (Anime) getIntent().getExtras().getSerializable("anime");

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
        miLike.setIcon(isLiked ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.like) {
            isLiked = !isLiked;
            item.setIcon(isLiked ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24);
            Toast.makeText(this, isLiked ? "Guardado en favoritos" : "Quitado de favoritos", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}