package com.naziksoft.tvviewer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.naziksoft.tvviewer.entity.TvCanal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CanalsPresenter.PresenterFeedback {

    private static final int PERMISSIONS_REQUEST_PHONE_STATE = 100;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private CanalsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private CanalsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecyclerView();
        checkPermission();
    }

    @Override
    public void onShowList(List<TvCanal> canalList) {
        progressBar.setVisibility(View.GONE);
        adapter.updateData(canalList);
    }

    @Override
    public void onAddToTopList(List<TvCanal> canalList) {
        progressBar.setVisibility(View.GONE);
        adapter.addDataToTop(canalList);
    }

    @Override
    public void onAddToBottomList(List<TvCanal> canalList) {
        progressBar.setVisibility(View.GONE);
        adapter.addDataToBottom(canalList);
    }

    @Override
    public void onError(String displayMessage) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(recyclerView, displayMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    initPresenter();
                } else {
                    // permission denied
                    Snackbar.make(recyclerView, R.string.toast_permission_deny, Snackbar.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_PHONE_STATE);
        } else {
            // Permission has already been granted
            initPresenter();
        }
    }

    private void initPresenter() {
        presenter = new CanalsPresenter(this, this);
        // get default data from server
        progressBar.setVisibility(View.VISIBLE);
        presenter.getDefaultData();
    }

    private void initRecyclerView() {
        // set empty data to adapter before update
        adapter = new CanalsAdapter(new ArrayList<TvCanal>());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (adapter.getItemCount()== 0){
                    return;
                }
                if (layoutManager.findLastVisibleItemPosition() == adapter.getItemCount()-1) {
                    progressBar.setVisibility(View.VISIBLE);
                    // load next data from server
                    presenter.destinationEndList(adapter.getCanalId(adapter.getItemCount()-1));
                }
                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    progressBar.setVisibility(View.VISIBLE);
                    // load previous data from server
                    presenter.destinationTopList(adapter.getCanalId(0));
                }
            }
        });
    }
}
