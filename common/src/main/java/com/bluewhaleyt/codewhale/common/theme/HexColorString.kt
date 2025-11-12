package com.bluewhaleyt.codewhale.common.theme

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class HexColorString(val value: String) {

    val color: Color
        get() = Color(value.toColorInt())

    companion object {

        val Unspecified: HexColorString
            get() = HexColorString("#00000000")
    }
}