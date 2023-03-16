package com.atech.feature_home.presentation.fragment

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.atech.base.BaseFragment
import com.atech.base.util.GlideHelper
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.subscriber.ResultState
import com.atech.feature_home.databinding.FragmentHomeBinding
import com.atech.feature_home.presentation.adapter.ClassroomAdapter
import com.atech.feature_home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, BaseViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onInitViews() {
        super.onInitViews()
        initRecyclerViewAdapter()
    }

    private fun initRecyclerViewAdapter() {
        binding.rvClass.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ClassroomAdapter {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDialogClassroomDetailFragment(
                        it
                    )
                )
            }
        }

        binding.swRefreshClass.setOnRefreshListener {
            viewModel.checkLoginState()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onInitObservers() {
        super.onInitObservers()
        viewModel.profileResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResultState.Success -> {
                    binding.txtTitleName.text = "${it.data.name}"
                    GlideHelper.showThumbnail(
                        it.data.avatar,
                        binding.imgProfile,
                        requireContext()
                    )
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

        viewModel.classResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResultState.Success -> {
                    showLoading(false)
                    (binding.rvClass.adapter as ClassroomAdapter)
                        .updateData(it.data)
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
        binding.swRefreshClass.isRefreshing = show
    }

}