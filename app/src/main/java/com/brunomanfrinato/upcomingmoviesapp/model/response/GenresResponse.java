package com.brunomanfrinato.upcomingmoviesapp.model.response;

import java.util.List;

public class GenresResponse {
    private List<GenreResponse> genres;

    public List<GenreResponse> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreResponse> genres) {
        this.genres = genres;
    }
}
