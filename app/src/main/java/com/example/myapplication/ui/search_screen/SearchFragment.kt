package com.example.myapplication.ui.search_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.common.showKeyboard
import com.example.myapplication.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fsEditText.requestFocus()
        binding.fsEditText.showKeyboard()

        binding.fsToolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.fsEditText.addTextChangedListener {
            if (it != null) {
                binding.fsDeleteButton.isVisible = it.toString().isNotEmpty()

            }
        }
        binding.fsDeleteButton.setOnClickListener {
            binding.fsEditText.text = null
        }

        binding.fsViewPager.adapter = SearchPagerAdapter(fm = childFragmentManager)
        binding.fsTabLayout.getTabAt(0)?.text = getString(R.string.pf_text_photo)
        binding.fsTabLayout.getTabAt(1)?.text = getString(R.string.pf_text_collection)
        binding.fsTabLayout.getTabAt(2)?.text = getString(R.string.fs_text_users)
    }
}