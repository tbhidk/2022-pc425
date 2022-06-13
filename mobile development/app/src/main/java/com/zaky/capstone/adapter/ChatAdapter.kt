package com.zaky.capstone.adapter

import android.text.Layout
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zaky.capstone.R
import com.zaky.capstone.data.Chat
import com.zaky.capstone.databinding.ItemRowChatBinding
import com.zaky.capstone.helper.PostDiffCallback

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ListViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ListViewHolder {
        val binding = ItemRowChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ListViewHolder, position: Int) {
        holder.bind(listChats[position])
    }

    private val listChats = ArrayList<Chat>()
    fun setListNotes(listChat: List<Chat>) {
        val diffCallback = PostDiffCallback(this.listChats, listChat)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listChats.clear()

        this.listChats.addAll(listChat)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = listChats.size

    inner class ListViewHolder(var binding: ItemRowChatBinding, var parent: ViewGroup) : RecyclerView.ViewHolder(binding.root){
        fun bind(chat: Chat) {
            if (chat.user == "you") {
                binding.wadah.gravity = Gravity.START
                binding.message.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                binding.date.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                binding.content.setBackgroundDrawable(ContextCompat.getDrawable(parent.context, R.drawable.shadow_chat_you))
                binding.content.setPadding(50, 50, 50, 50)
            }

            binding.message.text = chat.message
            binding.date.text = chat.date.toString()
//
//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, Detail::class.java)
//                intent.putExtra(Detail.EXTRA_PERSON, post)
//
//                val optionsCompat: ActivityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        itemView.context as Activity,
//                        androidx.core.util.Pair(binding.photo, "photo"),
//                        androidx.core.util.Pair(binding.name, "name"),
//                        androidx.core.util.Pair(binding.desc, "desc")
//                    )
//                itemView.context.startActivity(intent, optionsCompat.toBundle())
//            }
        }
    }
}












//class PostAdapter(private val listPost: ArrayList<D>) : RecyclerView.Adapter<PostAdapter.ListViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ListViewHolder {
//        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ListViewHolder(binding)
//    }
//
//
//    override fun onBindViewHolder(holder: PostAdapter.ListViewHolder, position: Int) {
//        holder.bind(listPost[position])
//    }
//
//    override fun getItemCount(): Int = listPost.size
//
//    class ListViewHolder(var binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(post: Data) {
//            Glide.with(itemView.context)
//                .load(post.photo)
//                .into(binding.photo)
//            binding.name.text = post.name
//            binding.desc.text = post.desc
//
//
//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, Detail::class.java)
//                intent.putExtra(Detail.EXTRA_PERSON, post)
//
//                val optionsCompat: ActivityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        itemView.context as Activity,
//                        androidx.core.util.Pair(binding.photo, "photo"),
//                        androidx.core.util.Pair(binding.name, "name"),
//                        androidx.core.util.Pair(binding.desc, "desc")
//                    )
//                itemView.context.startActivity(intent, optionsCompat.toBundle())
//            }
//        }
//    }
//}