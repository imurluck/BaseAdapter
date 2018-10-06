package com.example.zzx.baseadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.library.BaseAdapter;
import com.example.library.IEntity;

public class ImgEntity implements IEntity<ImgEntity> {

    @Override
    public int getLayoutId() {
        return R.layout.item_img;
    }

    @Override
    public void bindView(final BaseAdapter baseAdapter, final BaseAdapter.ViewHolder holder,
                         ImgEntity data, int position) {
        ((ImageView) holder.getRootView().findViewById(R.id.img)).setImageResource(R.drawable.ic_launcher_foreground);
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                baseAdapter.add(new ImgEntity());
                baseAdapter.update(holder.getAdapterPosition(), new TvEntity("Tv Update"));
            }
        });
    }
}
