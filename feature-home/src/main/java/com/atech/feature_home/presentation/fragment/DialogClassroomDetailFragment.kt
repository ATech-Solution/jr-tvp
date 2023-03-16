package com.atech.feature_home.presentation.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.atech.base.BaseDialogFragment
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.entities.StudentsModelItem
import com.atech.domain.subscriber.ResultState
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

    private val args: DialogClassroomDetailFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onInitViews() {
        initRecyclerAdapter()
        args.schedule?.let {
            viewModel.getStudents(it.id)
            if (viewModel.isStudent) {
                binding.btnAction.text = "Show QR Code"
                binding.txtTitleMentors.text = "Mentors:"

                val teachers = mutableListOf<StudentsModelItem>()
                teachers.add(
                    StudentsModelItem(
                        it.id,
                        it.teacher
                    )
                )
                (binding.rvPeople.adapter as PeopleAdapter)
                    .updateData(teachers)
            } else {
                binding.btnAction.text = "Take Attendance"
                binding.txtTitleMentors.text = "Students:"
            }
            binding.txtClassroomTitle.text = it.category
            binding.txtDescription.text = "${it.location} ${it.time_summary}"
            (binding.rvSchedule.adapter as ScheduleAdapter)
                .updateData(it.schedules)
        }

        binding.btnAction.setOnClickListener {
            if (viewModel.isStudent) {
                findNavController().navigate(
                    DialogClassroomDetailFragmentDirections
                        .actionDialogClassroomDetailFragmentToQrGeneratorFragment()
                )
            } else {
                findNavController().navigate(
                    DialogClassroomDetailFragmentDirections
                        .actionDialogClassroomDetailFragmentToQrScannerFragment()
                )
            }
        }
    }

    private fun initRecyclerAdapter() {
        binding.rvPeople.adapter = PeopleAdapter {

        }

        binding.rvSchedule.adapter = ScheduleAdapter {

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onInitObserver() {
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
}