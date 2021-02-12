package com.estealexis.todoestealexis.auth

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
import com.estealexis.todoestealexis.databinding.SignupFragmentBinding
import com.estealexis.todoestealexis.userinfo.UserInfoViewModel

class SignupFragment : Fragment() {
    private lateinit var binding: SignupFragmentBinding
    private val userViewModel: UserInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = SignupFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener{
           if(binding.signupFirstname.text.toString() != "" && binding.signupLastname.text.toString() != "" &&
                   binding.signupEmail.text.toString() != "" && binding.signupPassword.text.toString() != ""
               && binding.signupConfPassword.text.toString() != "")
           {
               val user = SignUpForm()
               user.firstname = binding.signupFirstname.text.toString()
               user.lastname = binding.signupLastname.text.toString()
               user.email = binding.signupEmail.text.toString()
               user.password = binding.signupPassword.text.toString()
               user.password_confirmation = binding.signupConfPassword.text.toString()
               userViewModel.signUp(user)
               userViewModel.sign_up_user.observe(viewLifecycleOwner, {
                   if(it.token != "")
                   {
                       PreferenceManager.getDefaultSharedPreferences(context).edit().putString(SHARED_PREF_TOKEN_KEY, it.token).commit()
                       findNavController().navigate(R.id.loginFragment)
                   }
                   else
                   {
                       Toast.makeText(context, "Informations Incorrect", Toast.LENGTH_LONG).show()
                   }
           })
        }
    }
}
}