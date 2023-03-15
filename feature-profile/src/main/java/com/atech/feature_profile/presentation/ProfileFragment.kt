package com.atech.feature_profile.presentation

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.atech.base.BaseFragment
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.subscriber.ResultState
import com.atech.feature_profile.databinding.FragmentProfileBinding
import com.atech.feature_profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, BaseViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()
    override val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    private val args: ProfileFragmentArgs by navArgs()

    override fun onInitViews() {
        super.onInitViews()
//        findNavController().restoreState(args.toBundle())
//        binding.tvTitlePage.text = args.title
//        binding.root.setOnClickListener {
////            throw RuntimeException("Test Crash")
//        }
//        findNavController().saveState()?.putAll(args.toBundle())
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()
        viewModel.logoutResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResultState.Success -> {
                    findNavController().navigate(ProfileFragmentDirections.actionNavProfileToAuth())
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