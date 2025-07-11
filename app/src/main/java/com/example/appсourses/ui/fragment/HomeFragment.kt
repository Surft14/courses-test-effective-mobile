package com.example.appcourses.ui.fragment

import com.example.appcourses.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.core.widget.doAfterTextChanged

private val courseImages = listOf(
    R.drawable.course1,
    R.drawable.course2,
    R.drawable.course3
)

// Делегат для одного объекта в списке
private fun courseItemDelegate(onDetailsClick: (Course) -> Unit) =
    adapterDelegateViewBinding<Course, Course, ItemCourseBinding>({ inflater, parent ->
        ItemCourseBinding.inflate(inflater, parent, false)
    }) {
        binding.bFavorite.setOnClickListener {
            item.hasLike = !item.hasLike
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
    private val onDetailsClick: (Course) -> Unit,
) : ListDelegationAdapter<List<Course>>() {
    init {
        delegatesManager.addDelegate(courseItemDelegate(onDetailsClick))
    }
}


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val courseViewModel: CourseViewModel by viewModel()
    private val screenViewModel: MainScreenViewModel by viewModel()
    private val courseAdapter =
        CourseAdapter { course ->
            screenViewModel.itemSelected(MainScreenViewModel.Screen.COURSE)
            courseViewModel.selectCurse(course)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.listCoursesHome.adapter = courseAdapter
        courseViewModel.getCurses()

        binding.bFilter.setOnClickListener {
            courseViewModel.changeIsAsc()
        }

        //Если будет isAsc тру то отсартирует по воростанию (Выдаст сначала самые старые курсы, потом новые) если фадсе то наоборот
        if (courseViewModel.isAsc.value){
            courseViewModel.getCoursesFromDBOrderByASC()
        } else {
            courseViewModel.getCoursesFromDBOrderByDESC()
        }

        /*binding.editTSearchField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s?.toString().orEmpty()
                courseViewModel.getCoursesSearch(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })*/

        //Ищет совпадение в text и в title с текстом editTSearchField каждый раз когда обновляется текст
        binding.editTSearchField.doAfterTextChanged { editable ->
            val query = editable?.toString().orEmpty()
            courseViewModel.getCoursesSearch(query)
        }

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
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}