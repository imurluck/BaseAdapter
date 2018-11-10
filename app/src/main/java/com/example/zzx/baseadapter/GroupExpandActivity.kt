package com.example.zzx.baseadapter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.SimpleItemAnimator
import android.widget.Button
import android.widget.Toast
import com.example.library.GroupExpandAdapter
import com.example.library.IChildEntity
import com.example.library.IEntity
import com.example.library.IGroupEntity
import kotlinx.android.synthetic.main.activity_group_expand.*

class GroupExpandActivity : AppCompatActivity() {

    lateinit var mGroupExpandAdapter : GroupExpandAdapter

    lateinit var mGroupList : MutableList<GroupEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_expand)

        mGroupList = setupGroupList()

        setupRecyclerView()
    }

    private fun setupGroupList() : MutableList<GroupEntity> {
        var groupList  = mutableListOf<GroupEntity>()
        for (i in 1..10) {
            if (i == 5) {
                groupList.add(GroupEntity("group$i", null))
            }
            groupList.add(GroupEntity("group$i"))
        }
        return groupList
    }

    private fun setupRecyclerView() {
        var header = Button(this)
        header.text = "add 0 group(header)"
        header.setOnClickListener {
            Toast.makeText(this, "header click", Toast.LENGTH_SHORT).show()
            mGroupExpandAdapter.addGroup(0, GroupEntity("add 0 group"))
        }
        var header1 = Button(this)
        header1.text = "add 0 group child"
        header1.setOnClickListener {
            mGroupExpandAdapter.addChild(0, ChildEntity("group0 child add"), mGroupExpandAdapter.getGroup(0))
        }
        var rooter = Button(this)
        rooter.text = "it's group rooter"
        rooter.setOnClickListener {
            Toast.makeText(this, "rooter click", Toast.LENGTH_SHORT).show()
            mGroupExpandAdapter.addGroup(GroupEntity("group add"))
        }
        mGroupExpandAdapter = GroupExpandAdapter.Builder()
                .addHeader(header)
                .addHeader(header1)
                .addRooter(rooter)
                .groupList(mGroupList)
                .expandAll()
                .build()
        mGroupExpandAdapter.setOnToogleListener(object : GroupExpandAdapter.OnToogleListener {
            override fun onExpand(groupEntity: IGroupEntity<out IChildEntity<IChildEntity<*>>, out IEntity<IEntity<*>>>?) {
                (groupEntity as GroupEntity).animateExpand()
            }

            override fun onClloapse(groupEntity: IGroupEntity<out IChildEntity<IChildEntity<*>>, out IEntity<IEntity<*>>>?) {
                (groupEntity as GroupEntity).animateClloapse()
            }


        })
        expandRecycler.adapter = mGroupExpandAdapter
        expandRecycler.itemAnimator = DefaultItemAnimator()
        (expandRecycler.itemAnimator as SimpleItemAnimator).removeDuration = 0
    }
}
