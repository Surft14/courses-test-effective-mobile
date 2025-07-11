package com.example.appcourses.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.appcourses.R

class MainScreenViewModel : ViewModel() {

    enum class Screen{
        HOME,
        FAVORITE,
        ACCOUNT,
        COURSE,
    }

    private val _screen = MutableStateFlow(Screen.HOME)
    val screen : StateFlow<Screen> = _screen

    fun onNavigationItemSelected(id: Int){
        _screen.value = when(id){
            R.id.nav_home -> Screen.HOME
            R.id.nav_favorites -> Screen.FAVORITE
            R.id.nav_account -> Screen.ACCOUNT
            else -> Screen.HOME
        }
    }

    fun itemSelected(screen: Screen){
        _screen.value = screen
    }

}