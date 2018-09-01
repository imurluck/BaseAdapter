package com.example.zzx.baseadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.library.BaseViewHolder;

public class BtnViewHolder extends BaseViewHolder<BtnEntity> {


    public BtnViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_btn, null);
    }

    @Override
    protected void bind(final BtnEntity data) {
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
