package com.brunomanfrinato.upcomingmoviesapp.details;

import android.content.Context;
import android.text.TextUtils;

import com.brunomanfrinato.upcomingmoviesapp.api.ApiEndpoint;
import com.brunomanfrinato.upcomingmoviesapp.api.ApiService;
import com.brunomanfrinato.upcomingmoviesapp.model.MovieDetail;
import com.brunomanfrinato.upcomingmoviesapp.model.response.GenreResponse;
import com.brunomanfrinato.upcomingmoviesapp.model.response.MovieDetailResponse;
import com.brunomanfrinato.upcomingmoviesapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsPresenter {

    private DetailsView view;
    private Context context;

    DetailsPresenter(DetailsView view, Context context) {
        this.view = view;
        this.context = context;
    }

    void getMovieDetail(int movieId) {
        view.hideDetails();
        view.hideError();
        view.showLoading();
        final Retrofit retrofit = ApiService.getRetrofit(context);
        ApiEndpoint apiEndpoint = retrofit.create(ApiEndpoint.class);
        Call<MovieDetailResponse> call = apiEndpoint.getMovieDetail(movieId);
        call.enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                if (response.code() == Constants.STATUS_OK) {

                    MovieDetail movieDetail = new MovieDetail();
                    movieDetail.setTitle(response.body().getTitle());
                    movieDetail.setReleaseDate(response.body().getRelaseDate());
                    movieDetail.setOverview(response.body().getOverview());
                    movieDetail.setBackdropPath(response.body().getBackdropPath());
                    movieDetail.setPosterPath(response.body().getPosterPath());
                    movieDetail.setRuntime(response.body().getRuntime());

                    List<String> genresNames = new ArrayList<>();
                    for (GenreResponse genreResponse : response.body().getGenres()) {
                        genresNames.add(genreResponse.getName());
                    }

                    movieDetail.setGenres(TextUtils.join(" | ", genresNames));

                    view.showDetails();
                    view.showMovieDetails(movieDetail);
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                view.hideDetails();
                view.hideLoading();
                view.showError();
            }
        });

    }
}
