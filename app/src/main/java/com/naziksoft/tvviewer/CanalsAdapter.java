package com.naziksoft.tvviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.naziksoft.tvviewer.entity.TvCanal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nazar on 25.03.18.
 */

public class CanalsAdapter extends RecyclerView.Adapter<CanalsAdapter.CanalViewHolder> {

    private List<TvCanal> list = new ArrayList<>();

    public CanalsAdapter(List<TvCanal> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CanalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chanel, parent, false);
        return new CanalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanalViewHolder holder, int position) {
        TvCanal canal = list.get(position);
        Picasso.get().load(canal.getIconUrl()).placeholder(R.drawable.default_placeholder).into(holder.canalIcon);
        holder.canalName.setText(canal.getName());
    }

    public void updateData(List<TvCanal> newList) {
        if (newList == null) {
            list = new ArrayList<>();
        } else {
            list = newList;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public long getCanalId(int position) {
        if (list != null && position < list.size()) {
            return list.get(position).getId();
        } else {
            return -1;
        }
    }

    public void addDataToTop(List<TvCanal> canalList) {
        list.addAll(0, canalList);
        notifyItemRangeInserted(0, canalList.size());
    }

    public void addDataToBottom(List<TvCanal> canalList) {
        int position = list.size();
        list.addAll(position, canalList);
        notifyItemInserted(position);
    }

    static class CanalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.canal_icon)
        ImageView canalIcon;
        @BindView(R.id.canal_name)
        TextView canalName;

        CanalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
