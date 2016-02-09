package example.com.udacitymovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import example.com.udacitymovie.DetailActivity;
import example.com.udacitymovie.R;
import example.com.udacitymovie.data.MovieContract;

/**
 * Created by VPPL-10132 on 2/1/2016.
 */
public class RecycleVieMovieAdapter extends CursorRecyclerViewAdapter {
    private String TAG = RecycleVieMovieAdapter.class.getSimpleName();
    Context context;
    Cursor cursor;

    public RecycleVieMovieAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(this.cursor);
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        return super.swapCursor(this.cursor);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_grid_item, parent, false);
        MovieHolder movieHolder = new MovieHolder(view);
        return movieHolder;

    }

    @Override
    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public void onBindViewHolder(final MovieHolder viewHolder, Cursor cursor) {
        Log.d(TAG, " on Bind View Holder" + cursor.getString(cursor.getColumnIndex(MovieContract.Favourite.COLUMN_BACKDROP_PATH)));
        final View v = viewHolder.movieItemFooter;

        String url = cursor.getString(cursor.getColumnIndex(MovieContract.Favourite.COLUMN_BACKDROP_PATH));
        this.cursor = cursor;
        viewHolder.serverId = getCursor().getString(getCursor().getColumnIndex(MovieContract.Favourite.COLUMN_SERVER_ID));

        Glide.with(context)
                .load(url)
                .crossFade()
                .placeholder(R.color.movie_poster_placeholder)
                .listener(GlidePalette.with(url)
                        .intoCallBack(new GlidePalette.CallBack() {

                            @Override
                            public void onPaletteLoaded(Palette palette) {
                                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                                Palette.Swatch mutedSwatch = palette.getMutedSwatch();

                                if (mutedSwatch != null) {
                                    //get rgb
                                    viewHolder.movieItemFooter.setBackgroundColor(palette.getMutedSwatch().getRgb());
                                } else if (vibrantSwatch != null) {
                                    //get another swatch
                                    viewHolder.movieItemFooter.setBackgroundColor(palette.getVibrantSwatch().getRgb());

                                }

                            }
                        }))
                .into(viewHolder.imageView);

        viewHolder.movieTitle = getCursor().getString(getCursor().getColumnIndex(MovieContract.Favourite.COLUMN_TITLE));
        viewHolder.movieVote = getCursor().getString(getCursor().getColumnIndex(MovieContract.Favourite.COLUMN_VOTE_AVERAGE));

        viewHolder.movieItemTitle.setText(getCursor().getString(getCursor().getColumnIndex(MovieContract.Favourite.COLUMN_TITLE)));
        viewHolder.movieItemVote.setText(getCursor().getString(getCursor().getColumnIndex(MovieContract.Favourite.COLUMN_VOTE_AVERAGE)));
//        }


    }

    @Override
    public void onBindViewHolder(MovieHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);


    }


    class MovieHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView movieItemTitle, movieItemVote;
        String serverId;
        String movieTitle, movieVote;
        View movieItemFooter;
        Button movieItemButtonDetails;

        public MovieHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.iv_movie);
            movieItemTitle = (TextView) itemView.findViewById(R.id.movie_item_title);
            movieItemVote = (TextView) itemView.findViewById(R.id.movie_item_vote);
            movieItemFooter = (View) itemView.findViewById(R.id.movie_item_footer);
            movieItemButtonDetails = (Button) itemView.findViewById(R.id.movie_item_btn_details);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "" + serverId, Toast.LENGTH_SHORT).show();
                    Uri uri = MovieContract.Favourite.BuildFavouriteUri(Long.parseLong(serverId));
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("selectedUri", uri);
                    context.startActivity(i);


                }
            });

            movieItemButtonDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "" + serverId, Toast.LENGTH_SHORT).show();
                    Uri uri = MovieContract.Favourite.BuildFavouriteUri(Long.parseLong(serverId));
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("selectedUri", uri);
                    context.startActivity(i);

                }
            });


        }

    }
}
