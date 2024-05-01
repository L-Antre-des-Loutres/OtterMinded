package com.example.otterminded.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.otterminded.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    // Initialisation de _binding à null
    private var _binding: FragmentGalleryBinding? = null

    // Initialisation de binding à partir de _binding
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    // Suppression de _binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}