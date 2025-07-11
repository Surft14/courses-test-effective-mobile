package com.example.appcourses.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcourses.R
import com.example.appcourses.databinding.FragmentHomeBinding
import com.example.appcourses.databinding.ItemCourseBinding
import com.example.appcourses.viewmodel.MainScreenViewModel
import com.example.appcourses.viewmodel.CourseViewModel
import com.example.data.dto.model.Course
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

private val courseImages = listOf(
    R.drawable.course1,
    R.drawable.course2,
    R.drawable.course3
)

// Делегат для одного объекта в списке
private fun courseItemDelegate(onDetailsClick: (Course) -> Unit, onLikeClick: (Course) -> Unit) =
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
private class CourseAdapter(
    onDetailsClick: (Course) -> Unit,
    onLikeClick: (Course) -> Unit,
) : ListDelegationAdapter<List<Course>>() {
    init {
        delegatesManager.addDelegate(courseItemDelegate(onDetailsClick, onLikeClick))
    }
}


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val courseViewModel: CourseViewModel by activityViewModel()
    private val screenViewModel: MainScreenViewModel by activityViewModel()
    private val courseAdapter =
        CourseAdapter(
            onLikeClick = { course -> courseViewModel.changeHasLike(course) },
            onDetailsClick = { course ->
                Log.i("LogFragment", "Click details")
                screenViewModel.itemSelected(MainScreenViewModel.Screen.COURSE)
                courseViewModel.selectCurse(course)
            }
        )

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listCoursesHome.layoutManager = LinearLayoutManager(context)
        binding.listCoursesHome.adapter = courseAdapter

        binding.bFilter.setOnClickListener {
            courseViewModel.changeIsAsc()
            //Если будет isAsc тру то отсартирует по воростанию (Выдаст сначала самые старые курсы, потом новые) если фадсе то наоборот
            if (courseViewModel.isAsc.value) {
                courseViewModel.getCoursesFromDBOrderByASC()
            } else {
                courseViewModel.getCoursesFromDBOrderByDESC()
            }
        }




        //Ищет совпадение в text и в title с текстом editTSearchField каждый раз когда обновляется текст
        binding.editTSearchField.doAfterTextChanged { editable ->
            val query = editable?.toString().orEmpty()
            courseViewModel.getCoursesSearch(query)
        }

        //Отображает и сообщаем все изменения в списке
        viewLifecycleOwner.lifecycleScope.launch {
            courseViewModel.listCourses.collect { list ->
                courseAdapter.items = list
                Log.d("DEBUG", "List size: ${list.size}")
                courseAdapter.notifyDataSetChanged()
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}