package com.example.convidados.view.listener

import android.view.GestureDetector.OnDoubleTapListener

interface OnGuestListener {
    fun onClick(id: Int)
    fun onDelete(id: Int)
    fun OnDoubleTapListener(id: Int)
}