package com.atech.feature_home.presentation.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.atech.base.BaseFragment
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.entities.ClassScheduleModelItem
import com.atech.domain.entities.StudentsModelItem
import com.atech.domain.subscriber.ResultState
import com.atech.feature_home.databinding.FragmentClassroomDetailBinding
import com.atech.feature_home.presentation.adapter.PeopleAdapter
import com.atech.feature_home.presentation.adapter.ScheduleAdapter
import com.atech.feature_home.presentation.viewmodel.ClassroomDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
@AndroidEntryPoint
class ClassroomDetailFragment : BaseFragment<FragmentClassroomDetailBinding, BaseViewModel>() {

    override val viewModel: ClassroomDetailViewModel by viewModels()

    override val binding: FragmentClassroomDetailBinding by lazy {
        FragmentClassroomDetailBinding.inflate(layoutInflater)
    }

    private val args: ClassroomDetailFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onInitViews() {
        initOnClick()
        initRecyclerAdapter()
        args.schedule?.let {
            initClassView(it)
        }
    }

    private fun initOnClick() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAction.setOnClickListener {
            findNavController().navigate(
                ClassroomDetailFragmentDirections
                    .actionClassroomDetailFragmentToQrGeneratorFragment()
            )
        }
    }

    private fun initRecyclerAdapter() {
        binding.rvPeople.adapter = PeopleAdapter {

        }

        binding.rvSchedule.adapter = ScheduleAdapter(viewModel.isStudent) {
            findNavController().navigate(
                ClassroomDetailFragmentDirections
                    .actionClassroomDetailFragmentToQrScannerFragment(
                        it.id
                    )
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initClassView(classScheduleModelItem: ClassScheduleModelItem) {
        viewModel.getStudents(classScheduleModelItem.id)
        var detailText = "${classScheduleModelItem.location} ${classScheduleModelItem.time_summary}"
        if (viewModel.isStudent) {
            detailText += "\nRemaining Class: ${classScheduleModelItem.remaining_class}"
            binding.btnAction.visibility = View.VISIBLE
            binding.btnAction.text = "Show QR Code"
            binding.txtTitleMentors.text = "Mentors:"

            val teachers = mutableListOf<StudentsModelItem>()
            teachers.add(
                StudentsModelItem(
                    classScheduleModelItem.id,
                    classScheduleModelItem.teacher
                )
            )
            (binding.rvPeople.adapter as PeopleAdapter)
                .updateData(teachers)
        } else {
            binding.btnAction.visibility = View.GONE
            binding.txtTitleMentors.text = "Students:"
        }
        binding.txtClassroomTitle.text = classScheduleModelItem.category
        binding.txtDescription.text = detailText
        (binding.rvSchedule.adapter as ScheduleAdapter)
            .updateData(classScheduleModelItem.schedules)
    }

    @SuppressLint("SetTextI18n")
    override fun onInitObservers() {
        super.onInitObservers()
        if (viewModel.isStudent) {
            setFragmentResultListener(REQUEST_KEY) { _, _ ->
                viewModel.getStudentClass()
            }
        }

        viewModel.classResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResultState.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    args.schedule?.let { classScheduleItem ->
                        it.data.findLast { data -> data.id == classScheduleItem.id }?.let { item ->
                            initClassView(item)
                        }
                    }
                }
                is ResultState.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is ResultState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.throwable.message,
                        Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    // unhandled state
                }
            }
        }

        viewModel.studentsResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResultState.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.txtTotalStudent.visibility = View.VISIBLE
                    binding.txtTotalStudent.text = "${it.data.size} students"

                    if (!viewModel.isStudent) {
                        binding.txtTitleMentors.visibility = if (it.data.isEmpty()) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                        (binding.rvPeople.adapter as PeopleAdapter)
                            .updateData(it.data)
                    }
                }
                is ResultState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.txtTotalStudent.visibility = View.GONE
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
    }

    companion object {
        const val REQUEST_KEY = "REQUEST_KEY"
    }
}