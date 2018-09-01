package com.example.zzx.baseadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btn.setText(mDataList.get(position));
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