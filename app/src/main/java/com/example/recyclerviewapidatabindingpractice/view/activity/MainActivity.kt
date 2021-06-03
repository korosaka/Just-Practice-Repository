package com.example.recyclerviewapidatabindingpractice.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewapidatabindingpractice.R
import com.example.recyclerviewapidatabindingpractice.view.fragment.CharactersFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, CharactersFragment())
                .commit()
        }
    }
}