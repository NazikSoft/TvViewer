package com.naziksoft.tvviewer.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nazar on 25.03.18.
 */

public class TvCanal {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private  String name;
    @SerializedName("icon")
    @Expose
    private String iconUrl;

    public TvCanal(long id, String name, String iconUrl) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TvCanal tvCanal = (TvCanal) o;

        if (id != tvCanal.id) return false;
        if (name != null ? !name.equals(tvCanal.name) : tvCanal.name != null) return false;
        return iconUrl != null ? iconUrl.equals(tvCanal.iconUrl) : tvCanal.iconUrl == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (iconUrl != null ? iconUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TvCanal{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
