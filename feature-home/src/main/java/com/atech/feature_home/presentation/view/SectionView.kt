package com.atech.feature_home.presentation.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.atech.base.config.Constants
import com.atech.domain.entities.MovieModel
import com.atech.domain.subscriber.ResultState
import com.atech.feature_home.R
import com.atech.feature_home.databinding.ViewSectionBinding
import com.atech.feature_home.presentation.adapter.MovieAdapter
import com.atech.feature_home.presentation.fragment.DetailFragment
import com.atech.feature_home.presentation.viewmodel.HomeViewModel


/**
 * Created by Abraham Lay on 12/06/20.
 */
class SectionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr),
    MovieAdapter.OnClickListener {
    private var type: Int = 0
    private val adapter: MovieAdapter by lazy { MovieAdapter() }

    var doneLoadingListener: OnDoneLoading? = null
    private var binding: ViewSectionBinding

    var homeViewModel: HomeViewModel? = null
    lateinit var lifecycle: LifecycleOwner

    init {
        adapter.onClickListener = this
        val array: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SectionView
        )

        val type = array.getInt(R.styleable.SectionView_svModule, Constants.POPULAR)
        val label = array.getString(R.styleable.SectionView_svLabel)
        array.recycle()
        binding = ViewSectionBinding.inflate(LayoutInflater.from(context), this, true)
        binding.tvLabelSection.text = label
        this.type = type
        Log.d(TAG, "Type: $type")
        binding.errorView.btnRetry.setOnClickListener {
            fetchData(lifecycle)
        }

        setAdapter()
    }

    fun fetchData(lifecycleOwner: LifecycleOwner) {
        lifecycle = lifecycleOwner
        showLoading()
        hideError()
        binding.rvMovieList.visibility = View.VISIBLE

        when (type) {
            Constants.POPULAR -> {
                Log.d(TAG, "fetched Popular")
                homeViewModel?.fetchPopularMovie()
                homeViewModel?.popularMovies?.observe(lifecycle, Observer {
                    setView(it)
                })
            }
            Constants.TOP_RATED -> {
                Log.d(TAG, "fetched Top Rated")
                homeViewModel?.fetchTopRatedMovie()
                homeViewModel?.topRatedMovies?.observe(lifecycle, Observer {
                    setView(it)
                })
            }
            Constants.NOW_PLAYING -> {
                Log.d(TAG, "fetched Now Playing")
                homeViewModel?.fetchNowPlayingMovie()
                homeViewModel?.nowPlayingMovies?.observe(lifecycle, Observer {
                    setView(it)
                })
            }
            Constants.FAVORITE -> {
                Log.d(TAG, "fetched Favorite")
                homeViewModel?.fetchFavoriteMovies()
                homeViewModel?.favoriteMovies?.observe(lifecycle, Observer {
                    setView(it)
                })
            }
        }
    }

    private fun setView(resultState: ResultState<List<MovieModel>?>?) {
        when (resultState) {
            is ResultState.Success -> {
                hideLoading()
                setData(resultState.data)
            }
            is ResultState.Error -> {
                hideLoading()
                showError(resultState.throwable)
            }
            is ResultState.Loading -> {
                setData(resultState.data)
            }
        }
    }


    private fun setData(data: List<MovieModel>?) {
        if (data.isNullOrEmpty()) {
            visibility = GONE
            return
        }
        adapter.data = data
        adapter.notifyDataSetChanged()
    }


    private fun setAdapter() = with(binding) {
        rvMovieList.adapter = adapter
        val helper: SnapHelper = PagerSnapHelper()
        val linearLayoutManager = LinearLayoutManager(context)
        helper.attachToRecyclerView(rvMovieList)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvMovieList.layoutManager = linearLayoutManager
    }

    private fun showError(throwable: Throwable) = with(binding) {
        rvMovieList.visibility = View.GONE
        errorView.root.visibility = View.VISIBLE
        binding.errorView.tvErrorMessage.text = throwable.localizedMessage
    }

    private fun hideError() = with(binding) {
        errorView.root.visibility = View.GONE
    }

    private fun showLoading() = with(binding) {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() = with(binding) {
        doneLoadingListener?.doneLoading()
        progressBar.visibility = View.GONE
    }

    override fun onItemClicked(data: Any) {
        Toast.makeText(context, (data as MovieModel).title, Toast.LENGTH_SHORT).show()
        val bundle = bundleOf(Pair(DetailFragment.PARAM_DETAIL_MOVIE, data))
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    interface OnDoneLoading {
        fun doneLoading()
    }

    companion object {
        private const val TAG = "SectionView"
    }
}