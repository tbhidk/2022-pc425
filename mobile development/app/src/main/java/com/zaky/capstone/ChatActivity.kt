package com.zaky.capstone

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaky.capstone.adapter.ChatAdapter
import com.zaky.capstone.data.Chat
import com.zaky.capstone.databinding.ActivityChatBinding
import com.zaky.capstone.helper.ViewModelFactory
import com.zaky.capstone.ui.gallery.GalleryViewModel

class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding
    private lateinit var adapter : ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvChat.layoutManager = LinearLayoutManager(this)





    }

}