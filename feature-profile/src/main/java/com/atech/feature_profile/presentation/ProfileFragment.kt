package com.atech.feature_profile.presentation

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.atech.base.BaseFragment
import com.atech.base.util.GlideHelper
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
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()
        viewModel.profileResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResultState.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    GlideHelper.showThumbnail(
                        it.data.avatar,
                        binding.imgProfile,
                        requireContext()
                    )
                    binding.txtName.text = it.data.name
                    binding.txtEmail.text = it.data.email
                    binding.txtAddress.text = it.data.address
                }
                is ResultState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.throwable.message,
                        Toast.LENGTH_SHORT)
                        .show()
                }
                is ResultState.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                else -> {
                    //unhandled state
                }
            }
        }

        viewModel.logoutResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResultState.Success -> {
                    showLoading(false)
                    findNavController().navigate(ProfileFragmentDirections.actionNavProfileToAuth())
                }
                is ResultState.Error -> {
                    showLoading(false)
                    Toast.makeText(
                        requireContext(),
                        it.throwable.message,
                        Toast.LENGTH_SHORT)
                        .show()
                }
                is ResultState.Loading -> {
                    showLoading(true)
                }
                else -> {
                    //unhandled state
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.pbLogout.visibility = View.VISIBLE
            binding.btnLogout.visibility = View.INVISIBLE
        } else {
            binding.pbLogout.visibility = View.GONE
            binding.btnLogout.visibility = View.VISIBLE
        }
    }

}