package com.helios.composeinstagram.common.view

class ErrorEvent(private val content: String) {

    private var hasBeenHandled: Boolean = false

    fun getContentOrNull(): String? {
        return if (hasBeenHandled || content.isBlank()) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}