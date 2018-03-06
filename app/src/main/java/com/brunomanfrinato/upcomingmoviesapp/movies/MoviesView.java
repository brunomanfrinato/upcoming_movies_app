package com.brunomanfrinato.upcomingmoviesapp.movies;

import android.support.annotation.StringRes;

import com.brunomanfrinato.upcomingmoviesapp.model.Movie;

import java.util.List;

public interface MoviesView {
    void showLoading();
    void hideLoading();

    void showLoadingMore();
    void hideLoadingMore();

    void showTotalMovies(int totalMovies);

    void loadMovies(List<Movie> movies);
    void clearMovies();

    void showErrorLayout();
    void hideErrorLayout();

    void showError(@StringRes int stringId);

    void navitageToDetail(int movieId);
}
