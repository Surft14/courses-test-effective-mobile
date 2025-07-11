package com.example.appcourses.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.appcourses.R
import com.example.appcourses.databinding.ActivityMainScreenBinding
import com.example.appcourses.ui.fragment.AccountFragment
import com.example.appcourses.ui.fragment.CourseFragment
import com.example.appcourses.ui.fragment.FavoritesFragment
import com.example.appcourses.ui.fragment.HomeFragment
import com.example.appcourses.viewmodel.CourseViewModel
import com.example.appcourses.viewmodel.MainScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainScreenBinding
    private val mainViewModel: MainScreenViewModel by viewModel()
    private val courseViewModel: CourseViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        courseViewModel.getCurses()
        enableEdgeToEdge()
        setContentView(binding.root)

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        lifecycleScope.launchWhenStarted {
            mainViewModel.screen.collect { screen ->
                val fragment = when (screen) {
                    MainScreenViewModel.Screen.HOME -> HomeFragment()
                    MainScreenViewModel.Screen.FAVORITE -> FavoritesFragment()
                    MainScreenViewModel.Screen.ACCOUNT -> AccountFragment()
                    MainScreenViewModel.Screen.COURSE -> CourseFragment()
                }
                loadFragment(fragment)
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            mainViewModel.onNavigationItemSelected(it.itemId)
            true
        }

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}