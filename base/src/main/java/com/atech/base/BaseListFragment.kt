package com.atech.base

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.atech.base.viewmodel.BaseViewModel


/**
 * Created by Abraham Lay on 2020-06-09.
 */
abstract class BaseListFragment<DATA, VB : ViewBinding, VM : BaseViewModel> : BaseFragment<VB, VM>(),
    SwipeRefreshLayout.OnRefreshListener {

    protected var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    protected var mItemList: List<DATA>? = null
}