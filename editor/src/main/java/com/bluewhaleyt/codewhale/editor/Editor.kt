package com.bluewhaleyt.codewhale.editor

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.text.font.FontFamily
import com.bluewhaleyt.codewhale.common.theme.ThemeDefinition

@SuppressLint("ViewConstructor")
class Editor(
    context: Context,
    themeDefinition: ThemeDefinition
) : BaseEditor(context, themeDefinition) {

    init {
        setPinLineNumber(true)
        setCursorWidth(4f)
        isStickyTextSelection = true
        placeholder = "Enter text..."
        fontFamily = FontFamily.Monospace
    }
}