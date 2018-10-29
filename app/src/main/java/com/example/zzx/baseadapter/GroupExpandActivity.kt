package com.example.zzx.baseadapter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.SimpleItemAnimator
import android.widget.Button
import android.widget.Toast
import com.example.library.GroupExpandAdapter
import com.example.library.IEntity
import com.example.library.IGroupEntity
import kotlinx.android.synthetic.main.activity_group_expand.*

class GroupExpandActivity : AppCompatActivity() {

    lateinit var mGroupExpandAdapter : GroupExpandAdapter

    lateinit var mGroupList : MutableList<GroupEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_expand)

        setupGroupList()

        setupRecyclerView()
    }

    private fun setupGroupList() {
        mGroupList = mutableListOf()
        for (i in 1..10) {
            if (i == 5) {
                mGroupList.add(GroupEntity("group$i", null))
            }
            mGroupList.add(GroupEntity("group$i"))
        }
    }

    private fun setupRecyclerView() {
        var header = Button(this)
        header.text = "it's group header"
        header.setOnClickListener {
            Toast.makeText(this, "header click", Toast.LENGTH_SHORT).show()
        }
        var rooter = Button(this)
        rooter.text = "it's group rooter"
        rooter.setOnClickListener {
            Toast.makeText(this, "rooter click", Toast.LENGTH_SHORT).show()
        }
        mGroupExpandAdapter = GroupExpandAdapter.Builder()
                .addHeader(header)
                .addRooter(rooter)
                .groupList(mGroupList)
                .expandAll()
                .build()
        mGroupExpandAdapter.setOnToogleListener(object : GroupExpandAdapter.OnToogleListener {
            override fun onExpand(groupEntity: IGroupEntity<out IEntity<IEntity<*>>, out IEntity<IEntity<*>>>?) {
                (groupEntity as GroupEntity).animateExpand()
            }

            override fun onClloapse(groupEntity: IGroupEntity<out IEntity<IEntity<*>>, out IEntity<IEntity<*>>>?) {
                (groupEntity as GroupEntity).animateClloapse()
            }


        })
        expandRecycler.adapter = mGroupExpandAdapter
        expandRecycler.itemAnimator = DefaultItemAnimator()
        (expandRecycler.itemAnimator as SimpleItemAnimator).removeDuration = 0
    }
}
