package ru.netology.nmedia.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R

class AppActivity : AppCompatActivity(R.layout.activity_app) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<StatsView>(R.id.stats).setDataWithUnfilled(listOf(
            100F,
            200F,
            300F,
            400F,
        ), 200F)
    }
}