package com.brunomanfrinato.upcomingmoviesapp.movies;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.brunomanfrinato.upcomingmoviesapp.R;
import com.brunomanfrinato.upcomingmoviesapp.api.ApiEndpoint;
import com.brunomanfrinato.upcomingmoviesapp.api.ApiService;
import com.brunomanfrinato.upcomingmoviesapp.exception.NoConnectivityException;
import com.brunomanfrinato.upcomingmoviesapp.model.Movie;
import com.brunomanfrinato.upcomingmoviesapp.model.response.GenreResponse;
import com.brunomanfrinato.upcomingmoviesapp.model.response.GenresResponse;
import com.brunomanfrinato.upcomingmoviesapp.model.response.MovieResponse;
import com.brunomanfrinato.upcomingmoviesapp.model.response.UpcomingMovieResponse;
import com.brunomanfrinato.upcomingmoviesapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MoviesPresenter {

    private MoviesView view;
    private Context context;
    private static final int FIRST_PAGE = 1;
    private int currentPage = FIRST_PAGE;
    private int totalPages = 0;
    private SparseArray<String> genres = new SparseArray<>();

    MoviesPresenter(MoviesView view, Context context) {
        this.context = context;
        this.view = view;
    }

    void loadUpcomingMovies() {
        view.hideErrorLayout();
        view.showLoading();
        final Retrofit retrofit = ApiService.getRetrofit(context);
        ApiEndpoint apiEndpoint = retrofit.create(ApiEndpoint.class);
        Call<GenresResponse> call = apiEndpoint.getGenres();
        call.enqueue(new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                if (response.code() == Constants.STATUS_OK) {
                    genres.clear();
                    for (GenreResponse genreResponse : response.body().getGenres()) {
                        genres.put(genreResponse.getId(), genreResponse.getName());
                    }

                    loadFirstPage();
                }
            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable t) {
                view.hideLoading();
                view.showErrorLayout();
            }
        });
    }

    void loadMore() {
        if (currentPage == totalPages) {
            return;
        }

        currentPage += 1;

        view.showLoadingMore();
        final Retrofit retrofit = ApiService.getRetrofit(context);
        ApiEndpoint apiEndpoint = retrofit.create(ApiEndpoint.class);
        Call<UpcomingMovieResponse> call = apiEndpoint.getUpcomingMovies(currentPage);
        call.enqueue(new Callback<UpcomingMovieResponse>() {
            @Override
            public void onResponse(Call<UpcomingMovieResponse> call, Response<UpcomingMovieResponse> response) {
                if (response.code() == Constants.STATUS_OK) {
                    UpcomingMovieResponse upcomingMovieResponse = response.body();

                    view.loadMovies(prepareMovies(upcomingMovieResponse.getMovies()));
                    view.hideLoadingMore();
                }
            }

            @Override
            public void onFailure(Call<UpcomingMovieResponse> call, Throwable t) {
                view.hideLoadingMore();

                if (t instanceof NoConnectivityException) {
                    view.showError(R.string.error_no_connection);
                } else {
                    view.showError(R.string.error_loading_more);
                }
            }
        });
    }

    private void loadFirstPage() {
        final Retrofit retrofit = ApiService.getRetrofit(context);
        ApiEndpoint apiEndpoint = retrofit.create(ApiEndpoint.class);
        Call<UpcomingMovieResponse> call = apiEndpoint.getUpcomingMovies(FIRST_PAGE);
        call.enqueue(new Callback<UpcomingMovieResponse>() {
            @Override
            public void onResponse(Call<UpcomingMovieResponse> call, Response<UpcomingMovieResponse> response) {
                if (response.code() == Constants.STATUS_OK) {
                    UpcomingMovieResponse upcomingMovieResponse = response.body();

                    totalPages = upcomingMovieResponse.getTotalPages();
                    view.clearMovies();
                    view.loadMovies(prepareMovies(upcomingMovieResponse.getMovies()));
                    view.showTotalMovies(upcomingMovieResponse.getTotalResults());
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<UpcomingMovieResponse> call, Throwable t) {
                view.hideLoading();
                view.showErrorLayout();
            }
        });
    }

    private List<Movie> prepareMovies(List<MovieResponse> moviesResponse) {
        List<Movie> movies = new ArrayList<>();
        Movie movie;

        for (MovieResponse movieResponse : moviesResponse) {
            movie = new Movie();
            movie.setId(movieResponse.getId());
            movie.setTitle(movieResponse.getTitle());
            movie.setReleaseDate(movieResponse.getReleaseDate());
            movie.setPoster(movieResponse.getPosterPath());

            List<String> genresNames = new ArrayList<>();
            for (int genreId : movieResponse.getGenreIds()) {
                genresNames.add(genres.get(genreId));
            }

            movie.setGenres(TextUtils.join(" | ", genresNames));

            movies.add(movie);
        }

        return movies;
    }
}
