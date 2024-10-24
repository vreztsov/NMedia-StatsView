package ru.netology.nmedia.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R

class AppActivity : AppCompatActivity(R.layout.activity_app) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = findViewById<StatsView>(R.id.stats)
        view.data = listOf(
            500F,
            500F,
            500F,
            500F,
        )

        // Пример 1 android.view.animation
//        val label = findViewById<TextView>(R.id.label)
//        val viewAnim = AnimationUtils.loadAnimation(
//            this, R.anim.view_animation
//        ).apply {
//            setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationStart(animation: Animation?) {
//                    label.text = "started"
//                }
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    label.text = "ended"
//                }
//
//                override fun onAnimationRepeat(animation: Animation?) {
//                    label.text = "repeat"
//                }
//
//            })
//        }
//
//        view.startAnimation(viewAnim)
// Конец примера 1

// Пример 2 ObjectAnimator через рефлексию
//        ObjectAnimator.ofFloat(view, "alpha", 0.25F, 1F).apply {
//            startDelay = 500
//            duration = 300
//            interpolator = BounceInterpolator()
//        }.start()
// Конец примера 2

// Пример 3 ObjectAnimator через готовые property
//        ObjectAnimator.ofFloat(view, View.ALPHA, 0.25F, 1F).apply {
//            startDelay = 500
//            duration = 300
//            interpolator = BounceInterpolator()
//        }.start()
// Конец примера 3

// Пример 4 Анимация нескольких свойств через PropertyValuesHolder
//        val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, 0F, 360F)
//        ObjectAnimator.ofPropertyValuesHolder(view, rotation)
//            .apply {
//                startDelay = 500
//                duration = 500
//                interpolator = AccelerateDecelerateInterpolator()
//            }.start()
// Конец примера 4

// Пример 5 Использование ViewPropertyAnimator
//        view.animate()
//            .rotation(360F)
//            .scaleX(1.2F)
//            .scaleY(1.2F)
//            .setInterpolator(LinearInterpolator())
//            .setStartDelay(500)
//            .setDuration(500)
//            .start()
// Конец примера 5

// Пример 6 Комбинация нескольких анимаций через AnimatorSet
//        val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.25F, 1F).apply {
//            duration = 3000
//            interpolator = LinearInterpolator()
//        }
//        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0F, 1F)
//        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0F, 1F)
//        val scale = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY).apply {
//            duration = 3000
//            interpolator = BounceInterpolator()
//        }
//        AnimatorSet().apply {
//            startDelay = 500
//            playSequentially(scale, alpha)
//        }.start()
// Конец примера 6

    }
}