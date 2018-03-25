package com.naziksoft.tvviewer;

import com.naziksoft.tvviewer.entity.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nazar on 25.03.18.
 */

public interface ServerConnection {
    @GET("/demo")
    Call<ServerResponse> getFromServer(@Query("serial_number") String serialNumber,
                                       @Query("borderId") long borderId,
                                       @Query("direction") int direction);
}
