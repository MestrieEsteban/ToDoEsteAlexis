package com.estealexis.todoestealexis.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.databinding.AuthenticationFragmentBinding

class AuthenticationFragment : Fragment() {
    private lateinit var binding: AuthenticationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = AuthenticationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener({
            findNavController().navigate(R.id.loginFragment)
        })
        binding.btnSignup.setOnClickListener({
            findNavController().navigate(R.id.signupFragment)
        })
    }
}