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

public class AnimesAdapter extends ArrayAdapter<String> {
        private Context mContext;

        public AnimesAdapter(Context context, String[] animes) {
            super(context, 0, animes);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String anime = getItem(position);

            String animeName = anime;
            String animeCategory = "Romcom";
            String posterUrl = "https://media.kitsu.io/anime/45619/poster_image/tiny-d78c06d3e95dcd6603688e2e737f359d.jpeg";

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_anime, parent, false);
            }

            TextView tvAnimeName = (TextView) convertView.findViewById(R.id.anime_name);
            tvAnimeName.setText(animeName);

            TextView tvAnimeCategory = (TextView) convertView.findViewById(R.id.anime_category);
            tvAnimeCategory.setText(animeCategory);

            ImageView ivAnimePoster = (ImageView) convertView.findViewById(R.id.anime_poster);

            Glide.with(mContext)
                    .load(posterUrl)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivAnimePoster);


            return convertView;
        }

}
