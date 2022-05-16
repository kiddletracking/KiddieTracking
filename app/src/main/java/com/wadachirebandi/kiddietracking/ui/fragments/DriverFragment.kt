package com.wadachirebandi.kiddietracking.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wadachirebandi.kiddietracking.daos.DriverDao
import com.wadachirebandi.kiddietracking.databinding.FragmentDriverBinding
import com.wadachirebandi.kiddietracking.models.Driver

class DriverFragment : Fragment() {

    private var _binding: FragmentDriverBinding? = null

    private val binding get() = _binding!!

    private val driverDao = DriverDao()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverBinding.inflate(layoutInflater, container, false)

        driverDao.driverCollection.document("OtQfKg2Qxi6QAvzQIMLG")
            .get().addOnSuccessListener {
                updateUi((it.toObject(Driver::class.java)))
            }
        return binding.root
    }

    private fun updateUi(driverDetails: Driver?) {
        if (driverDetails != null) {
            binding.driverName.text = driverDetails.driver_name
            binding.driverNumber.text = driverDetails.driver_number
            binding.callDriverButton.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + driverDetails.driver_number)
                startActivity(dialIntent)
            }
        }
    }

}