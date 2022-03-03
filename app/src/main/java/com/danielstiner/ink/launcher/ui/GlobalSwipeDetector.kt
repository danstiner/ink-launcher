package com.danielstiner.ink.launcher.ui

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.NavController
import com.danielstiner.ink.launcher.R

class GlobalSwipeDetector(context: Context, navController: NavController) :
    View.OnTouchListener {

    private val detector = GestureDetectorCompat(context, NavSwipeListener(navController))

    fun onTouchEvent(event: MotionEvent) = detector.onTouchEvent(event)

    override fun onTouch(view: View, event: MotionEvent) = onTouchEvent(event)

    class NavSwipeListener(private val navController: NavController) : SwipeGestureListener() {

        override fun onSwipe(direction: Direction): Boolean {
            Log.d(TAG, "Swiper no swiping $direction")
            return when (direction) {
                Direction.UP -> {
                    navController.navigate(R.id.categories)
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        private const val TAG = "GlobalNavSwipeListener"
    }
}
