package com.zaky.capstone.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.zaky.capstone.R
import com.zaky.capstone.data.Response
import com.zaky.capstone.databinding.FragmentHomeBinding
import com.zaky.capstone.helper.NetworkConfig
import com.zaky.capstone.helper.ViewModelFactory
import com.zaky.capstone.ui.gallery.GalleryFragment
import retrofit2.Call
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private var terkunci : Boolean = false
    private var kunci : Boolean = false
    private var hujan : Boolean = false
    private var jemur : Boolean = false
    private var gasAman : Boolean = false
    private var sekring : Boolean = false
    private var lampu : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sdf = SimpleDateFormat("HH")
        val hours = sdf.format(Date())
        if (hours.toString().toInt() < 3) {
            binding.sapa.text = "Selamat Malam"
            binding.iconSapa.setImageResource(R.drawable.malam)
        } else if (hours.toString().toInt() < 10) {
            binding.sapa.text = "Selamat Pagi"
            binding.iconSapa.setImageResource(R.drawable.morning)
        } else if (hours.toString().toInt() < 15) {
            binding.sapa.text = "Selamat Siang"
            binding.iconSapa.setImageResource(R.drawable.siang)
        } else if (hours.toString().toInt() < 19) {
            binding.sapa.text = "Selamat Sore"
            binding.iconSapa.setImageResource(R.drawable.sore)
        } else {
            binding.sapa.text = "Selamat Malam"
            binding.iconSapa.setImageResource(R.drawable.malam)
        }







        binding.pintasanKunci.setOnClickListener {
            if (kunci) {
                homeViewModel.kunci(false)
                binding.pintasanKunci.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_off) }
            } else {
                homeViewModel.kunci(true)
                binding.pintasanKunci.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_on) }
            }
        }

        binding.pintasanLampu.setOnClickListener {
            if (lampu) {
                homeViewModel.lampu(false)
                binding.pintasanLampu.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_off) }
            } else {
                homeViewModel.lampu(true)
                binding.pintasanLampu.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_on) }
            }
        }

        binding.pintasanJemuran.setOnClickListener {
            if (jemur) {
                homeViewModel.jemur(false)
                binding.pintasanJemuran.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_off) }
            } else {
                homeViewModel.jemur(true)
                binding.pintasanJemuran.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_on) }
            }
        }

        binding.btnMessage.setOnClickListener {
            val mFragmentManager = parentFragmentManager
            mFragmentManager?.commit{
                replace(R.id.nav_host_fragment_content_main, GalleryFragment())
                addToBackStack(null)

            }
        }
        loadingMulai()
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = getActivity()
        homeViewModel = activity?.let { obtainViewModel(it, binding.root) }!!
        cekRumah(homeViewModel)
        cekAktif(binding)
        quotes(binding)
        cekUser(homeViewModel)
    }

    private fun cekUser (homeViewModel: HomeViewModel) {
        homeViewModel.user.observe(viewLifecycleOwner) {
            binding.name.text = it.name
        }
    }

    private fun loadingMulai() {
        binding.progress.visibility = View.VISIBLE
    }
    private fun loadingSelesai() {
        binding.progress.visibility = View.GONE
    }

    private fun cekAktif(binding: FragmentHomeBinding) {
        if (terkunci) {
            binding.pintasanKunci.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_on) }
        } else {
            binding.pintasanKunci.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_off) }
        }
        if (lampu) {
            binding.pintasanLampu.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_on) }
        } else {
            binding.pintasanLampu.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_off) }
        }
        if (jemur) {
            binding.pintasanJemuran.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_on) }
        } else {
            binding.pintasanJemuran.background = context?.let { ContextCompat.getDrawable(it, R.drawable.shadow_pintasan_off) }
        }
    }

    private fun cekRumah(homeViewModel: HomeViewModel) {
        homeViewModel.terkunci.observe(viewLifecycleOwner) {
            terkunci = it
        }
        homeViewModel.kunci.observe(viewLifecycleOwner) {
            kunci = it
        }
        homeViewModel.hujan.observe(viewLifecycleOwner) {
            hujan = it
        }
        homeViewModel.jemur.observe(viewLifecycleOwner) {
            jemur = it
        }
        homeViewModel.gasAman.observe(viewLifecycleOwner) {
            gasAman = it
        }
        homeViewModel.sekring.observe(viewLifecycleOwner) {
            sekring = it
        }
        homeViewModel.lampu.observe(viewLifecycleOwner) {
            lampu = it
        }
    }

    private fun quotes(binding : FragmentHomeBinding) {
        NetworkConfig().getService()
            .getUsers()
            .enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Log.d("TAG", "onResponse: $t")
                }
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    binding.quote.text = response.body()?.content
                    loadingSelesai()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun obtainViewModel(activity: FragmentActivity, view: View) : HomeViewModel {
        val factory = ViewModelFactory.getInstance(view)
        return ViewModelProvider(activity, factory).get(HomeViewModel::class.java)
    }
}