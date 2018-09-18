package com.example.zzx.baseadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.library.IEntity;

public class ImgEntity implements IEntity<ImgEntity> {
    @Override
    public View getLayoutView(LayoutInflater inflater) {
        ImageView imgView = new ImageView(inflater.getContext());
        imgView.setImageResource(R.drawable.ic_launcher_foreground);
        return imgView;
    }

    @Override
    public void bindView(View rootView, ImgEntity data) {

    }
}
