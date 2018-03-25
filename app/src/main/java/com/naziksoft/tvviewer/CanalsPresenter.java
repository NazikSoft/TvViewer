package com.naziksoft.tvviewer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.naziksoft.tvviewer.entity.ServerRequest;
import com.naziksoft.tvviewer.entity.ServerResponse;
import com.naziksoft.tvviewer.entity.TvCanal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nazar on 25.03.18.
 */

public class CanalsPresenter {

    private static final String LOG = "TvViewerLog";

    private Context context;
    private PresenterFeedback feedback;
    private ServerConnection connection;
    private String serialNumber;
    private boolean canScrollTop = true;
    private boolean canScrollBottom = true;

    public interface PresenterFeedback {
        void onShowList(List<TvCanal> canalList);

        void onAddToTopList(List<TvCanal> canalList);

        void onAddToBottomList(List<TvCanal> canalList);

        void onError(String displayMessage);
    }

    public CanalsPresenter(Context context, PresenterFeedback feedback) {
        this.context = context;
        this.feedback = feedback;
        initConnection();
        initSerial();
    }

    public void getDefaultData() {
        ServerRequest request = new ServerRequest(serialNumber, 0, 0);
        getData(request);
    }

    public void destinationEndList(long lastItemId) {
        if (canScrollBottom) {
            ServerRequest request = new ServerRequest(serialNumber, lastItemId, 1);
            getData(request);
        } else {
            feedback.onError(context.getString(R.string.message_all_list_loaded));
        }
    }

    public void destinationTopList(long firstItemId) {
        if (canScrollTop) {
            ServerRequest request = new ServerRequest(serialNumber, firstItemId, -1);
            getData(request);
        } else {
            feedback.onError(context.getString(R.string.message_all_list_loaded));
        }
    }

    private void getData(final ServerRequest request) {
        if (!isDeviceOnline()) {
            Log.d(LOG, "no internet connection");
            feedback.onError(context.getString(R.string.internet_connection_error));
            return;
        }
        connection.getFromServer(request.getSerialNumber(), request.getBorderId(), request.getDirection())
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                        ServerResponse data = response.body();
                        if (data == null) {
                            Log.d(LOG, "empty request body");
                            return;
                        }
                        Log.d(LOG, "request OK, + offset=" + data.getOffset()
                                + ", hasMore=" + data.getHasMore()
                                + ", direction=" + request.getDirection());
                        switch (request.getDirection()) {
                            case -1:
                                feedback.onAddToTopList(data.getViewingCanals());
                                break;
                            case 0:
                                feedback.onShowList(data.getViewingCanals());
                                break;
                            case 1:
                                feedback.onAddToBottomList(data.getViewingCanals());
                                break;
                        }
                        updateScrollingMarkers(data);
                    }

                    @Override
                    public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                        Log.d(LOG, "request failure");
                        t.printStackTrace();
                        feedback.onError(context.getString(R.string.request_connection_error));
                    }
                });
    }

    private void updateScrollingMarkers(ServerResponse response) {
        canScrollBottom = response.getHasMore() > 0;
        canScrollTop = response.getOffset() > 0;
    }

    @SuppressLint("MissingPermission")
    private void initSerial() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            serialNumber = Build.getSerial();
        } else {
            serialNumber = Build.SERIAL;
        }
    }

    private void initConnection() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://oll.tv")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        connection = retrofit.create(ServerConnection.class);
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            return false;
        }
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
