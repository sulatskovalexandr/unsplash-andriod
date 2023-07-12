package com.example.myapplication.main.screens.general_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentGeneralBinding


class GeneralFragment : Fragment() {

    private lateinit var adapter: PhotosAdapter

    private var fragmentGeneralBinding: FragmentGeneralBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGeneralBinding.inflate(inflater, container, false)
        fragmentGeneralBinding = binding

        adapter = PhotosAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        adapter.addPhoto(
            listOf(
                Photos(
                    "Alex",
                    "USA",
                    R.drawable.plant5,
                    R.drawable.plant5
                ),
                Photos(
                    "Pavel",
                    "Russia",
                    R.drawable.plant5,
                    R.drawable.plant5
                ),
            )
        )
        return binding.root
    }


}