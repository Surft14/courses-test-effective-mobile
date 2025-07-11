package com.example.appсourses.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appcourses.R
import com.example.appcourses.databinding.FragmentCourseBinding
import com.example.appcourses.databinding.FragmentFavoritesBinding
import com.example.appсourses.viewmodel.CourseViewModel
import com.example.appсourses.viewmodel.MainScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseFragment : Fragment() {


    private var _binding: FragmentCourseBinding? = null
    private val binding get() = _binding!!
    private val courseViewModel: CourseViewModel by viewModel()
    private val screenViewModel: MainScreenViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val course = courseViewModel.selectedCourse.value

        binding.bFavorite.setOnClickListener {
           courseViewModel.changeHasLike()
        }

        binding.bBack.setOnClickListener {
            screenViewModel.itemSelected(MainScreenViewModel.Screen.HOME)
        }

        binding.tvTextF.text = course?.text
        binding.tvTitleCourseF.text = course?.title

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}