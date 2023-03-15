package com.atech.feature_home.presentation.fragment

import androidx.fragment.app.viewModels
import com.atech.base.BaseDialogFragment
import com.atech.base.viewmodel.BaseViewModel
import com.atech.feature_home.databinding.DialogClassroomDetailBinding
import com.atech.feature_home.presentation.adapter.PeopleAdapter
import com.atech.feature_home.presentation.adapter.ScheduleAdapter
import com.atech.feature_home.presentation.viewmodel.ClassroomDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
@AndroidEntryPoint
class DialogClassroomDetailFragment :
    BaseDialogFragment<DialogClassroomDetailBinding, BaseViewModel>() {

    override val viewModel: ClassroomDetailViewModel by viewModels()

    override val binding: DialogClassroomDetailBinding by lazy {
        DialogClassroomDetailBinding.inflate(layoutInflater)
    }

    override fun onInitViews() {
        binding.rvPeople.adapter = PeopleAdapter {

        }.apply {
            updateData(listOf(1))
        }

        binding.rvSchedule.adapter = ScheduleAdapter {

        }.apply {
            updateData(listOf(1, 2, 3, 4))
        }
    }

    override fun onInitObserver() {
    }
}