package com.example.zzx.baseadapter

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.library.BaseAdapter
import com.example.library.IChildEntity

class ChildEntity(name : String) : IChildEntity<ChildEntity>() {

    private val mChildNam : String = name

    override fun getLayoutId(): Int {
        return R.layout.item_child
    }

    override fun bindView(baseAdapter: BaseAdapter, holder: BaseAdapter.ViewHolder, data: ChildEntity, position: Int) {
        var childTv = holder.itemView.findViewById<TextView>(R.id.child_tv)
        childTv.text = mChildNam
        childTv.setOnClickListener{
            Toast.makeText(childTv.context, "tv$mChildNam", Toast.LENGTH_SHORT).show()
        }
        var childBtn = holder.itemView.findViewById<Button>(R.id.child_btn)
        childBtn.text = mChildNam
        childBtn.setOnClickListener {
            Toast.makeText(childBtn.context, "btn$mChildNam", Toast.LENGTH_SHORT).show()
        }
    }
}