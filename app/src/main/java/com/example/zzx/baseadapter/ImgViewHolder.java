package com.example.zzx.baseadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.library.BaseViewHolder;

public class ImgViewHolder extends BaseViewHolder<ImgEntity> {


    public ImgViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater) {
        ImageView imgView = new ImageView(inflater.getContext());
        imgView.setImageResource(R.drawable.ic_launcher_foreground);
        return imgView;
    }

    @Override
    protected void bind(ImgEntity data) {

    }
}
