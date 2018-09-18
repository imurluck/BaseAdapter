package com.example.zzx.baseadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.library.IEntity;

public class BtnEntity implements IEntity<BtnEntity> {

    String text;

    public BtnEntity(String content) {
        this.text = content;
    }

    @Override
    public View getLayoutView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_btn, null);
    }

    @Override
    public void bindView(View rootView, final BtnEntity data) {
        final Button btn = rootView.findViewById(R.id.btn);
        btn.setText(data.text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(btn.getContext(), data.text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
