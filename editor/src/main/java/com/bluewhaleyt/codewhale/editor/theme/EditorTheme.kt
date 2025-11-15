package com.bluewhaleyt.codewhale.editor.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.bluewhaleyt.codewhale.common.theme.ThemeDefinition
import com.bluewhaleyt.codewhale.common.theme.colors.EditorThemeColors
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry

class EditorTheme internal constructor(
    private val definition: ThemeDefinition?
) : TextMateColorScheme(themeRegistry, themeRegistry.currentThemeModel) {

    val colors: EditorThemeColors?
        get() = definition?.colors?.editor

    init {
        definition?.let {
            themeRegistry.setTheme(it.name)
        }
    }

    override fun applyDefault() {
        super.applyDefault()
        definition?.let {
            with(it.colors.editor) {
                setColorForTypes(
                    background.color,
                    WHOLE_BACKGROUND, LINE_NUMBER_BACKGROUND
                )
                setColorForTypes(
                    foreground.color,
                    TEXT_NORMAL
                )
                setColorForTypes(
                    Color.Transparent,
                    LINE_DIVIDER
                )
            }
        }
    }

    private fun setColorForTypes(color: Color, vararg types: Int) {
        types.forEach {
            setColor(it, color.toArgb())
        }
    }
}

private val themeRegistry = ThemeRegistry.getInstance()