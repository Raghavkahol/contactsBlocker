package com.example.contactsblocker

import android.content.Intent

sealed class ViewModelLifecycleState{
    object FinishedWithResult : ViewModelLifecycleState()
    data class StartWithRequest(val request: Int) : ViewModelLifecycleState()
}

