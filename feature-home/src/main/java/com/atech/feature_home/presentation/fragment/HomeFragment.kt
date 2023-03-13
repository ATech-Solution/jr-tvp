package com.atech.feature_home.presentation.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.atech.base.BaseFragment
import com.atech.base.viewmodel.BaseViewModel
import com.atech.feature_home.databinding.FragmentHomeBinding
import com.atech.feature_home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, BaseViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    override val viewModel: HomeViewModel by viewModels()

    override val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    override fun onInitViews() {
        super.onInitViews()
//        (activity as BaseActivity<*, *>).setSupportActionBar(binding.toolbar)
//
//        val supportActionBar = (activity as BaseActivity<*, *>).supportActionBar
//        supportActionBar?.title = getString(R.string.app_name)
//        binding.txtTitle.setOnClickListener {
////            findNavController().navigate(
////                HomeFragmentDirections
////                    .actionHomeFragmentToQrScannerFragment()
////            )
//            findNavController().navigate(
//                HomeFragmentDirections
//                    .actionHomeFragmentToQrGeneratorFragment()
//            )
//        }
    }

    override fun onInitObservers() {
        super.onInitObservers()
    }

    override fun onRefresh() {

    }

}