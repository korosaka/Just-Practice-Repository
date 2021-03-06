package com.example.recyclerviewapidatabindingpractice.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewapidatabindingpractice.R
import com.example.recyclerviewapidatabindingpractice.view.fragment.CharacterDetailFragment

class CharacterDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        if (savedInstanceState == null) {
            val characterId = intent.getStringExtra("character_id")
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, CharacterDetailFragment.newInstance(characterId))
                .commit()
        }
    }
}