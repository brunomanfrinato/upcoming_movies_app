package com.brunomanfrinato.upcomingmoviesapp.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingMovieResponse {

    private Integer page;

    @SerializedName("total_pages")
    private Integer totalPages;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("results")
    private List<MovieResponse> movieResponses;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<MovieResponse> getMovies() {
        return movieResponses;
    }

    public void setMovies(List<MovieResponse> movieResponses) {
        this.movieResponses = movieResponses;
    }
}
