package com.atech.feature_auth.presentation.fragment

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.atech.base.BaseFragment
import com.atech.domain.subscriber.ResultState
import com.atech.feature_auth.databinding.FragmentLoginBinding
import com.atech.feature_auth.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onInitViews() {
        super.onInitViews()
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password)
            }
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResultState.Success -> {
                    findNavController().navigate(LoginFragmentDirections.actionNavAuthToHome())
                }
                is ResultState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.throwable.message,
                        Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    //unhandled state
                }
            }
        }
    }

}