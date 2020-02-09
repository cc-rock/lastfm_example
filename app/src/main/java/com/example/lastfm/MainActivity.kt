package com.example.lastfm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lastfm.search.ui.ArtistSearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ArtistSearchFragment.newInstance())
                .commitNow()
        }
    }

}
