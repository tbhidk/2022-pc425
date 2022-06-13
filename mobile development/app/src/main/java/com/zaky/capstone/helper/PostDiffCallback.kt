package com.zaky.capstone.helper

import androidx.recyclerview.widget.DiffUtil
import com.zaky.capstone.data.Chat

class PostDiffCallback(private val mOldPostist : List<Chat>, private val mNewPostList : List<Chat>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldPostist.size
    }

    override fun getNewListSize(): Int {
        return mNewPostList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldPostist[oldItemPosition].id == mNewPostList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldPostist[oldItemPosition]
        val newEmployee = mNewPostList[newItemPosition]
        return oldEmployee.id == newEmployee.id
    }

}