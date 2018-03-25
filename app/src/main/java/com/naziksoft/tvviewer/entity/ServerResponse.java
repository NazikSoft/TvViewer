package com.naziksoft.tvviewer.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nazar on 25.03.18.
 */

public class ServerResponse {
    @SerializedName("items")
    @Expose
    private List<TvCanal> viewingCanals;
    @SerializedName("items_number")
    @Expose
    private int viewingSize;
    @SerializedName("total")
    @Expose
    private int totalSize;
    @SerializedName("offset")
    @Expose
    private int offset;
    @SerializedName("hasMore")
    @Expose
    private int hasMore;

    public ServerResponse(List<TvCanal> viewingCanals, int viewingSize, int totalSize, int offset, int hasMore) {
        this.viewingCanals = viewingCanals;
        this.viewingSize = viewingSize;
        this.totalSize = totalSize;
        this.offset = offset;
        this.hasMore = hasMore;
    }

    public List<TvCanal> getViewingCanals() {
        return viewingCanals;
    }

    public int getViewingSize() {
        return viewingSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public int getOffset() {
        return offset;
    }

    public int getHasMore() {
        return hasMore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerResponse that = (ServerResponse) o;

        if (viewingSize != that.viewingSize) return false;
        if (totalSize != that.totalSize) return false;
        if (offset != that.offset) return false;
        if (hasMore != that.hasMore) return false;
        return viewingCanals != null ? viewingCanals.equals(that.viewingCanals) : that.viewingCanals == null;
    }

    @Override
    public int hashCode() {
        int result = viewingCanals != null ? viewingCanals.hashCode() : 0;
        result = 31 * result + viewingSize;
        result = 31 * result + totalSize;
        result = 31 * result + offset;
        result = 31 * result + hasMore;
        return result;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "viewingCanals=" + viewingCanals +
                ", viewingSize=" + viewingSize +
                ", totalSize=" + totalSize +
                ", offset=" + offset +
                ", hasMore=" + hasMore +
                '}';
    }
}
