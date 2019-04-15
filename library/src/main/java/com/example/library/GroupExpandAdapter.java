package com.example.library;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GroupExpandAdapter extends BaseAdapter {

    private List<IGroupEntity> mGroupList;

    /**
     * 保存Group实体在列表中的位置，此处位置包含{@link #mHeaderList} 和 {@link #mRooterList}
     * 暂时保存，还未有用处
     */

    private boolean expandAll;

    private static final int STATE_EXPAND = 1;
    private static final int STATE_CLLOAPSE = 0;

    private OnToogleListener mOnToogleListener;

    private IGroupEntity mCurrentBindGroup;

    /**
     * 构造方法，初始化实体集序列
     */
    private GroupExpandAdapter() {
        super();
        mGroupList = new ArrayList<>();
    }

    @NonNull
    @Override
    public BaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (isInEmptyState) {
            return ;
        }
        IEntity currentEntity = getItem(position);
        if (currentEntity instanceof IGroupEntity) {
            ((IGroupEntity) currentEntity).position = position;
            mCurrentBindGroup = (IGroupEntity) currentEntity;
        }
        //在Child中保存Group实体
        if (currentEntity instanceof IChildEntity) {
            if (((IChildEntity) currentEntity).groupEntity == null) {
                ((IChildEntity) currentEntity).groupEntity = mCurrentBindGroup;
            }
        }
    }

    public IGroupEntity getGroup(int index) {
        return mGroupList.get(index);
    }

    public void addGroup(int index, IGroupEntity groupEntity) {
        mGroupList.add(index, groupEntity);
        int realIndex = getRealIndex(index);
        if (expandAll) {
            if (groupEntity.getChildList() != null) {
                ArrayList<IEntity> tmpList = new ArrayList<>();
                tmpList.add(groupEntity);
                tmpList.addAll(groupEntity.getChildList());
                groupEntity.state = STATE_EXPAND;
                add(realIndex, tmpList);
                return ;
            }
            add(realIndex, groupEntity);
            groupEntity.state = STATE_EXPAND;
            return ;
        }
        add(realIndex, groupEntity);
        groupEntity.state = STATE_CLLOAPSE;
    }

    public void addGroup(IGroupEntity groupEntity) {
        addGroup(mGroupList.size(), groupEntity);
    }

    /**
     * 添加child 实体
     * @param index 这里的index 指在{@link IGroupEntity#getChildList()} 中的位置
     * @param childEntity
     * @param groupEntity
     */
    public void addChild(int index, IChildEntity childEntity, IGroupEntity groupEntity) {
        childEntity.groupEntity = groupEntity;
        groupEntity.addChild(index, childEntity);
        int position = groupEntity.position - mHeaderList.size() + index + 1;
        if (groupEntity.state == STATE_EXPAND) {
            add(position, childEntity);
        }
    }

    public void addChild(IChildEntity childEntity, IGroupEntity groupEntity) {
        addChild(groupEntity.getChildSize(), childEntity, groupEntity);
    }

    public IGroupEntity getLastGroup() {
        if (mGroupList.size() == 0) {
            return null;
        }
        return mGroupList.get(mGroupList.size() - 1);
    }

    /**
     * 找到group在{@link #mDataList} 中 除去 {@link #mHeaderList} 和 {@link #mRooterList} 的位置
     * @param index group 在 {@link #mGroupList} 中的位置
     * @return
     */
    private int getRealIndex(int index) {
        int result = 0;
        for (int i = 0; i < index; i++) {
            IGroupEntity tmp = mGroupList.get(i);
            if (tmp.state == STATE_EXPAND) {
                result += tmp.getChildSize();
            }
            result++;
        }
        return result;
    }

    public void addGroupList(List<? extends IGroupEntity> groupList) {
        mGroupList.addAll(groupList);
        if (expandAll) {
            List<IEntity> tmpList = new ArrayList<>();
            for (int i = 0; i < groupList.size(); i++) {
                IGroupEntity groupEntity = groupList.get(i);
                tmpList.add(groupEntity);
                if (groupEntity.getChildList() != null) {
                    tmpList.addAll(groupEntity.getChildList());
                }
                groupEntity.state = STATE_EXPAND;
            }
            add(tmpList);
        } else {
            for (int i = 0; i < groupList.size(); i++) {
                groupList.get(i).state = STATE_CLLOAPSE;
            }
            add(groupList);
        }
    }

    public int getGroupSize() {
        return mGroupList.size();
    }

    public List<IGroupEntity> getGroupList() {
        return mGroupList;
    }

    public void clearGroup() {
        mGroupList.clear();
        clearData();
    }

    public void setOnToogleListener(@Nullable OnToogleListener listener) {
        this.mOnToogleListener = listener;
    }

    public void toogle(int startPosition, IGroupEntity groupEntity) {
        if (groupEntity.state == STATE_CLLOAPSE) {
            expand(startPosition, groupEntity);
        } else {
            clloapse(startPosition, groupEntity);
        }
    }

    public void expand(int startPosition, IGroupEntity groupEntity) {
        if (groupEntity.getChildList() == null) {
            return ;
        }
        add(startPosition + 1 - mHeaderList.size(), groupEntity.getChildList());
        if (mOnToogleListener != null) {
            mOnToogleListener.onExpand(groupEntity);
        }
        groupEntity.state = STATE_EXPAND;
    }

    public void clloapse(int startPosition, IGroupEntity groupEntity) {
        if (groupEntity.getChildList() == null) {
            return ;
        }
        remove(startPosition, groupEntity.getChildList());
        if (mOnToogleListener != null) {
            mOnToogleListener.onClloapse(groupEntity);
        }
        groupEntity.state = STATE_CLLOAPSE;
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
            mGroupExpandAdapter.expandAll = true;
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

        public Builder emptyView(View emptyView) {
            mGroupExpandAdapter.mEmptyEntity = new EmptyEntity(emptyView);
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

        void onExpand(IGroupEntity<? extends IChildEntity, ? extends IEntity> groupEntity);

        void onClloapse(IGroupEntity<? extends IChildEntity, ? extends IEntity> groupEntity);
    }
}
