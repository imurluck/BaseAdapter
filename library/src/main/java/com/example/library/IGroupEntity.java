package com.example.library;

import java.util.List;

/**
 *
 * create by zzx
 * create at 18-10-26
 * {@code CT} child type
 */
public interface IGroupEntity<CT extends IEntity, D extends IEntity> extends IEntity<D> {

    List<CT> getChildList();

    int getChildSize();
}
