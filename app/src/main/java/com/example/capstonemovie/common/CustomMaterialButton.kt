package com.example.capstonemovie.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.example.capstonemovie.R
import com.google.android.material.button.MaterialButton

class CustomMaterialButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {

    private var stateRemoveFromList = false
    private var stateUnauthenticated = false
    private val removeStateList: IntArray = intArrayOf(R.attr.state_remove_from_list)
    private val unauthenticatedState: IntArray = intArrayOf(R.attr.state_unauthenticated)

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        var state = super.onCreateDrawableState(2)
        if (stateRemoveFromList) {
            state = View.mergeDrawableStates(state, removeStateList)
        } else if (stateUnauthenticated) {
            state = View.mergeDrawableStates(state, unauthenticatedState)
        }
        return state
    }
}