package com.example.myapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.data.User
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val vm: MainVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewObservers()

        btn_save.setOnClickListener {
            val userData = et_name.text.toString().split(" ")
            if (userData.size == 2) {
                vm.saveUser(User(userData[0], userData[1]))
            }
        }
    }

    private fun setupViewObservers() {
        vm.user.observe(this, {
            it?.let {
                val userText = it.name + " " + it.surname
                tv_name.text = userText
            }
        })
    }
}