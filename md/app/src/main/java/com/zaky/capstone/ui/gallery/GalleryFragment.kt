package com.zaky.capstone.ui.gallery

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.renderscript.Element
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.fitness.data.DataType
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ml.custom.*
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import com.google.gson.internal.bind.TypeAdapters.STRING
import com.zaky.capstone.R
import com.zaky.capstone.adapter.ChatAdapter
import com.zaky.capstone.data.Chat
import com.zaky.capstone.data.Home
import com.zaky.capstone.databinding.FragmentGalleryBinding
import com.zaky.capstone.helper.ViewModelFactory
import com.zaky.capstone.ml.Model
import org.tensorflow.lite.schema.TensorType.FLOAT32
import org.tensorflow.lite.schema.TensorType.STRING
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.xpath.XPathConstants.STRING
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var galleryViewModel: GalleryViewModel
    private var lastItem : Int? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val context = container?.context
        loadingMulai()

        val db = Firebase.firestore
        val layout = LinearLayoutManager(context)
        binding.rvChat.layoutManager = layout
        val activity = getActivity()
        galleryViewModel = activity?.let { obtainViewModel(it, binding.root) }!!
        galleryViewModel.chat.observe(viewLifecycleOwner) {
            Log.d("TAG", "showRv: $it")
            val userAdapter = ChatAdapter()
            userAdapter.setListNotes(it)
            binding.rvChat.adapter = userAdapter
        }
        galleryViewModel.lastItem.observe(viewLifecycleOwner) {
            lastItem = it
            loadingSelesai()
        }


        binding.send.setOnClickListener {
            val message = binding.message.text.toString()
//            val date = "20:00"
            val sdf = SimpleDateFormat("HH:mm")
            val currentDate = sdf.format(Date())
            val id = lastItem?.toInt()
            if(id == null) {
                Snackbar.make(it, "Maaf, terdapat kesalahan. Mungkin koneksi anda buruk", Snackbar.LENGTH_LONG).show()
            } else {
                val chat = Chat(id, "me", message, currentDate)
                galleryViewModel.tambahData(chat as Chat)


                val balas = galleryViewModel.tafsir(chat.message.toString().toLowerCase())
                val balasan = Chat(id+1, "you", balas, currentDate)
                galleryViewModel.tambahData(balasan as Chat)

                binding.message.text = null
            }


        }

        val conditions = CustomModelDownloadConditions.Builder()
            .requireWifi()
            .build()
        FirebaseModelDownloader.getInstance()
            .getModel("Capstone", DownloadType.LOCAL_MODEL, conditions)
            .addOnCompleteListener {
                // Download complete. Depending on your app, you could enable the ML
                // feature, or switch from the local model to the remote model, etc.
                val model = it

            }

       

//        val inputOutputOptions = FirebaseModelInputOutputOptions.Builder()
//            .setInputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 224, 224, 3))
//            .setOutputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 5))
//            .build()
//
//        val input = TensorBuffer.createFixedSize(intArrayOf(1, 4), org.tensorflow.lite.DataType.STRING)
//        input.loadBuffer(byteBuffer)
//
//        val inputs = FirebaseModelInputs.Builder()
//            .add(input) // add() as many input arrays as your model requires
//            .build()
//
//        val options = FirebaseModelInterpreterOptions.Builder(localModel).build()
//        val interpreter = FirebaseModelInterpreter.getInstance(options)
//        interpreter?.run(inputs, inputOutputOptions)
//            ?.addOnSuccessListener { result ->
//                // ...
//                val output = result.getOutput<Array<FloatArray>>(0)
//                val probabilities = output[0]
//
//                Log.d(TAG, "onCreateView: Ini dari MLnya Dila bree $output")
//            }
//            ?.addOnFailureListener { e ->
//                // Task failed with an exception
//                // ...
//            }








        return root
    }

    fun obtainViewModel(activity: FragmentActivity, view: View) : GalleryViewModel {
        val factory = ViewModelFactory.getInstance(view)
        return ViewModelProvider(activity, factory).get(GalleryViewModel::class.java)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        val TAG = "Chat Fragment"
    }

    private fun loadingMulai() {
        binding.progress.visibility = View.VISIBLE
    }
    private fun loadingSelesai() {
        binding.progress.visibility = View.GONE
    }
 }


