package com.example.zzx.baseadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;
/**
 * 普通官方版的写法，用于对比BaseAdapter的生成速度
 * create by zzx
 * create at 18-10-6
 */
public class SingleTypeAdapter extends RecyclerView.Adapter<SingleTypeAdapter.ViewHolder> {

    private List<String> mDataList;

    public SingleTypeAdapter(List<String> dataList) {
        this.mDataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new Button(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.btn.setText(mDataList.get(position));
        holder.btn.setOnClickListener((view) -> {
            mDataList.remove(position);
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            btn = (Button) itemView;
        }
    }

}
