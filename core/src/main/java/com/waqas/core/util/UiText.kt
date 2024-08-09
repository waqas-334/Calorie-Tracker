package com.waqas.core.util

import android.content.Context

sealed class UiText {
    data class DynamicString(val string: String) : UiText()
    data class StringResource(val resourceId: Int) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> string
            is StringResource -> context.getString(resourceId)
        }
    }
}