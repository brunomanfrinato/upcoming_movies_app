package com.brunomanfrinato.upcomingmoviesapp.movies;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.brunomanfrinato.upcomingmoviesapp.base.BaseActivity;
import com.brunomanfrinato.upcomingmoviesapp.R;
import com.brunomanfrinato.upcomingmoviesapp.details.DetailsActivity;
import com.brunomanfrinato.upcomingmoviesapp.model.Movie;
import com.brunomanfrinato.upcomingmoviesapp.util.Constants;
import com.brunomanfrinato.upcomingmoviesapp.util.CustomToast;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MoviesActivity extends BaseActivity implements MoviesView {

    @BindView(R.id.layout_error)
    LinearLayout layoutError;

    @BindView(R.id.swipe_movies)
    SwipeRefreshLayout swipeMovies;

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    @BindView(R.id.spinkit_loading)
    SpinKitView skvLoadingMore;

    private MoviesAdapter moviesAdapter;
    private MoviesPresenter presenter;
    private LinearLayoutManager linearLayoutManager;
    private List<Movie> movies = new ArrayList<>();

    private boolean isScrolling = false;
    private int currentMovies, totalMovies, scrollOutMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();

        presenter = new MoviesPresenter(this, this);
        presenter.loadUpcomingMovies();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_movies;
    }

    @OnClick(R.id.btn_refresh_load_movies)
    public void onReloadClick() {
        presenter.loadUpcomingMovies();
    }

    @Override
    public void showLoading() {
        swipeMovies.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeMovies.setRefreshing(false);
    }

    @Override
    public void showLoadingMore() {
        skvLoadingMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingMore() {
        skvLoadingMore.setVisibility(View.GONE);
    }

    @Override
    public void showTotalMovies(int totalMovies) {
        Resources resources = getResources();
        setUpToolbarSubtitle(resources.getQuantityString(R.plurals.subtitle_total_movies, totalMovies, totalMovies));
    }

    @Override
    public void loadMovies(List<Movie> movies) {
        moviesAdapter.addMovies(movies);
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearMovies() {
        moviesAdapter.clearMovies();
    }

    @Override
    public void showErrorLayout() {
        layoutError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorLayout() {
        layoutError.setVisibility(View.GONE);
    }

    @Override
    public void showError(int stringId) {
        new CustomToast().showToast(this, getString(stringId) );
    }

    @Override
    public void navitageToDetail(int movieId) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constants.MOVIE_ID_EXTRA, movieId);
        startActivity(intent);
        overridePendingTransition(R.anim.right_enter, R.anim.left_out);
    }

    public void initViews() {
        swipeMovies.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark);

        swipeMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadUpcomingMovies();
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        moviesAdapter = new MoviesAdapter(this, movies, this);

        rvMovies.setLayoutManager(linearLayoutManager);
        rvMovies.setAdapter(moviesAdapter);
        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentMovies = linearLayoutManager.getChildCount();
                totalMovies = linearLayoutManager.getItemCount();
                scrollOutMovies = linearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currentMovies + scrollOutMovies == totalMovies)) {
                    isScrolling = false;
                    presenter.loadMore();
                }
            }
        });
    }
}
