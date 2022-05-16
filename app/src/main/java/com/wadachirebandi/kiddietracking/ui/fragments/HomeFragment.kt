package com.wadachirebandi.kiddietracking.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.wadachirebandi.kiddietracking.R
import com.wadachirebandi.kiddietracking.databinding.FragmentHomeBinding
import com.wadachirebandi.kiddietracking.ui.TrackBusActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.userDetails.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_userDetailsFragment)
        }

        binding.driverDetails.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_driverFragment)
        }

        binding.trackBusCV.setOnClickListener {
            startActivity(Intent(requireContext(), TrackBusActivity::class.java))
        }

        return binding.root
    }
}