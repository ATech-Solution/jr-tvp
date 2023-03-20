package com.atech.feature_home.presentation.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.atech.base.BaseActivity
import com.atech.base.BaseFragment
import com.atech.base.viewmodel.BaseViewModel
import com.atech.feature_home.R
import com.atech.feature_home.databinding.FragmentHomeBinding
import com.atech.feature_home.presentation.view.HeaderView
import com.atech.feature_home.presentation.view.SectionView
import com.atech.feature_home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, BaseViewModel>(),
    SwipeRefreshLayout.OnRefreshListener,
    SectionView.OnDoneLoading, HeaderView.OnDoneLoading {

    override val viewModel: HomeViewModel by viewModels()

    override val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    // private val args: HomeFragmentArgs by navArgs()

    override fun onInitViews() {
        super.onInitViews()
        (activity as BaseActivity<*, *>).setSupportActionBar(binding.toolbar)

        val supportActionBar = (activity as BaseActivity<*, *>).supportActionBar
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onInitObservers() {
        super.onInitObservers()
        initListener()
        fetchData()
        Log.d("MainFragment", "onInitObserver")
    }

    private fun initListener() {
        binding.refresh.setOnRefreshListener(this)
        binding.headerView.homeViewModel = viewModel
        binding.favoriteView.homeViewModel = viewModel
        binding.topRatedView.homeViewModel = viewModel
        binding.popularView.homeViewModel = viewModel

        binding.headerView.doneLoadingListener = this
        binding.favoriteView.doneLoadingListener = this
        binding.topRatedView.doneLoadingListener = this
        binding.popularView.doneLoadingListener = this
    }

    private fun fetchData() = with(binding) {
        headerView.fetchData(viewLifecycleOwner)
        favoriteView.fetchData(viewLifecycleOwner)
        topRatedView.fetchData(viewLifecycleOwner)
        popularView.fetchData(viewLifecycleOwner)
    }

    override fun onRefresh() {
        fetchData()
    }

    private fun hideLoading() {
        binding.refresh.isRefreshing = false
    }

    override fun doneLoading() {
        if (isAdded) hideLoading()
    }
}