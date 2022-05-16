package com.wadachirebandi.kiddietracking.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.wadachirebandi.kiddietracking.R
import com.wadachirebandi.kiddietracking.daos.UserDao
import com.wadachirebandi.kiddietracking.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    private val auth = Firebase.auth

    private val userDao = UserDao()

    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        binding.btnLogin.setOnClickListener {
            signInWithEmail()
        }

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            token = it
        }

        return binding.root
    }

    private fun signInWithEmail() {
        if (binding.email.text.toString() != "" && binding.password.text.toString() != "") {
            binding.loadingView.visibility = View.VISIBLE
            binding.btnLogin.visibility = View.GONE
            auth.signInWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            ).addOnCompleteListener(requireActivity()){task ->
                if (task.isSuccessful) {
                    binding.loadingView.visibility = View.GONE
                    binding.btnLogin.visibility = View.VISIBLE
                    updateToken()
                } else {
                    binding.loadingView.visibility = View.GONE
                    binding.btnLogin.visibility = View.VISIBLE
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }else{
            Toast.makeText(
                requireContext(), "Please enter the details before LOGIN",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateToken() {
        if (auth.currentUser != null){
            userDao.userCollection.document(auth.currentUser!!.uid).update("token", token)
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

}