package com.example.zzx.baseadapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.library.BaseAdapter;
import com.example.library.IEntity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecycler;

    private List<IEntity> mDataList;

    private List<String> mSingleDataList;

    private BaseAdapter mAdapter;

    private SingleTypeAdapter mSingleAdapter;


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
        headerBtn.setText("go to GroupExpandActivity");
        headerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GroupExpandActivity.class));
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
//        mRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        mRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        Log.e(TAG, "setupRecyclerView: " + System.currentTimeMillis());
        mAdapter = new BaseAdapter.Builder()
                .setDataList(mDataList)
                .addHeader(headerBtn)
                .addRooter(rooterBtn)
                .autoLoadMore(true)
                .build();
        mRecycler.setAdapter(mAdapter);
        mRecycler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: " + System.currentTimeMillis());
//                mRecycler.setAdapter(new SingleTypeAdapter(mSingleDataList));
                mRecycler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "run: " + System.currentTimeMillis());

                    }
                });
            }
        });
        //自动加载更多
        mAdapter.setOnLoadMoreListener(new BaseAdapter.OnLoadMoreListener() {
            @Override
            public void loadMore(final BaseAdapter baseAdapter) {
                Log.e(TAG, "loadMore: ");
                /**
                 * 这里请用{@link RecyclerView#post(Runnable)}，因为RecyclerView视图还在刷新过程中时
                 * 不允许添加新的Item
                 */
                mRecycler.post(new Runnable() {
                    @Override
                    public void run() {

//                        mAdapter.add(createDataList());
                    }
                });
            }
        });
    }

    private void setupData() {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }

        for (int i = 0; i < 30; i++) {
            if (i % 3 == 0) {
                mDataList.add(new BtnEntity("button " + i));
            } else if (i % 5 == 0) {
                mDataList.add(new TvEntity("text view " + i));
            } else {
                mDataList.add(new ImgEntity());
            }
        }
    }

    private void setupSingleData() {
        if (mSingleDataList == null) {
            mSingleDataList = new ArrayList<>();
        }
        for (int i = 0; i < 8; i++) {
            mSingleDataList.add("button " + i);
        }
    }

    private List<IEntity> createDataList() {
        List<IEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                list.add(new BtnEntity("button " + i));
            } else if (i % 5 == 0) {
                list.add(new TvEntity("text view " + i));
            } else {
                list.add(new ImgEntity());
            }
        }
        return list;
    }
}
