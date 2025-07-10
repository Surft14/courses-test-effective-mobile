package com.example.appсourses.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appcourses.R
import com.example.appcourses.databinding.ActivityEntryBinding
import com.example.appcourses.ui.activity.MainScreenActivity

class EntryActivity : AppCompatActivity() {
    lateinit var binding : ActivityEntryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.bEntry.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPass.text.toString()

            if (isValidEmail(email) && password.isNotBlank()) {
                // Открываем MainScreenActivity
                val intent = Intent(this, MainScreenActivity::class.java)
                startActivity(intent)
                finish() // чтобы нельзя было вернуться назад кнопкой закрываем этот экран
            } else {
                Toast.makeText(this, "Проверьте корректность данных", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ibVK.setOnClickListener {
            openLink("https://vk.com/")
        }

        binding.ibOK.setOnClickListener {
            openLink("https://ok.ru/")
        }

    }

    private fun isValidEmail(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return regex.matches(email)
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        // Проверка, есть ли браузер
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Браузер не найден", Toast.LENGTH_SHORT).show()
        }
    }
}