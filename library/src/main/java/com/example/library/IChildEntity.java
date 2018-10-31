package com.example.library;
/**
 * 由于需要保存Group实体，以及一些其他信息，故由接口改为抽象类
 * 由此造成Child实体类型受到限制，实属无奈
 * create by zzx
 * create at 18-10-31
 */
public abstract class IChildEntity<D extends IChildEntity> implements IEntity<D> {

    IGroupEntity groupEntity;
}
