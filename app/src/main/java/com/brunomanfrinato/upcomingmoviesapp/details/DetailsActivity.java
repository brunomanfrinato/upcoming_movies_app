package com.brunomanfrinato.upcomingmoviesapp.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.brunomanfrinato.upcomingmoviesapp.R;
import com.brunomanfrinato.upcomingmoviesapp.base.BaseActivity;
import com.brunomanfrinato.upcomingmoviesapp.model.MovieDetail;
import com.brunomanfrinato.upcomingmoviesapp.movies.MoviesActivity;
import com.brunomanfrinato.upcomingmoviesapp.util.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.ybq.android.spinkit.SpinKitView;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailsActivity extends BaseActivity implements DetailsView {

    @BindView(R.id.layout_details)
    ScrollView layoutDetails;

    @BindView(R.id.layout_loading)
    LinearLayout layoutLoading;

    @BindView(R.id.layout_error)
    LinearLayout layoutError;

    @BindView(R.id.spin_loading_backdrop)
    SpinKitView loadingBackdrop;

    @BindView(R.id.image_detail_backdrop)
    ImageView imageBackdrop;

    @BindView(R.id.image_detail_poster)
    ImageView imagePosterThumb;

    @BindView(R.id.text_detail_title)
    TextView textTitle;

    @BindView(R.id.text_detail_release_date)
    TextView textReleaseDate;

    @BindView(R.id.text_detail_runtime)
    TextView textRuntime;

    @BindView(R.id.text_detail_genres)
    TextView textGenres;

    @BindView(R.id.text_detail_overview)
    TextView textOverview;

    private DetailsPresenter presenter;
    private int movieId;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        movieId = getIntent().getIntExtra(Constants.MOVIE_ID_EXTRA, 0);

        presenter = new DetailsPresenter(this, this);
        presenter.getMovieDetail(movieId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        navigateToMovies();
        return true;
    }

    @Override
    public void onBackPressed() {
        navigateToMovies();
    }

    @OnClick(R.id.btn_refresh_movie_detail)
    public void onReloadMovieDetailClick() {
        presenter.getMovieDetail(movieId);
    }

    @Override
    public void showLoading() {
        layoutLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        layoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        layoutError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        layoutError.setVisibility(View.GONE);
    }

    @Override
    public void showDetails() {
        layoutDetails.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDetails() {
        layoutDetails.setVisibility(View.GONE);
    }

    @Override
    public void showMovieDetails(MovieDetail movieDetail) {
        textTitle.setText(movieDetail.getTitle());
        textReleaseDate.setText(movieDetail.getReleaseDate());
        textRuntime.setText(getString(R.string.runtime_detail, movieDetail.getRuntime()));
        textGenres.setText(movieDetail.getGenres());
        textOverview.setText(movieDetail.getOverview());

        loadingBackdrop.setVisibility(View.VISIBLE);
        Glide.with(this).load(Constants.IMAGE_URL + movieDetail.getBackdropPath()).crossFade().centerCrop().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                loadingBackdrop.setVisibility(View.GONE);
                return false;
            }
        }).into(imageBackdrop);

        Glide.with(this).load(Constants.IMAGE_URL + movieDetail.getPosterPath()).crossFade().centerCrop().into(imagePosterThumb);
    }

    private void navigateToMovies() {
        Intent intent = new Intent(this, MoviesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.left_enter, R.anim.right_out);
    }
}


