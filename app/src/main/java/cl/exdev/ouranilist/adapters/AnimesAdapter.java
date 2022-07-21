package cl.exdev.ouranilist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import cl.exdev.ouranilist.R;
import cl.exdev.ouranilist.models.Anime;

public class AnimesAdapter extends ArrayAdapter<Anime> {
        private Context mContext;

        public AnimesAdapter(Context context, ArrayList<Anime> animes) {
            super(context, 0, animes);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Anime anime = getItem(position);

            String animeName = anime.getAttributes().getCanonicalTitle();
            String animeYear = String.valueOf(anime.getAttributes().getStartYear());
            String posterUrl = anime.getAttributes().getPosterImage().getTiny();

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_anime, parent, false);
            }

            TextView tvAnimeName = (TextView) convertView.findViewById(R.id.anime_name);
            tvAnimeName.setText(animeName);

            TextView tvAnimeCategory = (TextView) convertView.findViewById(R.id.anime_category);
            tvAnimeCategory.setText(animeYear);

            ImageView ivAnimePoster = (ImageView) convertView.findViewById(R.id.anime_poster);

            Glide.with(mContext)
                    .load(posterUrl)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivAnimePoster);


            return convertView;
        }

}
