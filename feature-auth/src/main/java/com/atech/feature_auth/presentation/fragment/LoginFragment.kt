package com.atech.feature_auth.presentation.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.atech.base.BaseFragment
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
            findNavController().navigate(LoginFragmentDirections.actionNavAuthToHome())
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()

    }

}