package com.example.library;

import java.util.ArrayList;
import java.util.List;

/**
 * 由于需要保存伸缩状态，以及一些其他信息，故由接口改为抽象类
 * 由此造成Group实体类型受到限制，实属无奈
 * create by zzx
 * create at 18-10-26
 * {@code CT} child type
 */
public abstract class IGroupEntity<CT extends IChildEntity, D extends IEntity> implements IEntity<D> {

    int position;
    int state;
    private List<CT> childList;

    /**
     * {@link GroupExpandAdapter} 调用
     * @return
     */
    List<CT> getChildList() {
        if (childList == null) {
            childList = returnChildList();
        }
        return childList;
    }

    public void addChild(int index, CT child) {
        if (childList == null) {
            childList = new ArrayList<>();
            childList.add(0, child);
        } else {
            childList.add(index, child);
        }
    }

    public void addChild(CT child) {
        if (childList == null) {
            addChild(0, child);
        } else {
            addChild(childList.size(), child);
        }
    }

    /**
     * 自类实现
     * @return
     */
    protected abstract List<CT> returnChildList();

    protected abstract int returnChildSize();

    public int getChildSize() {
        return childList == null ? returnChildSize() : childList.size();
    }
}
