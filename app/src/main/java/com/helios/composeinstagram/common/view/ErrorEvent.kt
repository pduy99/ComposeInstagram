package com.helios.composeinstagram.common.view

class ErrorEvent(private val content: String) {

    var hasBeenHandled: Boolean = false
        private set

    fun getContentOrNull(): String? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}