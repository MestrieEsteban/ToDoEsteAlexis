package com.estealexis.todoestealexis.auth

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.databinding.LoginFragmentBinding
import com.estealexis.todoestealexis.userinfo.UserInfoViewModel


class LoginFragment : Fragment() {
    private lateinit var binding: LoginFragmentBinding
    private val userViewModel: UserInfoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            if (binding.loginEmail.text.toString() !== "" && binding.loginPassword.text.toString() != "") {
                val user = LoginForm()
                user.email = binding.loginEmail.text.toString()
                user.password = binding.loginPassword.text.toString()
                userViewModel.login(user)
                userViewModel.token.observe(viewLifecycleOwner, {
                    if (it == "null") {
                        Toast.makeText(context, "Password or Email Incorrect", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        val sharedPref =
                            PreferenceManager.getDefaultSharedPreferences(context)
                        var editor: SharedPreferences.Editor? = sharedPref.edit()
                        editor?.putString(SHARED_PREF_TOKEN_KEY, it)
                        editor?.commit()
                        findNavController().navigate(R.id.taskListFragment)
                    }
                })
            }
        }
    }
}