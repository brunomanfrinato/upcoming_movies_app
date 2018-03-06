package com.brunomanfrinato.upcomingmoviesapp.details;

import com.brunomanfrinato.upcomingmoviesapp.model.MovieDetail;
import com.brunomanfrinato.upcomingmoviesapp.model.response.MovieDetailResponse;

public interface DetailsView {
    void showLoading();
    void hideLoading();

    void showError();
    void hideError();

    void showDetails();
    void hideDetails();

    void showMovieDetails(MovieDetail movieDetail);
}
