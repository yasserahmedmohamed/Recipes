package com.yasser.recipes.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.core.view.isVisible


fun View.HideWithAnimation() {

    this.animate()
        .translationY(this.getHeight().toFloat())
        .alpha(0.0f)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                this@HideWithAnimation.visibility = View.GONE
            }
        })
}



fun View.showWithAnimation() {
// Prepare the View for the animation



    if (!this.isShown) {
        this.setVisibility(View.VISIBLE);
        this.setAlpha(0.0f);
    }
    this.animate()
        .translationY(0.0f)
        .alpha(1.0f)
        .setListener(null)

}