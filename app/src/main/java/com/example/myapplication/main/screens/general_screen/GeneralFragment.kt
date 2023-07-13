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

    private var _binding: FragmentGeneralBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneralBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PhotosAdapter()

        binding.fgRvListPhotos.layoutManager = LinearLayoutManager(requireContext())
        binding.fgRvListPhotos.adapter = adapter

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
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}