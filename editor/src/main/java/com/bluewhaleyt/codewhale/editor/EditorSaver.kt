package com.bluewhaleyt.codewhale.editor

import android.content.Context
import androidx.compose.runtime.saveable.Saver
import com.bluewhaleyt.codewhale.common.theme.ThemeDefinition
import java.io.Serializable

@PublishedApi
@Suppress("FunctionName")
internal fun EditorSaver(
    context: Context,
    themeDefinition: ThemeDefinition
) = Saver<Editor, SaveData>(
    save = { editor ->
        SaveData(
            textScale = editor.textSizePx,
            textSelected = editor.cursor.isSelected,
            imeVisible = editor.isSoftKeyboardEnabled,
            cursorLeft = editor.cursor.leftLine to editor.cursor.leftColumn,
            cursorRight = editor.cursor.rightLine to editor.cursor.rightColumn,
            scrollOffset = editor.scroller.startX to editor.scroller.startY
        )
    },
    restore = { saved ->
        Editor(context, themeDefinition).apply {
            post {
                saved.textScale?.let { textSizePx = it }
                if (saved.textSelected) {
                    setSelectionRegion(
                        saved.cursorLeft.first, saved.cursorLeft.second,
                        saved.cursorRight.first, saved.cursorRight.second
                    )
                } else {
                    setSelection(saved.cursorLeft.first, saved.cursorLeft.second)
                }
                scroller.startScroll(saved.scrollOffset.first, saved.scrollOffset.second, 0, 0)

                requestFocus()
                if (saved.imeVisible) {
                    showSoftInput()
                } else {
                    hideSoftInput()
                }
            }
        }
    }
)

@PublishedApi
internal data class SaveData(
    val textScale: Float? = null,
    val textSelected: Boolean = false,
    val imeVisible: Boolean = false,
    val cursorLeft: Pair<Int, Int> = 0 to 0,
    val cursorRight: Pair<Int, Int> = 0 to 0,
    val scrollOffset: Pair<Int, Int> = 0 to 0
) : Serializable