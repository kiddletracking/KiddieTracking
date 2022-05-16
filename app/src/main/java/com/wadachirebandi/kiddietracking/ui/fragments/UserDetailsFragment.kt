package com.wadachirebandi.kiddietracking.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.wadachirebandi.kiddietracking.daos.UserDao
import com.wadachirebandi.kiddietracking.databinding.FragmentUserDetailsBinding
import com.wadachirebandi.kiddietracking.models.User

class UserDetailsFragment : Fragment() {

    private var _binding: FragmentUserDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(layoutInflater, container, false)

        UserDao().getUser().addOnSuccessListener {
            it.toObject(User::class.java)?.let { it1 -> setUser(it1) }
        }

        return binding.root
    }

    private fun setUser(userDetails: User) {
        binding.apply {
            Glide.with(requireActivity()).load(userDetails.image).circleCrop().into(userImage)
            childName.text = userDetails.name
            childClass.text = userDetails.classStd
        }

    }

}