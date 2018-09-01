package com.example.zzx.baseadapter;

import android.media.tv.TvView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.library.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecycler;

    private List<Object> mDataList;

    private List<String> mSingleDataList;

    private BaseAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupData();

        setupSingleData();

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        Button headerBtn = new Button(this);
        headerBtn.setText("Header");
        headerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Header Button", Toast.LENGTH_SHORT).show();
            }
        });
        Button rooterBtn = new Button(this);
        rooterBtn.setText("Rooter");
        rooterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Rooter Button", Toast.LENGTH_SHORT).show();
            }
        });
        mRecycler = findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mAdapter = new BaseAdapter.Builder()
                .register(BtnEntity.class, BtnViewHolder.class)
                .register(TvEntity.class, TvViewHolder.class)
                .setDataList(mDataList)
                .addHeader(headerBtn)
                .addRooter(rooterBtn)
                .build();
        Log.e(TAG, "setupRecyclerView: " + System.currentTimeMillis());
        mRecycler.setAdapter(mAdapter);
        mRecycler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: " + System.currentTimeMillis());
                //mRecycler.setAdapter(new SingleTypeAdapter(mSingleDataList));
                Log.e(TAG, "run: " + System.currentTimeMillis());
            }
        });
    }

    private void setupData() {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        for (int i = 0; i < 10000; i++) {
            if (i % 3 == 0) {
                mDataList.add(new BtnEntity("button " + i));
            } else {
                mDataList.add(new TvEntity("text view " + i));
            }
        }
    }

    private void setupSingleData() {
        if (mSingleDataList == null) {
            mSingleDataList = new ArrayList<>();
        }
        for (int i = 0; i < 30; i++) {
            mSingleDataList.add("button " + i);
        }
    }
}
