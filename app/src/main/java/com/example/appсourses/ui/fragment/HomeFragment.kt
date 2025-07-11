package com.example.appcourses.ui.fragment

import com.example.appcourses.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.appcourses.databinding.FragmentHomeBinding
import com.example.appcourses.databinding.ItemCourseBinding
import com.example.appсourses.viewmodel.CourseViewModel
import com.example.appсourses.viewmodel.MainScreenViewModel
import com.example.data.dto.model.Course
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private val courseImages = listOf(
    R.drawable.course1,
    R.drawable.course2,
    R.drawable.course3
)

// Делегат для одного объекта в списке
fun courseItemDelegate(onDetailsClick: () -> Unit) =
    adapterDelegateViewBinding<Course, Course, ItemCourseBinding>({ inflater, parent ->
        ItemCourseBinding.inflate(inflater, parent, false)
    }) {
        binding.bFavorite.setOnClickListener {
            item.hasLike = !item.hasLike
        }
        binding.bDetails.setOnClickListener {
            onDetailsClick()
        }
        bind {
            binding.tvTitle.text = item.title
            binding.tvText.text = item.text
            binding.tvPrice.text = "${item.price}Р"
            binding.tvRaiting.text = item.rate.toString()
            binding.ivCourse.setImageResource(courseImages.random())
            binding.bFavorite.setImageResource(
                if (item.hasLike) {
                    R.drawable.ic_bookmark_green
                } else {
                    R.drawable.ic_bookmark
                }
            )
        }
    }

//Адаптер с помощью AdapterDelegates
class CourseAdapter(
    private var onDetailsClick: () -> Unit,
) : ListDelegationAdapter<List<Course>>() {
    init {
        delegatesManager.addDelegate(courseItemDelegate(onDetailsClick))
    }
}


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: CourseViewModel by viewModel()
    private val screenViewModel: MainScreenViewModel by viewModel()
    private val courseAdapter =
        CourseAdapter { screenViewModel.itemSelected(MainScreenViewModel.Screen.COURSE) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel.getCurses()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listCourses.collect { list ->
                courseAdapter.items = list
                courseAdapter.notifyDataSetChanged()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}