package com.brunomanfrinato.upcomingmoviesapp.movies;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brunomanfrinato.upcomingmoviesapp.R;
import com.brunomanfrinato.upcomingmoviesapp.model.Movie;
import com.brunomanfrinato.upcomingmoviesapp.util.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Context context;
    private MoviesView moviesView;

    MoviesAdapter(Context context, List<Movie> movies, MoviesView moviesView) {
        this.context = context;
        this.movies = movies;
        this.moviesView = moviesView;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Movie movie = movies.get(position);

        if (movie == null) {
            return;
        }

        holder.textMovieName.setText(movie.getTitle());
        holder.textRelaseDate.setText(movie.getReleaseDate());

        if (TextUtils.isEmpty(movie.getGenres())) {
            holder.textMovieGenres.setText("");
        } else {
            holder.textMovieGenres.setText(movie.getGenres());
        }

        Glide.with(context).load(Constants.IMAGE_URL + movie.getPoster()).crossFade().centerCrop().placeholder(R.drawable.ic_movie_grey_light).into(holder.imageMoviePosterThumb);

        holder.layoutItem.setOnClickListener(new MovieClickListener(movie.getId()));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
    }

    public void clearMovies() {
        final int totalMovies = movies.size();
        movies.clear();
        notifyItemRangeRemoved(0, totalMovies);
    }


    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_item)
        CardView layoutItem;

        @BindView(R.id.text_movie_name)
        TextView textMovieName;

        @BindView(R.id.text_movie_release_date)
        TextView textRelaseDate;

        @BindView(R.id.image_poster_thumb)
        ImageView imageMoviePosterThumb;

        @BindView(R.id.text_movie_genres)
        TextView textMovieGenres;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class MovieClickListener implements View.OnClickListener {
        private final int movieId;

        MovieClickListener(int movieId) {
            this.movieId = movieId;
        }

        @Override
        public void onClick(View v) {
            moviesView.navitageToDetail(movieId);
        }
    }
}
