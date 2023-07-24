package com.example.myapplication.main.screens.general_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentGeneralBinding
import com.example.myapplication.main.screens.general_screen.general_use_case.GeneralViewModel


class GeneralFragment : Fragment() {

    private val viewModel: GeneralViewModel by viewModels()

    private val adapter = PhotosAdapter()

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

        binding.fgRvListPhotos.layoutManager = LinearLayoutManager(requireContext())
        binding.fgRvListPhotos.adapter = adapter
        viewModel.photoFlow
            .observe(viewLifecycleOwner) { photos ->
                adapter.addPhoto(photos)
            }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}