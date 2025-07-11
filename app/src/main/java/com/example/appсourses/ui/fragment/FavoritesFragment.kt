package com.example.appcourses.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcourses.R
import com.example.appcourses.databinding.FragmentFavoritesBinding
import com.example.appcourses.databinding.ItemCourseBinding
import com.example.appcourses.viewmodel.MainScreenViewModel
import com.example.appcourses.viewmodel.CourseViewModel
import com.example.data.dto.model.Course
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private val courseImages = listOf(
    R.drawable.course1,
    R.drawable.course2,
    R.drawable.course3
)

// Делегат для одного объекта в списке
private fun courseItemDelegateFavorite(
    onDetailsClick: (Course) -> Unit,
    onLikeClick: (Course) -> Unit,
) =
    adapterDelegateViewBinding<Course, Course, ItemCourseBinding>({ inflater, parent ->
        ItemCourseBinding.inflate(inflater, parent, false)
    }) {
        binding.bFavorite.setOnClickListener {
            onLikeClick(item)
        }
        binding.bDetails.setOnClickListener {
            onDetailsClick(item)
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
private class CourseAdapterFavorite(
    onDetailsClick: (Course) -> Unit,
    onLikeClick: (Course) -> Unit,
) : ListDelegationAdapter<List<Course>>() {
    init {
        delegatesManager.addDelegate(courseItemDelegateFavorite(onDetailsClick, onLikeClick))
    }
}


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val courseViewModel: CourseViewModel by activityViewModel()
    private val screenViewModel: MainScreenViewModel by activityViewModel()
    private val courseAdapter =
        CourseAdapterFavorite(
            onDetailsClick = { course ->
                screenViewModel.itemSelected(MainScreenViewModel.Screen.COURSE)
                courseViewModel.selectCurse(course)
            },
            onLikeClick = { course ->
                courseViewModel.changeHasLike(course)
            }
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listFavorites.layoutManager = LinearLayoutManager(context)
        binding.listFavorites.adapter = courseAdapter
        courseViewModel.getFavoriteCourses()
        //Отображает и сообщаем все изменения в списке
        viewLifecycleOwner.lifecycleScope.launch {
            courseViewModel.listCourses.collect { list ->
                courseAdapter.items = list
                courseAdapter.notifyDataSetChanged()
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}