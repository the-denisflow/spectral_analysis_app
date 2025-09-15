package com.example.kotlin_app.util.core.permissions

import androidx.fragment.app.Fragment

class AudioPermissionRequesterImpl(private val fragment: Fragment) : AudioPermissionRequester{
    override fun requestAudioPermission() {
        fragment.requestPermissions(arrayOf(android.Manifest.permission.RECORD_AUDIO), REQUEST_CODE)
    }
    companion object {
        const val REQUEST_CODE = 1001
    }
}