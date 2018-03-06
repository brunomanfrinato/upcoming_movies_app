package com.brunomanfrinato.upcomingmoviesapp.api;

import com.brunomanfrinato.upcomingmoviesapp.model.response.GenresResponse;
import com.brunomanfrinato.upcomingmoviesapp.model.response.MovieDetailResponse;
import com.brunomanfrinato.upcomingmoviesapp.model.response.UpcomingMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoint {
    @GET("movie/upcoming")
    Call<UpcomingMovieResponse> getUpcomingMovies(@Query("page") int page);

    @GET("movie/{id}")
    Call<MovieDetailResponse> getMovieDetail(@Path("id") int movieId);

    @GET("genre/movie/list")
    Call<GenresResponse> getGenres();
}
