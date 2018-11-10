package com.example.zzx.baseadapter

import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.example.library.BaseAdapter
import com.example.library.GroupExpandAdapter
import com.example.library.IEntity
import com.example.library.IGroupEntity

class GroupEntity : IGroupEntity<ChildEntity, GroupEntity> {



    private var mGroupName : String

    private var mChildList : MutableList<ChildEntity>?

    private lateinit var mArrowImg : ImageView

    constructor(name : String) {
        mGroupName = name
        mChildList = setupChildList()
    }
    constructor(name : String, childList : MutableList<ChildEntity>?) {
        mChildList = childList
        mGroupName = name
    }


    private fun setupChildList(): MutableList<ChildEntity> {
        var childList : MutableList<ChildEntity> = mutableListOf()
        for (i in 1..5) {
            childList.add(ChildEntity("$mGroupName child $i"))
        }
        return childList
    }

    override fun returnChildSize(): Int {
        return if (mChildList == null) 0 else mChildList!!.size
    }

    override fun getLayoutId(): Int {
        return R.layout.item_group
    }

    override fun bindView(baseAdapter: BaseAdapter, holder: BaseAdapter.ViewHolder, data: GroupEntity, position: Int) {
        mArrowImg = holder.rootView.findViewById(R.id.group_arrow)
        holder.rootView.findViewById<TextView>(R.id.group_tv).text = mGroupName
        holder.rootView.setOnClickListener {
            (baseAdapter as GroupExpandAdapter).toogle(holder.adapterPosition, this)
        }
    }


    override fun returnChildList() = mChildList

    fun animateExpand() {
        var rotateAnimation = RotateAnimation(360f, 180f, RELATIVE_TO_SELF,
                0.5f, RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = 300
        rotateAnimation.fillAfter = true
        mArrowImg.animation = rotateAnimation
    }

    fun animateClloapse() {
        var rotateAnimation = RotateAnimation(180f, 360f, RELATIVE_TO_SELF,
                0.5f, RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = 300
        rotateAnimation.fillAfter = true
        mArrowImg.animation = rotateAnimation
    }

}
