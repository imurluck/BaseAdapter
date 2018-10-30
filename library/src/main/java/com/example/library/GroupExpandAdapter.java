package com.example.library;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GroupExpandAdapter extends BaseAdapter {

    private List<IGroupEntity> mGroupList;

    private HashMap<IGroupEntity, Integer> mGroupIndexMap;

    private HashMap<IGroupEntity, Integer> mGroupStateMap;

    private boolean mExpandAll;

    private static final int STATE_EXPAND = 1;
    private static final int STATE_CLLOAPSE = 0;

    private OnToogleListener mOnToogleListener;

    /**
     * 构造方法，初始化实体集序列
     */
    private GroupExpandAdapter() {
        super();
        mGroupList = new ArrayList<>();
        mGroupIndexMap = new HashMap<>();
        mGroupStateMap = new HashMap<>();
    }

    @NonNull
    @Override
    public BaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.ViewHolder holder, int position) {
        if (getItem(position) instanceof IGroupEntity) {
            mGroupIndexMap.put((IGroupEntity) getItem(position), position);
        }
        super.onBindViewHolder(holder, position);
    }


    public void addGroupList(List<? extends IGroupEntity> groupList) {
        mGroupList.addAll(groupList);
        if (mExpandAll) {
            for (int i = 0; i < groupList.size(); i++) {
                IGroupEntity groupEntity = groupList.get(i);
                add(groupEntity);
                if (groupEntity.getChildList() != null) {
                    add(groupEntity.getChildList());
                }
                mGroupStateMap.put(groupList.get(i), STATE_EXPAND);
            }
        } else {
            for (int i = 0; i < groupList.size(); i++) {
                mGroupStateMap.put(groupList.get(i), STATE_CLLOAPSE);
            }
            add(groupList);
        }
    }

    public void setOnToogleListener(@Nullable OnToogleListener listener) {
        this.mOnToogleListener = listener;
    }

    public void toogle(int startPosition, IGroupEntity groupEntity) {
        if (mGroupStateMap.get(groupEntity) == STATE_CLLOAPSE) {
            expand(startPosition, groupEntity);
        } else {
            clloapse(startPosition, groupEntity);
        }
    }

    public void expand(int startPosition, IGroupEntity groupEntity) {
        if (groupEntity.getChildList() == null) {
            return ;
        }
        add(startPosition + 1, groupEntity.getChildList());
        if (mOnToogleListener != null) {
            mOnToogleListener.onExpand(groupEntity);
        }
        mGroupStateMap.put(groupEntity, STATE_EXPAND);
    }

    public void clloapse(int startPosition, IGroupEntity groupEntity) {
        if (groupEntity.getChildList() == null) {
            return ;
        }
        remove(startPosition, groupEntity.getChildList());
        if (mOnToogleListener != null) {
            mOnToogleListener.onClloapse(groupEntity);
        }
        mGroupStateMap.put(groupEntity, STATE_CLLOAPSE);
    }

    private int getGroupIndex(IGroupEntity groupEntity) {
        return getDataList().indexOf(groupEntity);
    }

    public static class Builder {

        private GroupExpandAdapter mGroupExpandAdapter;

        private List<? extends IGroupEntity> mGroupList;

        public Builder() {
            mGroupExpandAdapter = new GroupExpandAdapter();
        }

        public Builder expandAll() {
            mGroupExpandAdapter.mExpandAll = true;
            return this;
        }

        public Builder groupList(List<? extends IGroupEntity> groupList) {
            mGroupList =  groupList;
            return this;
        }

        /**
         * 添加一个头部
         * @param headerView
         * @return
         */
        public Builder addHeader(View headerView) {
            mGroupExpandAdapter.mHeaderList.add(0, new HeaderEntity(headerView));
            mGroupExpandAdapter.mDataList.add(0, new HeaderEntity(headerView));
            return this;
        }

        /**
         * 添加头部集合
         * @param headerList
         * @return
         */
        public Builder addHeader(List<View> headerList) {
            for (int i = headerList.size() - 1; i >= 0; i--) {
                mGroupExpandAdapter.mHeaderList.add(0, new HeaderEntity(headerList.get(i)));
                mGroupExpandAdapter.mDataList.add(0, new HeaderEntity(headerList.get(i)));
            }
            return this;
        }

        /**
         * 设置是否开启自动加载更多
         * @param autoLoadMore
         * @return
         */
        public Builder autoLoadMore(boolean autoLoadMore) {
            mGroupExpandAdapter.autoLoadMore = autoLoadMore;
            return this;
        }

        /**
         * 添加尾部
         * @param view
         * @return
         */
        public Builder addRooter(View view) {
            mGroupExpandAdapter.mRooterList.add(new RooterEntity(view));
            mGroupExpandAdapter.mDataList.add(new RooterEntity(view));
            return this;
        }

        /**
         * 添加尾部集合
         * @param rooterList
         * @return
         */
        public Builder addRooter(List<View> rooterList) {
            for (int i = rooterList.size() - 1; i >= 0; i--) {
                mGroupExpandAdapter.mRooterList.add(new RooterEntity(rooterList.get(i)));
                mGroupExpandAdapter.mDataList.add(new RooterEntity(rooterList.get(i)));
            }
            return this;
        }

        public GroupExpandAdapter build() {
            if (mGroupList != null) {
                mGroupExpandAdapter.addGroupList(mGroupList);
            }
            return mGroupExpandAdapter;
        }
    }

    public interface OnToogleListener {

        void onExpand(IGroupEntity<? extends IEntity, ? extends IEntity> groupEntity);

        void onClloapse(IGroupEntity<? extends IEntity, ? extends IEntity> groupEntity);
    }
}
