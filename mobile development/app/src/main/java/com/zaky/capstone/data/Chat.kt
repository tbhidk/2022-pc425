package com.zaky.capstone.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Chat(
    var id : Int,
    var user : String,
    var message : String,
    var date : String
) : Parcelable
